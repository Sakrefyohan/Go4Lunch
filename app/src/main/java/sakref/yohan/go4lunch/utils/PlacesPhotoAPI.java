package sakref.yohan.go4lunch.utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sakref.yohan.go4lunch.models.Photo;
import sakref.yohan.go4lunch.models.Places;

/**
 * Class which configures a {@link Retrofit} service.
 */

public interface PlacesPhotoAPI {
    //https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s
    @GET("photo?")
    Call<Photo> getPlacesPhotos(@Query("maxwidth") int maxWidthPhoto,
                                @Query("photoreference") String referencePhoto,
                                //API-KEY : AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s
                                @Query("key") String key);
}
