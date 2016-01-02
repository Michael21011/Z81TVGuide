package info.z81.z81tvguide;

import android.app.ActionBar;
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

public class ChannelListActivity extends ActionBarActivity {

    private String m_Text = "";
    private ProgramList currentProgram;
    TVProgram tvProgram;
    ChannelAdapter adapter;
    SharedPreferences  favoriteChannelListPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvProgram = MainActivity.tvProgram;
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
                MarkAllFavorites(true);
                return true;
            case R.id.action_mark_favorite_none:
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

        /*
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tw = (TextView) findViewById(R.id.channel);
                Toast.makeText(getBaseContext(), tw.getText(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
        */

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                currentProgram = tvProgram.GetItem(position);

                InputText(m_Text);

            }
        });
    }

    public void onStarClick(View v){
  Integer  tag1  =  (Integer) v.getTag();
    String tag =  tag1.toString();
        ProgramList pl =  tvProgram.GetProgramList(tag);
        pl.setStared(!pl.isStared());
        adapter.notifyDataSetChanged();

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
