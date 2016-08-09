package vertx.exchange.core.service;

import io.vertx.core.Vertx;
import vertx.exchange.core.verticles.OrderFillUpdVert;
import vertx.exchange.core.verticles.OrderMatchVert;

import java.util.List;

public class CoreVertService {

    public void deployInstrMarchVerts(Vertx vertx, List<String> instruments) {
        instruments.stream().forEach(instrument -> vertx.deployVerticle(new OrderMatchVert(instrument)));
        instruments.stream().forEach(instrument -> vertx.deployVerticle(new OrderFillUpdVert(instrument)));
    }

}
