package com.gamma.exchange.akka.actors;

import akka.actor.UntypedActor;
import vertx.exchange.core.model.Order;
import vertx.exchange.core.services.OrderMatchService;

public class OrderMatchActor extends UntypedActor {

    private final OrderMatchService service;
    private final String instrument;

    public OrderMatchActor(String instrument) {
        this.instrument = instrument;
        service = new OrderMatchService(order->getContext().system().eventStream().publish(order));
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof Order) {
            service.receive((Order) o);
        }
    }

}
