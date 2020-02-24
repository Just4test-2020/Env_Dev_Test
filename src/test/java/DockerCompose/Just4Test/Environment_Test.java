package DockerCompose.Just4Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Environment_Test {
	// Ce port 4444 permet au programme de communiquer avec le hub
	// Et le hub à son tour communiquera avec les nodes car ils sont liés
	static String url_local = "http://localhost:4444/wd/hub";
	static String url_test = "http://www.google.com";
	static String path_gecko = "/root/Bureau/gecko/geckodriver";
	static By input_search = By.xpath("//*[@name='q']");
	static By link_seleniumhq = By.partialLinkText("SeleniumHQ");
	static By link_downloads = By.partialLinkText("Downloads");
	static int timeOut = 5;
	static WebDriver driver = null;
	static DesiredCapabilities dcap =DesiredCapabilities.firefox();
	
	@Test
	public void test() throws MalformedURLException, InterruptedException {
		 dcap = initParam();	
		
		navigateToSeleniumWebsite(driver);
		
		// Retour aux précedentes fenetres
		for (int i = 1; i <= 3; i++) {
			driver.navigate().forward();
			Thread.sleep(timeOut * 1000);
		}
		
		driver.quit();
	}
	

	/**
	 * Recherche par texte sur Google
	 * @param driver: selenium driver
	 */
	private void navigateToSeleniumWebsite(WebDriver driver) {
		driver.get(url_test);
		driver.findElement(input_search).sendKeys("Seleniumhq");
		driver.findElement(input_search).sendKeys(Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.elementToBeClickable(link_seleniumhq));
		driver.findElement(link_seleniumhq).click();
		wait.until(ExpectedConditions.elementToBeClickable(link_downloads));
		driver.findElement(link_downloads).click();
	}

	/**
	 * Initialisation des parametres de configuration et lancement de la navigation
	 * @return
	 * @throws MalformedURLException
	 */
	private DesiredCapabilities initParam() throws MalformedURLException {
		URL gamelan = new URL(url_local);		
		driver = new RemoteWebDriver(gamelan, dcap);
	
		DesiredCapabilities dcap = DesiredCapabilities.firefox();
		dcap.setPlatform(org.openqa.selenium.Platform.LINUX);
		dcap.setCapability("command-timeout", 600);
		dcap.setCapability("idle-timeout", 800);
		System.setProperty("webdriver.gecko.driver", path_gecko);
		return dcap;
	}	
}
