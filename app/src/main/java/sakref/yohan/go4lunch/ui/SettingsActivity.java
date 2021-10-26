package sakref.yohan.go4lunch.ui;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import sakref.yohan.go4lunch.databinding.ActivitySettingBinding;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.utils.NotificationReceiver;
import sakref.yohan.go4lunch.utils.WorkmatesHelper;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    private static final String NOTIFICATION = "notificationActive";
    private ActivitySettingBinding binding;
    public WorkmatesViewModel workmateViewModel;

    private Workmates workmates;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private FirebaseUser user;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        workmateViewModel = new ViewModelProvider(this).get(WorkmatesViewModel.class);
        user = FirebaseAuth.getInstance().getCurrentUser();

        //initDB();
        sharedPreferences = getSharedPreferences(NOTIFICATION, MODE_PRIVATE);
        initListener();



    }



    private void initListener(){
        Boolean isNotificationOn = sharedPreferences.getBoolean(NOTIFICATION,false);
        if(isNotificationOn){
            binding.notificationSwitchButton.setChecked(true);
        }else
            binding.notificationSwitchButton.setChecked(false);


        binding.notificationSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(NOTIFICATION, true);
                editor.apply();
                    setBetterNotification();
                }
                else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(NOTIFICATION, false);
                    editor.apply();
                    clearBetterNotification();
                }
            }
        });

        binding.backButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.buttonEmailText.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(SettingsActivity.this, "Please Verify Email", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, "onClick: Click bouton email " + binding.buttonEmailText.getText().toString());
                    WorkmatesHelper.updateWorkmatEmail(binding.buttonEmailText.getText().toString(), user.getUid());
                    user.updateEmail(email);
                    Toast.makeText(SettingsActivity.this, "Email updated.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.buttonPseudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pseudo = binding.loginPseudoText.getText().toString();
                if(pseudo.isEmpty()){
                    Toast.makeText(SettingsActivity.this, "Please Verify PSEUDO", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, "onClick: click button pseudo : " +  pseudo);
                    WorkmatesHelper.updateWorkmateName(pseudo, user.getUid());
                    Toast.makeText(SettingsActivity.this, "Name Changed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image = binding.buttonImageText.getText().toString();
                if(image.isEmpty()){
                    Toast.makeText(SettingsActivity.this, "Please Verify URL.", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, "onClick: click button pseudo : " +  image);
                    WorkmatesHelper.updateWorkmatePicture(image, user.getUid());
                    Toast.makeText(SettingsActivity.this, "New picture Added.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        binding.buttonPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = binding.buttonPasswordText.getText().toString();
                if(password.isEmpty()){
                    Toast.makeText(SettingsActivity.this, "Please Verify Password.", Toast.LENGTH_SHORT).show();
                }else{
                    user.updatePassword(password);
                    Toast.makeText(SettingsActivity.this, "New picture Added.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the object of
                // AlertDialog Builder class
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(SettingsActivity.this);

                // Set the message show for the Alert time
                builder.setMessage("Are you sure you want to delete your profile ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false
                // for when the user clicks on the outside
                // the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name
                // OnClickListener method is use of
                // DialogInterface interface.

                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // When the user click yes button
                                        // then app will close
                                        WorkmatesHelper.deleteWorkmate(user.getUid());
                                        user.delete();
                                        finishAffinity();
                                    }
                                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.
                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // If user click no
                                        // then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();
            }
        });
    }


    // TODO: 12/10/2021 Search for Settings UI  

    public void setBetterNotification(){
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),152,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);


    }

    public void clearBetterNotification(){
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),152,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(alarmManager == null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();


    }


}



