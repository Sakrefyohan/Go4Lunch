package sakref.yohan.go4lunch.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.ui.ConnectionActivity;
import sakref.yohan.go4lunch.ui.SettingsActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager managerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intentNotification = new Intent(context, ConnectionActivity.class);
        intentNotification.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 152, intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "0")
                .setSmallIcon(R.drawable.bowl_logo)
                .setContentTitle("A table, pensez à choisir un restaurant!")
                .setContentText("Avez-vous choisis un restaurant pour ce midi ?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        managerCompat.notify( 1, builder.build());
    }
}
