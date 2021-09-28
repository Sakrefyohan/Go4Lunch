package sakref.yohan.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.databinding.ActivityMainBinding;
//import sakref.yohan.go4lunch.databinding.ActivitySettingBinding;
import sakref.yohan.go4lunch.databinding.ActivitySettingBinding;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import androidx.core.app.NotificationCompat;

import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

public class SettingsActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "0";
    private static final String TAG = "SettingsActivity";
    private static final int notificationId = 1;
    private ActivitySettingBinding binding;
    private WorkmatesViewModel workmateViewModel;
    private Workmates workmates;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        workmateViewModel = new ViewModelProvider(this).get(WorkmatesViewModel.class);
        user = FirebaseAuth.getInstance().getCurrentUser();

        //initDB();

        initListener();

    }



    private void initListener(){
        binding.notificationSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    setNotification();

                }
                else{
                    Toast.makeText(SettingsActivity.this, "Notification deactivated", Toast.LENGTH_SHORT).show();
                    alarmManager.cancel(pendingIntent);
                    Log.d(TAG, "onCheckedChanged: "+ alarmManager);
                }
            }
        });

        binding.backButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
/*
    private void initDB(){
        workmateViewModel.getCurrentUserFromDB(user.getUid()).observe(this, workmate -> {
            this.workmate = workmate;
            if(workmate.getNotification())
                binding.notificationSwitchButton.setChecked(true);
        });
    }

    public void showWorkmatesEatingNotification(){

        workmateViewModel.getAllUserFromDB(true).observe(this, workmateList -> {
            StringBuilder workmateEating = new StringBuilder("will eat here.");
            for (int i = 0; i<workmateList.size();i++){
                if(workmate.getCurrentRestaurant().equals(workmateList.get(i).getCurrentRestaurant()) && !workmate.getName().equals(workmateList.get(i).getName())){
                    workmateEating.insert(0, workmateList.get(i).getName() + " ,");
                }
            }
            scheduleNotification(getNotification(workmateEating.toString()));
        });
    }
*/



    public void setNotification(){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, ConnectionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(SettingsActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.bowl_logo)
                .setContentTitle("A table, pensez à choisir un restaurant!")
                .setContentText("Avez-vous choisis un restaurant pour ce midi ?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SettingsActivity.this);
        managerCompat.notify(notificationId, builder.build());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 26);

        long futureInMillis = SystemClock.elapsedRealtime() + 5000;

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);



    }


}