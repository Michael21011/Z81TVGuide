package info.z81.z81tvguide;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;


public class ChannelListActivity extends ActionBarActivity {

    ChannelList channelList;
    ChannelAdapter adapter;
    SharedPreferences  favoriteChannelListPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       channelList = MainActivity.channelList;
        setContentView(R.layout.activity_channel_list);
        favoriteChannelListPreference = MainActivity.favoriteChannelListPreference;

        updateListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_channel_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.channels_favorite_all:
                MarkAllChannels(true);
                return true;
         /*   case R.id.action_settings:
            	sendMessage(null);
                return true;
          */  case R.id.channels_favorite_none:
                MarkAllChannels(false);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void MarkAllChannels(boolean Starred) {

    }

    protected void updateListView()    {

        final ListView lv1 = (ListView)findViewById(R.id.listView1);
        adapter = new ChannelAdapter(this, channelList, favoriteChannelListPreference);
        lv1.setAdapter(adapter);
        SetListViewListeners();
    }
    protected void SetListViewListeners()    {
        ListView list = (ListView)findViewById(R.id.listView1);
        final ImageView starView = (ImageView) findViewById(R.id.star);

      /*  starView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ChannelItem ni = (ChannelItem)channelList.GetItem(position);

                    TextView tw = (TextView)findViewById(R.id.channel);
                    Toast.makeText(getBaseContext(),ni.ChannelName, Toast.LENGTH_LONG).show();
            }

            }

        );
        */
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tw = (TextView)findViewById(R.id.channel);
                Toast.makeText(getBaseContext(), tw.getText(), Toast.LENGTH_LONG).show();
                // ?????????? "??????", ????? ????????? ??????? ?????, ?????
                // onListItemClick ?????? ?? ?????????
                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ChannelItem ni = (ChannelItem)channelList.GetItem(position);
               // TextView tw = (TextView)findViewById(R.id.channel);

                ni.setStared(!ni.getStared());

                adapter.notifyDataSetChanged();



            }
        });
    }
}
