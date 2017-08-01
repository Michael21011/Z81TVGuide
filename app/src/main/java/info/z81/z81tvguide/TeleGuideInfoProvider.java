package info.z81.z81tvguide;

/**
 * Created by Michael_Shapiro on 16.05.2017.
 */

public final class TeleGuideInfoProvider extends CustomProvider {
    @Override
    public String DownloadUrl() {
        return "http://www.teleguide.info/download/new3/xmltv.xml.gz";
    }

    @Override
    public String LocalFileName() {
        return "teleguideinfo.xml.gz";
    }

    @Override
    public String FilePreviousURL() {
        return "http://www.teleguide.info/download/old/xmltv.xml.gz";
    }

}
