package sakref.yohan.go4lunch.ui.adapters;

import android.content.Context;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.FragmentViewBinding;
import sakref.yohan.go4lunch.databinding.FragmentViewItemBinding;
import sakref.yohan.go4lunch.models.Result;
import sakref.yohan.go4lunch.ui.viewholder.ListViewFragmentViewHolder;

import static java.security.AccessController.getContext;

public class ListViewFragmentAdapters extends RecyclerView.Adapter<ListViewFragmentViewHolder>{



    private List<Result> restaurants;

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
