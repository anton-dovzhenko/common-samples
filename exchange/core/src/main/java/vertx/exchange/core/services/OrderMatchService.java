package vertx.exchange.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vertx.exchange.core.model.Order;
import vertx.exchange.core.model.Side;
import vertx.exchange.core.model.Status;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.function.Consumer;

public class OrderMatchService {

    private final static Logger log = LoggerFactory.getLogger(OrderMatchService.class.getSimpleName());

    private final Consumer<Order> callback;
    private final TreeSet<Order> bids = new TreeSet<>(new Order.BidComparator());
    private final TreeSet<Order> asks = new TreeSet<>(new Order.AskComparator());

    public OrderMatchService(Consumer<Order> callback) {
        this.callback = callback;
    }

    public void receive(Order order) {
        log.info("TO_MATCH order: id={}, instr={}, side={}, size={}, price={}"
                , order.id
                , order.instrument
                , order.side
                , order.size
                , order.price);
        if (order.side == Side.BID) {
            matchOrders(order, asks);
            if (order.getStatus() != Status.FILLED) {
                bids.add(order);
            }
        } else {
            matchOrders(order, bids);
            if (order.getStatus() != Status.FILLED) {
                asks.add(order);
            }
        }
    }

    private void matchOrders(Order toMatchOrder, TreeSet<Order> orders) {
        for (Order order : orders) {
            if (toMatchOrder.side == Side.BID && order.price <= toMatchOrder.price
                    || toMatchOrder.side == Side.ASK && order.price >= toMatchOrder.price) {
                int matchedSize = Math.min(toMatchOrder.getSizeLeft(), order.getSizeLeft());
                double matchedPrice = 0.5 * (toMatchOrder.price + order.price);
                toMatchOrder.match(matchedSize, matchedPrice);
                order.match(matchedSize, matchedPrice);

                if (matchedSize > 0) {
                    callback.accept(order);
                }
                if (toMatchOrder.getStatus() == Status.FILLED) {
                    break;
                }
            } else {
                break;
            }
        }
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            final Order order = iterator.next();
            if (order.getStatus() == Status.FILLED) {
                iterator.remove();
            } else {
                break;
            }
        }
        if (toMatchOrder.size != toMatchOrder.getSizeLeft()) {
            callback.accept(toMatchOrder);
        }
    }

}
