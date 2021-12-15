package sakref.yohan.go4lunch.utils;

import android.content.Context;
import android.util.Log;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.models.Result;
import sakref.yohan.go4lunch.models.Workmates;

public class RestaurantUtils {

    public static String getRatingRestaurant(double result) {

        if (result != 0) {
            if (result == 5 && result > 4.4) {
                return (result + " 🌟🌟🌟");
            } else if (result <= 4.3 && result > 4.1) {
                return (result + " ⭐⭐⭐");
            } else if (result <= 4 && result > 3.1) {
                return (result + " ⭐⭐");
            } else if (result <= 3 && result > 2.1) {
                return (result + " ⭐");
            } else if (result <= 2) {
                return ("☀");
            }

        } else {
            return String.valueOf(R.string.no_note);
        }


        return null;
    }

    public static String getWorkmatesRestaurant(String restaurantName, String workmateName, boolean onRestaurantDetails) {

        if (!onRestaurantDetails) {
            if (restaurantName.equals("")) {
                return (workmateName + " hasn't decided yet");
            } else {
                return (workmateName + " is eating at " + restaurantName);
            }


        } else {

            return (workmateName + " is joining");
        }

    }

    public static int getRestaurantDistance(double latRestau, double longRestau, double lat2, double lon2) {


        double theta = longRestau - lon2;
        double dist = Math.sin(deg2rad(latRestau)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(latRestau)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        dist = dist * 1000;
        return (int) dist;

    }

    //This function converts decimal degrees to radians

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    //This function converts radians to decimal degrees

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
