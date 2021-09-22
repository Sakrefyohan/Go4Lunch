package sakref.yohan.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.CompoundButton;

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

        //initListener();

    } 
/*
    private void initListener(){
        binding.notificationSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                workmateViewModel.updateNotification(isChecked);
                if(isChecked) {
                    if (workmate != null && workmate.getCurrentRestaurant() != null){
                        showWorkmatesEatingNotification();
                    }
                    else{
                        binding.notificationSwitchButton.setChecked(false);
                    }
                }
                else{
                    scheduleNotification(getNotification(""));
                    alarmManager.cancel(pendingIntent);
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

    private void scheduleNotification(Notification notification) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 26);

        long futureInMillis = SystemClock.elapsedRealtime() + 5000;

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String workmateEatingHere) {
        if(workmateEatingHere.equals("will eat here."))
            workmateEatingHere = getString(R.string.no_one_eating);
        else
            workmateEatingHere = workmateEatingHere.replace(",will eat here.",getString(R.string.will_eat_here));

        String name = getString(R.string.channel_name);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, name)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Go4Lunch")
                .setContentText(workmateEatingHere)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder.build();
    }*/
}