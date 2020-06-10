package Scenarios;

import Setup.SetupTests;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class TestValidationTV extends SetupTests {
        private final WebDriver driver;

        public TestValidationTV() {
                driver = SetupTests.driver;
        }

        @Test
        public void scenario1() {
                driver.get("http://www.google.com");
                driver.manage().window().maximize();
                // Temps d'attente pour rallonger le test et observer les stats du tableau de bord
                try {
                        Thread.sleep(30000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                String actualTitle1 = driver.getTitle();
                String expectedTitle1 = "Google";
                Assert.assertEquals("Le titre de la page n'est pas exacte", actualTitle1, expectedTitle1);
        }
}
