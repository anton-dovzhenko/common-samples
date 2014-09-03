package com.gammadevs.quoteviewer.atmosphere;

import com.sun.istack.internal.logging.Logger;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.*;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;

import java.io.IOException;

/**
 * Created by Anton on 9/1/2014.
 */
@AtmosphereHandlerService(
        path="/atmosphere/quote"
)
public class QuoteAtmosphereHandler implements AtmosphereHandler {

    private final static Logger logger = Logger.getLogger(QuoteAtmosphereHandler.class);

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
