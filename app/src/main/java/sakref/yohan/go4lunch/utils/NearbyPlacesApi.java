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
    // test : https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s
    //After the json : radius=1500&type=restaurant&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s
    @GET("nearbysearch/json?")
    Call<Places> getNearbyRestaurants(@Query("location") String location,
                                      @Query("radius") int radius,
                                      @Query("type") String type,
                                      @Query("key") String key);

}
