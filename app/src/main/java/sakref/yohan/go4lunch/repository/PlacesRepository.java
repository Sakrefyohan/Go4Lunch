package sakref.yohan.go4lunch.repository;

import androidx.lifecycle.LiveData;

import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.utils.PlacesService;

class PlacesRepository {

    private PlacesService mPlacesService;

    public PlacesRepository(PlacesService mPlacesService) {this.mPlacesService = mPlacesService;}

    public LiveData<Places> getNearbySearch(){return mPlacesService.getNearbyPlaces();   }
}
