package sakref.yohan.go4lunch.ui.viewholder;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.FragmentViewItemBinding;
import sakref.yohan.go4lunch.databinding.FragmentWorkmatesItemBinding;
import sakref.yohan.go4lunch.models.Result;
import sakref.yohan.go4lunch.models.Workmates;

public class WorkmateFragmentViewHolder extends RecyclerView.ViewHolder{

    private static final String TAG = "FragmentViewHolder";
    TextView mName;
    ImageView mPhoto;



    public WorkmateFragmentViewHolder(FragmentWorkmatesItemBinding itemView){
        super(itemView.getRoot());
        mName =  itemView.fragmentWkmItemText;
        mPhoto = itemView.fragmentWkmItemPhoto;



    }

    public void bind(Workmates result){
        //setText
        mName.setText(result.getWorkmatesName());

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
