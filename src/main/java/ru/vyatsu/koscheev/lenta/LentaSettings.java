package ru.vyatsu.koscheev.lenta;

import ru.vyatsu.koscheev.ParserSettings;

public class LentaSettings extends ParserSettings {
    public LentaSettings(int numOfBlocks) {
        this.numOfBlocks = numOfBlocks - 1;
        BASE_URL = "https://lenta.ru/parts/news/";
    }
}
