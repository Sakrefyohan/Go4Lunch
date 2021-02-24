package sakref.yohan.go4lunch.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.tabs.TabLayout;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.repository.PlacesRepository;
import sakref.yohan.go4lunch.ui.fragments.MapsFragment;

public class MapsViewModel extends ViewModel {


    private LiveData<Places> mPlaces;
    private PlacesRepository mPlacesRepository;
    private MapsFragment mapsFragment;
    private static final String TAG = "MapsViewModel";


    public MapsViewModel() {
        Log.d(TAG, "MapViewModel Called");
         mPlaces = PlacesRepository.getInstance().getNearbyRestaurants("48.8587741,2.2069771",1500,"restaurant", "AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s");
        Log.d(TAG, "MapsViewModel: Places get --> " + mPlaces);


    }

    public LiveData<Places> getPlaces(){return  mPlaces;}







}