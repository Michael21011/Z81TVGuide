package info.z81.z81tvguide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class It999Provider extends CustomProvider {
    @Override
    public String DownloadUrl() {
        return "http://epg.it999.ru/ru.xml.gz";
    }

    @Override
    public String LocalFileName() {
        return "ru.xml.gz";
    }

    @Override
    public String FilePreviousURL() {
        return null;
    }

    @Override
    public String ProviderName() {
        return "epg.it999.ru";
    }
}
