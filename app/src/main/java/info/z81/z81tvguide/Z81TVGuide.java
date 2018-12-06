package info.z81.z81tvguide;

        import android.app.Application;
        import android.content.Context;

     /*   import com.google.android.gms.analytics.GoogleAnalytics;
        import com.google.android.gms.analytics.Logger;
        import com.google.android.gms.analytics.Tracker;
        */

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app, such as
 * the {@link Tracker}.
 */
public class Z81TVGuide extends Application {
   // private Tracker mTracker;
    private static Context context;
    public TVProgram tvProgram;

    public void onCreate() {
        super.onCreate();
        Z81TVGuide.context = getApplicationContext();

    }

    public static Context getAppContext() {
        return Z81TVGuide.context;
    }

}