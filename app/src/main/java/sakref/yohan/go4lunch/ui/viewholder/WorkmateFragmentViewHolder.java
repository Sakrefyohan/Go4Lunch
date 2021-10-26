package sakref.yohan.go4lunch.ui.viewholder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.FragmentViewItemBinding;
import sakref.yohan.go4lunch.databinding.FragmentWorkmatesItemBinding;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.models.newapiplaces.PlacesDetails;
import sakref.yohan.go4lunch.models.newapiplaces.Result;
import sakref.yohan.go4lunch.ui.RestaurantDetailsActivity;
import sakref.yohan.go4lunch.viewmodels.MainViewModel;

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
        if (!onRestaurantDetails){
            if (result.getRestaurantName()== ""){
                mName.setText(result.getWorkmatesName() + " hasn't decided yet");
            }else{mName.setText(result.getWorkmatesName() + " is eating at " + result.getRestaurantName());}


        }else{mName.setText(result.getWorkmatesName() + " is joining");}


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
