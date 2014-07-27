package com.gammadevs.cucumber.console;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Project: cucumber.web
 * User: Anton_Dovzhenko - Gammadevs inc
 * Date: 2/9/14
 */
public class BrowserDriver {

    private static Logger log = Logger.getLogger(BrowserDriver.class.getName());
    private static WebDriver mDriver;


    public synchronized static WebDriver getCurrentDriver() {
        if (mDriver == null) {
            try {
                Properties properties = new Properties();
                InputStream input = new FileInputStream("src/test/resources/web.properties");
                properties.load(input);
                log.info("web.roperties loaded");
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
                ChromeOptions options = new ChromeOptions();
                options.setBinary(properties.getProperty("chrome.binary"));
                mDriver = new ChromeDriver(options);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            } finally {
                Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
            }
        }
        return mDriver;
    }

    private static class BrowserCleanup implements Runnable {
        public void run() {
            log.info("Closing the browser");
            close();
        }
    }

    public static void close() {
        try {
            getCurrentDriver().quit();
            mDriver = null;
            log.info("Closing the browser");
        } catch (UnreachableBrowserException e) {
            log.info("Cannot close browser: unreachable browser");
        }
    }

}
