package sakref.yohan.go4lunch.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.ActivityConnectionBinding;
import sakref.yohan.go4lunch.utils.WorkmatesHelper;
import sakref.yohan.go4lunch.viewmodels.MainViewModel;

import static sakref.yohan.go4lunch.utils.WorkmatesHelper.createUser;

public class ConnectionActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final String CHANNEL_ID = "0";
    private final String TAG = "connection";
    private ActivityConnectionBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userConnected;
    private WorkmatesHelper workmatesHelper;
    private MainViewModel mainViewModel;
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
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        createNotificationChannel();
    }



    @Override
    protected void onStart() {
        user = mAuth.getCurrentUser();
        super.onStart();
        if (user != null ){

            //Check if workmate is on database, if not add it.
            fetchWorkmate();

        } else {
            Log.d(TAG, "onStart: No user Connected");
        }

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
        Intent sign = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(sign, RC_SIGN_IN);

    }

    public void signInFacebook() {
        //Initialize Facebook Login button
        //fetchWorkmate();
        LoginManager.getInstance().logInWithReadPermissions(ConnectionActivity.this, Arrays.asList("email","public_profile", "user_friends"));


    }

    public void signInTwitter (){
        Toast.makeText(this, "Connection à twitter", Toast.LENGTH_SHORT).show();
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
        Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // User is signed in.

                                    String email = authResult.getUser().getEmail();
                                    String displayName = authResult.getUser().getDisplayName();
                                    String uid = authResult.getUser().getUid();
                                    Uri photoUrl = authResult.getUser().getPhotoUrl();


                                    Log.d(TAG, "Twitter : onSuccess: " + email);
                                    Log.d(TAG, "Twitter : onSuccess: " + displayName);
                                    Log.d(TAG, "Twitter : onSuccess: " + uid);
                                    Log.d(TAG, "Twitter : onSuccess: " + photoUrl);

                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                    // The OAuth secret can be retrieved by calling:
                                    // authResult.getCredential().getSecret().
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Twitter : onFailure: 1st Else" + e);
                                    // Handle failure.
                                }
                            });
        } else {
            Log.d(TAG, "signInTwitter: Else");
            mAuth
                    .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Log.d(TAG, "signInTwitter : onSuccess: Else OnSuccess ");

                                    String email = authResult.getUser().getEmail();
                                    String displayName = authResult.getUser().getDisplayName();
                                    String uid = authResult.getUser().getUid();
                                    Uri photoUrl = authResult.getUser().getPhotoUrl();


                                    Log.d(TAG, "Twitter : Else / OnSuccess: " + email);
                                    Log.d(TAG, "Twitter : Else / OnSuccess: " + displayName);
                                    Log.d(TAG, "Twitter : Else / OnSuccess: " + uid);
                                    Log.d(TAG, "Twitter : Else / OnSuccess: " + photoUrl);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "signInTwitter : onFailure: Else OnFailure : " + e);
                                    // Handle failure.
                                }
                            });
        }
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
                                user = mAuth.getCurrentUser();
                                if (user.isEmailVerified()) {
                                    fetchWorkmate();
                                } else {
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(ConnectionActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
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
                            finish();
                            fetchWorkmate();


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
                                fetchWorkmate();
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


    public void signup(){

        String email = binding.loginEmailText.getText().toString();
        String password = binding.passwordPasswordText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();
                            fetchWorkmate();
                            user.sendEmailVerification();

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

    public void fetchWorkmate(){

        WorkmatesHelper.getWorkmate(user.getUid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String workmatesName;
                    Uri userPhotoUrl = user.getPhotoUrl();
                    String photo;
                    String email = user.getEmail();

                    if (!task.getResult().exists()) {
                        Log.d(TAG, "onComplete: Workmate doesn't exist");


                        if(userPhotoUrl == null){
                            Log.d(TAG, "onComplete: Workmate Have no picture adding one ... ");
                            photo = getString(R.string.missing_Avatar);
                        }else{
                            photo = user.getPhotoUrl().toString();
                            Log.d(TAG, "onComplete: Workmate Have a picture  :  " + photo);
                        }

                        if(user.getDisplayName() == null || user.getDisplayName() == ""){
                           workmatesName = "Ano Nyme";
                            Log.d(TAG, "onComplete: Workmate Have no name, creating one ... ");
                        }else{
                            workmatesName = user.getDisplayName();
                            Log.d(TAG, "onComplete: Workmate Have a name :  " + workmatesName);
                        }


                        createUser(user.getUid(), workmatesName, email, photo, "", "");

                        
                    }else{
                        Log.d(TAG, "onComplete: Workmate Already Exist ... Cheking info : ");
                        String username = task.getResult().getString("workmatesName");
                        if(username == null || username == ""){
                            workmatesName = "Ano Nyme";
                            Log.d(TAG, "onComplete: Workmate Have no name, creating one ... ");
                        }else{
                            workmatesName = username;
                            Log.d(TAG, "onComplete: Workmate Have a name :  " + workmatesName);
                        }
                        String restaurantJoined = task.getResult().getString("restaurantJoined");
                        String restaurantName = task.getResult().getString("restaurantName");
                        photo = task.getResult().getString("urlPicture");
                    }
                    Log.d(TAG, "onComplete: Workmates 2  / " + mainViewModel.getUserAvatar() + " / " + mainViewModel.getUserMail() + " / " + mainViewModel.getUserName());



                    Intent intentUser = new Intent(getApplicationContext(), MainActivity.class);
                    intentUser.putExtra("username", workmatesName);
                    intentUser.putExtra("email", email);
                    intentUser.putExtra("photoUrl", photo);
                    intentUser.putExtra("uid", user.getUid());
                    startActivity(intentUser);
                }
            }
        });

        WorkmatesHelper.getWorkmate(user.getUid()).addOnFailureListener(this.onFailureListener());


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Rappel de choix";
            String description = "Rappel de choix de restaurant";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // --------------------
    // ERROR HANDLER
    // --------------------

    protected OnFailureListener onFailureListener(){
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "fetchWorkmate : onFailure: " + e);
                Toast.makeText(getApplicationContext(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();
            }
        };
    }


}




