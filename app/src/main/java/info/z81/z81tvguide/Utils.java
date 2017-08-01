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
import java.util.Date;

/**
 * Created by Michael on 24.04.2015.
 */


public class Utils {
    public final static String ProgramProviderParamName = "program_source_index";
    public final static String FavoritSetParamName = "favorit_set_index";

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
        } else if (ChannelName.equalsIgnoreCase("8 канал")) {
            id = R.drawable.a8moj;
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
        } else if (ChannelName.equalsIgnoreCase("Nat Geo Wild")) {
            id = R.drawable.natgeowild;
        } else if (ChannelName.equalsIgnoreCase("National Geographic Channel")) {
            id = R.drawable.nationalgeographic;
        } else if (ChannelName.equalsIgnoreCase("Nautical Channel")) {
            id = R.drawable.nautical;
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
        } else
            id = R.drawable.empty;


        return id;

    }

    public static Date StringToDate(String InputString) {
        int year = Integer.parseInt(InputString.subSequence(0, 4).toString());
        int month = Integer.parseInt(InputString.subSequence(4, 6).toString());
        int day = Integer.parseInt(InputString.subSequence(6, 8).toString());
        int hour = Integer.parseInt(InputString.subSequence(8, 10).toString());
        int min = Integer.parseInt(InputString.subSequence(10, 12).toString());
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
            result = 0;
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


}
