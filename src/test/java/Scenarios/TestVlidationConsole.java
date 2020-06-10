package Scenarios;

import Setup.SetupTests;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TestVlidationConsole extends SetupTests {

        private final WebDriver driver;

        public TestVlidationConsole() {
                driver = SetupTests.driver;
        }


        @Test
        public void scenario2() {
                driver.manage().window().maximize();
                driver.get("http://www.ldlc.com");
                String actualTitle2 = driver.getTitle();
                String expectedTitle2 = "LDLC.com - High-Tech Expérience";
                // Temps d'attente pour rallonger le test et observer les stats du tableau de bord
                try {
                        Thread.sleep(30000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                Assert.assertEquals("Le titre de la page n'est pas exacte", expectedTitle2, actualTitle2);
                driver.findElement(By.xpath("//*[@id=\"search_search_text\"]")).sendKeys("tv");
                driver.findElement(By.className("submit")).click();
                Assert.assertTrue(driver.getPageSource().contains("Les résultats pour tv"));
        }


        @Test()
        public void scenario3() {
                driver.manage().window().maximize();
                driver.get("http://www.facebook.com");
                // Temps d'attente pour rallonger le test et observer les stats du tableau de bord
                try {
                        Thread.sleep(30000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                //L'assert fail ici a pour but d'avoir un test qui ne passe pas et pouvoir le visualiser sur le Dashboard GRAFANA
                Assert.fail();
        }


        @Test
        public void scenario4() {

                driver.get("http://www.ldlc.com");
                String actualTitle2 = driver.getTitle();
                String expectedTitle2 = "LDLC.com - High-Tech Expérience";
                // Temps d'attente pour rallonger le test et observer les stats du tableau de bord
                try {
                        Thread.sleep(30000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                Assert.assertEquals("Le titre de la page n'est pas exacte", expectedTitle2, actualTitle2);
                driver.findElement(By.xpath("//*[@id=\"search_search_text\"]")).sendKeys("son");
                driver.findElement(By.className("submit")).click();
                Assert.assertTrue(driver.getPageSource().contains("Les résultats pour son"));
        }


        @Ignore
        @Test
        public void scenario5() {
//                Ce test est ignoré pour avoir un test @Ignored dans les compteurs du Dashboard GRAFANA

        }


}
