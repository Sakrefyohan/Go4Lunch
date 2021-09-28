package sakref.yohan.go4lunch.viewmodels;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.models.newapiplaces.PlacesDetails;
import sakref.yohan.go4lunch.repository.PlacesDetailsRepository;
import sakref.yohan.go4lunch.utils.WorkmatesHelper;

public class WorkmatesViewModel extends ViewModel {


    private MutableLiveData<List<Workmates>> mWorkmates = new MutableLiveData<>();
    private String placeName;
    private static String TAG = "WorkmatesViewModel";

    public void fetchWorkmates(){

    WorkmatesHelper.getAllWorkmate().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
            List<Workmates> listWorkmates = new ArrayList<Workmates>();

            for (QueryDocumentSnapshot document:task.getResult())
            {
              listWorkmates.add(document.toObject(Workmates.class));
            }
            mWorkmates.setValue(listWorkmates);
        }
    });

    WorkmatesHelper.getAllWorkmate().addOnFailureListener(this.onFailureListener());
    }

    public void fetchWorkmatesJoined(String restaurantJoined){

        WorkmatesHelper.getAllJoiningWorkmate(restaurantJoined).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                List<Workmates> listWorkmates = new ArrayList<Workmates>();

                for (QueryDocumentSnapshot document:task.getResult())
                {
                    listWorkmates.add(document.toObject(Workmates.class));
                }
                mWorkmates.setValue(listWorkmates);

            }
        });

    }

    public void createNotification(Boolean activate){

    }

    public void setPlaceName(String placeName){
        this.placeName = placeName;
    }

    public String getPlaceName(){
        Log.d(TAG, "getPlaceName: " + placeName);
        return placeName;}


    public MutableLiveData<List<Workmates>> getWorkmates() {return mWorkmates;}

    // --------------------
    // ERROR HANDLER
    // --------------------

    protected OnFailureListener onFailureListener(){
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "fetchWorkmate : onFailure: " + e);
            }
        };
    }
}