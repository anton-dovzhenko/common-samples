package com.gamma.exchange.akka;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

public class ExchangeVertxWebServer {

    public static void main(String[] args) {
        int port = 8080;

        final Vertx vertx = Vertx.vertx();
        final HttpServer server = vertx.createHttpServer();
        server.requestHandler(request -> {
            HttpServerResponse response = request.response();
            response.putHeader("content-type", "text/plain");
            response.end("Hello World!");
        });

        server.listen(port);
    }

}
