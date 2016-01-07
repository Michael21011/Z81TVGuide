package info.z81.z81tvguide;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michael on 08.10.15.
 */
public class ProgramItem {
    public String Title;
    public Date DateStart;
    public Date DateEnd;
    public String Description;


    public String GetDateStartTimeOnly()
    {
        return new SimpleDateFormat("HH:mm").format(DateStart);
    }
    public String GetDayHeader()
    {
        return new SimpleDateFormat("EEEE, d MMMM").format(DateStart);
    }


}
