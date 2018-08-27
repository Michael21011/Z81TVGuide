package info.z81.z81tvguide;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static info.z81.z81tvguide.MainActivity.settingPreference;


/**
 * Created by Michael on 24.04.2015.
 */


public class Utils {
    public final static String ProgramProviderParamName = "program_source_index";
    public final static String FavoritSetParamName = "mychannels_set_index";

    public static void ShowMessage(Context context, String title, String text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(text);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                System.exit(0);
            }
        });

        /*
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                */

        alert.show();
    }

    public static int getChannelLogoByName(String ChannelName) {

        int id;


        if (ChannelName.equalsIgnoreCase("Техно 24")) {
            id = R.drawable.a24tehno;
        } else if (ChannelName.equalsIgnoreCase(consts.c_8Channel)) {
            id = R.drawable.a8moj;

        } else if (ChannelName.equalsIgnoreCase(consts.c_A2)) {
            id = R.drawable.a2;
        } else if (ChannelName.equalsIgnoreCase("Animal Planet")) {
            id = R.drawable.animalplanet;
        } else if (ChannelName.equalsIgnoreCase("Беларусь 1")) {
            id = R.drawable.belarus1;
        } else if (ChannelName.equalsIgnoreCase("Да Винчи")) {
            id = R.drawable.davinci;
        } else if (ChannelName.equalsIgnoreCase("Discovery")) {
            id = R.drawable.discovery;
        } else if (ChannelName.equalsIgnoreCase("Discovery Science")) {
            id = R.drawable.discoveryscience;
        } else if (ChannelName.equalsIgnoreCase("Discovery World")) {
            id = R.drawable.discoveryworld;
        } else if (ChannelName.equalsIgnoreCase("Gulli")) {
            id = R.drawable.gulli;
        } else if (ChannelName.equalsIgnoreCase("Eureka HD")) {
            id = R.drawable.eureka;
        } else if (ChannelName.equalsIgnoreCase("Europa Plus TV")) {
            id = R.drawable.europaplus;
        } else if (ChannelName.equalsIgnoreCase("History")) {
            id = R.drawable.history;
        } else if (ChannelName.equalsIgnoreCase("Nat Geo Wild")) {
            id = R.drawable.natgeowild;
        } else if (ChannelName.equalsIgnoreCase("National Geographic Channel")) {
            id = R.drawable.nationalgeographic;
        } else if (ChannelName.equalsIgnoreCase("Nautical Channel")) {
            id = R.drawable.nautical;
        } else if (ChannelName.equalsIgnoreCase("Nick Jr")) {
            id = R.drawable.nickjr;
        } else if (ChannelName.equalsIgnoreCase("RU.TV")) {
            id = R.drawable.rutv;
        } else if (ChannelName.equalsIgnoreCase("Travel and Adventure")) {
            id = R.drawable.traveladventure;
        } else if (ChannelName.equalsIgnoreCase("ТВ 1000-Экшн")) {
            id = R.drawable.tv1000action;
        } else if (ChannelName.equalsIgnoreCase("Виасат Эксплорер")) {
            id = R.drawable.viasatexplorer;
        } else if (ChannelName.equalsIgnoreCase("Виасат Хистори")) {
            id = R.drawable.viasathistory;
        } else if (ChannelName.equalsIgnoreCase("Виасат Нэйчер")) {
            id = R.drawable.viasatnature;
        } else if (ChannelName.equalsIgnoreCase("Спортивный телеканал Виасат")) {
            id = R.drawable.viasatsport;
        } else if (ChannelName.equalsIgnoreCase("Авто Плюс")) {
            id = R.drawable.autoplus;
        } else if (ChannelName.equalsIgnoreCase("Беларусь 2")) {
            id = R.drawable.belarus2;
        } else if (ChannelName.equalsIgnoreCase("Беларусь 3")) {
            id = R.drawable.belarus3;
        } else if (ChannelName.equalsIgnoreCase("Беларусь 5")) {
            id = R.drawable.belarus5;
        } else if (ChannelName.equalsIgnoreCase("БЕЛМУЗТВ")) {
            id = R.drawable.belmuztv;
        } else if (ChannelName.equalsIgnoreCase("ВТВ")) {
            id = R.drawable.vtv;
        } else if (ChannelName.equalsIgnoreCase("Детский мир")) {
            id = R.drawable.detskijmir;
        } else if (ChannelName.equalsIgnoreCase("Жест")) {
            id = R.drawable.zhest;
        } else if (ChannelName.equalsIgnoreCase("Карусель")) {
            id = R.drawable.karusel;
        } else if (ChannelName.equalsIgnoreCase("Комедия ТВ")) {
            id = R.drawable.camedytv;
        } else if (ChannelName.equalsIgnoreCase("Мать и дитя")) {
            id = R.drawable.momditya;
        } else if (ChannelName.equalsIgnoreCase("Минск 24ДОК")) {
            id = R.drawable.minsk24doc;
        } else if (ChannelName.equalsIgnoreCase("Мир")) {
            id = R.drawable.mir;
        } else if (ChannelName.equalsIgnoreCase("Моя Планета")) {
            id = R.drawable.myplanet;
        } else if (ChannelName.equalsIgnoreCase("Мужское кино")) {
            id = R.drawable.muzkino;
        } else if (ChannelName.equalsIgnoreCase("Наше кино")) {
            id = R.drawable.nashekino;
        } else if (ChannelName.equalsIgnoreCase("НТВ Беларусь")) {
            id = R.drawable.ntvbelarus;
        } else if (ChannelName.equalsIgnoreCase("НТВ-ПЛЮС КИНО ПЛЮС")) {
            id = R.drawable.ntvkino;
        } else if (ChannelName.equalsIgnoreCase("ОНТ")) {
            id = R.drawable.ont;
        } else if (ChannelName.equalsIgnoreCase("Охота и рыбалка")) {
            id = R.drawable.oxotarybalka;
        } else if (ChannelName.equalsIgnoreCase("БелБизнесЧенел (РБК)")) {
            id = R.drawable.rbk;
        } else if (ChannelName.equalsIgnoreCase("Россия-Культура")) {
            id = R.drawable.rossiyaculture;
        } else if (ChannelName.equalsIgnoreCase("РТР Беларусь")) {
            id = R.drawable.rtrbelarus;
        } else if (ChannelName.equalsIgnoreCase("Русское экстремальное телевидение")) {
            id = R.drawable.russianextrim;
        } else if (ChannelName.equalsIgnoreCase("Русский иллюзион")) {
            id = R.drawable.russkijilluzion;
        } else if (ChannelName.equalsIgnoreCase("Сетанта спорт")) {
            id = R.drawable.setantasport;
        } else if (ChannelName.equalsIgnoreCase("Сетанта спорт+")) {
            id = R.drawable.setantasportplus;
        } else if (ChannelName.equalsIgnoreCase("Союз")) {
            id = R.drawable.souz;
        } else if (ChannelName.equalsIgnoreCase("СТВ")) {
            id = R.drawable.ctv;
        } else if (ChannelName.equalsIgnoreCase("ТВ 3")) {
            id = R.drawable.tv3;
        } else if (ChannelName.equalsIgnoreCase("ТВ 1000")) {
            id = R.drawable.tv1000;
        } else if (ChannelName.equalsIgnoreCase("ТВ-1000. Русское кино")) {
            id = R.drawable.tv1000russkoekino;
        } else if (ChannelName.equalsIgnoreCase("Телеклуб")) {
            id = R.drawable.teleclub;
        } else if (ChannelName.equalsIgnoreCase("Усадьба-ТВ")) {
            id = R.drawable.usadba;
        } else if (ChannelName.equalsIgnoreCase("Перец I")) {
            id = R.drawable.perec;
        } else if (ChannelName.equalsIgnoreCase("Мультимания")) {
            id = R.drawable.multimania;
        } else if (ChannelName.equalsIgnoreCase("Домашние животные")) {
            id = R.drawable.home_animale;
        } else if (ChannelName.equalsIgnoreCase("Zee Russia")) {
            id = R.drawable.zeetv;
        } else if (ChannelName.equalsIgnoreCase("TV 1000 Comedy")) {
            id = R.drawable.tv1000comedy;
        } else if (ChannelName.equalsIgnoreCase("Парк развлечений")) {
            id = R.drawable.park;
        } else if (ChannelName.equalsIgnoreCase("ДОМАШНИЕ ЖИВОТНЫЕ")) {
            id = R.drawable.home_animale;
        } else if (ChannelName.equalsIgnoreCase("Любимое кино")) {
            id = R.drawable.lubimoe_kino;
        } else if (ChannelName.equalsIgnoreCase("Наука")) {
            id = R.drawable.nauka_2_0;
        } else if (ChannelName.equalsIgnoreCase("ТНТ Int")) {
            id = R.drawable.tnt;
        } else if (ChannelName.equalsIgnoreCase("MIНСК ТV")) {
            id = R.drawable.minsk_tv;
        } else if (ChannelName.equalsIgnoreCase("Мама")) {
            id = R.drawable.mama;
        } else if (ChannelName.equalsIgnoreCase("Морской (nautical channel)")) {
            id = R.drawable.morskoj;
        } else if (ChannelName.equalsIgnoreCase("КХЛ ТВ")) {
            id = R.drawable.khl;
        } else if (ChannelName.equalsIgnoreCase("Домашний I")) {
            id = R.drawable.domashniy;
        } else if (ChannelName.equalsIgnoreCase("+TV")) {
            id = R.drawable.plustv;
        } else if (ChannelName.equalsIgnoreCase("Кинокомедия")) {
            id = R.drawable.kinokomedia;
        } else if (ChannelName.equalsIgnoreCase("Киномикс")) {
            id = R.drawable.kinomiks;
        } else if (ChannelName.equalsIgnoreCase("Телеканал Деда Мороза")) {
            id = R.drawable.dedmoroz;
        } else if (ChannelName.equalsIgnoreCase("Paramount Comedy")) {
            id = R.drawable.paramountcomedy;
        } else if (ChannelName.equalsIgnoreCase("Сарафан")) {
            id = R.drawable.sarafan;
        } else if (ChannelName.equalsIgnoreCase("Мульт")) {
            id = R.drawable.mult;
        } else if (ChannelName.equalsIgnoreCase("SONY Sci-Fi")) {
            id = R.drawable.sonyscifi;
        } else if (ChannelName.equalsIgnoreCase(consts.c_RTVI)) {
            id = R.drawable.rtvi;
        } else if (ChannelName.equalsIgnoreCase(consts.c_TiJi)) {
            id = R.drawable.tiji;
        } else if (ChannelName.equalsIgnoreCase(consts.c_EuroNews)) {
            id = R.drawable.euronews;
        } else if (ChannelName.equalsIgnoreCase(consts.c_Galaxy)) {
            id = R.drawable.galaxy;
        } else if (ChannelName.equalsIgnoreCase(consts.c_SonyTurbo)) {
            id = R.drawable.sonyturbo;
        } else if (ChannelName.equalsIgnoreCase(consts.c_Boomerang)) {
            id = R.drawable.boomerang;
        } else if (ChannelName.equalsIgnoreCase(consts.c_Индийскоекино)) {
            id = R.drawable.inida;
        } else if (ChannelName.equalsIgnoreCase(consts.c_FineLivingNetwork)) {
            id = R.drawable.finelivingnetwork;
        } else if (ChannelName.equalsIgnoreCase(consts.c_Оружие)) {
            id = R.drawable.orujie;
        } else if (ChannelName.equalsIgnoreCase(consts.c_Спорт1укр)) {
            id = R.drawable.sport1ukr;
        } else if (ChannelName.equalsIgnoreCase(consts.c_Спорт2укр)) {
            id = R.drawable.sport2ukr;
        } else if (ChannelName.equalsIgnoreCase(consts.c_Т24)) {
            id = R.drawable.texno24;
        } else if (ChannelName.equalsIgnoreCase(consts.c_Nickelodeon)) {
            id = R.drawable.nickelodeon;
        } else if (ChannelName.equalsIgnoreCase(consts.c_NickelodeonHD)) {
            id = R.drawable.nick_hd;
        } else if (ChannelName.equalsIgnoreCase(consts.c_AmediaPremiumHD)){
            id = R.drawable.amediapremium;
        } else if (ChannelName.equalsIgnoreCase(consts.c_ОхотникиРыболовHD)){
            id = R.drawable.oxotiribolov;
        } else
            id = R.drawable.empty;


        return id;

    }

    public static Date StringToDate(String InputString) {
        int year;
        int month;
        int day;
        int hour;
        int min;
        if (InputString.subSequence(4, 5).toString().equals("-"))
        { year = Integer.parseInt(InputString.subSequence(0, 4).toString());
            month = Integer.parseInt(InputString.subSequence(5, 7).toString());
            day = Integer.parseInt(InputString.subSequence(8, 10).toString());
            hour = Integer.parseInt(InputString.subSequence(11, 13).toString());
            min = Integer.parseInt(InputString.subSequence(14, 16).toString());
        }
        else {
            year = Integer.parseInt(InputString.subSequence(0, 4).toString());
            month = Integer.parseInt(InputString.subSequence(4, 6).toString());
            day = Integer.parseInt(InputString.subSequence(6, 8).toString());
            hour = Integer.parseInt(InputString.subSequence(8, 10).toString());
            min = Integer.parseInt(InputString.subSequence(10, 12).toString());
        }
        Calendar StartDate = Calendar.getInstance();
        StartDate.set(year, month - 1, day, hour, min);
        return StartDate.getTime();
    }

    public static Date DateAdd(Date inputDate, int timePeriodType, int timePeriodValue) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.add(timePeriodType, timePeriodValue);
        return cal.getTime();
    }

    public static void LoadBitmapFromInternet(ImageView imageView, String channelName, String iconURL) {
        // String urlAddress = String.format( "https://raw.githubusercontent.com/Michael21011/Z81TVGuide/RollBecasuLoop/Icons/%s.png", channelName);
        final int CacheTimeInHours = 1;
        final int CacheTimeInMinutes = 5;
        final long CacheTimeOnDisk = (long) 30 * 24 * 60 * 60 * 1000;
        final int CacheTimeOnNet = 1 * 60 * 60 * 1000;
        String urlMask;
        if (iconURL.equals("")) {
            urlMask = "https://raw.githubusercontent.com/Michael21011/Z81TVGuide/RollBecasuLoop/Icons/%s";

        } else {
            urlMask = iconURL;
        }
        final String FileName = String.format("%s.png", channelName);
        final String FullFileName = Z81TVGuide.getAppContext().getFilesDir() + "/" + FileName;
        File file = new File(FullFileName);
        SharedPreferences prefs = Z81TVGuide.getAppContext().getSharedPreferences("z81tvgudeIcons", 0);
        Long date_load = prefs.getLong(channelName, 0);
        if (file.exists()) {
            Boolean needDelete = false;
            if (System.currentTimeMillis() >= date_load +
                    (CacheTimeOnDisk)) {
                needDelete = true;
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove(channelName);
                editor.commit();

            }
            /*   BitmapFactory.Options options = new BitmapFactory.Options();
               options.inJustDecodeBounds = true;*/
            Bitmap myBitmap = BitmapFactory.decodeFile(FullFileName);
            if (myBitmap == null || needDelete) {
                file.delete();
                imageView.setImageResource(R.drawable.empty);
            } else {
                imageView.setImageBitmap(myBitmap);
            }
        }
//Do something
        else {


            Boolean needDownload = false;
            if (date_load == 0) {

                needDownload = true;
            }

            // Wait at least n days before opening

            if (System.currentTimeMillis() >= date_load +
                    (CacheTimeOnNet)) {
                needDownload = true;

            }
            imageView.setImageResource(R.drawable.empty);
            if (needDownload) {
                date_load = System.currentTimeMillis();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putLong(channelName, date_load);
                editor.commit();
                new AsyncTaskLoadImage(imageView).execute(urlMask, FileName);
            }


        }


    }

    public static void WriteSharedPreference(String refName, String refValue) {
        SharedPreferences prefs = GetSettingsSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(refName, refValue);
        editor.commit();
    }

    public static void WriteSharedPreference(String refName, long refValue) {
        SharedPreferences prefs = GetSettingsSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(refName, refValue);
        editor.commit();
    }

    public static void WriteSharedPreference(String refName, int refValue) {
        SharedPreferences prefs = GetSettingsSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(refName, refValue);
        editor.commit();
    }


    public static long ReadSharedPreference(String refName, long defValue) {
        return GetSettingsSharedPreferences().getLong(refName, defValue);
    }

    public static int ReadSharedPreference(String refName, int defValue) {
        SharedPreferences prefs = GetSettingsSharedPreferences();
        int result;
        try {
            result = prefs.getInt(refName, defValue);
        } catch (Exception ex) {
            result = defValue;
        }
        return result;
    }

    public static String ReadSharedPreference(String refName, String defValue) {

        return GetSettingsSharedPreferences().getString(refName, defValue);
    }

    public static SharedPreferences GetSettingsSharedPreferences() {
        return Z81TVGuide.getAppContext().getSharedPreferences("settings", 0);
    }

    public static String XMLGetElementValue(String xml, String ElementName) {
        String result="";
        try {
            int StartPosition = xml.indexOf("<" + ElementName);
            int EndPosition = xml.indexOf(">", StartPosition);
            if (xml.indexOf("/>", StartPosition) != EndPosition - 1) {
                StartPosition = EndPosition + 1;
                EndPosition = xml.indexOf("<", StartPosition);
                result = xml.substring(StartPosition, EndPosition);

            }
            return result;
        } catch(Exception e)
        {
            return "";
        }
    }

    public static String XMLGetAttributeValue(String xml, String ElementName, String AttributeName) {
        try {
            String result = "";

            int StartPosition = xml.indexOf("<" + ElementName);
            int EndPosition = xml.indexOf(">", StartPosition);
            String HeaderString = xml.substring(StartPosition + ElementName.length() + 1, EndPosition);

            StartPosition = HeaderString.indexOf(AttributeName);
            int AttrStartPosition = HeaderString.indexOf("\"", StartPosition);
            int AttrEndPosition = HeaderString.indexOf("\"", AttrStartPosition + 1);
            result = HeaderString.substring(AttrStartPosition + 1, AttrEndPosition);
            return result;
        } catch (Exception e)
        {
            return "";
        }

    }

    public  static Map<String, String> GetNormalaizedList()
    {
        Map<String, String> list = new HashMap<>();

        /*  custom name, general name */
        list.put("1HD","1HD");
        list.put("2 КАНАЛ Могилёв","2 КАНАЛ Могилёв");
        list.put("2+2","2+2");
        list.put("2x2","2x2");
        list.put("Канал 2x2","2x2");
        list.put("2x2 +2","2x2 +2");
        list.put("360 Подмосковье","360 Подмосковье");
        list.put("360° Подмосковье HD","360° Подмосковье HD");
        list.put("365 дней","365 дней");
        list.put("5 канал (Россия)","5 канал (Россия)");
        list.put("5-й канал (Укр)","5-й канал (Укр)");
        list.put("8 канал (Беларусь)",consts.c_8Channel);
        list.put("8 Канал (Минск)",consts.c_8Channel);
        list.put("8 Канал (СПб)","8 Канал (СПб)");
        list.put("A-ONE","A-ONE");
        list.put("AMC","AMC");
        list.put("AMEDIA 2","A2");
        list.put("А2","A2");
        list.put("Amedia Hit","Amedia Hit");
        list.put("Amedia Premium","Amedia Premium HD");
        list.put("Amedia1","Amedia1");
        list.put("Ani","Ani");
        list.put("Animal Family HD","Animal Family HD");
        list.put("Animal Planet","Animal Planet");
        list.put("Animal Planet HD","Animal Planet HD");
        list.put("BBC World News","BBC World News");
        list.put("Bollywood HD","Bollywood HD");
        list.put("Boomerang","Boomerang");
        list.put("BRIDGE TV","BRIDGE TV");
        list.put("Candy TV","Candy TV");
        list.put("Cartoon Network","Cartoon Network");
        list.put("CBS Reality","CBS Reality");
        list.put("Cinema","Cinema");
        list.put("Cinema (Космос ТВ)","Cinema (Космос ТВ)");
        list.put("Da Vinci","Da Vinci");
        list.put("Discovery Channel Россия",consts.c_DiscoveryChannel);
        list.put("Discovery Channel Россия",consts.c_DiscoveryChannel);
        list.put("Discovery Восточная Европа",consts.c_DiscoveryChannel);

        list.put("Discovery HD Showcase","Discovery HD Showcase");
        list.put("Discovery Science","Discovery Science");
        list.put("Discovery World","Discovery World");
        list.put("DTX","DTX");
        list.put("English Club TV","English Club TV");
        list.put("Eureka HD","Eureka HD");
        list.put("EuroNews","EuroNews");
        list.put("Europa Plus TV","Europa Plus TV");
        list.put("Eurosport",consts.c_Eurosport);
        list.put("Eurosport 1",consts.c_Eurosport);
        list.put("Eurosport 2","Eurosport 2");
        list.put("Eurosport 2 HD","Eurosport 2 HD");
        list.put("Eurosport HD","Eurosport HD");
        list.put("Eurosport News","Eurosport News");
        list.put("Extreme Sports","Extreme Sports");
        list.put("Fashion One HD","Fashion One HD");
        list.put("Fashion One",consts.c_FashionOne);
        list.put("Fashion TV",consts.c_FashionTV);
        list.put("Filmbox","Filmbox");
        list.put("Fine Living Network","Fine Living Network");
        list.put("Food Network","Food Network");
        list.put("FOX","Fox");
        list.put("Fox HD","Fox HD");
        list.put("Fox Life","Fox Life");
        list.put("Fox Life HD","Fox Life HD");
        list.put("Galaxy TV","Galaxy");

        list.put("Gulli","Gulli");
        list.put("HD Life","HD Life");
        list.put("History","History");
        list.put("History Россия HD","History Россия HD");
        list.put("ID xtra","ID xtra");
        list.put("IQ HD","IQ HD");
        list.put("JimJam","JimJam");
        list.put("Live Music Channel","Live Music Channel");
        list.put("Mezzo","Mezzo");
        list.put("Mezzo Live HD","Mezzo Live HD");
        list.put("MGM","MGM");
        list.put("MGM HD","MGM HD");
        list.put("MTV Hits International", consts.c_MTVHitsInternational);
        list.put("MTV Hits","MTV Hits International");

        list.put("MTV Live HD","MTV Live HD");
        list.put("MTV Россия","MTV Россия");
        list.put("Nat Geo WILD","Nat Geo Wild");
        list.put("Nat Geo Wild HD","Nat Geo Wild HD");
        list.put("National Geographic","National Geographic Channel");
        list.put("National Geographic HD","National Geographic HD");
        list.put("Nature/History HD","Nature/History HD");
        list.put("Nick Jr","Nick Jr");
        list.put("Nickelodeon","Nickelodeon");
        list.put("Nickelodeon HD","Nickelodeon HD");
        list.put("Ocean TV","Ocean TV");
        list.put("Outdoor Channel","Outdoor Channel");
        list.put("Paramount Channel","Paramount Channel");
        list.put("Paramount Comedy","Paramount Comedy");
        list.put("RTG HD","RTG HD");
        list.put("RTG Interantional","RTG Interantional");
        list.put("RTG TV","RTG TV");
        list.put("RTVI","RTVI");
        list.put("Ru.TV","RU.TV");
        list.put("Rusong TV","Rusong TV");
        list.put("Russia Today","Russia Today");
        list.put("RUSSIAN MUSICBOX","RUSSIAN MUSICBOX");
        list.put("SET","SET");
        list.put("SONY SCI-FI","SONY Sci-Fi");
        list.put("Sony Turbo","Sony Turbo");
        list.put("Sony ТВ HD","Sony ТВ HD");
        list.put("STV-ЖАРА","STV-ЖАРА");
        list.put("TiJi","TiJi");
        list.put("TLC","TLC");
        list.put("Topsong TV","Topsong TV");
        list.put("Travel Channel",consts.c_TravelChannel);
        list.put("Travel",consts.c_TravelChannel);
        list.put("Travel Channel HD","Travel Channel HD");
        list.put("Travel+Adventure","Travel and Adventure");
        list.put("Travel+Adventure HD","Travel and Adventure HD");
        list.put("TV XXI","TV XXI");
        list.put("TV1000","ТВ 1000");
        list.put("TV1000 Action","ТВ 1000-Экшн");
        list.put("TV1000 Comedy HD","TV 1000 Comedy");
        list.put("ViP Comedy","TV 1000 Comedy");

        list.put("TV1000 Megahit HD","TV1000 Megahit HD");
        list.put("TV1000 Premium HD","TV1000 Premium HD");
        list.put("TV1000 Русское кино","ТВ-1000. Русское кино");
        list.put("VH1 Classic","VH1 Classic");
        list.put("VH1 European","VH1 European");
        list.put("Viasat Explore",consts.c_ViasatExplore);
        list.put("Viasat Explorer",consts.c_ViasatExplore);
        list.put("Viasat History","Виасат Хистори");
        list.put("Viasat Nature CEE","Виасат Нэйчер");
        list.put("Viasat Sport","Спортивный телеканал Виасат");
        list.put("World Fashion Channel","");
        list.put("Zee-TV","Zee Russia");
        list.put("Zooпарк","Zooпарк");
        list.put("Авто 24",consts.c_Авто24);
        list.put("Авто24 HD",consts.c_Авто24);
        list.put("Авто Плюс","Авто Плюс");
        list.put("Авто+","Авто Плюс");
        list.put("Анекдот ТВ","Анекдот ТВ");
        list.put("Арт-Видео","Арт-Видео");
        list.put("Беларусь 1","Беларусь 1");
        list.put("Беларусь 2","Беларусь 2");
        list.put("Беларусь 3","Беларусь 3");
        list.put("Беларусь 4 Брест","Беларусь 4 Брест");
        list.put("Беларусь 4 Витебск","Беларусь 4 Витебск");
        list.put("Беларусь 4 Гомель","Беларусь 4 Гомель");
        list.put("Беларусь 4 Гродно","Беларусь 4 Гродно");
        list.put("Беларусь 4 Могилёв","Беларусь 4 Могилёв");
        list.put("Беларусь 5","Беларусь 5");
        list.put("Беларусь-24","Беларусь-24");
        list.put("БелБизнесЧенел (ББЧ)",consts.c_БелБизнесЧенел);
        list.put("БелБизнесЧенел (РБК)",consts.c_БелБизнесЧенел);
        list.put("БЕЛМУЗТВ","БЕЛМУЗТВ");
        list.put("Бестселлер Плюс","Бестселлер Плюс");
        list.put("Бобёр","Бобёр");
        list.put("Бокс ТВ","Бокс ТВ");
        list.put("БУГ-ТВ Брест","БУГ-ТВ Брест");
        list.put("В гостях у сказки","В гостях у сказки");
        list.put("Вопросы и ответы","Вопросы и ответы");
        list.put("Время","Время");
        list.put("ВТВ","ВТВ");
        list.put("Гродно Плюс","Гродно Плюс");
        list.put("Губерния 33","Губерния 33");
        list.put("Детский",consts.c_Детский);
        list.put("Детский канал",consts.c_Детский);
        list.put("Детский мир","Детский мир");
        list.put("Детский мир + Телеклуб","Детский мир + Телеклуб");
        list.put("Дождь","Дождь");
        list.put("Доктор","Доктор");
        list.put("Дом кино","Дом кино");
        list.put("Дом Кино Премиум","Дом Кино Премиум");
        list.put("ДОМАШНИЕ ЖИВОТНЫЕ","Домашние животные");
        list.put("Домашний","Домашний");
        list.put("Домашний Int","Домашний I");
        list.put("Домашний кинотеатр HD","Домашний кинотеатр HD");
        list.put("Домашний Магазин","Домашний Магазин");
        list.put("Драйв","Драйв");
        list.put("Еврокино","Еврокино");
        list.put("ЕГЭ ТВ","ЕГЭ ТВ");
        list.put("ЕДА HD","ЕДА HD");
        list.put("Живая планета","Живая планета");
        list.put("ЖИВИ!","ЖИВИ!");
        list.put("Загородная жизнь","Загородная жизнь");
        list.put("Загородный","Загородный");
        list.put("Звезда","Звезда");
        list.put("Здоровое ТВ","Здоровое ТВ");
        list.put("Зоо ТВ","Зоо ТВ");
        list.put("Иллюзион+",consts.c_ИллюзионPlus);
        list.put("Иллюзион +",consts.c_ИллюзионPlus);
        list.put("Индийское кино","Индийское кино");
        list.put("Инфо ТВ-КОМ (Кричев)","Инфо ТВ-КОМ (Кричев)");
        list.put("История","История");
        list.put("Калейдоскоп ТВ","Калейдоскоп ТВ");
        list.put("Канал Disney","Канал Disney");
        list.put("Карусель","Карусель");
        list.put("Карусель INT",consts.c_КарусельINT);
        list.put("Карусель International",consts.c_КарусельINT);
        list.put("КВН ТВ","КВН ТВ");
        list.put("Кино ТВ","Кино ТВ");
        list.put("Кинозал SD1","Кинозал SD1");
        list.put("Кинозал SD2","Кинозал SD2");
        list.put("Кинозал SD3","Кинозал SD3");
        list.put("Кинокомедия","Кинокомедия");
        list.put("КИНОМИКС","Киномикс");
        list.put("Кинопоказ","Кинопоказ");
        list.put("КиноПремиум HD","КиноПремиум HD");
        list.put("Кинопремьера HD",consts.c_КинопремьераHD);
        list.put("Кинопремьера",consts.c_КинопремьераHD);
        list.put("Киносвидание","Киносвидание");
        list.put("Киносемья","Киносемья");
        list.put("Киносерия","Киносерия");
        list.put("Кинохит","Кинохит");
        list.put("Комедия","Комедия ТВ");
        list.put("Конный мир HD","Конный мир HD");
        list.put("Красная линия","Красная линия");
        list.put("Кто есть кто","Кто есть кто");
        list.put("Культура",consts.c_Культура);
        list.put("Россия Культура",consts.c_Культура);

        list.put("Кухня ТВ","Кухня ТВ");

        list.put("КХЛ",consts.c_КХЛ);
        list.put("КХЛ-ТВ",consts.c_КХЛ);
        list.put("КХЛ HD",consts.c_КХЛHD);
        list.put("ЛДПР ТВ","ЛДПР ТВ");
        list.put("Лида ТВ","Лида ТВ");
        list.put("Ля-Минор",consts.c_ЛяМинор);
        list.put("Мама",consts.c_Мама);
        list.put("Мать и дитя",consts.c_Мама);

        list.put("Матч HD","Матч HD");
        list.put("Матч ТВ","Матч ТВ");
        list.put("МАТЧ! АРЕНА","МАТЧ! АРЕНА");
        list.put("МАТЧ! АРЕНА HD","МАТЧ! АРЕНА HD");
        list.put("МАТЧ! Боец","МАТЧ! Боец");
        list.put("Матч! Игра","Матч! Игра");
        list.put("МАТЧ! НАШ СПОРТ","МАТЧ! НАШ СПОРТ");
        list.put("Матч! Планета","Матч! Планета");
        list.put("Матч! Футбол 1","Матч! Футбол 1");
        list.put("Матч! Футбол 3","Матч! Футбол 3");
        list.put("Матч! Футбол2","Матч! Футбол2");
        list.put("Мать и дитя","Мать и дитя");
        list.put("Минск 24 ДОК","Минск 24ДОК");
        list.put("МИР","Мир");
        list.put("МИР 24","МИР 24");
        list.put("Мир HD","Мир HD");
        list.put("Мир сериала","Мир сериала");
        list.put("Морской","Морской (nautical channel)");
        list.put("Моя Планета","Моя Планета");
        list.put("Мужское кино","Мужское кино");
        list.put("Мужской","Мужской");
        list.put("МУЗ-ТВ","МУЗ-ТВ");
        list.put("Музыка Первого","");
        list.put("МУЛЬТ","Мульт");
        list.put("Мультимания","Мультимания");
        list.put("Нано ТВ","Нано ТВ");
        list.put("Настоящее Страшное Телевидение","Настоящее Страшное Телевидение");
        list.put("Наука 2.0","Наука");
        list.put("Наш Город Плюс","Наш Город Плюс");
        list.put("Наш футбол","Наш футбол");
        list.put("НТВ-ПЛЮС Наш Футбол","Наш футбол");
        list.put("Наш футбол HD","Наш футбол HD");
        list.put("Наше любимое кино","Наше любимое кино");
        list.put("Наше Новое Кино","Наше Новое Кино");
        list.put("Наше ТВ (Витебск)","Наше ТВ (Витебск)");
        list.put("Новый Канал","Новый Канал");
        list.put("Ностальгия","Ностальгия");
        list.put("НТВ","НТВ");
        list.put("НТВ HD","НТВ HD");
        list.put("НТВ Беларусь","НТВ Беларусь");
        list.put("ОНТ","ОНТ");
        list.put("ОНТ (Беларусь)","ОНТ");
        list.put("Оружие","Оружие");
        list.put("Остросюжетное HD","Остросюжетное HD");
        list.put("Открытый мир","Открытый мир");
        list.put("ОТР","ОТР");
        list.put("Охота и рыбалка","Охота и рыбалка");
        list.put("Охотник и Рыболов","Охотник и Рыболов");
        list.put("Охотник и Рыболов HD","Охотник и Рыболов HD");
        list.put("Первый (Европа)","Первый (Европа)");
        list.put("Первый (Россия)","Первый (Россия)");
        list.put("Первый HD","Первый HD");
        list.put("Первый образовательный","Первый образовательный");
        list.put("Первый Ярославский","Первый Ярославский");
        list.put("Перец International","Перец I");
        list.put("Планета HD","Планета HD");
        list.put("Плюс ТВ","Плюс ТВ");
        list.put("Психология 21","Психология 21");
        list.put("Пятница","Пятница");
        list.put("Пятница +2","Пятница +2");
        list.put("Радость Моя","Радость Моя");
        list.put("Раз ТВ","Раз ТВ");
        list.put("РБК","РБК");
        list.put("Рен ТВ","Рен ТВ");
        list.put("Ретро ТВ","Ретро ТВ");
        list.put("Речица ТВ","Речица ТВ");
        list.put("Родное кино","Родное кино");
        list.put("Россия 1","Россия 1");
        list.put("Россия 24","Россия 24");
        list.put("РОССИЯ HD","РОССИЯ HD");
        list.put("Россия-Планета","Россия-Планета");
        list.put("РТВ - Любимое кино","Любимое кино");
        list.put("РТР Беларусь","РТР Беларусь");
        list.put("Русский бестселлер","Русский бестселлер");
        list.put("Русский детектив","Русский детектив");
        list.put("Русский Иллюзион","Русский Иллюзион");
        list.put("Русский роман","Русский роман");
        list.put("Русский Экстрим","Русское экстремальное телевидение");
        list.put("Рыжий","Рыжий");
        list.put("Сарафан ТВ",consts.c_Сарафан);
        list.put("Светлое ТВ","Светлое ТВ");
        list.put("Сетанта Спорт",consts.c_СетантаСпорт);
        list.put("Сетанта Спорт Плюс",consts.c_СетантаСпортПлюс );
        list.put("Setanta Sports +",consts.c_СетантаСпортПлюс );

        list.put("Скиф Витебск","Скиф Витебск");
        list.put("Совершенно секретно","Совершенно секретно");
        list.put("Союз","Союз");
        list.put("Спас","Спас");
        list.put("Спорт 1 (укр)","Спорт 1 (укр)");
        list.put("Спорт 1 (Украина)","Спорт 1 (укр)");
        list.put("Спорт 2 (Украина)","Спорт 2 (укр)");
        list.put("Спорт 2 (укр)","Спорт 2 (укр)");
        list.put("СТБ","СТБ");
        list.put("СТВ","СТВ");
        list.put("STV","СТВ");
        list.put("Страна","Страна");
        list.put("СТС","СТС");
        list.put("СТС International","СТС International");
        list.put("СТС Love","СТС Love");
        list.put("Т24","Т24");
        list.put("24 Техно","Т24");
        list.put("ТВ-3","ТВ-3");
        list.put("ТВ-Центр","ТВ-Центр");
        list.put("ТВ3 +2","ТВ3 +2");
        list.put("ТВ3 Минск","ТВ 3");
        list.put("Твой Дом","Твой Дом");
        list.put("ТДК","ТДК");
        list.put("Театр","Театр");
        list.put("Телеканал Витебск","Телеканал Витебск");
        list.put("Телекафе","Телекафе");
        list.put("Телепутешествия","Телепутешествия");
        list.put("ТК ВАРИАНТ","ТК ВАРИАНТ");
        list.put("ТНВ","ТНВ");
        list.put("ТНТ","ТНТ");
        list.put("ТНТ +2","ТНТ +2");
        list.put("ТНТ 4","ТНТ 4");
        list.put("ТНТ International","ТНТ Int");
        list.put("ТНТ Music","ТНТ Music");
        list.put("ТНТ-Comedy","ТНТ-Comedy");
        list.put("Тонус ТВ","Тонус ТВ");
        list.put("Точка ТВ","Точка ТВ");
        list.put("Три Ангела","Три Ангела");
        list.put("ТРК Киев","ТРК Киев");
        list.put("ТРО","ТРО");
        list.put("Усадьба","Усадьба-ТВ");
        list.put("Успех","Успех");
        list.put("Феникс+ Кино",consts.c_ФениксPlusКино);
        list.put("Феникс+Кино",consts.c_ФениксPlusКино);

        list.put("Футбол","Футбол");
        list.put("Футбол 1(укр)","Футбол 1(укр)");
        list.put("Футбол 2(укр)","Футбол 2(укр)");
        list.put("Че","Че");
        list.put("Шансон ТВ","Шансон-TB");
        list.put("Эгоист ТВ","Эгоист ТВ");
        list.put("Ю ТВ","Ю ТВ");
        list.put("Юмор BOX","Юмор BOX");
        return Collections.unmodifiableMap(list);
    }
    public static String NormalazeChannelName(String channelName, Map<String, String> list){
        if (list.containsKey(channelName))
        {
            return list.get(channelName);}
        else return channelName;
    }



    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }



    public static long bytesToLong(byte[] b) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }
        return result;
    }


    public static byte[] intToBytes(int l) {
        byte[] result = new byte[4];
        for (int i = 3; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 4;
        }
        return result;
    }



    public static int bytesToInt(byte[] b) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result <<= 4;
            result |= (b[i] & 0xFF);
        }
        return result;
    }
public static byte[] CombyneBytes(byte[] one , byte[] two ) {

    byte[] combined = new byte[one.length + two.length];

    for (int i = 0; i < combined.length; ++i) {
        combined[i] = i < one.length ? one[i] : two[i - one.length];
    }
    return combined;
   }

    public static byte[] AddSizeToBytes(byte[] one) {

        int size = one.length;
        byte[] two = intToBytes(size);

        return CombyneBytes(two, one);
    }

    public static Boolean Preference_ShowNotes(){
        return settingPreference.getBoolean(MainActivity.APP_PREFERENCES_SHOWNOTES, true);

    }


    public static void WritePreference_ShowNotes(Boolean ShowNotes){
        SharedPreferences.Editor editor = settingPreference.edit();
        editor.putBoolean(MainActivity.APP_PREFERENCES_SHOWNOTES, ShowNotes);
        editor.commit();
    }
}
