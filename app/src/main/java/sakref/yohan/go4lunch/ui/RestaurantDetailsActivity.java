package sakref.yohan.go4lunch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import sakref.yohan.go4lunch.databinding.ActivityRestaurantDetailBinding;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.ui.adapters.WorkmatesFragmentAdapters;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

public class RestaurantDetailsActivity extends AppCompatActivity {

    private ActivityRestaurantDetailBinding binding;
    public WorkmatesViewModel workmatesViewModel;
    private WorkmatesFragmentAdapters WorkmatesFragmentAdapters;
    Intent intent;
    String APIs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        this.configureViewModel();
        bindData();

    }

    private void configureViewModel(){
        workmatesViewModel = new ViewModelProvider(this).get(WorkmatesViewModel.class);
        workmatesViewModel.fetchWorkmates();
        workmatesViewModel.getWorkmates().observe(this, this::configureRecyclerView);

    }

    private void configureRecyclerView(List<Workmates> workmates){

        WorkmatesFragmentAdapters = new WorkmatesFragmentAdapters(workmates, true);
        binding.restaurantDetailWorkmates.setAdapter(WorkmatesFragmentAdapters);
        binding.restaurantDetailWorkmates.setLayoutManager(new LinearLayoutManager(this));

    }

    public void bindData(){

        //TODO:API PLACES
        fetchDetailsRestaurant();


        //API = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photoreference=" + mRef + "&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s";
        binding.restaurantDetailName.setText("RESTO"); //intent.getStringExtra("KEY_DETAIL")
        // Todo: take number of the restaurant to call it
        binding.restaurantDetailBtnCall.setOnClickListener(v -> {
            Toast.makeText(this, "Dring Dring", Toast.LENGTH_SHORT).show();

        });
        //TODO: intent Filter for action call
        binding.restaurantDetailBtnChoose.setOnClickListener(v -> {
            Toast.makeText(this, "Choisi", Toast.LENGTH_SHORT).show();
        });
        binding.restaurantDetailBtnLike.setOnClickListener(v -> {
            Toast.makeText(this, "Liké", Toast.LENGTH_SHORT).show();
        });
        binding.restaurantDetailBtnWebsite.setOnClickListener(v -> {
            Toast.makeText(this, "Website", Toast.LENGTH_SHORT).show();
        });
        binding.restaurantDetailVicinity.setText("français");

        /*
        if(resultId.getPhotos() != null) {
            int mRefSize = resultId.getPhotos().size();
            String mRef = resultId.getPhotos().get(mRefSize-1).getPhotoReference();
            APIs = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photoreference=" + mRef + "&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s";
            //
        }else{
            binding.restaurantDetailImg.setImageResource(R.drawable.ic_no_image);
        }

        Glide
                .with(binding.restaurantDetailImg.getContext())
                .load(APIs)
                .centerCrop()
                .error(R.drawable.ic_no_image)
                .into(binding.restaurantDetailImg);

*/
    }

    public void fetchDetailsRestaurant(){
        intent = getIntent();
        String resultId = intent.getStringExtra("KEY_DETAIL");

        //AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s
        //API Places : https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=address_component,adr_address,business_status,formatted_address,geometry,icon,name,permanently_closed,photo,place_id,plus_code,type,url,utc_offset,vicinity&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s
    }




}
