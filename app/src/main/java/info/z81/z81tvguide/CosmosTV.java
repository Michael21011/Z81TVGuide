package info.z81.z81tvguide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class CosmosTV extends CustomProvider {
    @Override
    public String DownloadUrl() {
        Date startD = new Date();
        Date endD= Utils.DateAdd(startD, Calendar.DAY_OF_MONTH,7);
        startD = Utils.DateAdd(startD, Calendar.DAY_OF_MONTH,-1);
        return String.format("http://cosmos-telecom.by/modules/mod_tv_program/mod_tv_program.php?start_date=%1$s&stop_date=%2$s&service=2&type=xml",new SimpleDateFormat("dd.MM.yyyy").format(startD),new SimpleDateFormat("dd.MM.yyyy").format(endD));
    }

    @Override
    public String LocalFileName() {
        return "program.xml";
    }

    @Override
    public String FilePreviousURL() {
        return null;
    }

    @Override
    public String ProviderName() {
        return "Cosmos";
    }
}
