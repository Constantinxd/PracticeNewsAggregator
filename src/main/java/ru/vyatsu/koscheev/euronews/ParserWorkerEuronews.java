package ru.vyatsu.koscheev.euronews;

import org.openqa.selenium.WebDriver;
import ru.vyatsu.koscheev.*;

import java.io.IOException;
import java.util.ArrayList;

public class ParserWorkerEuronews<T> {
    private Parser<T> parser;
    private ParserSettings parserSettings;
    private HtmlLoader loader;

    private WebDriver driver;

    public final ArrayList<OnNewDataHandler<T>> onNewDataList;
    public final ArrayList<OnCompletedHandler> onCompletedList;

    public ParserWorkerEuronews(Parser<T> parser) {
        setParser(parser);
        setDriver();
        this.onNewDataList = new ArrayList<>();
        this.onCompletedList = new ArrayList<>();
    }

    public void setParserSettings(ParserSettings parserSettings) {
        this.parserSettings = parserSettings;
        loader = new EuronewsHtmlLoader(parserSettings);
    }

    public void setParser(Parser<T> parser) { this.parser = parser; }

    private void setDriver() {
        DriverLoader driverLoader = new EuronewsDriverLoader();
        driver = driverLoader.getDriver();
    }

    public void Start() throws IOException, InterruptedException {
        Worker();
    }

    protected void Worker() {
        driver.get(loader.getUrl());
        loader.removeBanners(driver);
        loader.uploadContent(driver, parserSettings.numOfBlocks);

        T result = parser.Parse(driver);
        onNewDataList.get(0).OnNewData(this, result);

        driver.quit();
        onCompletedList.get(0).OnCompleted(this);
    }
}
