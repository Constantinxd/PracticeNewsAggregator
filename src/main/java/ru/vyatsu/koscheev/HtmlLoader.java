package ru.vyatsu.koscheev;

import org.openqa.selenium.WebDriver;

public abstract class HtmlLoader {
    public String url;

    public String getUrl() { return this.url; }

    public abstract void uploadContent(WebDriver driver, int count);

    public void removeBanners(WebDriver driver) {
    }
}
