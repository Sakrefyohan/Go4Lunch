package sakref.yohan.go4lunch.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Objects;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.ActivitySettingBinding;
import sakref.yohan.go4lunch.utils.NotificationReceiver;
import sakref.yohan.go4lunch.utils.WorkmatesHelper;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

public class SettingsActivity extends AppCompatActivity {
    private static final String NOTIFICATION = "notificationActive";
    private ActivitySettingBinding binding;
    public WorkmatesViewModel workmateViewModel;
    private AlarmManager alarmManager;
    private FirebaseUser user;
    SharedPreferences sharedPreferences;
    Intent intentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intentUser = new Intent(getApplicationContext(), MainActivity.class);
        workmateViewModel = new ViewModelProvider(this).get(WorkmatesViewModel.class);
        user = FirebaseAuth.getInstance().getCurrentUser();

        //initDB();
        sharedPreferences = getSharedPreferences(NOTIFICATION, MODE_PRIVATE);
        initListener();


    }


    private void initListener() {
        boolean isNotificationOn = sharedPreferences.getBoolean(NOTIFICATION, false);
        binding.notificationSwitchButton.setChecked(isNotificationOn);


        binding.notificationSwitchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isChecked) {
                editor.putBoolean(NOTIFICATION, true);
                editor.apply();
                setBetterNotification();
            } else {
                editor.putBoolean(NOTIFICATION, false);
                editor.apply();
                clearBetterNotification();
            }
        });


        binding.backButtonSettings.setOnClickListener(v -> {
            String email = Objects.requireNonNull(binding.buttonEmailText.getText()).toString();
            String pseudo = Objects.requireNonNull(binding.loginPseudoText.getText()).toString();
            String image = Objects.requireNonNull(binding.buttonImageText.getText()).toString();
            String password = Objects.requireNonNull(binding.buttonPasswordText.getText()).toString();
            if (email.isEmpty()) {
                if (pseudo.isEmpty()) {
                    if (image.isEmpty()) {
                        if (password.isEmpty()) {
                            finish();
                        }
                    }
                }
            }
            setResult(Activity.RESULT_OK, intentUser);
            finish();


        });


        binding.buttonEmail.setOnClickListener(view -> {

            String email = Objects.requireNonNull(binding.buttonEmailText.getText()).toString();
            if (email.isEmpty()) {
                Toast.makeText(SettingsActivity.this, (R.string.please_verify_email), Toast.LENGTH_SHORT).show();
            } else {
                WorkmatesHelper.updateWorkmatEmail(binding.buttonEmailText.getText().toString(), user.getUid());
                user.updateEmail(email);
                Toast.makeText(SettingsActivity.this, (R.string.email_updated), Toast.LENGTH_SHORT).show();
                reloadUser();
            }
        });

        binding.buttonPseudo.setOnClickListener(view -> {
            String pseudo = Objects.requireNonNull(binding.loginPseudoText.getText()).toString();
            if (pseudo.isEmpty()) {
                Toast.makeText(SettingsActivity.this, (R.string.please_verify_pseudo), Toast.LENGTH_SHORT).show();
            } else {
                WorkmatesHelper.updateWorkmateName(pseudo, user.getUid());
                Toast.makeText(SettingsActivity.this, (R.string.name_changed), Toast.LENGTH_SHORT).show();
                reloadUser();
            }
        });

        binding.buttonImage.setOnClickListener(view -> {
            String image = Objects.requireNonNull(binding.buttonImageText.getText()).toString();
            if (image.isEmpty()) {
                Toast.makeText(SettingsActivity.this, (R.string.please_verify_url), Toast.LENGTH_SHORT).show();
            } else {
                WorkmatesHelper.updateWorkmatePicture(image, user.getUid());
                Toast.makeText(SettingsActivity.this, (R.string.new_picture_added), Toast.LENGTH_SHORT).show();
                reloadUser();

            }
        });

        binding.buttonPassword.setOnClickListener(view -> {
            String password = Objects.requireNonNull(binding.buttonPasswordText.getText()).toString();
            if (password.isEmpty()) {
                Toast.makeText(SettingsActivity.this, (R.string.please_verify_password), Toast.LENGTH_SHORT).show();
            } else {
                user.updatePassword(password);
                Toast.makeText(SettingsActivity.this, (R.string.new_picture_added), Toast.LENGTH_SHORT).show();
                reloadUser();
            }
        });

        binding.buttonDelete.setOnClickListener(view -> {
            // Create the object of
            // AlertDialog Builder class
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(SettingsActivity.this);

            // Set the message show for the Alert time
            builder.setMessage(R.string.are_you_sure_you_want_to_delete_your_profile);

            // Set Alert Title
            builder.setTitle(R.string.alert);

            // Set Cancelable false
            // for when the user clicks on the outside
            // the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name
            // OnClickListener method is use of
            // DialogInterface interface.

            builder
                    .setPositiveButton(
                            R.string.yes,
                            (dialog, which) -> {

                                // When the user click yes button
                                // then app will close
                                WorkmatesHelper.deleteWorkmate(user.getUid());
                                user.delete();
                                finishAffinity();
                            });

            // Set the Negative button with No name
            // OnClickListener method is use
            // of DialogInterface interface.
            builder
                    .setNegativeButton(
                            R.string.no,
                            (dialog, which) -> {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
        });
    }

    public void setBetterNotification() {
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 152, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


    }

    public void clearBetterNotification() {
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);

        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 152, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, (R.string.alarm_cancelled), Toast.LENGTH_SHORT).show();


    }

    public void reloadUser() {
        WorkmatesHelper.getWorkmate(user.getUid()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                intentUser.putExtra("username", task.getResult().getString("workmatesName"));
                intentUser.putExtra("email", task.getResult().getString("workmatesEmail"));
                intentUser.putExtra("photoUrl", task.getResult().getString("urlPicture"));
                intentUser.putExtra("uid", user.getUid());

            }
        });
    }

    @Override
    public void onBackPressed() {

        String email = Objects.requireNonNull(binding.buttonEmailText.getText()).toString();
        String pseudo = Objects.requireNonNull(binding.loginPseudoText.getText()).toString();
        String image = Objects.requireNonNull(binding.buttonImageText.getText()).toString();
        String password = Objects.requireNonNull(binding.buttonPasswordText.getText()).toString();
        if (email.isEmpty()) {
            if (pseudo.isEmpty()) {
                if (image.isEmpty()) {
                    if (password.isEmpty()) {
                        finish();
                    }
                }
            }
        }
        setResult(Activity.RESULT_OK, intentUser);
        finish();
    }


}



