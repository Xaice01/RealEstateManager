package com.openclassrooms.realestatemanager;
import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class NetworkUtilsTest {

    @Test
    public void testInternetAvailable() {
        Context context = ApplicationProvider.getApplicationContext();

        // Assuming the device/emulator has internet connection
        boolean isConnected = Utils.isInternetAvailable(context);
        assertTrue("Device should be connected to the internet", isConnected);
    }

}
