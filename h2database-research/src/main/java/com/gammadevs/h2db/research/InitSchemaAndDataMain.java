package com.gammadevs.h2db.research;

import com.gammadevs.h2db.research.dao.Dao;
import com.gammadevs.h2db.research.dao.DaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Anton on 12/14/2014.
 */
public class InitSchemaAndDataMain {

    public static void main(String ... args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/context.xml");
        Dao dao = applicationContext.getBean(DaoImpl.class);

        dao.initSchema();
        dao.initClients();
        System.out.println("[DAO] initTransactionsAndPnL STARTED");
        long time = System.currentTimeMillis();
        dao.initTransactionsAndPnL();
        System.out.println("[DAO] initTransactionsAndPnL FINISHED in " + (System.currentTimeMillis() - time) + " ms");
        ((ConfigurableApplicationContext) applicationContext).close();
    }
}
