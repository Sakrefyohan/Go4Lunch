package sakref.yohan.go4lunch.viewmodels;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.tabs.TabLayout;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.models.Photo;
import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.repository.PhotosRepository;
import sakref.yohan.go4lunch.repository.PlacesRepository;
import sakref.yohan.go4lunch.ui.fragments.MapsFragment;

public class MainViewModel extends ViewModel {

    private LiveData<Places> mPlaces;
    private LiveData<Photo> mPhotosPlaces;
    private static final String TAG = "MapsViewModel";

    public MainViewModel() {
        Log.d(TAG, "MapViewModel Called");

    }

    public void FetchRestaurant(Location location){
        mPlaces = PlacesRepository.getInstance().getNearbyRestaurants(location.getLatitude() + "," + location.getLongitude() ,1500,"restaurant", "AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s");
    }

    public void FetchPhotos(String photoReference, int photoWidth){
        mPhotosPlaces = PhotosRepository.getInstance().getPhotosPlaces(photoWidth, photoReference,"AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s");
    }

    public LiveData<Places> getPlaces(){return  mPlaces;}

    public LiveData<Photo> getPhotos(){return mPhotosPlaces;}

}