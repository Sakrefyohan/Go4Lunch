package sakref.yohan.go4lunch;

import org.junit.Test;

import static org.junit.Assert.*;

import sakref.yohan.go4lunch.models.Workmates;
import sakref.yohan.go4lunch.utils.RestaurantUtils;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {

    private static final String TAG = "WorkmateUnitTest";

    Workmates workmate1;
    Workmates workmate2;
    Workmates workmate3;
    Workmates workmate4;
    Workmates workmate5;

    public void createMockWorkmates(){
        workmate1 = new Workmates("1","pierre","pierre@pierre.fr","R.drawable.ic_no_image","1","Restau1");
        workmate2 = new Workmates("2","Paul","Paul@Paul.fr","R.drawable.ic_no_image","2","Restau2");
        workmate3 = new Workmates("3","Jack","Jack@Jack.fr","R.drawable.ic_no_image","3","Restau3");
        workmate4 = new Workmates("4","René","René@René.fr","R.drawable.ic_no_image","2","Restau2");
        workmate5 = new Workmates("5","Richard","Richard@Richard.fr","R.drawable.ic_no_image","","");


    }

    @Test
    public void restaurant_Calc_Rating() {
        double result = 4;
        assertEquals("4.0 ⭐⭐", RestaurantUtils.getRatingRestaurant(result));

    }

    @Test
    public void workmate_has_not_decied_yet(){
        createMockWorkmates();
        assertEquals("Richard hasn't decided yet", RestaurantUtils.getWorkmatesRestaurant(workmate5.getRestaurantName(), workmate5.getWorkmatesName(),false));
    }

    @Test
    public void workmate_has_decided(){
        createMockWorkmates();
        assertEquals("René is eating at Restau2", RestaurantUtils.getWorkmatesRestaurant(workmate4.getRestaurantName(), workmate4.getWorkmatesName(),false));
    }

    @Test
    public void onPage_RestaurantDetails_workmate_is_joining() {
        createMockWorkmates();
        assertEquals("Jack is joining", RestaurantUtils.getWorkmatesRestaurant(workmate3.getRestaurantName(), workmate3.getWorkmatesName(),true));
    }

    @Test
    public void restaurant_Calc_distance(){
        double latRestau = 45.7295887, longRestau = 4.831189600000001, lat2 = 45.734995, lon2 = 4.8174367;
        int distance = RestaurantUtils.getRestaurantDistance(latRestau, longRestau, lat2, lon2);
        assertEquals(1225, distance);
    }
}