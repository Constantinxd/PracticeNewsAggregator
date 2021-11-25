package ru.vyatsu.koscheev.lenta;

import org.openqa.selenium.WebDriver;
import ru.vyatsu.koscheev.*;

import java.io.IOException;
import java.util.ArrayList;

public class ParserWorkerLenta<T> {
    private Parser<T> parser;
    private ParserSettings parserSettings;
    private HtmlLoader loader;
    private boolean isActive;
    private WebDriver driver;

    public final ArrayList<OnNewDataHandler<T>> onNewDataList;
    public final ArrayList<OnCompletedHandler> onCompletedList;

    public ParserWorkerLenta(Parser<T> parser) {
        setParser(parser);
        setDriver();
        this.onNewDataList = new ArrayList<>();
        this.onCompletedList = new ArrayList<>();
    }

    public void setParserSettings(ParserSettings parserSettings) {
        this.parserSettings = parserSettings;
        loader = new LentaHtmlLoader();
    }

    public void setParser(Parser<T> parser) { this.parser = parser; }

    private void setDriver() {
        DriverLoader driverLoader = new LentaDriverLoader();
        driver = driverLoader.getDriver();
    }

    public void Start() throws IOException, InterruptedException {
        isActive = true;
        Worker();
    }

    public void Abort() {
        isActive = false;
    }

    protected void Worker() {
        driver.get(loader.getUrl());

        loader.uploadContent(driver, parserSettings.numOfBlocks);
        T result = parser.Parse(driver);
        onNewDataList.get(0).OnNewData(this, result);
        isActive = false;
        driver.quit();
        onCompletedList.get(0).OnCompleted(this);
    }
}
