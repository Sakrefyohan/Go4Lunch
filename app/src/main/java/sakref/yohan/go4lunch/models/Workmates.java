package sakref.yohan.go4lunch.models;

import androidx.annotation.Nullable;

import java.util.List;

public class Workmates {


    private String uid;
    private String username;
    @Nullable
    private String urlPicture;
    private String restaurantJoined;

    public Workmates() { }

    public Workmates(String uid, String username, String urlPicture, String restaurantJoined) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.restaurantJoined = restaurantJoined;
    }

    // --- GETTERS ---

    public String getUid() { return uid; }
    public String getWorkmatesName() { return username; }
    public String getUrlPicture() { return urlPicture; }
    public String getRestaurantJoined() {return restaurantJoined;}

    // --- SETTERS ---

    public void setWorkmatesName(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setRestaurantJoined(String restaurantJoined) {this.restaurantJoined = restaurantJoined;}

}
