package firefox;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {

	public static void main(String[] args) {
		
		WebDriver driver = new FirefoxDriver(); 
		driver.get("http://www.terra.com.br");	
		boolean i = driver.getPageSource().contains("Lula");
		System.out.println("Lula no Terra? "+(i?"Sim":"Não")); 
		
		driver.get("http://www.uol.com.br");	
		i = driver.getPageSource().contains("Lula");
		System.out.println("Lula no Uol? "+(i?"Sim":"Não")); 
		driver.close(); 
		

	}

}
