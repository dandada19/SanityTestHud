package dzhu.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javafx.stage.Stage;

public class ControllerUtils {
	private static Stage mainStage;
	ControllerUtils(Stage s){
		mainStage = s;
	}
	public static void init(Stage s) {
		mainStage = s;
	}
	public static void hideStages(Stage... stages) {
		for(Stage s : stages) {
			s.hide();
		}
		mainStage.hide();
	}
	public static void showStages(Stage... stages) {
		for(Stage s : stages) {
			s.setHeight(650);
			s.show();
		}
		mainStage.show();
	}
	public static void doEnroll(String enrollPageAddress, String id, String pin) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		WebDriver chromeDriver = new ChromeDriver(options);
		chromeDriver.get(enrollPageAddress);
		WebElement cbAgree = chromeDriver.findElement(By.name("agreed"));
		cbAgree.click();
		WebElement inputId = chromeDriver.findElement(By.name("login"));
		inputId.sendKeys(id);
        WebElement inputPin = chromeDriver.findElement(By.name("pin"));
        inputPin.sendKeys(pin);
        WebElement submitLogin = chromeDriver.findElement(By.xpath("//button[text()='Enroll']"));
        submitLogin.click();	
	}
}
