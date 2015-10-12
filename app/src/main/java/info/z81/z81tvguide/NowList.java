package info.z81.z81tvguide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;



public class NowList  implements  Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<NowItem> list;


    public NowList()
    {
    	list = new ArrayList<NowItem>();
    }
    
	public  void Clear() {
		list.clear();
	
	}



	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void Add(String channelid, String channelName, String programTitle, Date startTime) {
		NowItem nowItem = new NowItem();
		nowItem.ChannelId=channelid;
		nowItem.ChannelName = channelName;
		nowItem.Title = programTitle;
		

    	nowItem.DateStart = startTime;
		list.add(nowItem);
        
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
			Collections.sort(list, new CustomComparator());
			
		}
		
	}

}
