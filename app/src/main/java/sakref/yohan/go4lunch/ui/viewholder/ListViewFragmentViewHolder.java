package sakref.yohan.go4lunch.ui.viewholder;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.FragmentViewItemBinding;
import sakref.yohan.go4lunch.models.OpeningHours;
import sakref.yohan.go4lunch.models.Result;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.ui.fragments.MapsFragment;
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
                mIsOpen.setText("Ouvert");
            } else {
                mIsOpen.setText("Fermé");
            }
        } else {
            mIsOpen.setText("Aucune information disponible");
        }


        if (result.getRating() != null) {
            if (result.getRating() == 5 && result.getRating() > 4.4) {
                mRating.setText(result.getRating().toString() + " 🌟🌟🌟");
            } else if (result.getRating() <= 4.3 && result.getRating() > 4.1) {
                mRating.setText(result.getRating().toString() + " ⭐⭐⭐");
            } else if (result.getRating() <= 4 && result.getRating() > 3.1) {
                mRating.setText(result.getRating().toString() + " ⭐⭐");
            } else if (result.getRating() <= 3 && result.getRating() > 2.1) {
                mRating.setText(result.getRating().toString() + " ⭐");
            } else if (result.getRating() <= 2) {
                mRating.setText("☀");
            }

        } else {
            mRating.setText("Pas de note");
        }

        if (result.getPhotos() != null) {
            int mRefSize = result.getPhotos().size();
            String mRef = result.getPhotos().get(mRefSize - 1).getPhotoReference();
            API = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photoreference=" + mRef + "&key=AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s";
            //
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

        int distance = distance(latRestau, longRestau, lat2, lon2);

        mDistance.setText(distance + "m");


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

    private int distance(double latRestau, double longRestau, double lat2, double lon2) {
        double theta = longRestau - lon2;
        double dist = Math.sin(deg2rad(latRestau)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(latRestau)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        dist = dist * 1000;

        return (int) dist;
    }

    //This function converts decimal degrees to radians

    private double deg2rad ( double deg){
        return (deg * Math.PI / 180.0);
    }

    //This function converts radians to decimal degrees

    private double rad2deg ( double rad){
        return (rad * 180.0 / Math.PI);
    }

}


