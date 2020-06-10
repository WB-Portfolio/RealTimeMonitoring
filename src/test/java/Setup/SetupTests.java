package Setup;

import Listeners.CustomRunListener;
import Listeners.TestSuiteDetails;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.influxdb.dto.Point;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class SetupTests extends Thread {

        private static final Logger logger = Logger.getLogger(SetupTests.class);
        static TestSuiteDetails testSuiteDetails;
        static TestSuiteDetails testSuiteDetails2;
        static Map<String, TestSuiteDetails> myTestResultMap = CustomRunListener.getMap();
        public static WebDriver driver;
        public static List<String> allscenarios = new ArrayList<>();
        public static List<String> Ignoredscenarios = new ArrayList<>();
        public static Boolean encours = true;
        static SetupTests newthread = null;
        static List<Double> loadlist = new ArrayList<>();
        static int runtestscount;
        private static int totaltestnumber;


        public void run() {
                SystemInfo si = new SystemInfo();
                HardwareAbstractionLayer hal = si.getHardware();
                CentralProcessor cpu = hal.getProcessor();
                GlobalMemory memory = hal.getMemory();
                long[] prevTicks = cpu.getSystemCpuLoadTicks();
                while (encours) {
                        loadlist.add(cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100);
                        try {
                                Thread.sleep(2000);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                        Point point1 = Point.measurement("CPU-Load")
                                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                                .addField("Load1", cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100)
                                .build();
                        DBLink.send(point1);
                        Point point2 = Point.measurement("Memory")
                                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                                .addField("Available memory", memory.getAvailable())
                                .addField("Used memory", memory.getTotal() - memory.getAvailable())
                                .build();
                        DBLink.send(point2);

                }
        }


        @Rule
        public TestRule watcher = new TestWatcher() {
                protected void starting(Description description) {
                        allscenarios.add(description.getMethodName());

                }

        };

        @BeforeClass
        public static void setup() throws IOException, ClassNotFoundException {
                encours = true;
                BasicConfigurator.configure();
                Logger.getRootLogger().setLevel(Level.INFO);
                totaltestnumber = Counter.testCounter();
                if (newthread == null) {
                        newthread = new SetupTests();
                        newthread.start();
                }

                switch (System.getProperty("browser")) {
                        case ("firefox"): {
                                if (System.getProperty("os.name").toLowerCase().contains("wind")) {
                                        System.setProperty("webdriver.gecko.driver", Streams.readers().getProperty("FirefoxDriverPath"));
                                } else {
                                        WebDriverManager.firefoxdriver().setup();
                                }
                                driver = new FirefoxDriver();
                                break;
                        }
                        case ("IE"): {
                                System.setProperty("webdriver.ie.driver", Streams.readers().getProperty("IeDriverPath"));
                                driver = new InternetExplorerDriver();
                                break;
                        }
                        case ("chrome"): {
                                if (System.getProperty("os.name").toLowerCase().contains("wind")) {
                                        System.setProperty("webdriver.chrome.driver", Streams.readers().getProperty("ChromeDriverPath"));
                                } else {
                                        WebDriverManager.chromedriver().setup();
                                }
                                driver = new ChromeDriver();
                                break;
                        }
                        default:
                                logger.info("The browser is not correctly defined");
                }

        }


        @Before
        public void before() {
                runtestscount++;
                if (allscenarios.size() > 1) {
                        testSuiteDetails = myTestResultMap.get(allscenarios.get(allscenarios.size() - 2));
                        Point point = Point.measurement("testmethod")
                                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                                .tag("testclass", testSuiteDetails.getTestClassName())
                                .tag("name", testSuiteDetails.getTestCaseName())
                                .tag("result", testSuiteDetails.getTestStatus())
                                .addField("duration", testSuiteDetails.getElaspsedTime())
                                .build();
                        DBLink.send(point);
                }


                if (Ignoredscenarios.size() > 1) {
                        testSuiteDetails2 = myTestResultMap.get(Ignoredscenarios.get(Ignoredscenarios.size() - 2));
                        Point point = Point.measurement("testmethod")
                                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                                .tag("testclass", testSuiteDetails2.getTestClassName())
                                .tag("name", testSuiteDetails2.getTestCaseName())
                                .tag("result", testSuiteDetails2.getTestStatus())
                                .addField("duration", testSuiteDetails2.getElaspsedTime())
                                .build();
                        DBLink.send(point);
                }
        }

        @After
        public void after() {
                Point point3 = Point.measurement("Test suite")
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .addField("Tests to be run Number", totaltestnumber - runtestscount)
                        .addField("Finished tests Number", runtestscount)
                        .build();
                DBLink.send(point3);

        }

        @AfterClass
        public static void tearDown() {
                if (driver != null) {
                        driver.quit();
                }
                if (!allscenarios.isEmpty()) {
                        if (testSuiteDetails != myTestResultMap.get(allscenarios.get(allscenarios.size() - 1))) {
                                testSuiteDetails = myTestResultMap.get(allscenarios.get(allscenarios.size() - 1));
                                Point point = Point.measurement("testmethod")
                                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                                        .tag("testclass", testSuiteDetails.getTestClassName())
                                        .tag("name", testSuiteDetails.getTestCaseName())
                                        .tag("result", testSuiteDetails.getTestStatus())
                                        .addField("duration", testSuiteDetails.getElaspsedTime())
                                        .build();
                                DBLink.send(point);
                        }
                }
                if (!Ignoredscenarios.isEmpty()) {
                        if (testSuiteDetails2 != myTestResultMap.get(Ignoredscenarios.get(Ignoredscenarios.size() - 1))) {
                                testSuiteDetails2 = myTestResultMap.get(Ignoredscenarios.get(Ignoredscenarios.size() - 1));
                                Point point = Point.measurement("testmethod")
                                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                                        .tag("testclass", testSuiteDetails2.getTestClassName())
                                        .tag("name", testSuiteDetails2.getTestCaseName())
                                        .tag("result", testSuiteDetails2.getTestStatus())
                                        .addField("duration", testSuiteDetails2.getElaspsedTime())
                                        .build();
                                DBLink.send(point);
                        }
                }
                allscenarios.clear();
                Ignoredscenarios.clear();
                newthread = null;
                encours = false;
        }
}


