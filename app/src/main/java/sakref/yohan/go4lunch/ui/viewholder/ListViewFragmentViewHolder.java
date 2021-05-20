package sakref.yohan.go4lunch.ui.viewholder;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import sakref.yohan.go4lunch.databinding.FragmentViewItemBinding;
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
        mRating.setText(String.valueOf(result.getRating().toString()));

        Glide.with(mPhoto).load(result.getPhotos().get(0).getPhotoReference()).into(mPhoto);
        Log.d(TAG, "bind: mPhoto : " + result.getPhotos());
        Log.d(TAG, "bind: mPhoto - photo reference : " + result.getPhotos().get(0).getPhotoReference());
        Log.d(TAG, "bind: mPhoto - size  : " + result.getPhotos().size());
        Log.d(TAG, "bind: mPhoto - html: " + result.getPhotos().get(0).getHtmlAttributions());
        Log.d(TAG, "bind: mPhoto - Height: " + result.getPhotos().get(0).getHeight());
        Log.d(TAG, "bind: mPhoto - width: " + result.getPhotos().get(0).getWidth());

    }


}
