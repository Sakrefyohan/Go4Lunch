package sakref.yohan.go4lunch.utils;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.Map;

import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

public class WorkmatesHelper {


    private static final String COLLECTION_NAME = "workmates";
    private static final String SUB_COLLECTION_NAME = "favrestaurant";
    private static final String TAG = "WorkmatesHelper";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getWorkmatesCollection() {

        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid, String username, String email, String urlPicture, String restaurantJoined, String restaurantName) {
        Workmates workmateToCreate = new Workmates(uid, username, email, urlPicture, restaurantJoined, restaurantName);
        return WorkmatesHelper.getWorkmatesCollection().document(uid).set(workmateToCreate);
    }

    public static Task<Void> addFavRestaurant(String restaurantUid, String uid, String restaurantName, String restaurantAddress) {
        Map<String, Object> restaurantFav = new HashMap<>();
        restaurantFav.put("restaurant", restaurantName);
        restaurantFav.put("restaurantUid", restaurantUid);
        restaurantFav.put("restaurantAddress", restaurantAddress);
        return WorkmatesHelper.getWorkmatesCollection().document(uid).collection(SUB_COLLECTION_NAME).document(restaurantUid).set(restaurantFav);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getWorkmate(String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).get();
    }

    public static Task<DocumentSnapshot> getWorkmateRestaurantJoined(String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).get();
    }

    public static Task<QuerySnapshot> getAllWorkmate() {
        return WorkmatesHelper.getWorkmatesCollection().get();
    }

    public static Task<QuerySnapshot> getAllJoiningWorkmate(String restaurantJoined) {
        return WorkmatesHelper.getWorkmatesCollection().whereEqualTo("restaurantJoined", restaurantJoined).get();
    }

    public static Task<DocumentSnapshot> getFavRestaurants(String userUid, String restaurantUid) {
        return WorkmatesHelper.getWorkmatesCollection().document(userUid).collection(SUB_COLLECTION_NAME).document(restaurantUid).get();
    }

    public static Task<QuerySnapshot> getFavRestaurant(String userUid, String restaurantUid) {
        return WorkmatesHelper.getWorkmatesCollection().document(userUid).collection(SUB_COLLECTION_NAME).whereEqualTo("restaurantUid", restaurantUid).get();
    }


    // --- UPDATE ---

    public static Task<Void> updateWorkmateName(String username, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).update("workmatesName", username);
    }

    public static Task<Void> updateWorkmatEmail(String email, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).update("workmatesEmail", email);
    }

    public static Task<Void> updateWorkmatePicture(String urlPicture, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).update("urlPicture", urlPicture);
    }

    public static Task<Void> updateRestaurantJoined(String restaurant, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).update("restaurantJoined", restaurant);
    }

    public static Task<Void> updateRestaurantName(String restaurant, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).update("restaurantName", restaurant);
    }

    public static Task<Void> updateRestaurantAddress(String restaurant, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).update("restaurantAddress", restaurant);
    }

    // --- DELETE ---

    public static Task<Void> deleteWorkmate(String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).delete();
    }

    public static void deleteFavRestaurant(String restaurantUid, String uid) {
        WorkmatesHelper.getWorkmatesCollection().document(uid).collection(SUB_COLLECTION_NAME).document(restaurantUid).delete();
    }

    public static Task<Void> deleteRestaurantJoined(String restaurantJoined, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).update("restaurantJoined", restaurantJoined);

    }


}
