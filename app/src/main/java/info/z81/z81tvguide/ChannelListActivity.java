package info.z81.z81tvguide;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
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
    private String filterString;
    TVProgram tvProgram;
    ChannelAdapter adapter;
    SharedPreferences  favoriteChannelListPreference;
    private Tracker mTracker;
    private Integer InitialChannelPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Z81TVGuide application = (Z81TVGuide) getApplication();
        mTracker = application.getDefaultTracker();

        tvProgram =  ((Z81TVGuide) getApplication()).tvProgram;
        setContentView(R.layout.activity_channel_list);
        favoriteChannelListPreference = MainActivity.favoriteChannelListPreference;

        String currentChannelId = getIntent().getStringExtra(MainActivity.const_programListIndex);
        if (currentChannelId==null)
        {
            InitialChannelPosition = 0;
        }
        else
        {
            InitialChannelPosition = tvProgram.GetProgramListIndex(currentChannelId);
        }


        updateListView();
        handleIntent(getIntent());

    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Image~ChannelListActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_channel_list, menu);

        inflater.inflate(R.menu.onechannel_option, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            searchView = (SearchView) menu.findItem(R.id.search).getActionView();

            // Assumes current activity is the searchable activity
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
            searchView.setQueryHint(getString(R.string.SearchQueryHint));

            searchView.setOnQueryTextListener(
                    new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            doMySearch(newText);

                            if (newText == null || newText.equals("")) {
                                doMySearch(newText);
                                return true;
                            } else

                                return false;
                        }

                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            doMySearch(query);
                            return false;
                        }

                    });
            searchView.setOnQueryTextFocusChangeListener(
                    new SearchView.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {

                            if (!hasFocus) {
                                doMySearch("");
                                v.clearFocus();
                            }

                        }
                    });



            searchView.setOnCloseListener(
                    new SearchView.OnCloseListener() {
                        @Override
                        public boolean onClose() {

                            doMySearch("");
                            return false;
                        }
                    });



        }


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
        adapter = new ChannelAdapter(this, tvProgram, filterString);
        lv1.setAdapter(adapter);
        SetListViewListeners();
        if (InitialChannelPosition!=null) {
            lv1.setSelection(InitialChannelPosition);
        }
        InitialChannelPosition = null;
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
                OpenOneChannelProgramActivity(position);
            }
        });
    }

    public void OpenOneChannelProgramActivity(int ItemIndex)
    {
        Intent intent = new Intent(ChannelListActivity.this, OneChannelProgramActivity.class);
        intent.putExtra(MainActivity.const_programListIndex, ItemIndex);
        intent.putExtra(MainActivity.const_filterString, filterString);

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

    public void onNumberDigitalClick(View v){
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("RowClick")
                .setAction("onNumberDigitalClick")
                .build());
        Integer  tag1  =  (Integer)v.getTag();
        currentProgram = tvProgram.GetItem(tvProgram.GetProgramListIndex(tag1.toString()));
        InputText(m_Text);
    }


    private void InputText(String value)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Введите номер канала ");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        m_Text = currentProgram.getDigitalNumberLabel(false);
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
                    currentProgram.setDigitalNumber(-1);
                    adapter.notifyDataSetChanged();
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

    private void doMySearch(String query) {
        filterString = query;
        updateListView();

    }
}
