package info.z81.z81tvguide;

import android.app.Application;

import java.util.Calendar;
import java.util.Date;

public class NowItem {
	
	public String ChannelId;
	public String ChannelName;
	public String Title;
	public Date DateStart;
	public String AgoTime;
	public String Description;
	public Integer DigitalNumber;
	public String getAgoTime()

	{
		String diffString="";
		if (DateStart.equals(new Date(Long.MIN_VALUE))){
			diffString="";}
		else
		{
		Calendar start = Calendar.getInstance();
		start.setTime(DateStart);
		Calendar curr = Calendar.getInstance();
        //Date today = new Date();
        //today.setTime(curr.getTimeInMillis()+(1000*60*60));
        //curr.setTime(today);
	    long diffMin = (curr.getTimeInMillis()-start.getTimeInMillis())/1000/60;
	    diffString=String.format("%d %s", diffMin, MainActivity.MyResources.getString(R.string.Minute_short));
	    if (diffMin>=60)
	    {
	    	long diffHour = diffMin/60;
	    	diffString=String.format("%d %s", diffHour, MainActivity.MyResources.getString(R.string.Hour_short));
	    }
		}
	    return diffString;
	}

	private int channelLogo;

	public int getChannelLogo() {
        return Utils.getChannelLogoByName(ChannelName);
	}

	public void setChannelLogo(int newFoo) {
		channelLogo = newFoo;
	}

}
