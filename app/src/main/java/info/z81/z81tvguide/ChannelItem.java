package info.z81.z81tvguide;

import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Date;

public class ChannelItem {
	
	public String ChannelId;
	public String ChannelName;
	public String Title;
	public Date DateStart;
    private Boolean stared;

	private int channelLogo;
	public int getChannelLogo() {
        return Utils.getChannelLogoByName(ChannelName);
	}
	public void setChannelLogo(int newFoo) {
		channelLogo = newFoo;
	}
    public void setStared(boolean Mark){
        stared = Mark;
        SharedPreferences.Editor ed = MainActivity.favoriteChannelListPreference.edit();
        ed.remove(ChannelName);
        if (stared) {
            ed.putBoolean(ChannelName, true);
        }
        ed.commit();
    }

    public boolean getStared(){
        if (stared==null) {
            stared =  MainActivity.favoriteChannelListPreference.getBoolean(ChannelName, false);
        }
        return stared;
    }

}
