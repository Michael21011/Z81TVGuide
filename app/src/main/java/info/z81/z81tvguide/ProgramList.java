package info.z81.z81tvguide;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import java.util.Date;
import java.util.GregorianCalendar;



/**
 * Created by michael on 08.10.15.
 */

public class ProgramList {

    public String ChannelId;
    public String ChannelName;
    public Integer DigitalNumber;
    public Integer AnalogNumber;
    public TVProgram tvProgram;
    private ArrayList<ProgramItem> list;
    private ArrayList<ProgramItem> filteredList;
    
    private Boolean stared;

    public ProgramList() {
        list = new ArrayList<>();
        filteredList = list;

    }

    public String getDigitalNumberLabel(boolean ShowEmpty) {
        if (DigitalNumber.equals(-1))
        {
            if (ShowEmpty) {
                return Z81TVGuide.getAppContext().getString(R.string.empty_channel_number);
            }
                else
                return "";
        }

        else return DigitalNumber.toString();
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

    public void setDigitalNumber(Integer dn) {
        if (!this.DigitalNumber.equals(dn)) {
            this.DigitalNumber = dn;
            SharedPreferences.Editor ed = MainActivity.digitalNumberPreference.edit();
            ed.putInt(ChannelName, dn);
            ed.commit();
        }

    }

    public void Add(String Title, Date DateFrom, String Description) {
        ProgramItem programItem = new ProgramItem();
        programItem.Title = Title;
        programItem.DateStart = DateFrom;
        programItem.Description = Description;
        list.add(programItem);

    }

    public int Count() {
        return filteredList.size();
    }

    public ProgramItem GetItem(int arg0) {
        // TODO Auto-generated method stub
        return filteredList.get(arg0);
    }

    private ProgramItem  GetListItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    public long GetItemId(int arg0) {
        // TODO Auto-generated method stub
        return filteredList.get(arg0).hashCode();
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

    public boolean IsFirstItemForDay(int position) {
        Date currentDate = GetItem(position).DateStart;
        if (position > 0) {
            Date prevDate = GetItem(position - 1).DateStart;
            if (prevDate.getDay() != currentDate.getDay()) {
                return true;
            } else return false;
        } else return true;

    }


    public ProgramItem GetCurrentItem(int shiftInMinutes) {
        return this.GetItem(GetCurrentItemIndex(shiftInMinutes));
    }

    public int GetCurrentItemIndex(int shiftInMinutes) {
        Calendar c = Calendar.getInstance();
        String CurrentTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime());
        Date today = new Date();
        today.setTime(c.getTimeInMillis() + (1000 * shiftInMinutes * 60));
        c.setTime(today);

        int index = -1;
        for (int j = 0; j < list.size(); j++) {
            Date programDate = this.GetListItem(j).DateStart;


            if ((j == 0) || ((programDate.compareTo(this.GetListItem(index).DateStart) > 0) & (programDate.compareTo(today) <= 0))) {
                index = j;

            }

        }
        if (!(index == -1)) {
            Calendar indexCalendar = Calendar.getInstance();
            indexCalendar.setTime(this.GetListItem(index).DateStart);

            long millisecondsNow = c.getTimeInMillis();
            long millisecondsItem = indexCalendar.getTimeInMillis();
            if ((millisecondsItem - millisecondsNow > 1 * 1000 * 60) || (millisecondsNow - millisecondsItem > 1000 * 60 * 60 * 4)) {
                index = -1;
            }
        }
        if (!(index == -1)) {
            long listHash=list.get(index).hashCode();
            index = -1;
            for (int j = 0; j < this.Count(); j++)
            {
                if (GetItemId(j)==listHash){
                    index=j;
                    break;
                }


            }


        }
            return index;
    }
    
    public void ApplyFilter(String FilterString){
        if (FilterString==null || FilterString.equals("")) {
            filteredList = new ArrayList<>();
            filteredList.addAll(list);
        }
        else
        {
            filteredList = new ArrayList<>();
            for (int j = 0; j < list.size(); j++)
            {
                if (GetListItem(j).Match(FilterString))
                    filteredList.add(this.GetListItem(j));
                
            }
            
        }
    }


}
