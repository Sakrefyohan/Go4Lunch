package sakref.yohan.go4lunch.ui.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import sakref.yohan.go4lunch.databinding.FragmentViewItemBinding;
import sakref.yohan.go4lunch.databinding.FragmentWorkmatesItemBinding;
import sakref.yohan.go4lunch.models.Photo;
import sakref.yohan.go4lunch.models.Result;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.ui.RestaurantDetailsActivity;
import sakref.yohan.go4lunch.ui.viewholder.ListViewFragmentViewHolder;
import sakref.yohan.go4lunch.ui.viewholder.WorkmateFragmentViewHolder;

public class WorkmatesFragmentAdapters extends RecyclerView.Adapter<WorkmateFragmentViewHolder> {


    private List<Workmates> workmates;
    private boolean onRestaurantDetails;


    public WorkmatesFragmentAdapters(List<Workmates> workmates, boolean onRestaurantDetails) {
        this.workmates = workmates;
        this.onRestaurantDetails = onRestaurantDetails;
    }

    @NonNull
    @NotNull
    @Override
    public WorkmateFragmentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        FragmentWorkmatesItemBinding binding = FragmentWorkmatesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WorkmateFragmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WorkmateFragmentViewHolder holder, int position) {

        holder.bind(workmates.get(position), onRestaurantDetails);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workmates.get(position).getRestaurantName().equals("")) {
                    Toast.makeText(v.getContext(), "No restaurant selected", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);
                    intent.putExtra("KEY_DETAIL", workmates.get(position).getRestaurantJoined());
                    intent.putExtra("KEY_DETAIL_NAME", workmates.get(position).getRestaurantName());
                    v.getContext().startActivity(intent);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return workmates.size();
    }
}

