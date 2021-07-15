package sakref.yohan.go4lunch.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import sakref.yohan.go4lunch.databinding.ActivityRestaurantDetailBinding;
import sakref.yohan.go4lunch.databinding.FragmentWorkmatesItemBinding;
import sakref.yohan.go4lunch.models.Workmates;

public class RestaurantDetailsViewHolder extends RecyclerView.ViewHolder {

    ActivityRestaurantDetailBinding activityRestaurantDetailBinding;
    ImageView dPhotos;
    TextView dRestaurantName;
    TextView dRestaurantVicinity;
    ImageView dRestaurantchoose;

    public RestaurantDetailsViewHolder(ActivityRestaurantDetailBinding itemView){
        super(itemView.getRoot());
        dPhotos = itemView.restaurantDetailImg;
        dRestaurantName = itemView.restaurantDetailName;
        dRestaurantVicinity = itemView.restaurantDetailVicinity;
        dRestaurantchoose = itemView.restaurantDetailBtnChoose;


    }

    public RestaurantDetailsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
    }

    public void showDetails(Workmates workmate){

        Glide.with(dPhotos.getContext())
                .load(workmate.getUrlPicture())
                .apply(RequestOptions.circleCropTransform())
                .into(dPhotos);

        dRestaurantName.setText(workmate.getWorkmatesName() +" is joining");



    }
}
