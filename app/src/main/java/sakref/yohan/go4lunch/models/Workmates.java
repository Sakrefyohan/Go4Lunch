package sakref.yohan.go4lunch.models;

import androidx.annotation.Nullable;

import java.util.List;

public class Workmates {


    private String uid;
    private String username;
    @Nullable
    private String urlPicture;

    public Workmates() { }

    public Workmates(String uid, String username, String urlPicture) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getWorkmatesName() { return username; }
    public String getUrlPicture() { return urlPicture; }

    // --- SETTERS ---
    public void setWorkmatesName(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }

}
