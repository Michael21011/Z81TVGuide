package info.z81.z81tvguide;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ChannelListActivity extends ActionBarActivity {

    ChannelList channelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        channelList = MainActivity.channelList;
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void updateListView()    {

        final ListView lv1 = (ListView)findViewById(R.id.listView1);
        ChannelAdapter adapter = new ChannelAdapter(this, channelList);
        lv1.setAdapter(adapter);
        SetListViewListeners();
    }
    protected void SetListViewListeners()    {
        ListView list = (ListView)findViewById(R.id.listView1);


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

                TextView tw = (TextView)findViewById(R.id.channel);
                Toast.makeText(getBaseContext(),ni.ChannelName, Toast.LENGTH_LONG).show();
            }
        });
    }
}
