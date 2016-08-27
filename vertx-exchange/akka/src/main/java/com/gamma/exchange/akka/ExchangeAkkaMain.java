package com.gamma.exchange.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.gamma.exchange.akka.actors.OrderFillActor;
import com.gamma.exchange.akka.actors.OrderMatchActor;
import vertx.exchange.core.model.Order;
import vertx.exchange.core.model.Side;
import vertx.exchange.core.services.InstrumentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ExchangeAkkaMain {

    public static void main(String[] args) throws InterruptedException {
        final InstrumentService instrService = new InstrumentService();
        List<String> instruments = instrService.getInstruments(1);

        final ActorSystem actorSystem = ActorSystem.create("order-system");
        final ActorRef fillActor = actorSystem.actorOf(Props.create(OrderFillActor.class));
        actorSystem.eventStream().subscribe(fillActor, Order.class);
        final Map<String, ActorRef> matchActors = new HashMap<>();
        for (String instrument : instruments) {
            final ActorRef matchActor = actorSystem.actorOf(Props.create(OrderMatchActor.class, instrument));
            matchActors.put(instrument, matchActor);
        }

        Thread.sleep(5_000);
        final Random rand = new Random();
        for (int i = 0; i < 10_000; i++) {
            final String instr = instruments.get(i % instruments.size());
            final Order order = new Order((long) i
                    , instr
                    , Side.values()[i % 2]
                    , (int) (100 * (0.01 + rand.nextDouble()))
                    , (int) (100 * (0.01 + rand.nextDouble())));
            matchActors.get(instr).tell(order, null);
            Thread.sleep(5_000);
        }
    }

}
