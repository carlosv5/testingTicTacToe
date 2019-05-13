package es.upm.eacs.pruebas;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebAppSeleniumSystemTest {

        protected RemoteWebDriver driver1, driver2;
        private String baseUrl;
        private String nickName1;
        private String nickName2;
        @Rule
        public BrowserWebDriverContainer chrome1 = new BrowserWebDriverContainer()
                         .withDesiredCapabilities(DesiredCapabilities.chrome())
                         .withRecordingMode(VncRecordingMode.RECORD_ALL, new File("./target/"));
        @Rule
        public BrowserWebDriverContainer chrome2 = new BrowserWebDriverContainer()
                         .withDesiredCapabilities(DesiredCapabilities.chrome());

        @BeforeClass
        public static void setupClass() {
                WebApp.start();
                Testcontainers.exposeHostPorts(8080);
        }

        @AfterClass
        public static void tearDownClass() {
                WebApp.stop();
        }

        @Before
        public void setupTest() {

                driver1 = chrome1.getWebDriver();
                driver2 = chrome2.getWebDriver();

                String rootUrl = "http://host.testcontainers.internal:8080";

                driver1.get(rootUrl);
                driver2.get(rootUrl);

                nickName1 = "PlayerX";
                nickName2 = "PlayerO";
                setUsers("PlayerX", "PlayerO");
        }

        @After
        public void teardown() {
                if (driver1 != null) {
                        driver1.quit();
                }
                if (driver2 != null) {
                        driver2.quit();
                }
        }

        private void setUsers(String nickname1, String nickName2) {
                driver1.findElement(By.id("nickname")).sendKeys(nickname1);
                driver1.findElement(By.id("startBtn")).click();
                driver2.findElement(By.id("nickname")).sendKeys(nickName2);
                driver2.findElement(By.id("startBtn")).click();
        }

        private void fillBoard(String[] cells) {
                for(int i = 0; i < cells.length; i++) {
                        if(i%2 == 0) {
                                driver1.findElement(By.id("cell-" + cells[i])).click();
                        } else {
                                driver2.findElement(By.id("cell-" + cells[i])).click();
                        }
                }
                try {
                        Thread.sleep(100);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
        }


        @Test
        public void GivenTwoPlayers_WhenFillingTheCells_ThenFirstPlayerWins() {

                // When
                String[] cells = {"4","0","2","6","3","7","8","1","5"};
                fillBoard(cells);

                // Then
                String body = driver1.switchTo().alert().getText();
                assertEquals(body, nickName1 + " wins! " + nickName2 + " looses.");
        }

        @Test
        public void GivenTwoPlayers_WhenFillingTheCells_ThenSecondPlayerWins() {

                // When
                String[] cells = {"0","4","6","3","7","5"};
                fillBoard(cells);

                // Then
                String body = driver1.switchTo().alert().getText();
                assertEquals(body, nickName2 + " wins! " + nickName1 + " looses.");
        }

        @Test
        public void GivenTwoPlayers_WhenFillingTheCells_ThenPlayersDraw() {

                // When
                String[] cells = {"4","0","2","6","3","5","7","1","8"};
                fillBoard(cells);

                // Then
                String body = driver1.switchTo().alert().getText();
                assertEquals(body, "Draw!");
        }

}

