package com.gammadevs.logging.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: java-logging
 * User: Anton_Dovzhenko - Gammadevs inc
 * Date: 1/21/14
 */
@Service
@Lazy(false)
public class StartupServiceImpl implements StartupService {

    private final static Logger log = Logger.getLogger(StartupServiceImpl.class.getName());

    @PostConstruct
    @Override
    public void init() {
        log.log(Level.ALL, "startup from LOGGER");
        log.log(Level.CONFIG, "startup from LOGGER");
        log.log(Level.FINE, "startup from LOGGER");
        log.log(Level.FINER, "startup from LOGGER");
        log.log(Level.FINEST, "startup from LOGGER");
        log.log(Level.INFO, "startup from LOGGER");
        log.log(Level.OFF, "startup from LOGGER");
        log.log(Level.SEVERE, "startup from LOGGER");
        log.log(Level.WARNING, "startup from LOGGER");
    }

    @PreDestroy
    @Override
    public void destroy() {
        log.info("shutdown from LOGGER");
    }

}
