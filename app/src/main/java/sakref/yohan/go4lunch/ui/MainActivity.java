package sakref.yohan.go4lunch.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.maps.SupportMapFragment;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import java.util.Arrays;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.ActivityConnectionBinding;

public class MainActivity extends AppCompatActivity  {

    private static final int RC_SIGN_IN = 123;
    private ActivityConnectionBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        Button button = (Button) findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
           public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void toaster(){
        Toast.makeText(getApplicationContext(),"Hello function toaster",Toast.LENGTH_SHORT).show();
    }



    /*
    @OnClick(R.id.main_activity_button_login)
    public void onClickLoginButton() {
        // 3 - Launch Sign-In Activity when user clicked on Login Button
        this.startSignInActivity();
    }
 */

    private void startSignInActivity(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), //EMAIL
                                        new AuthUI.IdpConfig.GoogleBuilder().build(), //GOOGLE
                                        new AuthUI.IdpConfig.FacebookBuilder().build())) // FACEBOOK
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_logo_login)
                        .build(),
                RC_SIGN_IN);
    }

}
