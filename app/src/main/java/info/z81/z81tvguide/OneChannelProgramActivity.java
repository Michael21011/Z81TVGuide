package info.z81.z81tvguide;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

public class OneChannelProgramActivity extends ActionBarActivity {
    
    private ProgramList programList;
    private int programListIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        programListIndex = getIntent().getIntExtra(MainActivity.const_programListIndex, 0);
        programList = MainActivity.tvProgram.GetItem(programListIndex);
        setContentView(R.layout.one_channel_program);
        setTitle(programList.ChannelName);
        updateListView();
    }

    protected void updateListView() {

        final ListView lv1 = (ListView) findViewById(R.id.oneChannelProgramListView);

        OneChannelProgramAdapter adapter = new OneChannelProgramAdapter(this, programList);
        lv1.setAdapter(adapter);
        SetListViewListeners();
        lv1.setSelection(programList.GetCurrentItemIndex(0));

    }

    private void SetListViewListeners() {

    }
}
