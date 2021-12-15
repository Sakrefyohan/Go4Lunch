package sakref.yohan.go4lunch.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sakref.yohan.go4lunch.models.newapiplaces.PlacesDetails;
import sakref.yohan.go4lunch.utils.DetailsPlacesApi;
import sakref.yohan.go4lunch.utils.RetrofitService;

public class PlacesDetailsRepository {

    private static final String TAG = "PlacesDetailsRepository";

    private static PlacesDetailsRepository mPlacesService;

    public static PlacesDetailsRepository getInstance() {
        if (mPlacesService == null) {
            mPlacesService = new PlacesDetailsRepository();
        }
        return mPlacesService;
    }

    private DetailsPlacesApi detailsPlacesApi;

    public PlacesDetailsRepository() {
        detailsPlacesApi = RetrofitService.createService(DetailsPlacesApi.class);
    }

    public MutableLiveData<PlacesDetails> getDetailsRestaurants(String placeID, String key, String fields) {
        MutableLiveData<PlacesDetails> detailsRestaurantData = new MutableLiveData<>();
        Log.d(TAG, "getDetailsRestaurants: fetch PlaceID + KEY " + placeID + " / " + key);
        detailsPlacesApi.getRestaurantDetails(placeID, fields, key)
                .enqueue(new Callback<PlacesDetails>() {

                    @Override
                    public void onResponse(Call<PlacesDetails> call, Response<PlacesDetails> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: fetch OK");
                            detailsRestaurantData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PlacesDetails> call, Throwable t) {
                        Log.d(TAG, "onFailure: fetch fail");
                        detailsRestaurantData.setValue(null);
                    }
                });
        Log.d(TAG, "getDetailsRestaurants: fetch detailsRestaurantData : " + detailsRestaurantData);
        return detailsRestaurantData;
    }


}
