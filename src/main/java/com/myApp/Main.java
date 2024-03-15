package com.myApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {

        FileInputStream fis;
        Properties property = new Properties();

        try {
        fis = new FileInputStream("src/main/resources/url.properties");
        property.load(fis);
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл отсуствует!");
        }

        String urlForConnect = property.getProperty("urlSite");

        List<Article> articleList = new ArrayList<>();

        Document doc = Jsoup.connect(urlForConnect).get();

        Elements h1Elements = doc.getElementsByAttributeValue("class", "listing-item__content");

        h1Elements.forEach(h1Element -> {
            Element aElement = h1Element.child(0);
            String url = aElement.attr("href");
            String name = aElement.child(0).text();

            articleList.add(new Article(url, name));
        });

        articleList.forEach(System.out::println);

    }

}

class Article {
    private String url;
    private String name;

    public Article(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Article{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}