package net.jasin.eliza.beritaboard.information;

/**
 * Created by elizajasin on 13/09/2017.
 */

public class NewsArticles {
    private String image;
    private String title;
    private String urlArticle;
    private String source;
    private String date;
    private String time;

    public NewsArticles(){

    }

    public NewsArticles(String image, String title, String urlArticle, String source, String date, String time) {

        this.image = image;
        this.title = title;
        this.urlArticle = urlArticle;
        this.source = source;
        this.date = date;
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrlArticle() {
        return urlArticle;
    }

    public void setUrlArticle(String urlArticle) {
        this.urlArticle = urlArticle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
