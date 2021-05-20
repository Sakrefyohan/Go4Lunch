package sakref.yohan.go4lunch.repository;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sakref.yohan.go4lunch.models.Photo;
import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.utils.PlacesPhotoAPI;
import sakref.yohan.go4lunch.utils.RetrofitService;

public class PhotosRepository {
    private static PhotosRepository mPhotosService;

    public static  PhotosRepository getInstance(){
        if (mPhotosService == null){
            mPhotosService = new PhotosRepository();
        }
        return mPhotosService;
    }

    private PlacesPhotoAPI photosApi;

    public PhotosRepository(){
        photosApi = RetrofitService.createService(PlacesPhotoAPI.class);
    }

    public MutableLiveData<Photo> getPhotosPlaces(int maxWidthPhoto, String referencePhoto, String key) {
        MutableLiveData<Photo> photosPlacesData = new MutableLiveData<>();
       // photosApi.getNearbyRestaurants(location, radius, type, key)
        photosApi.getPlacesPhotos(maxWidthPhoto, referencePhoto,key)
                .enqueue(new Callback<Photo>() {

                    @Override
                    public void onResponse(Call<Photo> call, Response<Photo> response) {
                        if (response.isSuccessful()) {
                            photosPlacesData.setValue(response.body());
                        }
                    }


                    @Override
                    public void onFailure(Call<Photo> call, Throwable t) {
                        photosPlacesData.setValue(null);
                    }
                });
        return photosPlacesData;
    }
}