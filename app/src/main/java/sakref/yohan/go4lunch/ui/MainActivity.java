package sakref.yohan.go4lunch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.SupportMapFragment;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.ActivityConnectionBinding;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private final String TAG = "connection";
    private ActivityConnectionBinding binding;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = ActivityConnectionBinding.inflate(getLayoutInflater());
        binding.setActivity(this);
        View view = binding.getRoot();
        setContentView(view);

    }

    @Override
    protected void onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        super.onStart();
    }

    // GOOGLE SIGN IN CONFIG -----------------------------------------------------------------------
    public void signInGoogle() {
        mAuth = FirebaseAuth.getInstance();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("357917711083-1r19llakmll6vuegenf728e9c60i8fnc.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
        Intent sign = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(sign, RC_SIGN_IN);

    }
/**
    public void signInFacebook() {
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        //   binding.login_facebook.setReadPermissions("email", "public_profile");
        //   binding.login_facebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
        //        @Override
        //        public void onSuccess(LoginResult loginResult) {

          handleFacebookAccessToken(loginResult.getAccessToken());
        Log.d(TAG, "facebook:onSuccess:" + loginResult);
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient = GoogleSignIn
                .getClient(MainActivity.this, gso);
        googleSignInClient.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
        Toast.makeText(MainActivity.this,
                "You successfully signed-in ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "facebook:onCancel");
        // ...
    }

    @Override
    public void onError(FacebookException error) {
        Log.d(TAG, "facebook:onError", error);
        // ...
    }
});


        }

 */
private void handleFacebookAccessToken(AccessToken token){
        Log.d(TAG,"handleFacebookAccessToken:"+token);

        AuthCredential credential=FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
@Override
public void onComplete(@NonNull Task<AuthResult> task){
        if(task.isSuccessful()){
        // Sign in success, update UI with the signed-in user's information
        Log.d(TAG,"signInWithCredential:success");
        FirebaseUser user=mAuth.getCurrentUser();

        }else{
        // If sign in fails, display a message to the user.
        Log.w(TAG,"signInWithCredential:failure",task.getException());
        Toast.makeText(MainActivity.this,"Authentication failed.",
        Toast.LENGTH_SHORT).show();

        }
        }
        });
        }


private void startSignInActivity(){
        startActivityForResult(
        AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setTheme(R.style.LoginTheme)
        .setAvailableProviders(
        Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), //EMAIL
        new AuthUI.IdpConfig.GoogleBuilder().build(), //GOOGLE
        new AuthUI.IdpConfig.FacebookBuilder().build())) // FACEBOOK
        .setIsSmartLockEnabled(false,true)
        .setLogo(R.drawable.ic_logo_login)
        .build(),
        RC_SIGN_IN);
        }

public void toaster(){
        Toast.makeText(getApplicationContext(),"Clicked 2",Toast.LENGTH_SHORT).show();
        }

        }
