package net.jasin.eliza.beritaboard;

/**
 * Created by elizajasin on 13/09/2017.
 */

public class NewsArticles {
    private String image;
    private String title;

    public NewsArticles(){

    }

    public NewsArticles(String image, String title) {

        this.image = image;
        this.title = title;
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
