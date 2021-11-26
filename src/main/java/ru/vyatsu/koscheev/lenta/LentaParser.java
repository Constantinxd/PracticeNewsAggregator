package ru.vyatsu.koscheev.lenta;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.vyatsu.koscheev.Parser;
import ru.vyatsu.koscheev.model.News;

import java.util.ArrayList;
import java.util.List;

public class LentaParser implements Parser<ArrayList<News>> {
    public ArrayList<News> Parse(WebDriver driver) {
        ArrayList<News> list = new ArrayList<>();

        List<String> links = getListOfHrefValue(driver, By.xpath("//div[@id='more']//div[contains(@class,'news')]//div[contains(@class,'titles')]//a"));

        for (String link : links) {
            News news = new News();
            try {
                Document document = Jsoup.connect(link).get();
                if (link.contains("lenta.ru") && !link.contains("moslenta.ru")) {
                    news.title = document.selectXpath("//h1[@itemprop='headline']").text();
                    news.time = document.selectXpath("//div[contains(@class, 'b-topic__info')]//time[@class='g-date']").text();

                    var imageUrl = document.selectXpath("//div[contains(@class,'b-topic__title-image')]//img[@class='g-picture']");
                    if (imageUrl.size() > 0)
                        news.imageUrl = imageUrl.get(0).attr("src");

                    news.content = new ArrayList<>();
                    for (var content : document.selectXpath("//div[@itemprop='articleBody']").get(0).children())
                        news.content.add(content.text());

                    list.add(news);
                }
                else if (link.contains("moslenta.ru")) {
                    news.title = document.selectXpath("//h1[contains(@class, 'title')]").get(0).text();
                    news.time = document.selectXpath("//span[contains(@class, 'time')]").get(0).text();

                    var imageUrl = document.selectXpath("//img");
                    for (var i : imageUrl) {
                        if (i.attr("alt").equals(news.title) && i.parent() != null) {
                            String l = i.parent().selectXpath("//link").get(0).attr("href");
                            news.imageUrl = "https://moslenta.ru"
                                    + l.replace("/filters:quality(75)", "");
                        }
                    }

                    var content = document.selectXpath("//div[contains(@class, 'content-background')]//div[contains(@class, 'content')]//article");
                    var inner = content.get(0).selectXpath("//div[contains(@class, 'vikont')]//p");
                    news.content = new ArrayList<>();
                    for (var i : inner)
                        news.content.add(i.text());

                    list.add(news);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return list;
    }

    private List<String> getListOfHrefValue(WebDriver driver, By by) {
        List<String> links = new ArrayList<>();

        var elems = driver.findElements(by);

        for (var l : elems)
            links.add(l.getAttribute("href"));

        return links;
    }
}
