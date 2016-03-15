package info.z81.z81tvguide;

import android.content.SharedPreferences;
import android.hardware.camera2.params.BlackLevelPattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by michael on 08.10.15.
 */
public class TVProgram  {

    private ArrayList<ProgramList> list;
    private DefaultChannelNumbers defaultChannelNumbers;

    public TVProgram() {
        list = new ArrayList<>();
        defaultChannelNumbers = new DefaultChannelNumbers();

    }

    public void Clear() {
        list.clear();
    }

    public void AddChannel(String ChannelId, String ChannelName) {
        if (!IsChannelExists(ChannelId)) {
            ProgramList pl = new ProgramList();
            pl.ChannelId = ChannelId;
            pl.ChannelName = ChannelName;
            pl.DigitalNumber = GetChannelDigitalNumber(ChannelName);
            pl.tvProgram = this;
            list.add(pl);
        }
    }

    public void AddProgram(String ChannelId, String ProgramTitle, Date DateStart, String Description) {
        ProgramList pl = GetProgramList(ChannelId);
        if (pl == null)
            return;
        pl.Add(ProgramTitle, DateStart, Description);

    }

    private boolean IsChannelExists(String ChannelId) {
        for (ProgramList item : list) {
            if (item.ChannelId.equals(ChannelId)) {
                return true;
            }
            ;
        }
        return false;
    }

    public ProgramList GetProgramList(String ChannelId) {
        int Index=-1;
        Index = GetProgramListIndex(ChannelId);
        if (Index==-1)
            return null;
                else return this.GetItem(Index);
    }

    public int GetProgramListIndex(String ChannelId)
    {
        for (int j=0;j<ChannelCount();j++) {
            if (this.GetItem(j).ChannelId.equals(ChannelId)) {
                return j;
            }


        }
        return -1;
    }



    public boolean IsEmpty() {
        return ChannelCount() == 0;
    }

    public int ChannelCount() {
        return list.size();
    }

    public ProgramList GetItem(int arg0) {

        return list.get(arg0);
    }

    public int IsCurrent() {
        if (IsEmpty()) {
            return -1;
        }

        Calendar c = Calendar.getInstance();
        Date today = new Date();
        today.setTime(c.getTimeInMillis());
        if ((GetItem(0).FirstDate().compareTo(today) < 0) & (GetItem(0).LastDate().compareTo(today) > 0)) {
            // It is current
            return 0;
        } else if (GetItem(0).FirstDate().compareTo(today) > 0) {
            // It is in future
            return 1;
        } else {
            //It is in past
            return -1;
        }
    }

    public long GetItemId(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0).hashCode();
    }

    public Integer GetChannelDigitalNumber(String ChannelName)
    {
        Integer n = MainActivity.digitalNumberPreference.getInt(ChannelName, -1);
        if (n.equals(-1)) {
            n = defaultChannelNumbers.GetByName(ChannelName);
        }
        return n;
    }



}
