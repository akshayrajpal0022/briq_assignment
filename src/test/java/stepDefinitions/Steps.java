package stepDefinitions;

import java.util.ArrayList;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import readAndWrite.ExcelWriter;

public class Steps {
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	ExcelWriter exwr;

	@Given("^User is on homepage$")
	public void user_is_on_homepage() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.bizjournals.com/milwaukee/feature/crane-watch");
		wait = new WebDriverWait(driver, 50000);
		js = (JavascriptExecutor) driver;
	}

	@When("^user goes to map view$")
	public void user_click_racism_card() throws Throwable {

		driver.findElement(By.xpath("//a[@data-ct=\"OPT: View Map Asset\"][1]")).click();

	}

	public void clickOnPin(WebElement ele, WebElement zoom) {
		try {
			js.executeScript("arguments[0].click();", zoom);
			js.executeScript("arguments[0].scrollIntoView(true);", ele);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
		} catch (Exception e) {
			clickOnPin(ele, zoom);
		}

	}

	@Then("^user should be able to get all the details of cards$")
	public void user_should_land_on_details_page() throws Throwable {

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cw-map")));

		Thread.sleep(15000);
		// wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[name()='image']"))));
		WebElement frame = driver.findElement(By.className("cw-map"));

		driver.switchTo().frame(frame);

		js.executeScript("window.scrollBy(0,1000)");

		List<WebElement> images = driver.findElements(By.xpath("//*[name()='image']"));

		System.out.println(images.size());

		WebElement zoom = driver.findElement(By.xpath("//div[@title=\"Zoom in\"]"));

		ArrayList<String> cardDetails = new ArrayList<String>();

		List<WebElement> card = driver.findElements(By.xpath("//table[@class=\"attrTable\"]/tbody/tr/td[2]"));

		for (WebElement element : images) {
			clickOnPin(element, zoom);
			for (int i = 0; i < card.size(); i++) {
				WebElement card1 = driver.findElement(By.xpath("//table[@class=\"attrTable\"]/tbody/tr/td[" + i + "]"));
				cardDetails.add(card1.getText());
			}

		}

		exwr.writeExcel("", "PinData.xlsx", "PinDetails", cardDetails);

		driver.quit();
	}

}
