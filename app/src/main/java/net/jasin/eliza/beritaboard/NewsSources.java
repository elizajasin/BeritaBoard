package net.jasin.eliza.beritaboard;

/**
 * Created by elizajasin on 12/09/2017.
 */

public class NewsSources {

//    int iconId;
//    String title;
//    String category;
    private String name;
    private String category;
    private String description;

    public NewsSources(){

    }

    public NewsSources(String title, String category, String description){
        this.name = title;
        this.category = category;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {

        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
