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
	private String baseUrl;
	private String nickName1;
	private String nickName2;

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
		driver1.get(baseUrl);
		driver2.get(baseUrl);
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
