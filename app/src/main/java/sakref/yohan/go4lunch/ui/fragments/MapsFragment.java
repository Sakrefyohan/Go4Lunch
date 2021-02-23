package sakref.yohan.go4lunch.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Collections;
import java.util.List;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.models.OpeningHours;
import sakref.yohan.go4lunch.models.Places;
import sakref.yohan.go4lunch.repository.PlacesRepository;
import sakref.yohan.go4lunch.viewmodels.ListviewViewModel;
import sakref.yohan.go4lunch.viewmodels.MapsViewModel;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.content.ContentValues.TAG;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MapsFragment extends Fragment {

    public MapsViewModel mapsViewModel;
    public LatLng mPlaces;
    public String pName;
    public int pSize;
    public double pLat;
    public double pLng;

    public static MapsFragment newInstance() {
        MapsFragment fragmentMap = new MapsFragment();
        return fragmentMap;
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            mapsViewModel.getPlaces().observe(getViewLifecycleOwner(),(places) -> {
                Log.d(TAG, "onViewCreated: Restaurant : " + places.getResults().get(0).getName());
                pSize = places.getResults().size();
                for(int j = 0 ; j < pSize ; j++){
                    pName = places.getResults().get(j).getName();
                    pLat = places.getResults().get(j).getGeometry().getLocation().getLat();
                    pLng = places.getResults().get(j).getGeometry().getLocation().getLng();
                    mPlaces = new LatLng(pLat,pLng);
                    googleMap.addMarker(new MarkerOptions().position(mPlaces).title(pName));
                    Log.d(TAG, "onViewCreated: onMapReady : " + pSize + " / " + pName + " / " + pLat + " / " + pLng);

                }
            });
        }
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);


        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }




    }
}