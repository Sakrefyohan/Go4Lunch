package sakref.yohan.go4lunch.ui;

import static android.Manifest.permission.CALL_PHONE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Objects;

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


    private static final String TAG = "RestDetailsActivity";
    private ActivityRestaurantDetailBinding binding;
    public WorkmatesViewModel workmatesViewModel;
    public MainViewModel mainViewModel;
    private String mUid;
    Intent intent;
    String APIs;
    Result restaurant;
    Task<DocumentSnapshot> mCurrentWorkmate;
    String resultId;
    String resultAddress;
    String restaurantJoined;
    String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        this.configureViewModel();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mCurrentWorkmate = WorkmatesHelper.getWorkmate(mUid);
        fetchDetailsRestaurant();

    }

    private void configureViewModel() {

        workmatesViewModel = new ViewModelProvider(this).get(WorkmatesViewModel.class);
        workmatesViewModel.getWorkmates().observe(this, this::configureRecyclerView);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

    }

    private void configureRecyclerView(List<Workmates> workmates) {

        sakref.yohan.go4lunch.ui.adapters.WorkmatesFragmentAdapters workmatesFragmentAdapters = new WorkmatesFragmentAdapters(workmates, true);
        binding.restaurantDetailWorkmates.setAdapter(workmatesFragmentAdapters);
        binding.restaurantDetailWorkmates.setLayoutManager(new LinearLayoutManager(this));

    }

    public void bindData(PlacesDetails placesDetails) {
        restaurant = placesDetails.getResult();
        fetchWorkmateJoined();
        fetchRestaurantJoined();
        fetchRestaurantFav();

        //API = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photoreference=" + mRef + "&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s";
        binding.restaurantDetailName.setText(restaurant.getName());

        binding.restaurantDetailBtnCall.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + restaurant.getInternationalPhoneNumber()));
                startActivity(callIntent);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{CALL_PHONE}, 44);
            }
        });

        binding.restaurantDetailBtnChoose.setOnClickListener(v -> {
            if (!restaurantJoined.equals(restaurant.getPlaceId())) {
                binding.restaurantDetailBtnChoose.setImageResource(R.drawable.ic_check_ok);
                WorkmatesHelper.updateRestaurantJoined(restaurant.getPlaceId(), mUid);
                WorkmatesHelper.updateRestaurantName(restaurant.getName(), mUid);
                WorkmatesHelper.updateRestaurantAddress(resultAddress, mUid);
            } else {
                WorkmatesHelper.updateRestaurantJoined("", mUid);
                WorkmatesHelper.updateRestaurantName("", mUid);
                WorkmatesHelper.updateRestaurantAddress("", mUid);
                binding.restaurantDetailBtnChoose.setImageResource(R.drawable.ic_check_empty);

            }
            fetchWorkmateJoined();
            fetchRestaurantJoined();
        });

        binding.restaurantDetailBtnLike.setOnClickListener(v -> WorkmatesHelper.getFavRestaurant(mUid, restaurant.getPlaceId()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().size() > 0) {
                    WorkmatesHelper.deleteFavRestaurant(restaurant.getPlaceId(), mUid);

                } else {
                    WorkmatesHelper.addFavRestaurant(restaurant.getPlaceId(), mUid, restaurant.getName(), restaurant.getFormattedAddress());

                }
                fetchRestaurantFav();

            }
        }));

        binding.restaurantDetailBtnWebsite.setOnClickListener(v -> {

            String website = placesDetails.getResult().getWebsite();
            if (!website.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(website));
                startActivity(intent);
            }
        });

        binding.restaurantDetailVicinity.setText(placesDetails.getResult().getFormattedAddress());

        List<Photo> photo = placesDetails.getResult().getPhotos();

        if (photo != null) {
            int mRefSize = photo.size();
            String mRef = photo.get(mRefSize - 1).getPhotoReference();

            APIs = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photoreference=" + mRef + "&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s";
            //
            Glide
                    .with(binding.restaurantDetailImg.getContext())
                    .load(APIs)
                    .centerCrop()
                    .error(R.drawable.ic_no_image)
                    .into(binding.restaurantDetailImg);
        } else {
            binding.restaurantDetailImg.setImageResource(R.drawable.ic_no_image);
        }
    }

    public void fetchDetailsRestaurant() {
        intent = getIntent();
        resultId = intent.getStringExtra("KEY_DETAIL");
        resultAddress = intent.getStringExtra("KEY_DETAIL_TITLE");
        Log.d(TAG, "fetchDetailsRestaurant: " + resultId);
        mainViewModel.FetchDetailsRestaurant(resultId);
        mainViewModel.getPlacesDetails().observe(this, this::bindData);

    }

    public void fetchRestaurantJoined() {
        WorkmatesHelper.getWorkmate(mUid)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        restaurantJoined = task.getResult().getString("restaurantJoined");
                        restaurantName = task.getResult().getString("restaurantName");
                        if (!restaurantJoined.equals(restaurant.getPlaceId())) {
                            binding.restaurantDetailBtnChoose.setImageResource(R.drawable.ic_check_empty);
                        } else {
                            binding.restaurantDetailBtnChoose.setImageResource(R.drawable.ic_check_ok);
                        }

                    }
                });
    }

    public void fetchRestaurantFav() {
        WorkmatesHelper.getFavRestaurant(mUid, restaurant.getPlaceId()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().size() > 0) {
                    binding.restaurantDetailBtnLike.setImageResource(R.drawable.ic_baseline_star_24);

                } else {
                    binding.restaurantDetailBtnLike.setImageResource(R.drawable.ic_baseline_star_outline_24);

                }

            }
        });
    }

    public void fetchWorkmateJoined() {
        workmatesViewModel.fetchWorkmatesJoined(restaurant.getPlaceId());
    }

}