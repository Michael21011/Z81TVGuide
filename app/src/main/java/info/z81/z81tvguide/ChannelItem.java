package info.z81.z81tvguide;

import android.content.SharedPreferences;
import java.util.Date;

public class ChannelItem {
	
	public String ChannelId;
	public String ChannelName;
    private Boolean stared;

	public int getChannelLogo() {
        return Utils.getChannelLogoByName(ChannelName);
	}

    public boolean isStared() {
        if (stared==null)
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


}
