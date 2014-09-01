package com.gammadevs.quoteviewer.controller;

import com.gammadevs.quoteviewer.model.Direction;
import com.gammadevs.quoteviewer.model.Quote;
import com.gammadevs.quoteviewer.model.Status;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Anton on 9/1/2014.
 */
@Controller
public class RestController {

    @RequestMapping(value = "/rest/quotes.do", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getQuotes() {
        List<Quote> quotes = new ArrayList<Quote>();
        quotes.add(new Quote(1L, 1, new Date(114, 1, 1, 0, 30, 0), new Date(114, 1, 1, 0, 30, 0)
                , Status.QUOTED, "EURUSD", Direction.BUY, new BigDecimal("1.3547"), new BigDecimal(1000000), 1547896L));
        quotes.add(new Quote(2L, 1, new Date(114, 1, 1, 0, 30, 0), new Date(114, 1, 1, 0, 30, 0)
                , Status.QUOTED, "EURUSD", Direction.BUY, new BigDecimal("1.3547"), new BigDecimal(10000000), 1547896L));
        quotes.add(new Quote(3L, 1, new Date(114, 1, 1, 1, 30, 0), new Date(114, 1, 1, 1, 30, 0)
                , Status.QUOTED, "EURUSD", Direction.BUY, new BigDecimal("1.3547"), new BigDecimal(2000000), 1547896L));
        quotes.add(new Quote(4L, 1, new Date(114, 1, 1, 2, 30, 0), new Date(114, 1, 1, 2, 30, 0)
                , Status.QUOTED, "AUDUSD", Direction.BUY, new BigDecimal("0.9546"), new BigDecimal(500000), 1547896L));
        quotes.add(new Quote(5L, 2, new Date(114, 1, 1, 3, 30, 0), new Date(114, 1, 1, 4, 30, 0)
                , Status.QUOTED, "AUDUSD", Direction.BUY, new BigDecimal("0.9256"), new BigDecimal(2000000), 1547896L));
        for (int i = 0; i < 10000; i++) {
            quotes.add(new Quote(5L + 1 + i, 2, new Date(114, 1, 1, 3, 30, 0), new Date(114, 1, 1, 4, 30, i)
                    , Status.QUOTED, "AUDUSD", Direction.BUY, new BigDecimal("0.9256"), new BigDecimal(2000000 + i), 1547896L));
        }
        return quotes;
    }


}
