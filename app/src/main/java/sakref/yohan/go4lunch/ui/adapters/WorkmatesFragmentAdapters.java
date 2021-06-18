package sakref.yohan.go4lunch.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import sakref.yohan.go4lunch.databinding.FragmentViewItemBinding;
import sakref.yohan.go4lunch.databinding.FragmentWorkmatesItemBinding;
import sakref.yohan.go4lunch.models.Photo;
import sakref.yohan.go4lunch.models.Result;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.ui.viewholder.ListViewFragmentViewHolder;
import sakref.yohan.go4lunch.ui.viewholder.WorkmateFragmentViewHolder;

public class WorkmatesFragmentAdapters extends RecyclerView.Adapter<WorkmateFragmentViewHolder>{


    //todo ; finish the adapter
    private List<Workmates> workmates;


    public WorkmatesFragmentAdapters(List<Workmates> workmates) {
        this.workmates = workmates;
    }



    @NonNull
    @NotNull
    @Override
    public WorkmateFragmentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        FragmentWorkmatesItemBinding binding = FragmentWorkmatesItemBinding.inflate(LayoutInflater.from(parent.getContext()) ,parent, false);
        return new WorkmateFragmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WorkmateFragmentViewHolder holder, int position) {
        holder.bind(workmates.get(position));
    }

    @Override
    public int getItemCount() {
        return workmates.size();
    }

}
