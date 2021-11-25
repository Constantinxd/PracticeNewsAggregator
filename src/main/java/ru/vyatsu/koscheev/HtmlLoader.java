package ru.vyatsu.koscheev;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public abstract class HtmlLoader {
    public String url;

    public String getUrl() { return this.url; }

    public abstract void uploadContent(WebDriver driver, int count);

    public void removeBanners(WebDriver driver) {
    }
}
