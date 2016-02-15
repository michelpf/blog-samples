package ie;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Main {

	public static void main(String[] args) {
		
		File file = new File("drivers/IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		WebDriver driver = new InternetExplorerDriver();
		
		// Load sample calc test URL. 
		//http://only-testing-blog.blogspot.com.br/2014/04/calc.html
		driver.get("http://michelpf.com/exemplo-de-calculadora-em-javascript/"); 
		// Execute sample calc test. 
		driver.findElement(By.xpath("//input[@id='1']")).click(); 
		driver.findElement(By.xpath("//input[@id='plus']")).click(); 
		driver.findElement(By.xpath("//input[@id='6']")).click(); 
		driver.findElement(By.xpath("//input[@id='equals']")).click(); 
		String result = driver.findElement(By.xpath("//input[@id='Resultbox']")).getAttribute("value"); 
		System.out.println("Resultado 1+6 = " + result); 
		driver.close();
		
		

	}

}
