package vertx.exchange.core;

import io.vertx.core.Vertx;
import vertx.exchange.core.model.Order;
import vertx.exchange.core.model.Side;
import vertx.exchange.core.service.CoreVertService;
import vertx.exchange.core.service.InstrumentService;

import java.util.List;
import java.util.Random;

public class VertExchangeApp {

    public static void main(String[] args) throws InterruptedException {
        final CoreVertService coreService = new CoreVertService();
        final InstrumentService instrService = new InstrumentService();
        final Vertx vertx = Vertx.vertx();
        vertx.eventBus().registerDefaultCodec(Order.class, new Order.MsgCodec());

        List<String> instruments = instrService.getInstruments(1);
        coreService.deployInstrMarchVerts(vertx, instruments);

        final Random rand = new Random();
        for (int i = 0; i < 10_000; i++) {
            final String instr = instruments.get(i % instruments.size());
            vertx.eventBus().send(CoreConstants.TOPIC_INSTR_MATCH + instr, new Order((long) i
                    , instr
                    , Side.values()[i % 2]
                    , (int) (100 * (0.01 + rand.nextDouble()))
                    , (int) (100 * (0.01 + rand.nextDouble()))));
            Thread.sleep(5_000);
        }
    }

}
