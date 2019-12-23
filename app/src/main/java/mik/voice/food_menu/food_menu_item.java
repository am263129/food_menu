package mik.voice.food_menu;

public class food_menu_item {
    private String name;
    private String photo;
    private String price;
    private String description;
    private String rating;



    public food_menu_item(String name, String photo, String price, String description,String rating){
        this.name =name;
        this.photo = photo;
        this.price = price;
        this.description = description;
        this.rating = rating;

    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }
}
