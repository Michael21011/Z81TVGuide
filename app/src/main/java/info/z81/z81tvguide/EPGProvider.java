package info.z81.z81tvguide;

/**
 * Created  on 03.11.2017.
 */

public class EPGProvider extends CustomProvider {
    @Override
    public String DownloadUrl() {
        return "https://epg.by/program?get=xml";
    }

    @Override
    public String LocalFileName() {
        return "xmltv.xml.gz";
    }

    @Override
    public String FilePreviousURL() {
        return "https://epg.by/program?get=xml";
    }

    @Override
    public String ProviderName() {
        return "epg.by";
    }

}
