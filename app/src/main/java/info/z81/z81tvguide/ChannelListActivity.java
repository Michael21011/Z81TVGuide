package info.z81.z81tvguide;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ChannelListActivity extends ActionBarActivity {

    private String m_Text = "";
    private ProgramList currentProgram;
    TVProgram tvProgram;
    ChannelAdapter adapter;
    SharedPreferences  favoriteChannelListPreference;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Z81TVGuide application = (Z81TVGuide) getApplication();
        mTracker = application.getDefaultTracker();

        tvProgram = MainActivity.tvProgram;
        setContentView(R.layout.activity_channel_list);
        favoriteChannelListPreference = MainActivity.favoriteChannelListPreference;


        updateListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Image~ChannelListActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_channel_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.action_search:
               // showContentInBackground(null);
                return true;
            case R.id.action_refresh:
                //showContentInBackground(null);
                return true;
         /*   case R.id.action_settings:
            	sendMessage(null);
                return true;
          */  case R.id.action_mark_favorite_all:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("MarkAllAsFavorites")
                        .build());
                MarkAllFavorites(true);
                return true;
            case R.id.action_mark_favorite_none:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("MarkAllAsNonFavorites")
                        .build());

                MarkAllFavorites(false);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void MarkAllFavorites(boolean Stared) {
        for (int i=0;i<tvProgram.ChannelCount();i++){
            ProgramList pl = tvProgram.GetItem(i);
            pl.setStared(Stared);
            adapter.notifyDataSetChanged();
        }

    }



    protected void updateListView()    {

        final ListView lv1 = (ListView)findViewById(R.id.listView1);
        adapter = new ChannelAdapter(this, tvProgram);
        lv1.setAdapter(adapter);
        SetListViewListeners();
    }
    protected void SetListViewListeners()    {
        ListView list = (ListView)findViewById(R.id.listView1);


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("RowClick")
                        .setAction("onItemLongClick")
                        .build());
                OpenOneChannelProgramActivity(position);

                return true;
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("RowClick")
                        .setAction("onItemClick")
                        .build());
                currentProgram = tvProgram.GetItem(position);

                InputText(m_Text);

            }
        });
    }

    public void OpenOneChannelProgramActivity(int ItemIndex)
    {
        Intent intent = new Intent(ChannelListActivity.this, OneChannelProgramActivity.class);
        intent.putExtra(MainActivity.const_programListIndex, ItemIndex);
        startActivity(intent);
    }

    public void onStarClick(View v){
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("RowClick")
                .setAction("onStarClick")
                .build());
        Integer  tag1  =  (Integer)v.getTag();
        String tag =  tag1.toString();
        ProgramList pl =  tvProgram.GetProgramList(tag);
        pl.setStared(!pl.isStared());
        adapter.notifyDataSetChanged();

    }

    public void onChannelLogoClick(View v){
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("RowClick")
                .setAction("onChannelLogoClick")
                .build());
        Integer  tag1  =  (Integer)v.getTag();
        OpenOneChannelProgramActivity(tag1);

    }

    private void InputText(String value)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Введите номер канала ");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        m_Text = currentProgram.getDigitalNumberLabel();
        input.setText(m_Text);
        input.selectAll();
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                try {
                    Integer res = Integer.parseInt(m_Text);
                    if (res>0) {
                        currentProgram.setDigitalNumber(res);
                        adapter.notifyDataSetChanged();
                    }
                } catch (NumberFormatException ex) {
                }


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                m_Text = "0";
            }
        });

        builder.show();
    }
}
