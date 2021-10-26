package sakref.yohan.go4lunch.utils;



import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.ui.ConnectionActivity;
import sakref.yohan.go4lunch.ui.SettingsActivity;
import sakref.yohan.go4lunch.viewmodels.WorkmatesViewModel;

public class NotificationReceiver extends BroadcastReceiver {

    private static String TAG = "NotificationReceiver";
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private MutableLiveData<List<Workmates>> mWorkmates = new MutableLiveData<>();

    @Override
    public void onReceive(Context context, Intent intent) {



        NotificationManager managerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intentNotification = new Intent(context, ConnectionActivity.class);
        intentNotification.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 152, intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);

        WorkmatesHelper.getWorkmate(user.getUid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){

                    String restaurantUid = task.getResult().getString("restaurantJoined");
                    String restaurantName = task.getResult().getString("restaurantName");
                    String restaurantAddress = task.getResult().getString("restaurantAddress");
                    String userUid = task.getResult().getString("uid");
                    String userName = task.getResult().getString("workmatesName");

                    String contentTitle;

                    if(restaurantUid.equals("") && restaurantName.equals("")){
                       contentTitle = "Vous n'avez pas encore choisi de restaurant pour ce midi!" ;
                    }else{
                        contentTitle = restaurantName + ", " + restaurantAddress;
                        WorkmatesHelper.getAllJoiningWorkmate(restaurantUid).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<Workmates> listWorkmates = new ArrayList<Workmates>();
                                String notificationText = "";

                                for (QueryDocumentSnapshot document:task.getResult())
                                {
                                    listWorkmates.add(document.toObject(Workmates.class));
                                }
                                mWorkmates.setValue(listWorkmates);

                                for(int i = 0; i < mWorkmates.getValue().size(); i++ ){
                                    Log.d(TAG, "onComplete: NotificationReceiver : " + i + " -  " + mWorkmates.getValue().get(i).getWorkmatesName());
                                    notificationText += mWorkmates.getValue().get(i).getWorkmatesName();
                                    if (i < mWorkmates.getValue().size() - 1) notificationText += ", ";
                                }

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "0")
                                        .setSmallIcon(R.drawable.bowl_logo)
                                        .setContentTitle(contentTitle)
                                        .setContentText(notificationText)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        // Set the intent that will fire when the user taps the notification
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true);

                                managerCompat.notify( 1, builder.build());

                            }
                        });
                    }
                }
            }
        });
    }
}
