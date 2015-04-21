package info.z81.z81tvguide;

import java.util.Comparator;


public class ChannelCustomComparator implements Comparator<ChannelItem> {
    @Override
    public int compare(ChannelItem o1, ChannelItem o2) {
        return o1.ChannelName.compareTo(o2.ChannelName);
    }
}