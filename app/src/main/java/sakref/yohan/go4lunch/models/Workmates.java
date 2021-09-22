package sakref.yohan.go4lunch.models;

import androidx.annotation.Nullable;

import java.util.List;

public class Workmates {

    private String uid;
    private String username;
    private String workmatesEmail;
    @Nullable
    private String urlPicture;
    private String restaurantJoined;
    private String restaurantName;

    public Workmates(){

    }

    public Workmates(String uid, String username, String workmatesEmail, String urlPicture, String restaurantJoined, String restaurantName) {
        this.uid = uid;
        this.username = username;
        this.workmatesEmail = workmatesEmail;
        this.urlPicture = urlPicture;
        this.restaurantJoined = restaurantJoined;
        this.restaurantName = restaurantName;
    }





    // --- GETTERS ---

    public String getUid() { return uid; }
    public String getWorkmatesName() { return username; }
    public String getWorkmatesEmail() {return workmatesEmail;}
    public String getUrlPicture() { return urlPicture; }
    public String getRestaurantJoined() {return restaurantJoined;}
    public String getRestaurantName() {return restaurantName;}


    // --- SETTERS ---

    public void setWorkmatesName(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setEmail(String workmatesEmail) { this.workmatesEmail = workmatesEmail; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setRestaurantJoined(String restaurantJoined) {this.restaurantJoined = restaurantJoined;}
    public void setRestaurantName(String restaurantName) {this.restaurantName = restaurantName;}

}
