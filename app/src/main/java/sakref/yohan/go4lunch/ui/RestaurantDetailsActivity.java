package sakref.yohan.go4lunch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.ActivityRestaurantDetailBinding;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.models.newapiplaces.Photo;
import sakref.yohan.go4lunch.models.newapiplaces.PlacesDetails;
import sakref.yohan.go4lunch.models.newapiplaces.Result;
import sakref.yohan.go4lunch.ui.adapters.WorkmatesFragmentAdapters;
import sakref.yohan.go4lunch.utils.WorkmatesHelper;
import sakref.yohan.go4lunch.viewmodels.MainViewModel;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

public class RestaurantDetailsActivity extends AppCompatActivity {

    private static String TAG = "RestaurantDetailsActivity";
    private ActivityRestaurantDetailBinding binding;
    public WorkmatesViewModel workmatesViewModel;
    public MainViewModel mainViewModel;
    private WorkmatesFragmentAdapters WorkmatesFragmentAdapters;
    private FirebaseAuth mAuth;
    private String mUid;
    Intent intent;
    String APIs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        this.configureViewModel();
        mAuth = FirebaseAuth.getInstance();
        mUid = mAuth.getCurrentUser().getUid();
        fetchDetailsRestaurant();

    }

    private void configureViewModel(){
        workmatesViewModel = new ViewModelProvider(this).get(WorkmatesViewModel.class);
        workmatesViewModel.fetchWorkmates();
        workmatesViewModel.getWorkmates().observe(this, this::configureRecyclerView);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

    }

    private void configureRecyclerView(List<Workmates> workmates){

        WorkmatesFragmentAdapters = new WorkmatesFragmentAdapters(workmates, true);
        binding.restaurantDetailWorkmates.setAdapter(WorkmatesFragmentAdapters);
        binding.restaurantDetailWorkmates.setLayoutManager(new LinearLayoutManager(this));

    }

    public void bindData(PlacesDetails placesDetails){

        //API = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photoreference=" + mRef + "&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s";
        binding.restaurantDetailName.setText(placesDetails.getResult().getName()); //intent.getStringExtra("KEY_DETAIL")
        // Todo: take number of the restaurant to call it
        binding.restaurantDetailBtnCall.setOnClickListener(v -> {
            Log.d(TAG, "bindData: Phone number = " + placesDetails.getResult().getInternationalPhoneNumber());
            Toast.makeText(this, "Dring Dring : " + placesDetails.getResult().getInternationalPhoneNumber(), Toast.LENGTH_SHORT).show();

        });
        //TODO: intent Filter for action call
        binding.restaurantDetailBtnChoose.setOnClickListener(v -> {
            Toast.makeText(this, "Choisi", Toast.LENGTH_SHORT).show();

            WorkmatesHelper.updateRestaurantJoined(placesDetails.getResult().getPlaceId(), mUid);
            //TODO: Change the effect for the ADD

        });
        binding.restaurantDetailBtnLike.setOnClickListener(v -> {
            Toast.makeText(this, "Liké", Toast.LENGTH_SHORT).show();
            //TODO: update document Collection
            WorkmatesHelper.addFavRestaurant(placesDetails.getResult().getPlaceId(), mUid);
        });
        binding.restaurantDetailBtnWebsite.setOnClickListener(v -> {
            Toast.makeText(this, "Website", Toast.LENGTH_SHORT).show();
            //TODO: Add the intent Filter : placesDetails.getResult().getUrl();
        });
        binding.restaurantDetailVicinity.setText(placesDetails.getResult().getVicinity());

        Photo photo = placesDetails.getResult().getPhotos()[0];

        if(photo != null) {
            //int mRefSize = photo.get;
            String mRef = photo.getPhotoReference();

            APIs = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photoreference=" + mRef + "&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s";
            //
            Glide
                    .with(binding.restaurantDetailImg.getContext())
                    .load(APIs)
                    .centerCrop()
                    .error(R.drawable.ic_no_image)
                    .into(binding.restaurantDetailImg);
        }else{
            binding.restaurantDetailImg.setImageResource(R.drawable.ic_no_image);
        }
    }

    public void fetchDetailsRestaurant(){
        intent = getIntent();
        String resultId = intent.getStringExtra("KEY_DETAIL");
        Log.d(TAG, "fetchDetailsRestaurant: " + resultId);
        mainViewModel.FetchDetailsRestaurant(resultId);
        mainViewModel.getPlacesDetails().observe(this, this::bindData);
    }

}
