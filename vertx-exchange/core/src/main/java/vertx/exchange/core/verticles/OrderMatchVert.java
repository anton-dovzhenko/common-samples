package vertx.exchange.core.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vertx.exchange.core.CoreConstants;
import vertx.exchange.core.model.Order;
import vertx.exchange.core.model.Side;
import vertx.exchange.core.model.Status;

import java.util.Iterator;
import java.util.TreeSet;

public class OrderMatchVert extends AbstractVerticle {

    private final static Logger log = LoggerFactory.getLogger(OrderMatchVert.class.getSimpleName());
    private final String instrument;
    private final TreeSet<Order> bids = new TreeSet<>(new Order.BidComparator());
    private final TreeSet<Order> asks = new TreeSet<>(new Order.AskComparator());

    public OrderMatchVert(String instrument) {
        this.instrument = instrument;
    }

    @Override
    public void start(Future<Void> startFuture) {
        vertx.eventBus().consumer(CoreConstants.TOPIC_INSTR_MATCH + instrument, new Handler<Message<Order>>() {
            @Override
            public void handle(Message<Order> message) {
                final Order order = message.body();
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
        });
    }

    private void matchOrders(Order toMatchOrder, TreeSet<Order> orders) {
        for (Order order : orders) {
            if (toMatchOrder.side == Side.BID && order.price <= toMatchOrder.price
                    || toMatchOrder.side == Side.ASK && order.price >= toMatchOrder.price) {
                int matchedSize = Math.min(toMatchOrder.getSizeLeft(), order.getSizeLeft());
                double matchedPrice = 0.5 * (toMatchOrder.price + order.price);
                toMatchOrder.match(matchedSize, matchedPrice);
                order.match(matchedSize, matchedPrice);
                sendOrderUpdate(order);
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

        sendOrderUpdate(toMatchOrder);
    }

    private void sendOrderUpdate(Order order) {
        vertx.eventBus().send(CoreConstants.TOPIC_INSTR_FILL + instrument, order);
    }
}
