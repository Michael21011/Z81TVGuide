package info.z81.z81tvguide;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Date;

/**
 * Created by michael on 08.10.15.
 */

public class ProgramList {
    private ArrayList<ProgramItem> list;
    public String ChannelId;
    public String ChannelName;



    public ProgramList()
    {
        list = new ArrayList<>();
    }
    public  void Clear() {
        list.clear();

    }



    public void Add(String Title, Date DateFrom) {
        ProgramItem programItem = new ProgramItem();
        programItem.Title = Title;
        programItem.DateStart = DateFrom;
        list.add(programItem);

    }

    public int Count() {
        return list.size();
    }

    public ProgramItem GetItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    public long GetItemId(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0).hashCode();
    }


}
