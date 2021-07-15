package sakref.yohan.go4lunch.ui.adapters;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import sakref.yohan.go4lunch.databinding.FragmentViewItemBinding;
import sakref.yohan.go4lunch.models.Result;
import sakref.yohan.go4lunch.ui.RestaurantDetailsActivity;
import sakref.yohan.go4lunch.ui.viewholder.ListViewFragmentViewHolder;

public class ListViewFragmentAdapters extends RecyclerView.Adapter<ListViewFragmentViewHolder>{



    private List<Result> restaurants;
    private String restaurantsChoose;

    public ListViewFragmentAdapters(List<Result> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @NotNull
    @Override
    public ListViewFragmentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        FragmentViewItemBinding binding = FragmentViewItemBinding.inflate(LayoutInflater.from(parent.getContext()) ,parent, false);
        return new ListViewFragmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListViewFragmentViewHolder holder, int position) {
        holder.bind(restaurants.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);

                //https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
                intent.putExtra("KEY_DETAIL", restaurants.get(position).getPlaceId());

                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    /*
    public void geocoder({
        Geocoder geo = new Geocoder(getContext());

        pLat = places.getResults().get(j).getGeometry().getLocation().getLat();
        pLng = places.getResults().get(j).getGeometry().getLocation().getLng();
        mPlaces = new LatLng(pLat, pLng);

                try {
            mAdress = geo.getFromLocation(pLat,pLng,1).get(0).getAddressLine(0);
        } catch (
        IOException e) {
            e.printStackTrace();
        }
    })


     */
}
