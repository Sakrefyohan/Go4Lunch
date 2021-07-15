package sakref.yohan.go4lunch.utils;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.Map;

import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

public class WorkmatesHelper {



    private static final String COLLECTION_NAME = "workmates";
    private static final String SUB_COLLECTION_NAME = "FavRestaurant";
    private static String TAG = "WorkmatesHelper";
    public static WorkmatesViewModel workmatesViewModel;
    // --- COLLECTION REFERENCE ---

    public static CollectionReference getWorkmatesCollection(){

        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid, String username, String urlPicture, String restaurantJoined) {
        Workmates workmateToCreate = new Workmates(uid, username, urlPicture, restaurantJoined);

        return WorkmatesHelper.getWorkmatesCollection().document(uid).set(workmateToCreate);
    }



    // --- GET ---

    public static Task<DocumentSnapshot> getWorkmate(String uid){
        return WorkmatesHelper.getWorkmatesCollection().document(uid).get();
    }

    public static Task<QuerySnapshot> getAllWorkmate(){
         return WorkmatesHelper.getWorkmatesCollection().get();
    }

    // --- UPDATE ---

    public static Task<Void> updateWorkmateName(String username, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).update("workmatesName", username);
    }

    public static Task<Void> updateRestaurantJoined(String restaurantJoined, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).update("restaurantJoined", restaurantJoined);
    }

    public static Task<Void> addFavRestaurant(String restaurantName, String uid) {
        Map<String, Object> restaurantFav = new HashMap<>();
        restaurantFav.put("restaurant", restaurantName);
        return WorkmatesHelper.getWorkmatesCollection().document(uid).collection(SUB_COLLECTION_NAME).document(restaurantName).set(restaurantFav);
    }



    // --- DELETE ---

    public static Task<Void> deleteWorkmate(String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).delete();
    }

    public static Task<Void> deleteFavRestaurant(String restaurantName, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).collection(SUB_COLLECTION_NAME).document(restaurantName).delete();
    }


}
