package sakref.yohan.go4lunch.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.utils.WorkmatesHelper;
import sakref.yohan.go4lunch.viewmodels.MainViewModel;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 123;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    TextView navDrawerWorkmates;
    ImageView navDrawerAvatar;
    TextView navDrawerMail;
    private String username;
    private String email;
    private String photoUrl;
    private String mUid;
    private String restaurantJoinedName;
    private String restaurantJoinedUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navigationView = findViewById(R.id.nav_view_drawer);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_mapview, R.id.navigation_listview, R.id.navigation_workmates)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        this.getUserConnected();
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.initializePlacesAutocomplete();
        this.updateDrawerText();
        SharedPreferences sharedPreferences = getSharedPreferences("notificationActive", MODE_PRIVATE);


    }


    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserConnected();
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_yourlunch:
                mUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                if (restaurantJoinedUid.equals("")) {
                    Toast.makeText(this, (R.string.you_have_not_selected_a_restaurant_yet), Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(this, RestaurantDetailsActivity.class);
                    intent.putExtra("KEY_DETAIL", restaurantJoinedUid);
                    intent.putExtra("KEY_DETAIL_NAME", restaurantJoinedName);
                    this.startActivity(intent);
                }
                break;
            case R.id.nav_settings:
                Intent intentSetting = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intentSetting, 23);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent i = new Intent(this, ConnectionActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // 1 - Configure Toolbar
    private void configureToolBar() {
        this.toolbar = findViewById(R.id.toolbar_drawer);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView() {
        this.navigationView = findViewById(R.id.nav_view_drawer);

        navigationView.setNavigationItemSelectedListener(this);
        navDrawerWorkmates = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_workmates);
        navDrawerAvatar = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_avatar);
        navDrawerMail = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_mail);
        String TAG = "Main Activity";
        Log.d(TAG, "configureNavigationView: " + navDrawerWorkmates + " / " + navDrawerAvatar + " / " + navDrawerMail);
    }

    private void initializePlacesAutocomplete() {
        String apiKey = "AIzaSyBujjCdAwqI3cLfIbUM6nRKtigecoCdn-s";

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        PlacesClient placesClient = Places.createClient(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.button_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startAutocompleteActivity(MenuItem item) {
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY,
                Arrays.asList(Place.Field.ID, Place.Field.NAME))
                .setCountry("FR")
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Intent intent = new Intent(this, RestaurantDetailsActivity.class);
                intent.putExtra("KEY_DETAIL", place.getId());
                intent.putExtra("KEY_DETAIL_NAME", place.getName());
                this.startActivity(intent);

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
        if (requestCode == 23) {
            if (resultCode == RESULT_OK) {
                username = data.getStringExtra("username");
                email = data.getStringExtra("email");
                photoUrl = data.getStringExtra("photoUrl");
                mUid = data.getStringExtra("uid");
                updateDrawerText();
            }

        }
    }

    public void setActionBarTitle(String title) {
        toolbar.setTitle(title);
    }

    public void updateDrawerText() {


        navDrawerWorkmates.setText(username);
        navDrawerMail.setText(email);
        Glide
                .with(this)
                .load(photoUrl)
                .centerCrop()
                .error(R.drawable.ic_no_image)
                .into(navDrawerAvatar);

    }


    public void getUserConnected() {
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        photoUrl = getIntent().getStringExtra("photoUrl");
        mUid = getIntent().getStringExtra("uid");
        WorkmatesHelper.getWorkmate(mUid)
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            restaurantJoinedName = task.getResult().getString("restaurantName");
                            restaurantJoinedUid = task.getResult().getString("restaurantJoined");
                        }
                    }
                });

    }

}
