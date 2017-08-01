package info.z81.z81tvguide;

import java.util.Comparator;


public class ChannelCustomComparator implements Comparator<ProgramList> {
    @Override
    public int compare(ProgramList o1, ProgramList o2) {
        return o1.ChannelName.compareTo(o2.ChannelName);
    }
}