package info.z81.z81tvguide;

import android.app.Application;
import android.os.Bundle;


import static info.z81.z81tvguide.Z81TVGuide.getAppContext;

public class Analitic {
   // private FirebaseAnalytics mFirebaseAnalytics;



    private static final Analitic ourInstance = new Analitic();

    public static Analitic getInstance() {
        return ourInstance;
    }

    private Analitic() {
       // mFirebaseAnalytics = FirebaseAnalytics.getInstance(getAppContext());



    }

    public void NewScreen(String screenName)
    {
        /*
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "screen");
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, screenName);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, params);
*/


    };

    public void NewClick(String activityName, String clickName)
    {
        /*
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, activityName);
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, clickName);
        mFirebaseAnalytics.logEvent("Click",  params);
*/
    };

    public void NewEvent(String eventName)
    {/*
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Event");
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, eventName);
        mFirebaseAnalytics.logEvent("Event",  params);
        */
    };

}
