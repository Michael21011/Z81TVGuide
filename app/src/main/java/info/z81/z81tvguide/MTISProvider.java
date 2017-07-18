package info.z81.z81tvguide;

/**
 * Created by Michael_Shapiro on 16.05.2017.
 */

public final class MTISProvider extends CustomProvider {
    @Override
    public String DownloadUrl() {
        return "http://mtis.by/program_xml.zip";
    }

    @Override
    public String LocalFileName() {
        return "program_xml.zip";
    }
}
