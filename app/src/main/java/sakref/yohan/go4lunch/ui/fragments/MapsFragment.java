package sakref.yohan.go4lunch.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.models.Photo;
import sakref.yohan.go4lunch.viewmodels.MainViewModel;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.content.ContentValues.TAG;

public class MapsFragment extends Fragment {

    public MainViewModel mainViewModel;
    public LatLng mPlaces;
    public String pName;
    public int pSize;
    public double pLat;
    public double pLng;
    public double dLat;
    public double dLng;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;

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
            if (ActivityCompat.checkSelfPermission(getActivity()
                    , ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                map = googleMap;
                map.getUiSettings().setMyLocationButtonEnabled(true);
                map.setMyLocationEnabled(true);
                Log.d(TAG, "onMapReady: isMyLocationEnabled ? " + map.isMyLocationEnabled() );
                getCurrentLocation();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 44);
            }


        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1] 
        == PackageManager.PERMISSION_GRANTED)){
            map.setMyLocationEnabled(true);
            getCurrentLocation();

        }else {
            Toast.makeText(getActivity(), "Permission denied.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(
               Context.LOCATION_SERVICE
        );
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {

                        mainViewModel.FetchRestaurant(location);
                        mainViewModel.setLat2(location.getLatitude());
                        mainViewModel.setLon2(location.getLongitude());
                        mainViewModel.getPlaces().observe(getViewLifecycleOwner(), (places) -> {

                            pSize = places.getResults().size();
                            for (int j = 0; j < pSize; j++) {
                                pName = places.getResults().get(j).getName();
                                pLat = places.getResults().get(j).getGeometry().getLocation().getLat();
                                pLng = places.getResults().get(j).getGeometry().getLocation().getLng();
                                mPlaces = new LatLng(pLat, pLng);
                                map.addMarker(new MarkerOptions().position(mPlaces).title(pName));

                            }
                        });
                    }else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                String vLatitude = String.valueOf(location1.getLatitude());
                                String vLongitude = String.valueOf(location1.getLongitude());
                                Intent intent = new Intent(getActivity(), MainViewModel.class);
                                intent.putExtra("vLatitude", vLatitude);
                                intent.putExtra("vLongitude",vLongitude);
                                Log.d(TAG, "onLocationResult: Latitude : " + vLatitude);
                                Log.d(TAG, "onLocationResult: Longitude : " + vLongitude);

                            }
                        };

                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }
                }
            });

        }else {startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        Log.d(TAG, "onCreateView: init mainViewModel : " + mainViewModel);

        return inflater.inflate(R.layout.fragment_maps, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}