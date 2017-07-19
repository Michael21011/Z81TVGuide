package info.z81.z81tvguide;

/**
 * Created by Michael_Shapiro on 16.05.2017.
 */

public final class PROGRAMTVProvider extends CustomProvider {
    @Override
    public String DownloadUrl() {
        return "http://programtv.ru/xmltv.xml.gz";
    }

    @Override
    public String LocalFileName() {
        return "xmltv.xml.gz";
    }

    @Override
    public String FilePreviousURL() {
        return DownloadUrl();
    }

}
