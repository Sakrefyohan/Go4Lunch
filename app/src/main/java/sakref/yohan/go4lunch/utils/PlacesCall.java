package sakref.yohan.go4lunch.utils;

import androidx.annotation.Nullable;

import java.util.List;

import sakref.yohan.go4lunch.models.Places;

public class PlacesCall {

    public interface Callbacks {
        void onResponse(@Nullable List<Places> places);
        void onFailure();
    }
}
