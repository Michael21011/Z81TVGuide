package info.z81.z81tvguide;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class OneChannelProgramActivity extends ActionBarActivity {
    
    private ProgramList programList;
    private int programListIndex;
    private int programItemIndex;
    private ContextMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        programListIndex = getIntent().getIntExtra(MainActivity.const_programListIndex, 0);
        programList = MainActivity.tvProgram.GetItem(programListIndex);
        setContentView(R.layout.one_channel_program);
        setTitle(programList.ChannelName);
        updateListView();

   /*     MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.one_channel_program_popupmenu, popupMenu);
        */
    }

    protected void SetListViewListeners()    {
        ListView list = (ListView)findViewById(R.id.listView1);


  /*      list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OpenOneChannelProgramActivity(position);

                return true;
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                programItemIndex = position;
            }


    });*/
    }

    protected void updateListView() {

        final ListView lv1 = (ListView) findViewById(R.id.oneChannelProgramListView);

        OneChannelProgramAdapter adapter = new OneChannelProgramAdapter(this, programList);
        lv1.setAdapter(adapter);
        SetListViewListeners();
     //    lv1.setOnCreateContextMenuListener(this);
        lv1.setSelection(programList.GetCurrentItemIndex(0));


    }

   /* @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //Do the initial here
        menu = popupMenu;
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Do the action by id here
        switch (item.getItemId()) {
            case R.id.menu_share:
                ShareItem();
                return true;
            case R.id.menu_createevent:
                //act for sub_menu_item1
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void ShareItem(){
     String t = programList.ChannelName+' '+ programList.GetItem(programListIndex).Title+ ' '+ programList.GetItem(programListIndex).GetDateStartTimeOnly();
        Toast.makeText(getBaseContext(), String.format("%s", t), Toast.LENGTH_LONG).show();
    }
    */

}
