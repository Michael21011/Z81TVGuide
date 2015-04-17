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

	public void Add(String channelid, String channelName, String programTitle, String startTime) {
		NowItem nowItem = new NowItem();
		nowItem.ChannelId=channelid;
		nowItem.ChannelName = channelName;
		nowItem.Title = programTitle;
		
		if (startTime!="")
        {
    	int year = Integer.parseInt(startTime.subSequence(0, 4).toString());
    	int month = Integer.parseInt(startTime.subSequence(4, 6).toString());
    	int day = Integer.parseInt(startTime.subSequence(6, 8).toString());
    	int hour = Integer.parseInt(startTime.subSequence(8, 10).toString());	    		        	
    	int min = Integer.parseInt(startTime.subSequence(10, 12).toString());	    		        	
    	Calendar StartDate = Calendar.getInstance();
    	StartDate.set(year, month-1, day, hour, min);
		nowItem.DateStart=StartDate.getTime();
        }
		else
			nowItem.DateStart = new Date(Long.MIN_VALUE);
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
