package com.gammadevs.logging.services;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
        log.info("startup from LOGGER");
        log.debug("startup from LOGGER");
        log.info("startup from LOGGER");
        log.error("startup from LOGGER");
        log.fatal("startup from LOGGER");
        log.trace("startup from LOGGER");
    }

    @PreDestroy
    @Override
    public void destroy() {
        log.info("shutdown from LOGGER");
    }

}
