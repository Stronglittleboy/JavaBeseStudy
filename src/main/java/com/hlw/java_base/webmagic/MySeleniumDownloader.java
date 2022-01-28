package com.hlw.java_base.webmagic;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0.0
 * @classname: MySeleniumDownloader
 * @author: LongWang·Hou
 * @description: TODO
 * @date: 2021/6/30 0030 22:32
 * @modified: LongWang·Hou
 */
public class MySeleniumDownloader implements Downloader, Closeable {

    private volatile WebDriverPool webDriverPool;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private int sleepTime = 0;

    private int poolSize = 1;

    private static final String DRIVER_PHANTOMJS = "phantomjs";

    /**
     * 新建
     *
     * @param chromeDriverPath chromeDriverPath
     */
    public MySeleniumDownloader(String chromeDriverPath) {
        System.getProperties().setProperty("webdriver.chrome.driver",
                chromeDriverPath);
    }

    /**
     * Constructor without any filed. Construct PhantomJS browser
     *
     * @author bob.li.0718@gmail.com
     */
    public MySeleniumDownloader() {
        // System.setProperty("phantomjs.binary.path",
        // "/Users/Bingo/Downloads/phantomjs-1.9.7-macosx/bin/phantomjs");
    }

    /**
     * set sleep time to wait until load success
     *
     * @param sleepTime sleepTime
     * @return this
     */
    public MySeleniumDownloader setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    @Override
    public Page download(Request request, Task task) {
        checkInit();
        WebDriver webDriver;
        try {
            webDriver = webDriverPool.get();
        } catch (InterruptedException e) {
            logger.warn("interrupted", e);
            return null;
        }
        //TODO
//        BrowserMobProxy proxy = new BrowserMobProxyServer();
//        proxy.start(0);
//        int port = proxy.getPort(); // get the JVM-assigned port
//        webDriver.manage()
        logger.info("downloading page " + request.getUrl());
        webDriver.get(request.getUrl());
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Selenium or HTTP client configuration goes here

        WebDriver.Options manage = webDriver.manage();
        Site site = task.getSite();
        if (site.getCookies() != null) {
            for (Map.Entry<String, String> cookieEntry : site.getCookies()
                    .entrySet()) {
                Cookie cookie = new Cookie(cookieEntry.getKey(),
                        cookieEntry.getValue());
                manage.addCookie(cookie);
            }
        }

        /*
         * TODO You can add mouse event or other processes
         *
         * @author: bob.li.0718@gmail.com
         */

        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");
        Page page = new Page();
        page.setRawText(content);
        page.setHtml(new Html(content, request.getUrl()));
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        webDriverPool.returnToPool(webDriver);
        return page;
    }

    private void checkInit() {
        if (webDriverPool == null) {
            synchronized (this) {
                webDriverPool = new WebDriverPool(poolSize);
            }
        }
    }

    @Override
    public void setThread(int thread) {
        this.poolSize = thread;
    }

    @Override
    public void close() throws IOException {
        webDriverPool.closeAll();
    }

    /**
     * @author code4crafter@gmail.com <br>
     *         Date: 13-7-26 <br>
     *         Time: 下午1:41 <br>
     */
    class WebDriverPool {
        private Logger logger = LoggerFactory.getLogger(getClass());

        private final static int DEFAULT_CAPACITY = 5;

        private final int capacity;

        private final static int STAT_RUNNING = 1;

        private final static int STAT_CLODED = 2;

        private AtomicInteger stat = new AtomicInteger(STAT_RUNNING);

        /*
         * new fields for configuring phantomJS
         */
        private WebDriver mDriver = null;
        private boolean mAutoQuitDriver = true;

        private static final String DEFAULT_CONFIG_FILE = "/data/webmagic/webmagic-selenium/config.ini";
        private static final String DRIVER_FIREFOX = "firefox";
        private static final String DRIVER_CHROME = "chrome";
        private static final String DRIVER_PHANTOMJS = "phantomjs";

        protected  Properties sConfig;
        protected  DesiredCapabilities sCaps;

        /**
         * Configure the GhostDriver, and initialize a WebDriver instance. This part
         * of code comes from GhostDriver.
         * https://github.com/detro/ghostdriver/tree/master/test/java/src/test/java/ghostdriver
         *
         * @author bob.li.0718@gmail.com
         * @throws IOException
         */
        public void configure() throws IOException {
            // Read config file
            sConfig = new Properties();
            String configFile = DEFAULT_CONFIG_FILE;
            if (System.getProperty("selenuim_config")!=null){
                configFile = System.getProperty("selenuim_config");
            }
            sConfig.load(new FileReader(configFile));

            // Prepare capabilities
            sCaps = new DesiredCapabilities();
            sCaps.setJavascriptEnabled(true);
            sCaps.setCapability("takesScreenshot", false);

            String driver = sConfig.getProperty("driver", DRIVER_PHANTOMJS);

            // Fetch PhantomJS-specific configuration parameters
            if (driver.equals(DRIVER_PHANTOMJS)) {
                // "phantomjs_exec_path"
                if (sConfig.getProperty("phantomjs_exec_path") != null) {
                    sCaps.setCapability(
                            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                            sConfig.getProperty("phantomjs_exec_path"));
                } else {
                    throw new IOException(
                            String.format(
                                    "Property '%s' not set!",
                                    PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY));
                }
                // "phantomjs_driver_path"
                if (sConfig.getProperty("phantomjs_driver_path") != null) {
                    System.out.println("Test will use an external GhostDriver");
                    sCaps.setCapability(
                            PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY,
                            sConfig.getProperty("phantomjs_driver_path"));
                } else {
                    System.out
                            .println("Test will use PhantomJS internal GhostDriver");
                }
            }

            // Disable "web-security", enable all possible "ssl-protocols" and
            // "ignore-ssl-errors" for PhantomJSDriver
            // sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new
            // String[] {
            // "--web-security=false",
            // "--ssl-protocol=any",
            // "--ignore-ssl-errors=true"
            // });

            ArrayList<String> cliArgsCap = new ArrayList<String>();
            cliArgsCap.add("--web-security=false");
            cliArgsCap.add("--ssl-protocol=any");
            cliArgsCap.add("--ignore-ssl-errors=true");
            sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
                    cliArgsCap);

            // Control LogLevel for GhostDriver, via CLI arguments
            sCaps.setCapability(
                    PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
                    new String[] { "--logLevel="
                            + (sConfig.getProperty("phantomjs_driver_loglevel") != null ? sConfig
                            .getProperty("phantomjs_driver_loglevel")
                            : "INFO") });

            // String driver = sConfig.getProperty("driver", DRIVER_PHANTOMJS);

            // Start appropriate Driver
            if (isUrl(driver)) {
                sCaps.setBrowserName("phantomjs");
                mDriver = new RemoteWebDriver(new URL(driver), sCaps);
            } else if (driver.equals(DRIVER_FIREFOX)) {
                mDriver = new FirefoxDriver(sCaps);
            } else if (driver.equals(DRIVER_CHROME)) {
                mDriver = new ChromeDriver(sCaps);
            } else if (driver.equals(DRIVER_PHANTOMJS)) {
                mDriver = new PhantomJSDriver(sCaps);
            }
        }

        /**
         * check whether input is a valid URL
         *
         * @author bob.li.0718@gmail.com
         * @param urlString urlString
         * @return true means yes, otherwise no.
         */
        private boolean isUrl(String urlString) {
            try {
                new URL(urlString);
                return true;
            } catch (MalformedURLException mue) {
                return false;
            }
        }

        /**
         * store webDrivers created
         */
        private List<WebDriver> webDriverList = Collections
                .synchronizedList(new ArrayList<WebDriver>());

        /**
         * store webDrivers available
         */
        private BlockingDeque<WebDriver> innerQueue = new LinkedBlockingDeque<WebDriver>();

        public WebDriverPool(int capacity) {
            this.capacity = capacity;
        }

        public WebDriverPool() {
            this(DEFAULT_CAPACITY);
        }

        /**
         *
         * @return
         * @throws InterruptedException
         */
        public WebDriver get() throws InterruptedException {
            checkRunning();
            WebDriver poll = innerQueue.poll();
            if (poll != null) {
                return poll;
            }
            if (webDriverList.size() < capacity) {
                synchronized (webDriverList) {
                    if (webDriverList.size() < capacity) {

                        // add new WebDriver instance into pool
                        try {
                            configure();
                            innerQueue.add(mDriver);
                            webDriverList.add(mDriver);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // ChromeDriver e = new ChromeDriver();
                        // WebDriver e = getWebDriver();
                        // innerQueue.add(e);
                        // webDriverList.add(e);
                    }
                }

            }
            return innerQueue.take();
        }

        public void returnToPool(WebDriver webDriver) {
            checkRunning();
            innerQueue.add(webDriver);
        }

        protected void checkRunning() {
            if (!stat.compareAndSet(STAT_RUNNING, STAT_RUNNING)) {
                throw new IllegalStateException("Already closed!");
            }
        }

        public void closeAll() {
            boolean b = stat.compareAndSet(STAT_RUNNING, STAT_CLODED);
            if (!b) {
                throw new IllegalStateException("Already closed!");
            }
            for (WebDriver webDriver : webDriverList) {
                logger.info("Quit webDriver" + webDriver);
                webDriver.quit();
                webDriver = null;
            }
        }

    }

}
