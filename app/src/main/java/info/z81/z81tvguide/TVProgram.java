package info.z81.z81tvguide;

import android.hardware.camera2.params.BlackLevelPattern;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by michael on 08.10.15.
 */
public class TVProgram {
    private ArrayList<ProgramList> list;

    public TVProgram() {
        list = new ArrayList<>();
    }

    public void Clear() {
        list.clear();
    }

    public void AddChannel(String ChannelId, String ChannelName) {
        if (!IsChannelExists(ChannelId)) {
            ProgramList pl = new ProgramList();
            pl.ChannelId = ChannelId;
            pl.ChannelName = ChannelName;
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

    private ProgramList GetProgramList(String ChannelId) {
        for (ProgramList item : list) {
            if (item.ChannelId.equals(ChannelId)) {
                return item;
            }
            ;
        }
        return null;
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
}
