package sakref.yohan.go4lunch.utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sakref.yohan.go4lunch.models.Places;

/**
 * Class which configures a {@link Retrofit} service.
 */

public interface NearbyPlacesApi {

    //After the json : radius=1500&type=restaurant&key=AIzaSyBoCsMWKLLF8WnNGK9Movq400WNYu1jR3I
    @GET("nearbysearch/json?")
    Call<Places> getNearbyRestaurants(@Query("location") String location,
                                                                      @Query("radius") int radius,
                                                                      @Query("type") String type,
                                                                      @Query("key") String key);

}
