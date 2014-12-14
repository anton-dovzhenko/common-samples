package com.gammadevs.h2db.research;

import com.gammadevs.h2db.research.dao.Dao;
import com.gammadevs.h2db.research.dao.DaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Anton on 12/14/2014.
 */
public class PnLByClientReportMain {

    private static final ExecutorService pool = Executors.newFixedThreadPool(1);

    public static void main(String ... args) throws InterruptedException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/context.xml");
        Dao dao = applicationContext.getBean(DaoImpl.class);

        String[] ccyPairs = {"USDEUR", "USDSEK", "USDNOK"};
        Random random = new Random();

        System.out.println("[DAO] getPnLByClientReport STARTED");
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            pool.submit(() -> {
                try {
                    dao.getPnLByClientReport(ccyPairs[Math.abs(random.nextInt()) % 3], random.nextDouble() <= 0.5, (random.nextDouble() - 0.5) * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            //dao.getPnLByClientReport(ccyPairs[Math.abs(random.nextInt()) % 3], random.nextDouble() <= 0.5, (random.nextDouble() - 0.5) * 1000);
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("[DAO] getPnLByClientReport FINISHED in " + (System.currentTimeMillis() - time) + " ms");
        ((ConfigurableApplicationContext) applicationContext).close();
    }
}
