package com.gamma.exchange.akka.actors;

import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vertx.exchange.core.model.Order;

public class OrderFillActor extends UntypedActor {

    private final static Logger log = LoggerFactory.getLogger(OrderFillActor.class.getSimpleName());

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof Order) {
            Order order = (Order) o;
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
    }

}
