package ru.vyatsu.koscheev.lenta;

import ru.vyatsu.koscheev.ParserSettings;

public class LentaSettings extends ParserSettings {
    public LentaSettings(int numOfBlocks) {
        this.numOfBlocks = numOfBlocks - 1;
        this.baseUrl = "https://lenta.ru/parts/news/";
    }
}
