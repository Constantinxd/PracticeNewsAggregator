package ru.vyatsu.koscheev.euronews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.vyatsu.koscheev.Parser;
import ru.vyatsu.koscheev.model.News;

import java.util.ArrayList;
import java.util.List;

public class EuronewsParser implements Parser<ArrayList<News>> {
    public ArrayList<News> Parse(WebDriver driver) {
        ArrayList<News> list = new ArrayList<>();

        List<String> links = getListOfHrefValue(driver, By.xpath("//div[@class='o-block-listing__articles']//article//h3[contains(@class,'m-object__title')]//a"));

        for (String link : links) {
            News news = new News();
            try {
                Document document = Jsoup.connect(link).get();

                Elements bodies = document.selectXpath("//div[@class='u-transform-none swiper-slide swiper-slide-active']");

                if (bodies.size() > 0) {
                    var body = bodies.get(0);

                    news.title = body.selectXpath("//h1[contains(@class, 'c-article-title')]").text();
                    news.time = body.selectXpath("//time[contains(@class, 'c-article-date')]").text();

                    var imagesUrl = body.selectXpath("//div[@class='c-video-player__container']//img[@class='c-article-media__img u-max-height-full']");
                    if (imagesUrl.size() > 0)
                        news.imageUrl = imagesUrl.get(0).selectXpath("//img").attr("src");

                    news.content = new ArrayList<>();
                    for (var content : body.selectXpath("//div[contains(@class, 'c-article-content') and contains(@class, 'js-article-content')]//p"))
                        news.content.add(content.text());

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
