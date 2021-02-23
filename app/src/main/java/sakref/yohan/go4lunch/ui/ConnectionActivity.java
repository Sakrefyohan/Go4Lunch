package sakref.yohan.go4lunch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import java.util.Arrays;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.ActivityConnectionBinding;

public class ConnectionActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private final String TAG = "connection";
    private ActivityConnectionBinding binding;
    private FirebaseAuth mAuth;
    private String userConnected;
    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding = ActivityConnectionBinding.inflate(getLayoutInflater());
        binding.setActivity(this);
        View view = binding.getRoot();
        setContentView(view);
        initFacebook();
        mAuth.useAppLanguage();

    }

    @Override
    protected void onStart() {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

       // Log.d(TAG, "onStart: " + user.getEmail() + "/" + user.getDisplayName());
        if (user != null ){
            userConnected = user.getEmail();
            Log.d(TAG, "onStart: Connected with " + userConnected);
            Toast.makeText(this, "Connected with : " + userConnected, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Log.d(TAG, "onStart: No user Connected");
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        // FIREBASE LOGOUT
        mAuth.signOut();
        // GOOGLE LOGOUT
        gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(ConnectionActivity.this, gso);
        googleSignInClient.signOut();
        // FACBOOK LOGOUT
        LoginManager.getInstance().logOut();
        super.onDestroy();
    }

    // GOOGLE SIGN IN CONFIG -----------------------------------------------------------------------
    public void signInGoogle() {
        mAuth.getCurrentUser();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        Intent sign = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(sign, RC_SIGN_IN);

    }

    public void signInFacebook() {
        //Initialize Facebook Login button
        LoginManager.getInstance().logInWithReadPermissions(ConnectionActivity.this, Arrays.asList("email","public_profile", "user_friends"));


    }

    public void signin(){

        String email = binding.loginEmailText.getText().toString();
        String password = binding.passwordPasswordText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {

            Toast.makeText(this, "Please Fill email or password", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user.isEmailVerified()) {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(ConnectionActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(ConnectionActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                            startActivity(new Intent(ConnectionActivity.this, MainActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(ConnectionActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void initFacebook() {

        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);

                        Toast.makeText(ConnectionActivity.this,
                                "Facebook Connection Established ", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(ConnectionActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(ConnectionActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // GOOGLE
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount signInAccount = signInAccountTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider
                        .getCredential(signInAccount.getIdToken(), null);
                mAuth.signInWithCredential(authCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                Toast.makeText(getApplicationContext(),
                                        "Connected by your Google account",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });


            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        // FACEBOOK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void toaster() {
        Toast.makeText(getApplicationContext(), "Clicked 2", Toast.LENGTH_SHORT).show();
    }



    public void signup(){

        String email = binding.loginEmailText.getText().toString();
        String password = binding.passwordPasswordText.getText().toString();

        Log.d(TAG, "Getting Information : " + email + " / " + password );

        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(getApplicationContext(),
                                    "Create a new account",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ConnectionActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}

