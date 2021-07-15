package sakref.yohan.go4lunch.ui.fragments;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import sakref.yohan.go4lunch.databinding.FragmentViewBinding;
import sakref.yohan.go4lunch.models.Geometry;
import sakref.yohan.go4lunch.models.Location;
import sakref.yohan.go4lunch.models.OpeningHours;
import sakref.yohan.go4lunch.models.Photo;
import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.models.Viewport;
import sakref.yohan.go4lunch.ui.MainActivity;
import sakref.yohan.go4lunch.ui.adapters.ListViewFragmentAdapters;
import sakref.yohan.go4lunch.viewmodels.MainViewModel;

import static android.content.ContentValues.TAG;


public class ListViewFragment extends Fragment {

    private MainViewModel mainViewModel;

    private static final String TAG = "ListViewFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentViewBinding binding = FragmentViewBinding.inflate(getLayoutInflater());
       View view = binding.getRoot();
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        Log.d(TAG, "onCreateView: init mainViewModel : " + mainViewModel);

       mainViewModel.getPlaces().observe(getViewLifecycleOwner(), (places) -> {
        Log.d(TAG, "onCreateView: Places Ready");

           ListViewFragmentAdapters adapters = new ListViewFragmentAdapters(places.getResults());
           binding.fragmentViewRecycler.setAdapter(adapters);
           binding.fragmentViewRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        });

        return view;
    }

}