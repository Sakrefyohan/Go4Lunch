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
    private double lat2;
    private double lon2;

    public MainViewModel() {
        Log.d(TAG, "MapViewModel Called");

    }

    public void FetchRestaurant(Location location){
        mPlaces = PlacesRepository.getInstance().getNearbyRestaurants(location.getLatitude() + "," + location.getLongitude() ,1500,"restaurant", "AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s");
    }

    public void FetchDetailsRestaurant(String placeID){
        mPlacesDetails = PlacesDetailsRepository.getInstance().getDetailsRestaurants(placeID, "AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s", "international_phone_number,geometry,name,photo,place_id,website" );

    }

    public LiveData<Places> getPlaces(){return  mPlaces;}


    public LiveData<PlacesDetails> getPlacesDetails() {
        return mPlacesDetails;
    }

    public double getLat2() {
        return lat2;
    }

    public void setLat2(double lat2) {
        this.lat2 = lat2;
    }

    public double getLon2() {
        return lon2;
    }

    public void setLon2(double lon2) {
        this.lon2 = lon2;
    }

}