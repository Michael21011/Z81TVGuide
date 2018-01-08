package info.z81.z81tvguide;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import java.util.Date;
import java.util.GregorianCalendar;



/**
 * Created by michael on 08.10.15.
 * Object contains TV Guide for ONE channel
 */

public class ProgramList implements Serializable  {

    public String ChannelId;
    public String ChannelName;
    public Integer DigitalNumber;
    public Integer AnalogNumber;
    //public TVProgram tvProgram;
    public String IconUrl;
    private boolean isNew;

    private ArrayList<ProgramItem> list;
    private ArrayList<ProgramItem> filteredList;
    
    private Boolean stared;

    public ProgramList(String channelId, String channelName, String iconUrl, Boolean IsFavorit) {
        list = new ArrayList<>();
        filteredList = list;
        this.ChannelId = channelId;
        this.ChannelName = channelName;
        this.IconUrl = iconUrl;
        this.stared=IsFavorit;
        CheckIsNew();


    }
    public boolean IsNew()
    {
        return false;
        /*
       if (tvProgram==null)
           return isNew;
        else
        return !tvProgram.IsFirstRun && isNew;
        */
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
            stared = MainActivity.favoriteChannelListPreference.getBoolean(ChannelName.toUpperCase(), false);
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
        SharedPreferences.Editor ed = MainActivity.favoriteChannelListPreference.edit();
        ed.remove(ChannelName.toUpperCase());
        if (stared) {
            ed.putBoolean(ChannelName.toUpperCase(), true);
        }
        ed.commit();
    }

    public boolean CheckIsNew() {

        isNew =  !MainActivity.knownChannelListPreference.contains(ChannelName);
        return IsNew();
    }



    public void setDigitalNumber(Integer dn) {
        if (!this.DigitalNumber.equals(dn)) {
            this.DigitalNumber = dn;
            SharedPreferences.Editor ed = MainActivity.digitalNumberPreference.edit();
            ed.putInt(ChannelName, dn);
            ed.commit();
        }

    }

    private int ConvertFilteredItemIndexToItemIndex(int filteredItemIndex)
    {
      ProgramItem filteredPI=  GetItem(filteredItemIndex);
      return  list.indexOf(filteredPI);
    }

    private ProgramItem GetListItemByFilteredItemIndex(int filteredItemIndex)
    {
        return GetListItem(ConvertFilteredItemIndexToItemIndex(filteredItemIndex));
    }

    public void Add(String Title, Date DateFrom, String Description) {
        ProgramItem programItem = new ProgramItem(Title,DateFrom,Description);
        list.add(programItem);
        programItem.Position = this.Count()-1;

    }
    public Date GetDateEnd(int filteredItemIndex){
        Date StartDate=GetItem(filteredItemIndex).DateStart;
        int itemIndex=ConvertFilteredItemIndexToItemIndex(filteredItemIndex);

        if (itemIndex<this.TotalCount()-1)
        {
            Date nd= GetListItem(itemIndex+1).DateStart;
            long StartDateTick=StartDate.getTime();
            long NextDateTick=nd.getTime();
            if (NextDateTick<StartDateTick)
            {
                nd = Utils.DateAdd(nd, Calendar.HOUR, 24);
            }
            long diff = nd.getTime() - StartDate.getTime();
            long diffMinutes = diff / (1000) % 60;
            if (diffMinutes>600)
            {
                return Utils.DateAdd(StartDate, Calendar.HOUR, 1);
            }
            else
                return nd;

        }
        else
        {//last program
            return Utils.DateAdd(StartDate, Calendar.HOUR, 1);
        }

    }
    public int Count() {
        return filteredList.size();
    }

    public int TotalCount()
    {
        return list.size();
    }

    public ProgramItem GetItem(int arg0) {
        return filteredList.get(arg0);
    }

    private ProgramItem  GetListItem(int arg0) {
        return list.get(arg0);
    }

    public long GetItemId(int arg0) {
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

    public boolean Match(String FilterString)
    {
        boolean result = this.ChannelName.toLowerCase().contains(FilterString.toLowerCase());
        if (!result) {
            for (int j = 0; j < list.size(); j++)
            {
                if (GetListItem(j).Match(FilterString))
                {
                    result = true;
                    break;
                }

            }
        }

        return result;
    }

    public void SetKnown() {

            SharedPreferences.Editor ed = MainActivity.knownChannelListPreference.edit();
            ed.putLong(ChannelName, System.currentTimeMillis());
            ed.commit();
    }

    public boolean ShouldHighligt() {
       boolean result=false;
       /*
        if (tvProgram.IsFirstRun)
        {return false;}
        else {
            long started = MainActivity.knownChannelListPreference.getLong(ChannelName, 0);
            if (System.currentTimeMillis() >= started +
                    (1 * 60 * 60 * 1000)) {
                return false;

            } else return true;
        }


         */
       return false;
    }


}
