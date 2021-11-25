package ru.vyatsu.koscheev;

import org.openqa.selenium.WebDriver;

public interface Parser<T> {
    T Parse(WebDriver driver);
}
