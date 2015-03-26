package com.gammadevs.logging.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Level;

/**
 * Project: java-logging
 * User: Anton_Dovzhenko - Gammadevs inc
 * Date: 1/21/14
 */
@Service
@Lazy(false)
public class StartupServiceImpl implements StartupService {

    private static final Logger log = LoggerFactory.getLogger(StartupServiceImpl.class);

    @PostConstruct
    @Override
    public void init() {
        log.debug("startup from LOGGER");
        log.error("startup from LOGGER");
        log.info("startup from LOGGER");
        log.trace("startup from LOGGER");
        log.warn("startup from LOGGER");
    }

    @PreDestroy
    @Override
    public void destroy() {
        log.info("shutdown from LOGGER");
    }

}
