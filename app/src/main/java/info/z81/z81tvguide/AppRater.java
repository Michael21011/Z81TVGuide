package info.z81.z81tvguide;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;




/**
 * Created on 26.05.2016.
 * To test it and to tweak the dialog appearence, you can call AppRater.showRateDialog(this, null) from your Activity.
 * Normal use is to invoke AppRater.app_launched(this) each time your activity is invoked (eg. from within the onCreate method).
 * If all conditions are met, the dialog appears.
 * hekko
 */
public class AppRater {
    private final static String APP_TITLE = "Телепрограмма в Минске";
    private final static String APP_PNAME = "info.z81.z81tvguide";

    private final static int DAYS_UNTIL_PROMPT = 14;
    private final static int LAUNCHES_UNTIL_PROMPT = 56;

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("z81tvgudeapprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }

        editor.commit();
    }

    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        Analitic.getInstance().NewScreen("AppRater");
     /*   tracker.send(new HitBuilders.EventBuilder()
                .setCategory("AppRater")
                .setAction("ShowDialog")
                .build());
                */
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle(mContext.getString(R.string.rate));
        int AdditionButtonVisibility = View.VISIBLE;
        if (editor == null)
        {
            AdditionButtonVisibility = View.GONE;
        }
        final boolean CallFromMenu=(editor == null);

        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView tv = new TextView(mContext);
        tv.setText(String.format(mContext.getString(R.string.rate_prompt), APP_TITLE));
        tv.setWidth(240);
        tv.setPadding(4, 0, 4, 10);
        ll.addView(tv);

        Button b1 = new Button(mContext);
        b1.setText(mContext.getString(R.string.rate) +" "+ APP_TITLE);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (CallFromMenu) {
                    Analitic.getInstance().NewClick("AppRater","RateFromAction");
                    /*tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("AppRater")
                            .setAction("RateFromAction")
                            .build());
                            */
                }
                else
                {
                    Analitic.getInstance().NewClick("AppRater","Rate");
                   /* tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("AppRater")
                            .setAction("Rate")
                            .build());
                            */
                }
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                dialog.dismiss();
            }
        });

        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText(mContext.getString(R.string.remind_me_later));
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Analitic.getInstance().NewClick("AppRater","RemindLater");
/*                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("AppRater")
                        .setAction("RemindLater")
                        .build());*/
                if (editor != null) {
                    editor.putLong("launch_count", 0);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        b2.setVisibility(AdditionButtonVisibility);
        ll.addView(b2);

        Button b3 = new Button(mContext);
        b3.setText(mContext.getString(R.string.no_thanks));
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Analitic.getInstance().NewClick("AppRater","DontShowAgain");
/*                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("AppRater")
                        .setAction("DontShowAgain")
                        .build());*/
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        b3.setVisibility(AdditionButtonVisibility);
        ll.addView(b3);

        dialog.setContentView(ll);
        dialog.show();
    }
}
