package ru.vyatsu.koscheev.lenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.vyatsu.koscheev.HtmlLoader;
import ru.vyatsu.koscheev.ParserSettings;

import java.time.Duration;

public class LentaHtmlLoader extends HtmlLoader {
    public LentaHtmlLoader() {
        this.url = ParserSettings.BASE_URL;
    }

    public void uploadContent(WebDriver driver, int count) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            for (int i = 0; i < count; i++) {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='b-more-button']//a"))).click();
                Thread.sleep(2000);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
