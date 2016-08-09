package vertx.exchange.core.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vertx.exchange.core.CoreConstants;
import vertx.exchange.core.model.Order;

public class OrderFillUpdVert extends AbstractVerticle {

    private final static Logger log = LoggerFactory.getLogger(OrderFillUpdVert.class.getSimpleName());
    private final String instrument;

    public OrderFillUpdVert(String instrument) {
        this.instrument = instrument;
    }

    @Override
    public void start(Future<Void> startFuture) {
        vertx.eventBus().consumer(CoreConstants.TOPIC_INSTR_FILL + instrument, new Handler<Message<Order>>() {
            @Override
            public void handle(Message<Order> message) {
                final Order order = message.body();
                log.info("FILLED order: id={}, instr={}, side={}, size={}, price={}, acgExecPrice={}, sizeLeft={}, status={}"
                        , order.id
                        , order.instrument
                        , order.side
                        , order.size
                        , order.price
                        , (int) order.getAvgExecPrice()
                        , order.getSizeLeft()
                        , order.getStatus());
            }
        });
    }
}
