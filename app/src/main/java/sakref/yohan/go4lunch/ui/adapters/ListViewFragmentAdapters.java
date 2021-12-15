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
import sakref.yohan.go4lunch.models.Location;
import sakref.yohan.go4lunch.models.Result;
import sakref.yohan.go4lunch.ui.RestaurantDetailsActivity;
import sakref.yohan.go4lunch.ui.viewholder.ListViewFragmentViewHolder;

public class ListViewFragmentAdapters extends RecyclerView.Adapter<ListViewFragmentViewHolder> {

    private List<Result> restaurants;
    private double lat;
    private double lng;
    private String restaurantsChoose;

    public ListViewFragmentAdapters(List<Result> restaurants, double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        this.restaurants = restaurants;
    }

    @NonNull
    @NotNull
    @Override
    public ListViewFragmentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        FragmentViewItemBinding binding = FragmentViewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ListViewFragmentViewHolder(binding, lat, lng);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListViewFragmentViewHolder holder, int position) {
        holder.bind(restaurants.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);
                intent.putExtra("KEY_DETAIL", restaurants.get(position).getPlaceId());
                intent.putExtra("KEY_DETAIL_NAME", restaurants.get(position).getName());
                v.getContext().startActivity(intent);


            }
        });


    }


    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}
