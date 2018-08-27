package info.z81.z81tvguide;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;


/**
 * A login screen that offers login via email/password.
 */

    public class SettingsActivity extends Activity {
    String[] data = {"Этот провайдер больше не доступен", "teleguide.info", "epg.by","Cosmos","telecom.by"};
    String[] favoriteSet = {"МТИС Цифровой", "Аксиома-сервис", "Атлант Телеком", "Космос ТВ *в планах*", "НТВ-Плюс *в планах*", "Триколор *в планах*", "Zala Базовый", "Zala Все включено","Voka"};
    String[][] favoritProgram = {
            /* 0 МТИС Цифровой*/ {
                consts.c_PlustTV,
                consts.c_1HD,
            consts.c_8Channel,
            consts.c_A2,
            consts.c_EuroNews,
            consts.c_EuropaPlusTV,
            consts.c_EnglishClubTV,
            consts.c_FashionOneHD,
            consts.c_FOX,
            consts.c_Galaxy,
            consts.c_Gulli,
            consts.c_HDLife,
            consts.c_History,
            consts.c_IQHD,
            consts.c_MTVHitsInternational,
            consts.c_NationalGeographic,
            consts.c_Nickelodeon,
            consts.c_ParamountComedy,
            consts.c_RuTV,

            consts.c_SONYSCIFI,
            consts.c_SonyTurbo,
            consts.c_TV1000,
            consts.c_TV1000Action,
            consts.c_TV1000ComedyHD,
            consts.c_TV1000Русскоекино,
            consts.c_ViasatExplore,
            consts.c_ViasatHistory,
            consts.c_ViasatNatureCEE,
            consts.c_АвтоПлюс,
            consts.c_БЕЛМУЗТВ,
            consts.c_Беларусь1,
            consts.c_Беларусь2,
            consts.c_Беларусь3,
            consts.c_Беларусь5,
            consts.c_ВТВ,
            consts.c_ДОМАШНИЕЖИВОТНЫЕ,
            consts.c_Домашний,
            consts.c_Домкино,
            consts.c_КИНОМИКС,
            consts.c_Киносемья,
            consts.c_КХЛHD,
            consts.c_КарусельINT,
            consts.c_Культура,
            consts.c_ЛяМинор,
            consts.c_МинскТВ,
            consts.c_МИР,
            consts.c_МирHD,
            consts.c_МУЛЬТ,
            consts.c_МояПланета,
            consts.c_Мужскоекино,
            consts.c_НТВБеларусь,
            consts.c_Наука2,
            consts.c_НашфутболHD,
            consts.c_ОНТ,
            consts.c_Охотаирыбалка,
            consts.c_ПерецInternational,
            consts.c_РБК,
            consts.c_РТРБеларусь,
            consts.c_РусскийИллюзион,
            consts.c_Сарафан,
            consts.c_СветлоеТВ,
            consts.c_СетантаСпорт,
            consts.c_СетантаСпортПлюс,
            consts.c_СТВ,
            consts.c_ТВ3,
            consts.c_Сарафан,
            consts.c_Союз,
            consts.c_Т24,
            consts.c_ТНТ,
            consts.c_ТРО,
            consts.c_Усадьба,
            consts.c_ШансонTB},
            
            /* 1 Аксиома-сервис */ {"+TV Belviasat", "365 Дней", consts.c_8Channel, "CNN International", consts.c_DiscoveryChannel, consts.c_EuroNews, consts.c_Eurosport, consts.c_Eurosport2, "JimJam", "Mezzo", consts.c_NationalGeographic, consts.c_Nickelodeon, consts.c_RTVI, consts.c_TV1000,
            consts.c_TV1000Action, consts.c_TV1000Русскоекино, "VH1 European", consts.c_ViasatExplore, consts.c_ViasatHistory, "VViasat Nature CEE", consts.c_БЕЛМУЗТВ, consts.c_Беларусь1, consts.c_Беларусь2, consts.c_Беларусь3, consts.c_Беларусь5, consts.c_РБК, consts.c_ВТВ,
            consts.c_Детский, consts.c_Детскиймир, consts.c_ИллюзионPlus, consts.c_КИНОМИКС, consts.c_МИР, consts.c_НТВБеларусь, consts.c_Наука2, consts.c_РТВЛюбимоекино, consts.c_Ностальгия, consts.c_ОНТ, consts.c_Охотаирыбалка, consts.c_РТРБеларусь, consts.c_Культура, consts.c_СТВ, consts.c_СетантаСпорт, consts.c_Союз,
            consts.c_ТВ3, "ТВ-3 Беларусь", consts.c_TVXXI, consts.c_ТНТ, "Телемагазин", consts.c_Усадьба, consts.c_ШансонTB},

            /* 2 Атлант Телеком */ {consts.c_Т24, consts.c_2multi2, consts.c_8Channel, "Amedia Hit", "Amedia Premium HD", "English Club TV", consts.c_EuroNews, consts.c_EuropaPlusTV, "Fox HD", "Fox Life HD",
            consts.c_HDLife, consts.c_History, "History HD", "MCM Top", "MTV Россия",
            "MTV Dance", "MTV Live HD", "MTV Rocks", "Mezzo", "Mezzo Live HD", "Nat Geo Wild HD", consts.c_NationalGeographic, "National Geographic HD", consts.c_NickJr, consts.c_Nickelodeon, consts.c_NickelodeonHD,
            consts.c_ParamountComedy, consts.c_RTVI, consts.c_TV1000, consts.c_TV1000Action,
            consts.c_TV1000Русскоекино, consts.c_TiJi, consts.c_TravelChannel, "Travel Channel HD", "Travel+Adventure HD", "VH1 European", "VH1 Classic", consts.c_ViasatExplore, consts.c_ViasatHistory, consts.c_ViasatNatureCEE,
            "Viasat Nature/History HD", consts.c_АвтоПлюс, consts.c_РБК,
            consts.c_Беларусь1, consts.c_Беларусь2, consts.c_Беларусь3, consts.c_Беларусь5, consts.c_БЕЛМУЗТВ, "Русский бестселлер (международный)", consts.c_ВТВ, consts.c_Детский, consts.c_Домашний, consts.c_Драйв, consts.c_Еврокино, "Здоровое Телевидение", "Зоопарк",
            consts.c_ИллюзионPlus, consts.c_Индийскоекино, consts.c_КХЛ, consts.c_КарусельINT, "Кинозал 1", "Кинозал 2", "Кинозал HD", consts.c_КИНОМИКС, "Кинопремьера", consts.c_Киносемья, consts.c_Кинохит, consts.c_Культура, consts.c_КухняТВ, consts.c_Мама, consts.c_МИР, consts.c_МояПланета,
            consts.c_Мужскоекино, consts.c_МУЛЬТ, consts.c_Мультимания, consts.c_НТВБеларусь, consts.c_Нашфутбол, "Настоящее Страшное ТВ", consts.c_Наука2, consts.c_Ностальгия, consts.c_ОНТ, consts.c_Оружие, consts.c_Охотаирыбалка, "Охотник и Рыболов HD", "Первый Музыкальный HD", consts.c_ПерецInternational,
            consts.c_РТРБеларусь, consts.c_РетроТВ, "Родное кино", consts.c_РусскийЭкстрим, consts.c_СТВ, consts.c_Сарафан, consts.c_СетантаСпорт, consts.c_СетантаСпортПлюс, consts.c_Совершенносекретно, consts.c_Союз, consts.c_ТВ3, consts.c_ТНТ, consts.c_Усадьба, consts.c_ФениксPlusКино, consts.c_ШансонTB},

            /* 3 Космос ТВ */ {consts.c_Беларусь1, consts.c_Беларусь2, consts.c_Беларусь3, consts.c_Беларусь5},
            /* 4 НТВ-Плюс */ {consts.c_Беларусь1, consts.c_Беларусь2, consts.c_Беларусь3, consts.c_Беларусь5},
            /* 5 Триколор */ {consts.c_Беларусь1, consts.c_Беларусь2, consts.c_Беларусь3, consts.c_Беларусь5},
            /* 6 Zala */ {consts.c_8Channel, consts.c_A2, consts.c_Драйв, consts.c_EuroNews, consts.c_EuropaPlusTV, consts.c_History, "MIНСК TV", consts.c_NickJr, consts.c_RTVI, consts.c_RuTV, consts.c_SONYSCIFI, consts.c_СТВ,
            consts.c_ViasatExplore, consts.c_ViasatHistory, consts.c_ViasatNatureCEE, consts.c_TV1000, consts.c_TV1000Русскоекино,
            consts.c_Беларусь1, consts.c_Беларусь2, consts.c_Беларусь3, consts.c_Беларусь4, consts.c_Беларусь5, "БелБизнесЧенел", consts.c_РБК, consts.c_БЕЛМУЗТВ, "БУГ-ТВ", "Варяг", consts.c_ВТВ,
            "Гомель ТВ", "Гродно Плюс", consts.c_Детскиймир, consts.c_Домашний, consts.c_Еврокино, consts.c_КИНОМИКС, "Лида ТВ", consts.c_РТВЛюбимоекино, consts.c_МИР, consts.c_МояПланета, consts.c_Мужскоекино, consts.c_Наука2, consts.c_НТВБеларусь,
            consts.c_ОНТ, consts.c_Охотаирыбалка, "Первый музыкальный канал", consts.c_ПерецInternational, "Пинск", consts.c_Культура,
            consts.c_РТРБеларусь, consts.c_РусскийИллюзион, consts.c_Сарафан, consts.c_СТВ, consts.c_TVXXI, consts.c_ТВ3, "Телеканал «+TV»", "Телеканал «СИТИ»", "Телеканал «ТВТ»", consts.c_2multi2, "Телемагазин",
            consts.c_ТНТ, consts.c_Усадьба, consts.c_ШансонTB, consts.c_SonyTurbo, consts.c_Кинокомедия},

            /* 7 Zala Все включено */ {consts.c_Т24, consts.c_8Channel, consts.c_AnimalPlanet, consts.c_Boomerang, consts.c_CartoonNetwork, consts.c_DiscoveryChannel, consts.c_EurekaHD, consts.c_EuroNews, consts.c_EuropaPlusTV,
            consts.c_Eurosport2, consts.c_Eurosport, consts.c_FineLivingNetwork, consts.c_Galaxy, consts.c_Gulli, consts.c_History, "MIНСК TV", consts.c_NickJr, consts.c_RTVI, consts.c_RuTV, consts.c_SONYSCIFI, consts.c_СТВ,
            consts.c_TVXXI, consts.c_TV1000Action, consts.c_TV1000Русскоекино, consts.c_TV1000, consts.c_TiJi, consts.c_TravelChannel, consts.c_TravelAdventure, consts.c_ViasatExplore, consts.c_ViasatHistory, consts.c_ViasatNatureCEE,
            consts.c_A2, "БУГ-ТВ",
            "БелБизнесЧенел", consts.c_БЕЛМУЗТВ, consts.c_Беларусь1, consts.c_Беларусь2, consts.c_Беларусь3, consts.c_Беларусь4, consts.c_Беларусь5, consts.c_ВТВ, "Варяг", "Гомель ТВ", "Гродно Плюс", consts.c_Детский, consts.c_Детскиймир,
            consts.c_Домкино, consts.c_Домашний, consts.c_Драйв, consts.c_Еврокино, consts.c_ИллюзионPlus, consts.c_Индийскоекино, consts.c_КХЛ, consts.c_2multi2, consts.c_КИНОМИКС, consts.c_Кинохит, consts.c_КухняТВ, "Лида ТВ", consts.c_МИР,
            consts.c_МояПланета, consts.c_Мужскоекино, consts.c_Мультимания, consts.c_НТВБеларусь, consts.c_Нашфутбол, consts.c_Наука2, consts.c_НашеНовоеКино, consts.c_Ностальгия, consts.c_ОНТ, consts.c_Оружие, consts.c_Охотаирыбалка,
            "Первый музыкальный канал", consts.c_ПерецInternational, "Пинск", consts.c_РБК, consts.c_РТВЛюбимоекино, consts.c_РТВЛюбимоекино, consts.c_РТРБеларусь, consts.c_Культура, consts.c_РусскийИллюзион, consts.c_РусскийЭкстрим,
            consts.c_СТВ, consts.c_Сарафан, consts.c_СетантаСпорт, consts.c_Совершенносекретно, consts.c_Союз, consts.c_Спорт1укр, consts.c_Спорт2укр, consts.c_ТВ3, consts.c_ТНТMusic, consts.c_ТНТ, consts.c_ТРО, "Телеканал «+TV»",
            "Телеканал «СИТИ»", "Телеканал «ТВТ»", "Телемагазин", consts.c_Усадьба, consts.c_ШансонTB, consts.c_ЭгоистТВ, consts.c_Карусель, consts.c_РетроТВ, consts.c_SonyTurbo, consts.c_Кинокомедия},

            /* 8 Voka*/ {


            //consts.c_2-i-gorodskoi-mogilev,
            //consts.c_kino-plus,
            //consts.c_pervyi-muzykalnyi,
            //consts.c_sovetskaya-kinoklassika,
            //consts.c_viasat-nature-history-hd,
            //consts.c_zarubezhnaya-kinoklassika
            //consts.c_Зоопарк,
            //consts.c_Кinozal_hd,
            consts.c_2multi2,
            consts.c_8Channel,
            consts.c_AmediaPremiumHD,
            consts.c_AnimalFamilyHD,
            consts.c_AnimalPlanet,
            consts.c_DiscoveryChannel,
            consts.c_EurekaHD,
            consts.c_EuroNews,
            consts.c_EuropaPlusTV,
            consts.c_Eurosport,
            consts.c_Gulli,
            consts.c_HDLife,
            consts.c_History,
            consts.c_MTVRussia,
            consts.c_NickJr,
            consts.c_Nickelodeon,
            consts.c_RTVI,
            consts.c_RuTV,
            consts.c_TV1000,
            consts.c_TV1000Action,
            consts.c_TV1000Русскоекино,
            consts.c_TravelAdventureHD,
            consts.c_TravelChannel,
            consts.c_ViasatExplore,
            consts.c_ViasatHistory,
            consts.c_ViasatNatureCEE,
            consts.c_ViasatSport,
            consts.c_Авто24,
            consts.c_БЕЛМУЗТВ,
            consts.c_БелБизнесЧенел,
            consts.c_Беларусь1,
            consts.c_Беларусь2,
            consts.c_Беларусь3,
            consts.c_Беларусь5,
            consts.c_ВТВ,
            consts.c_Детский,
            consts.c_Домашний,
            consts.c_ДомашнийInt,
            consts.c_Драйв,
            consts.c_Еврокино,
            consts.c_ЗдоровоеТВ,
            consts.c_ИллюзионPlus,
            consts.c_КХЛ,
            consts.c_КинозалSD1,
            consts.c_КинозалSD2,
            consts.c_КинозалSD3,
            consts.c_КинопремьераHD,
            consts.c_Кинохит,
            consts.c_Культура,
            consts.c_МИР,
            consts.c_Мама,
            consts.c_МояПланета,
            consts.c_Мультимания,
            consts.c_НТВБеларусь,
            consts.c_НастоящееСтрашноеТелевидение,
            consts.c_Наука2,
            consts.c_Нашфутбол,
            consts.c_ОНТ,
            consts.c_Оружие,
            consts.c_Охотаирыбалка,
            consts.c_ОхотникиРыболовHD,
            consts.c_ПерецInternational,
            consts.c_РТРБеларусь,
            consts.c_РетроТВ,
            consts.c_РусскийИллюзион,
            consts.c_РусскийЭкстрим,
            consts.c_СТВ,
            consts.c_Сарафан,
            consts.c_СветлоеТВ,
            consts.c_СетантаСпорт,
            consts.c_СетантаСпортПлюс,
            consts.c_Союз,
            consts.c_Т24,
            consts.c_ТВ3Минск,
            consts.c_ТНТInternational,
            consts.c_ТНТMusic,
            consts.c_Усадьба,
            consts.c_ФениксPlusКино,
            consts.c_ШансонTB,
            // consts.c_amc,
             }

    };
    Spinner sourceSpinner;
    Spinner faforitSpinner;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // адаптер
        ArrayAdapter<String> sourceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, data);
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sourceSpinner = (Spinner) findViewById(R.id.sourceSpinner);
        sourceSpinner.setAdapter(sourceAdapter);
        // заголовок
        sourceSpinner.setPrompt("Источник телепрограммы");
        // выделяем элемент
        int sourceSavedposition = Utils.ReadSharedPreference(Utils.ProgramProviderParamName, 0);
        if (sourceSavedposition > data.length - 1) {
            sourceSavedposition = 0;
        }
        sourceSpinner.setSelection(sourceSavedposition);
        // устанавливаем обработчик нажатия
        sourceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Utils.WriteSharedPreference(Utils.ProgramProviderParamName, position);
                findViewById(R.id.faforitSpinner).setEnabled(position > 0);
                // показываем позиция нажатого элемента
                //   Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        ArrayAdapter<String> favoritAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, favoriteSet);
        favoritAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Favorite Set
        faforitSpinner = (Spinner) findViewById(R.id.faforitSpinner);
        faforitSpinner.setAdapter(favoritAdapter);
        faforitSpinner.setEnabled(sourceSavedposition > 0);

        faforitSpinner.setPrompt("Набор каналов");

        int favoriteSavedposition = Utils.ReadSharedPreference(Utils.FavoritSetParamName, 0);
        if (favoriteSavedposition > favoriteSet.length - 1) {
            favoriteSavedposition = 0;
        }
        faforitSpinner.setSelection(favoriteSavedposition);
        // устанавливаем обработчик нажатия
        faforitSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Utils.WriteSharedPreference(Utils.FavoritSetParamName, position);



            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public void onPause() {
        try {
            SharedPreferences.Editor ed = MainActivity.myChannelsPreference.edit();
            ed.clear();
            if (findViewById(R.id.faforitSpinner).isEnabled()) {
                Map<String, String> normalMap = Utils.GetNormalaizedList();
                for (int i = 0; i < favoritProgram[faforitSpinner.getSelectedItemPosition()].length; i = i + 1) {
                    ed.putBoolean(Utils.NormalazeChannelName(favoritProgram[faforitSpinner.getSelectedItemPosition()][i], normalMap).toUpperCase(), true);
                }
            }
            ed.commit();
        }
        catch (Exception ex)
        {}
        super.onPause();


    }
}

