package sakref.yohan.go4lunch.ui.viewholder;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.FragmentWorkmatesItemBinding;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.utils.RestaurantUtils;

public class WorkmateFragmentViewHolder extends RecyclerView.ViewHolder{

    private static final String TAG = "FragmentViewHolder";
    TextView mName;
    ImageView mPhoto;



    public WorkmateFragmentViewHolder(FragmentWorkmatesItemBinding itemView){
        super(itemView.getRoot());
        mName =  itemView.fragmentWkmItemText;
        mPhoto = itemView.fragmentWkmItemPhoto;



    }

    public void bind(Workmates result, boolean onRestaurantDetails){

       mName.setText(RestaurantUtils.getWorkmatesRestaurant(result.getRestaurantName(), result.getWorkmatesName(), onRestaurantDetails));


        if(result.getUrlPicture() != null) {
            Glide
                    .with(mPhoto.getContext())
                    .load(result.getUrlPicture())
                    .centerCrop()
                    .error(R.drawable.ic_no_image)
                    .into(mPhoto);
        }else{
            mPhoto.setImageResource(R.drawable.ic_no_image);
        }

    }

}
