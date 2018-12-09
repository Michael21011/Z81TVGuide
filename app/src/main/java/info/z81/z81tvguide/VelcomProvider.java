package info.z81.z81tvguide;

public class VelcomProvider extends CustomProvider {
    @Override
    public String DownloadUrl() {
        return "http://help.telecom.by/_files/TelecomTV/TelecomTVepg/xmltv.xml";
    }

    @Override
    public String LocalFileName() {
        return "xmltv.xml";
    }

    @Override
    public String FilePreviousURL() {
        return "";
    }

    @Override
    public String ProviderName() {
        return "telecom.by";
    }

    @Override
    public int HourShift() {
        return 3;
    }
}
