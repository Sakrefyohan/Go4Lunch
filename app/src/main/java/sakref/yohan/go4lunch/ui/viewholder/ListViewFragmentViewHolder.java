package sakref.yohan.go4lunch.ui.viewholder;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;


import org.jetbrains.annotations.NotNull;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.FragmentViewItemBinding;
import sakref.yohan.go4lunch.models.OpeningHours;
import sakref.yohan.go4lunch.models.Result;
import sakref.yohan.go4lunch.utils.RestaurantUtils;
import sakref.yohan.go4lunch.utils.WorkmatesHelper;

public class ListViewFragmentViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "FragmentViewHolder";
    TextView mName;
    TextView mAdress;
    TextView mIsOpen;
    TextView mRating;
    TextView mDistance;
    TextView mPeople;
    ImageView mPhoto;
    String API;
    double latRestau;
    double longRestau;
    double lat2;
    double lon2;


    public ListViewFragmentViewHolder(FragmentViewItemBinding itemView, double lat, double lng) {
        super(itemView.getRoot());
        mName = itemView.fragmentMainItemName;
        mAdress = itemView.fragmentMainItemAddress;
        mIsOpen = itemView.fragmentMainItemOpening;
        mRating = itemView.fragmentMainItemRating;
        mPhoto = itemView.fragmentMainItemPhoto;
        mDistance = itemView.fragmentMainItemDistance;
        mPeople = itemView.fragmentMainItemPeople;
        lat2 = lat;
        lon2 = lng;



    }

    public void bind(Result result) {
        //setText
        mName.setText(result.getName());
        mAdress.setText(result.getVicinity());

        OpeningHours nIsOpen = result.getOpeningHours();
        if (nIsOpen != null) {
            if (result.getOpeningHours().getOpenNow() == true) {
                mIsOpen.setText(R.string.open);
            } else {
                mIsOpen.setText(R.string.close);
            }
        } else {
            mIsOpen.setText(R.string.no_information_available);
        }


        mRating.setText(RestaurantUtils.getRatingRestaurant(result.getRating()));

        if (result.getPhotos() != null) {
            int mRefSize = result.getPhotos().size();
            String mRef = result.getPhotos().get(mRefSize - 1).getPhotoReference();
            API = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photoreference=" + mRef + "&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s";

        } else {
            mPhoto.setImageResource(R.drawable.ic_no_image);
        }

        Glide
                .with(mPhoto.getContext())
                .load(API)
                .centerCrop()
                .error(R.drawable.ic_no_image)
                .into(mPhoto);


        latRestau= result.getGeometry().getLocation().getLat();
        longRestau = result.getGeometry().getLocation().getLng();

        mDistance.setText(RestaurantUtils.getRestaurantDistance(latRestau, longRestau, lat2, lon2) + "m");


        WorkmatesHelper.getAllJoiningWorkmate(result.getPlaceId()).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int numberPeople = task.getResult().size();

                    mPeople.setText("👤 (" + numberPeople + ")");
                }

            }
        });



    }

}


