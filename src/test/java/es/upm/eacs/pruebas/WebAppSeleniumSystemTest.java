package es.upm.eacs.pruebas;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebAppSeleniumSystemTest {

	protected WebDriver driver1, driver2;
	protected String baseUrl;

	@BeforeClass
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
		WebApp.start();
	}

	@AfterClass
	public static void tearDownClass() {
		WebApp.stop();
	}

	@Before
	public void setupTest() {
		driver1 = new ChromeDriver();
		driver2 = new ChromeDriver();
		baseUrl = "http://localhost:8080/";
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

	@Test
	public void GivenTwoPlayers_WhenFillingTheCells_ThenFirstOneWins() {
		// Given
		driver1.get(baseUrl);
		driver1.findElement(By.id("nickname")).sendKeys("PlayerX");
		driver1.findElement(By.id("startBtn")).click();
		driver2.get(baseUrl);
		driver2.findElement(By.id("nickname")).sendKeys("PlayerO");
		driver2.findElement(By.id("startBtn")).click();

		// When
		driver1.findElement(By.id("cell-4")).click();
		driver2.findElement(By.id("cell-0")).click();
		driver1.findElement(By.id("cell-2")).click();
		driver2.findElement(By.id("cell-6")).click();
		driver1.findElement(By.id("cell-3")).click();
		driver2.findElement(By.id("cell-7")).click();
		driver1.findElement(By.id("cell-5")).click();

		// Then
		String body = driver1.switchTo().alert().getText();
		assertEquals(body, "PlayerX wins! PlayerO looses.");
	}

	@Test
	public void GivenTwoPlayers_WhenFillingTheCells_ThenSecondOneWins() {
		// Given
		driver1.get(baseUrl);
		driver1.findElement(By.id("nickname")).sendKeys("PlayerX");
		driver1.findElement(By.id("startBtn")).click();
		driver2.get(baseUrl);
		driver2.findElement(By.id("nickname")).sendKeys("PlayerO");
		driver2.findElement(By.id("startBtn")).click();

		// When
		driver1.findElement(By.id("cell-0")).click();
		driver2.findElement(By.id("cell-4")).click();
		driver1.findElement(By.id("cell-6")).click();
		driver2.findElement(By.id("cell-3")).click();
		driver1.findElement(By.id("cell-7")).click();
		driver2.findElement(By.id("cell-5")).click();

		// Then
		String body = driver1.switchTo().alert().getText();
		assertEquals(body, "PlayerO wins! PlayerX looses.");
	}

	@Test
	public void GivenTwoPlayers_WhenFillingTheCells_ThenTheyDraw() {
		// Given
		driver1.get(baseUrl);
		driver1.findElement(By.id("nickname")).sendKeys("PlayerX");
		driver1.findElement(By.id("startBtn")).click();
		driver2.get(baseUrl);
		driver2.findElement(By.id("nickname")).sendKeys("PlayerO");
		driver2.findElement(By.id("startBtn")).click();

		// When
		driver1.findElement(By.id("cell-4")).click();
		driver2.findElement(By.id("cell-0")).click();
		driver1.findElement(By.id("cell-2")).click();
		driver2.findElement(By.id("cell-6")).click();
		driver1.findElement(By.id("cell-3")).click();
		driver2.findElement(By.id("cell-5")).click();
		driver1.findElement(By.id("cell-7")).click();
		driver2.findElement(By.id("cell-1")).click();
		driver1.findElement(By.id("cell-8")).click();

		// Then
		String body = driver1.switchTo().alert().getText();
		assertEquals(body, "Draw!");
	}

}
