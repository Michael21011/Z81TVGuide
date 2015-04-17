package info.z81.z81tvguide;

import java.util.Comparator;


public class CustomComparator implements Comparator<NowItem> {
    @Override
    public int compare(NowItem o1, NowItem o2) {
        return 0-o1.DateStart.compareTo(o2.DateStart);
    }
}