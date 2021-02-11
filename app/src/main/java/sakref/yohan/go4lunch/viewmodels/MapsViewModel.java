package sakref.yohan.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.repository.PlacesRepository;

public class MapsViewModel extends ViewModel {


    private LiveData<Places> mPlaces;
    private PlacesRepository mPlacesRepository;

    public MapsViewModel() {


         mPlaces = PlacesRepository.getInstance().getNearbyRestaurants("48.8587741,2.2069771",1500,"restaurant","AIzaSyBoCsMWKLLF8WnNGK9Movq400WNYu1jR3I");

    }

    public LiveData<Places> getPlaces(){return  mPlaces;}





}