package sakref.yohan.go4lunch.utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sakref.yohan.go4lunch.models.Location;
import sakref.yohan.go4lunch.models.Places;

/**
 * Class which configures a {@link Retrofit} service.
 */

public interface PlacesService {



    @GET("nearbysearch/json?radius=1500&type=restaurant&key=AIzaSyBoCsMWKLLF8WnNGK9Movq400WNYu1jR3I")
    Call<Places> getNearbyPlaces(@Query("location") String location);

    //@GET("details/json?key=AIzaSyDZrTJrp5DeQR5mwPAoj14LWCVo7huGjzw")
    //Call<android.telecom.Call.Details> getDetails(@Query("place_id")String placeId);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
