package ru.vyatsu.koscheev.euronews;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.vyatsu.koscheev.HtmlLoader;
import ru.vyatsu.koscheev.ParserSettings;

import java.time.Duration;

public class EuronewsHtmlLoader extends HtmlLoader {
    public EuronewsHtmlLoader() {
        this.url = ParserSettings.BASE_URL;
    }

    public void uploadContent(WebDriver driver, int count) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            for (int i = 0; i < count; i++) {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='See more']"))).click();
                Thread.sleep(2000);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeBanners(WebDriver driver) {

        var banners = driver
                .findElements(By.xpath("//button[@id='didomi-notice-agree-button']"));
        if (banners.size() > 0)
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(banners.get(0))).click();
    }
}
