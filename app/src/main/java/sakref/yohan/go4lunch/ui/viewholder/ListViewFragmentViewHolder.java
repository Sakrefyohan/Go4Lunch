package sakref.yohan.go4lunch.ui.viewholder;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.Random;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.FragmentViewItemBinding;
import sakref.yohan.go4lunch.models.Photo;
import sakref.yohan.go4lunch.models.Result;
import sakref.yohan.go4lunch.ui.adapters.ListViewFragmentAdapters;
import sakref.yohan.go4lunch.ui.fragments.ListViewFragment;

public class ListViewFragmentViewHolder extends RecyclerView.ViewHolder{

    private static final String TAG = "FragmentViewHolder";
    TextView mName;
    TextView mAdress;
    TextView mIsOpen;
    TextView mRating;
    TextView mDistance;
    TextView mPeople;
    ImageView mPhoto;
    String API;



    public ListViewFragmentViewHolder(FragmentViewItemBinding itemView){
        super(itemView.getRoot());
        mName =  itemView.fragmentMainItemName;
        mAdress = itemView.fragmentMainItemAddress;
        mIsOpen = itemView.fragmentMainItemOpening;
        mRating = itemView.fragmentMainItemRating;
        mPhoto = itemView.fragmentMainItemPhoto;
        mDistance = itemView.fragmentMainItemDistance;
        mPeople = itemView.fragmentMainItemPeople;

    }

    public void bind(Result result){
        //setText
        mName.setText(result.getName());
        mAdress.setText(result.getVicinity());

        /*Boolean nIsOpen = result.getOpeningHours().getOpenNow();
        if (nIsOpen){
            mIsOpen.setText("Open 24/7");
        }else{
            mIsOpen.setText("Closed Now");
        }*/
        if (result.getRating() != null) {
            mRating.setText(String.valueOf(result.getRating().toString()));
        }else{
            mRating.setText("");
        }

        if(result.getPhotos() != null) {
            int mRefSize = result.getPhotos().size();
            String mRef = result.getPhotos().get(mRefSize-1).getPhotoReference();
            Log.d(TAG, "bind: mRefSize : " + mRefSize);
            Log.d(TAG, "bind: mPhoto : https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + mRef + "&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s");
            API = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photoreference=" + mRef + "&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s";
            //
        }else{
            mPhoto.setImageResource(R.drawable.ic_no_image);
        }

        Glide
                .with(mPhoto.getContext())
                .load(API)
                .centerCrop()
                .error(R.drawable.ic_no_image)
                .into(mPhoto);

    }


}
