package ru.vyatsu.koscheev.euronews;

import ru.vyatsu.koscheev.ParserSettings;

public class EuronewsSettings extends ParserSettings {
    public EuronewsSettings(int numOfBlocks) {
        this.numOfBlocks = numOfBlocks - 1;
        BASE_URL = "https://www.euronews.com/european-affairs/european-news";
    }
}
