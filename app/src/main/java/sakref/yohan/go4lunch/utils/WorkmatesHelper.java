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


import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

public class WorkmatesHelper {



    private static final String COLLECTION_NAME = "workmates";
    private static String TAG = "WorkmatesHelper";
    public static WorkmatesViewModel workmatesViewModel;
    // --- COLLECTION REFERENCE ---

    public static CollectionReference getWorkmatesCollection(){

        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid, String username, String urlPicture) {
        Workmates workmateToCreate = new Workmates(uid, username, urlPicture);
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

    public static Task<Void> updateWorkmatename(String username, String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).update("username", username);
    }

    // --- DELETE ---

    public static Task<Void> deleteWorkmate(String uid) {
        return WorkmatesHelper.getWorkmatesCollection().document(uid).delete();
    }

}
