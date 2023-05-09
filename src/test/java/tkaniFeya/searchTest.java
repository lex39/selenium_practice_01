package tkaniFeya;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class searchTest {

  private WebDriver driver;

  @BeforeEach
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver");
    driver = new ChromeDriver();
  }

  @AfterEach
  public void tearDown() {
    driver.close();
  }

  @Test
  @DisplayName("Поиск ткани")
  public void positiveSearch() {
    String currentSearch = "Пальтовая \"Букле\"";

    driver.get("https://www.tkani-feya.ru/");

    searchButton("Пальтовая");

    List<WebElement> searchResults = driver.findElements(By.cssSelector("a.name"));

    boolean searchResult = false;
    for (WebElement result : searchResults) {
      String actualTitle = result.getText();
      if (actualTitle.equals(currentSearch)) {
        searchResult = true;
        break;
      }
    }

    assertTrue(searchResult, currentSearch + " не найдено в поисковой выдаче");
  }

  @Test
  @DisplayName("Негативный, поиск не существующий ткани. Заглушка")
  public void negativeSearch() {
    driver.get("https://www.tkani-feya.ru/");

    searchButton("Кевларовая");


    WebElement stubMessage = driver.findElement(By.cssSelector(".alert-warning"));
    assertEquals("По вашему запросу ничего не найдено",
            stubMessage.getText(),
            "Сообщение что 'Ничего не найдено' не вывелось");
  }

  private void searchButton(String value) {
    WebElement searchInput = driver.findElement(By.className("search-text"));
    searchInput.sendKeys(value);

    WebElement searchButton = driver.findElement(By.className("search-btn"));
    searchButton.click();
  }
}
