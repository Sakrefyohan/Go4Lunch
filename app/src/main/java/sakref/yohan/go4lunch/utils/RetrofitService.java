package sakref.yohan.go4lunch.utils;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final String TAG = "RetrofitService";

    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s

    private static Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/maps/api/place/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();



    public static <S> S createService(Class<S> serviceClass) {
        Log.d(TAG, "createService: fetch Retrofit");
        return retrofit.create(serviceClass);

    }
}

