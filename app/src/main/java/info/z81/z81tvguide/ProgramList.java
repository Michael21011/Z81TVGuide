package info.z81.z81tvguide;

import android.content.SharedPreferences;

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
    private Boolean stared;


    public ProgramList() {
        list = new ArrayList<>();
    }

    public void Clear() {
        list.clear();

    }

    public int getChannelLogo() {
        return Utils.getChannelLogoByName(ChannelName);
    }

    public boolean isStared() {
        if (stared == null)
            stared = MainActivity.favoriteChannelListPreference.getBoolean(ChannelName, false);
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
        SharedPreferences.Editor ed = MainActivity.favoriteChannelListPreference.edit();
        ed.remove(ChannelName);
        if (stared) {
            ed.putBoolean(ChannelName, true);
        }
        ed.commit();
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

    public Date FirstDate() {
        ProgramList pl = this;

        Date mindate = pl.GetItem(0).DateStart;

        for (int j = 0; j < pl.Count(); j++) {
            Date programDate = pl.GetItem(j).DateStart;
            if (programDate.compareTo(mindate) < 0) {
                mindate = pl.GetItem(j).DateStart;

            }
        }
        return mindate;
    }


    public Date LastDate() {
        ProgramList pl = this;

        Date maxdate = pl.GetItem(0).DateStart;

        for (int j = 0; j < pl.Count(); j++) {
            Date programDate = pl.GetItem(j).DateStart;
            if (programDate.compareTo(maxdate) > 0) {
                maxdate = pl.GetItem(j).DateStart;

            }
        }
        return maxdate;
    }


}
