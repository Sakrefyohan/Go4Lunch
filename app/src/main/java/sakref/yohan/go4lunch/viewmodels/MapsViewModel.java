package sakref.yohan.go4lunch.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.tabs.TabLayout;

import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.repository.PlacesRepository;
import sakref.yohan.go4lunch.ui.fragments.MapsFragment;

public class MapsViewModel extends ViewModel {


    private LiveData<Places> mPlaces;
    private PlacesRepository mPlacesRepository;

    private final String TAG = "MapsViewModel";

    public MapsViewModel() {
        Log.d(TAG, "MapsViewModel: MapViewModel Called ");




         mPlaces = PlacesRepository.getInstance().getNearbyRestaurants("48.8587741,2.2069771",1500,"restaurant","AIzaSyBoCsMWKLLF8WnNGK9Movq400WNYu1jR3I");

    }

    public LiveData<Places> getPlaces(){return  mPlaces;}





}