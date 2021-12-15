package sakref.yohan.go4lunch.ui;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn;
import static com.adevinta.android.barista.interaction.BaristaEditTextInteractions.writeTo;
import static com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep;

import sakref.yohan.go4lunch.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ConnectionActivityTest {

    @Rule
    public ActivityTestRule<ConnectionActivity> mActivityTestRule = new ActivityTestRule<>(ConnectionActivity.class);
    // https://stackoverflow.com/questions/31752303/espresso-startactivity-that-depends-on-intent/32227345#32227345
    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");
    @After
    public void logUserOut(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

    @Test
    public void connectionTest() {

        writeTo(R.id.password_password_text, "neyiy76990@nefacility.com");
        writeTo(R.id.login_email_text, "neyiy76990@nefacility.com");
        assertDisplayed("Sign in");
    }
}
