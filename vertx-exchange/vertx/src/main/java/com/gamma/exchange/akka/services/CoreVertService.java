package com.gamma.exchange.akka.services;

import com.gamma.exchange.akka.verticles.OrderFillUpdVert;
import com.gamma.exchange.akka.verticles.OrderMatchVert;
import io.vertx.core.Vertx;

import java.util.List;

public class CoreVertService {

    public void deployInstrMarchVerts(Vertx vertx, List<String> instruments) {
        instruments.stream().forEach(instrument -> vertx.deployVerticle(new OrderMatchVert(instrument)));
        instruments.stream().forEach(instrument -> vertx.deployVerticle(new OrderFillUpdVert(instrument)));
    }

}
