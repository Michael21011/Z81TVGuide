package info.z81.z81tvguide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class ChannelList implements  Comparable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<ChannelItem> list;


    public ChannelList()
    {
    	list = new ArrayList<ChannelItem>();
    }
    
	public  void Clear() {
		list.clear();
	
	}



	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void Add(String channelid, String channelName, boolean Stared) {
		ChannelItem channelItem = new ChannelItem();
		channelItem.ChannelId=channelid;
		channelItem.ChannelName = channelName;
		channelItem.Stared = Stared;
		
		list.add(channelItem);
        
	}

	public int Count() {
		return list.size();
	}

	public Object GetItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	public long GetItemId(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0).hashCode();
	}

	public void sort(int i) {

		if (i==1)
		{//by tyme

			Collections.sort(list, new ChannelCustomComparator());
		}
		
	}

}
