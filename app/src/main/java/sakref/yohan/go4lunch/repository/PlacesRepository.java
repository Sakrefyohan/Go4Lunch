package sakref.yohan.go4lunch.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.utils.NearbyPlacesApi;
import sakref.yohan.go4lunch.utils.RetrofitService;

public class PlacesRepository {

    private static PlacesRepository mPlacesService;

    public static PlacesRepository getInstance() {
        if (mPlacesService == null) {
            mPlacesService = new PlacesRepository();
        }
        return mPlacesService;
    }

    private NearbyPlacesApi nearbyPlacesApi;

    public PlacesRepository() {
        nearbyPlacesApi = RetrofitService.createService(NearbyPlacesApi.class);
    }

    public MutableLiveData<Places> getNearbyRestaurants(String location, int radius, String type, String key) {
        MutableLiveData<Places> nearbyRestaurantsData = new MutableLiveData<>();
        nearbyPlacesApi.getNearbyRestaurants(location, radius, type, key)
                .enqueue(new Callback<Places>() {

                    @Override
                    public void onResponse(Call<Places> call, Response<Places> response) {
                        if (response.isSuccessful()) {
                            nearbyRestaurantsData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Places> call, Throwable t) {
                        nearbyRestaurantsData.setValue(null);
                    }
                });
        return nearbyRestaurantsData;
    }
}
