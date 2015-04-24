package info.z81.z81tvguide;

import java.util.Calendar;
import java.util.Date;

public class ChannelItem {
	
	public String ChannelId;
	public String ChannelName;
	public String Title;
	public Date DateStart;
    public boolean Stared;

	private int channelLogo;
	public int getChannelLogo() {
        return Utils.getChannelLogoByName(ChannelName);
	}
	public void setChannelLogo(int newFoo) {
		channelLogo = newFoo;
	}

}
