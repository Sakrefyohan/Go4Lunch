package sakref.yohan.go4lunch.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.FragmentViewBinding;
import sakref.yohan.go4lunch.databinding.FragmentWorkmatesBinding;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.ui.adapters.WorkmatesFragmentAdapters;
import sakref.yohan.go4lunch.utils.WorkmatesHelper;
import sakref.yohan.go4lunch.viewmodels.MainViewModel;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

public class WorkmatesFragment extends Fragment {

    private static final String TAG = "WorkmatesFragment";
    private FragmentWorkmatesBinding binding;
    public WorkmatesViewModel workmatesViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkmatesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        workmatesViewModel= new ViewModelProvider(getActivity()).get(WorkmatesViewModel.class);
        workmatesViewModel.fetchWorkmates();

        workmatesViewModel.getWorkmates().observe(getViewLifecycleOwner(), (workmates) -> {

            WorkmatesFragmentAdapters adapters = new WorkmatesFragmentAdapters(workmates);
            binding.fragmentWorkmatesRecycler.setAdapter(adapters);
            binding.fragmentWorkmatesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        });

        return view;
    }
}