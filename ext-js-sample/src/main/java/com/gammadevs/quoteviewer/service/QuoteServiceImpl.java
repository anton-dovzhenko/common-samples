package com.gammadevs.quoteviewer.service;

import com.gammadevs.quoteviewer.model.Direction;
import com.gammadevs.quoteviewer.model.Quote;
import com.gammadevs.quoteviewer.model.Status;
import com.sun.istack.internal.logging.Logger;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by Anton on 9/1/2014.
 */
@Service("quoteService")
public class QuoteServiceImpl implements QuoteService {

    private final static Logger logger = Logger.getLogger(QuoteServiceImpl.class);

    private final String[] ccyPairs = new String[] {"EURUSD", "USDJPY", "AUDUSD", "NOKUSD", "SEKUSD"};
    private final double[] rates = new double[] {1.3575, 104.28, 0.92, 0.16, 0.14};
    private final Random random = new Random();

    private Map<Long, Quote> quotes;
    private Long lastQuoteId = 0L;
    private Thread updateThread;


    @PostConstruct
    private void init() {
        quotes = new LinkedHashMap<Long, Quote>();
        quotes.put(++lastQuoteId, new Quote(lastQuoteId, 1, new Date(114, 1, 1, 0, 30, 0), new Date(114, 1, 1, 0, 30, 0)
                , Status.QUOTED, "EURUSD", Direction.BUY, new BigDecimal("1.3547"), new BigDecimal(1000000), 1547896L));
        quotes.put(++lastQuoteId, new Quote(lastQuoteId, 1, new Date(114, 1, 1, 0, 30, 0), new Date(114, 1, 1, 0, 30, 0)
                , Status.QUOTED, "EURUSD", Direction.BUY, new BigDecimal("1.3547"), new BigDecimal(10000000), 1547896L));
        quotes.put(++lastQuoteId, new Quote(lastQuoteId, 1, new Date(114, 1, 1, 1, 30, 0), new Date(114, 1, 1, 1, 30, 0)
                , Status.QUOTED, "EURUSD", Direction.BUY, new BigDecimal("1.3547"), new BigDecimal(2000000), 1547896L));
        quotes.put(++lastQuoteId, new Quote(lastQuoteId, 1, new Date(114, 1, 1, 2, 30, 0), new Date(114, 1, 1, 2, 30, 0)
                , Status.QUOTED, "AUDUSD", Direction.BUY, new BigDecimal("0.9546"), new BigDecimal(500000), 1547896L));
        quotes.put(++lastQuoteId, new Quote(lastQuoteId, 2, new Date(114, 1, 1, 3, 30, 0), new Date(114, 1, 1, 4, 30, 0)
                , Status.QUOTED, "AUDUSD", Direction.BUY, new BigDecimal("0.9256"), new BigDecimal(2000000), 1547896L));
        for (int i = 0; i < 100; i++) {
            quotes.put(++lastQuoteId, new Quote(lastQuoteId, 2, new Date(114, 1, 1, 3, 30, 0), new Date(114, 1, 1, 4, 30, i)
                    , Status.QUOTED, "AUDUSD", Direction.BUY, new BigDecimal("0.9256"), new BigDecimal(2000000 + i), 1547896L));
        }
        updateThread = new Thread(new QuoteUpdateRunnable());
        updateThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                logger.logException(e, Level.SEVERE);
            }
        });
        updateThread.start();
    }

    public List<Quote> getQuotes() {
        synchronized (quotes) {
            return new ArrayList<Quote>(quotes.values());
        }
    }

    private void addOrUpdateQuote(Quote quote) {
        synchronized (quotes) {
            quotes.put(quote.getId(), quote);
        }
    }

    private Quote generateNextQuote() {
        int index = random.nextInt(4);
        String ccyPair = ccyPairs[index];
        BigDecimal rate = new BigDecimal(rates[index] + random.nextDouble() / 50).setScale(5, BigDecimal.ROUND_DOWN);
        BigDecimal volume = new BigDecimal(random.nextInt(10_000_000));
        Direction direction = Direction.values()[random.nextInt(Direction.values().length - 1)];
        Date date = new Date();
        return new Quote(++lastQuoteId, 1, date, date
                , Status.QUOTED, ccyPair, direction, rate, volume, 1547896L);
    }

    private Quote updateNextQuote() {
        synchronized (quotes) {
            Long idToUpdate = lastQuoteId - 1 - random.nextInt(10);
            Quote quote = quotes.get(idToUpdate);
            Quote updatedQuote = new Quote(quote.getId()
                    , quote.getVersion() + 1
                    , quote.getCreationDate()
                    , new Date()
                    , quote.getStatus()
                    , quote.getCcyPair()
                    , quote.getDirection()
                    , quote.getPrice()
                    , quote.getAmount()
                    , quote.getRequesterId());
            quotes.put(idToUpdate, updatedQuote);
            return updatedQuote;
        }
    }

    private class QuoteUpdateRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    Quote quote = generateNextQuote();
                    addOrUpdateQuote(quote);
                    Quote updatedQuote = updateNextQuote();
                    ObjectMapper mapper = new ObjectMapper();
                    List<Quote> quotes = new ArrayList<Quote>();
                    quotes.add(quote);
                    quotes.add(updatedQuote);
                    String json = mapper.writeValueAsString(quotes);
                    BroadcasterFactory.getDefault().lookup("/atmosphere/quote").broadcast(json);
                } catch (InterruptedException|IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
