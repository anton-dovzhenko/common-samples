package com.gamma.exchange.akka.verticles;

import com.gamma.exchange.akka.ExchangeVertxApp;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import vertx.exchange.core.model.Order;
import vertx.exchange.core.services.OrderMatchService;

public class OrderMatchVert extends AbstractVerticle {

    private final OrderMatchService service;
    private final String instrument;

    public OrderMatchVert(String instrument) {
        this.instrument = instrument;
        service = new OrderMatchService(order->vertx.eventBus().send(ExchangeVertxApp.TOPIC_INSTR_FILL + instrument, order));
    }

    @Override
    public void start(Future<Void> startFuture) {
        vertx.eventBus().consumer(ExchangeVertxApp.TOPIC_INSTR_MATCH + instrument, new Handler<Message<Order>>() {
            @Override
            public void handle(Message<Order> message) {
                final Order order = message.body();
                service.receive(order);
            }
        });
    }

}
