package sakref.yohan.go4lunch.viewmodels;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.models.newapiplaces.PlacesDetails;
import sakref.yohan.go4lunch.repository.PlacesDetailsRepository;
import sakref.yohan.go4lunch.repository.PlacesRepository;

public class MainViewModel extends ViewModel {

    private LiveData<Places> mPlaces;
    private LiveData<PlacesDetails> mPlacesDetails;
    private static final String TAG = "MapsViewModel";

    public MainViewModel() {
        Log.d(TAG, "MapViewModel Called");

    }

    public void FetchRestaurant(Location location){
        mPlaces = PlacesRepository.getInstance().getNearbyRestaurants(location.getLatitude() + "," + location.getLongitude() ,1500,"restaurant", "AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s");
    }

    public void FetchDetailsRestaurant(String placeID){
        mPlacesDetails = PlacesDetailsRepository.getInstance().getDetailsRestaurants(placeID, "AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s");

    }

    public LiveData<Places> getPlaces(){return  mPlaces;}


    public LiveData<PlacesDetails> getPlacesDetails() {
        return mPlacesDetails;
    }

}