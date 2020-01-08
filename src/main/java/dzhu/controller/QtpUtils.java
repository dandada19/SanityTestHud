package dzhu.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class QtpUtils {
	public static void runQtpTest(WebDriverWait wait, String product, String stack, String suite, String test) throws Exception{

		Select selectProductGroup = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.name("product_name"))));
		selectProductGroup.selectByVisibleText(product);
		try {
			Thread.sleep(800);
		} catch (Exception e) {
		}

		Select selectStackName = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.name("stack_name"))));
		selectStackName.selectByVisibleText(stack);
		
		Select selectSuiteType = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.name("suite_type"))));
		selectSuiteType.selectByVisibleText(suite);
		
		Select selectTestName = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.name("test_name"))));
		selectTestName.selectByVisibleText(test);
		
		WebElement btnRun = wait.until(ExpectedConditions.elementToBeClickable(By.name("submit_name")));
		btnRun.click();
	}

}
