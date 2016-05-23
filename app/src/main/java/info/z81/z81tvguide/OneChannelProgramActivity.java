package info.z81.z81tvguide;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OneChannelProgramActivity extends ActionBarActivity {
    
    private ProgramList programList;
    private int programListIndex;
    private int programItemIndex;
    private ContextMenu popupMenu;
    private Tracker mTracker;
    private String filterString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterString = new String("");

        Z81TVGuide application = (Z81TVGuide) getApplication();
        mTracker = application.getDefaultTracker();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        programListIndex = getIntent().getIntExtra(MainActivity.const_programListIndex, 0);
        programList =    ((Z81TVGuide) getApplication()).tvProgram.GetFilteredItem(programListIndex);
        filterString =getIntent().getStringExtra(MainActivity.const_filterString);
        setContentView(R.layout.one_channel_program);
        setTitle(programList.ChannelName);
        updateListView();
        handleIntent(getIntent());
        registerForContextMenu(findViewById(R.id.oneChannelProgramListView));
        //programItemIndex=-1;

   /*     MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.one_channel_program_popupmenu, popupMenu);
        */
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
    public void onResume() {
        super.onResume();
        //Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Image~OneChannelProgramActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    private void doMySearch(String query) {
        filterString = query;
        updateListView();

    }

    protected void SetListViewListeners()    {
        ListView list = (ListView)findViewById(R.id.oneChannelProgramListView);


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                programItemIndex = position;
                return false;
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                programItemIndex = position;
            }


    });
    }

    protected void updateListView() {

        final ListView lv1 = (ListView) findViewById(R.id.oneChannelProgramListView);

        OneChannelProgramAdapter adapter = new OneChannelProgramAdapter(this, programList, filterString);
        lv1.setAdapter(adapter);
        SetListViewListeners();
     //    lv1.setOnCreateContextMenuListener(this);
        lv1.setSelection(programList.GetCurrentItemIndex(0));
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Event")
                .setAction("updateListView")
                .build());


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
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
searchView.setQuery(filterString, false);
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
            searchView.setOnSystemUiVisibilityChangeListener(
                    new SearchView.OnSystemUiVisibilityChangeListener() {
                        @Override
                    public void onSystemUiVisibilityChange(int i){
                            doMySearch("");
                        }
                    }
            );
            searchView.setOnQueryTextFocusChangeListener(
                    new SearchView.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {

                            if (!hasFocus && !v.isFocusable()) {
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
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
      //  appData.putBoolean(this.JARGON, true);
        startSearch(null, false, appData, false);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //Do the initial here
        //menu = popupMenu;
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.one_channel_program_popupmenu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
       // AdapterViewCompat.AdapterContextMenuInfo info = (AdapterViewCompat.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_program_copy:
                CopyItem();
                return true;
            case R.id.menu_program_reminder:
                ReminderItem();
                return true;
            case R.id.menu_program_searchInInternet:
                SearchInInternetItem();
                return true;
            case R.id.menu_program_share:
                ShareItem();
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    private void ShareItem(){

        final ListView lv1 = (ListView) findViewById(R.id.oneChannelProgramListView);

        int position=programItemIndex;
        ProgramItem pi = programList.GetItem(position);
     String t = String.format("Программа %1$s на канале %3$s. Начало %2$s",pi.Title, new SimpleDateFormat("dd.MM.yyyy в HH:mm").format(pi.DateStart), programList.ChannelName);
       // Toast.makeText(getBaseContext(), String.format("%s", t), Toast.LENGTH_LONG).show();
        Intent sharingIntent = new Intent(android.content.Intent. ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Телепередача  ");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, t);
        startActivity(Intent.createChooser(sharingIntent, "Отправить через"));
    }

    private void SearchInInternetItem(){

        final ListView lv1 = (ListView) findViewById(R.id.oneChannelProgramListView);

        int position=programItemIndex;
        ProgramItem pi = programList.GetItem(position);
        String t = String.format("%1$s",pi.Title, new SimpleDateFormat("dd.MM.yyyy в HH:mm").format(pi.DateStart), programList.ChannelName);
        // Toast.makeText(getBaseContext(), String.format("%s", t), Toast.LENGTH_LONG).show();


        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH );
        intent.putExtra(SearchManager.QUERY, t);
        startActivity(intent);

    }

    private void CopyItem(){

        final ListView lv1 = (ListView) findViewById(R.id.oneChannelProgramListView);

        int position=programItemIndex;
        ProgramItem pi = programList.GetItem(position);
        String t = String.format("Программа %1$s на канале %3$s. Начало %2$s",pi.Title, new SimpleDateFormat("dd.MM.yyyy в HH:mm").format(pi.DateStart), programList.ChannelName);
        // Toast.makeText(getBaseContext(), String.format("%s", t), Toast.LENGTH_LONG).show();
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip;
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            myClip = ClipData.newPlainText("text", t);
            myClipboard.setPrimaryClip(myClip);
        }
    }

    private void ReminderItem(){

        final ListView lv1 = (ListView) findViewById(R.id.oneChannelProgramListView);

        int position=programItemIndex;
        ProgramItem pi = programList.GetItem(position);
        String t = String.format("Программа %1$s на канале %3$s. Начало %2$s",pi.Title, new SimpleDateFormat("dd.MM.yyyy в HH:mm").format(pi.DateStart), programList.ChannelName);
        // Toast.makeText(getBaseContext(), String.format("%s", t), Toast.LENGTH_LONG).show();
        Calendar cal = Calendar.getInstance();
        cal.setTime(pi.DateStart);

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", pi.Title);
        startActivity(intent);

    }





}
