package sakref.yohan.go4lunch.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sakref.yohan.go4lunch.models.newapiplaces.PlacesDetails;

public interface DetailsPlacesApi {
    //API Places : https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJ8ReFCynq9EcR6Sw7Z5gZGk0&fields=formatted_phone_number,international_phone_number,formatted_address,geometry,name,photo,place_id,website&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s
    @GET("details/json?")
    Call<PlacesDetails> getRestaurantDetails(@Query("place_id") String location,
                                             @Query("fields") String fields,
                                             @Query("key") String key);


}
