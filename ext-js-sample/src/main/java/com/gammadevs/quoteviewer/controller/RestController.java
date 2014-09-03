package com.gammadevs.quoteviewer.controller;

import com.gammadevs.quoteviewer.model.Quote;
import com.gammadevs.quoteviewer.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Created by Anton on 9/1/2014.
 */
@Controller
public class RestController {

    @Autowired
    private QuoteService quoteService;

    @RequestMapping(value = "/rest/quotes.do", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getQuotes() {
        return quoteService.getQuotes();
    }


}
