package com.gammadevs.quoteviewer.atmosphere;

import java.io.IOException;
import java.util.logging.Logger;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResponse;

/**
 * Created by Anton on 9/1/2014.
 */
@AtmosphereHandlerService(path="/atmosphere/quote")
public class QuoteAtmosphereHandler implements AtmosphereHandler {

    private final static Logger logger = Logger.getLogger(QuoteAtmosphereHandler.class.getName());

    @Override
    public void onRequest(AtmosphereResource resource) throws IOException {

        AtmosphereRequest req = resource.getRequest();
        // First, tell Atmosphere
        // to allow bi-directional communication by suspending.
        if (req.getMethod().equalsIgnoreCase("GET")) {
            resource.suspend();
            logger.info("!!!!!!!!!!!! New subscription established !!!!!!!!!!!!");
        }
    }

    @Override
    public void onStateChange(AtmosphereResourceEvent event) throws IOException {
        AtmosphereResource resource = event.getResource();
        AtmosphereResponse response = resource.getResponse();
        if (event.isSuspended()) {
            response.setContentType("application/json");
            response.getWriter().write(event.getMessage().toString());
            response.getWriter().flush();
        }
    }

    @Override
    public void destroy() {

    }
}
