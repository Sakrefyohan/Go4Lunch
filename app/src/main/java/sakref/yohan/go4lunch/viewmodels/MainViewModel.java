package sakref.yohan.go4lunch.viewmodels;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import sakref.yohan.go4lunch.models.Photo;
import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.repository.PlacesRepository;

public class MainViewModel extends ViewModel {

    private LiveData<Places> mPlaces;
    private static final String TAG = "MapsViewModel";

    public MainViewModel() {
        Log.d(TAG, "MapViewModel Called");

    }

    public void FetchRestaurant(Location location){
        mPlaces = PlacesRepository.getInstance().getNearbyRestaurants(location.getLatitude() + "," + location.getLongitude() ,1500,"restaurant", "AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s");
    }


    public LiveData<Places> getPlaces(){return  mPlaces;}


}