package info.z81.z81tvguide;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michael on 08.10.15.
 */
public class ProgramItem  {
    public String Title;
    public Date DateStart;
    public Date DateEnd;
    public String Description;
    public int Position;

    public ProgramItem(String title, Date dateStart, String description)
    {
        Title = title;
        DateStart = dateStart;
        Description = description;
    }

    public String GetDateStartTimeOnly()
    {
        return new SimpleDateFormat("HH:mm").format(DateStart);
    }
    public String GetDayHeader()
    {
        return new SimpleDateFormat("EEEE, d MMMM").format(DateStart);
    }
    public boolean Match(String FilterString){
        boolean result;
        result = (FilterString==null || FilterString.equals("") ||Title.toLowerCase().contains(FilterString.toLowerCase()) || Description.toLowerCase().contains(FilterString.toLowerCase()));
        return result;
    };


}
