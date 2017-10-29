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
import android.app.Activity;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;

/**
 * A login screen that offers login via email/password.
 */

    public class SettingsActivity extends Activity {
    String[] data = {"МТИС", "teleguide.info"};
    String[] favoriteSet = {"МТИС Цифровой",  "Аксиома-сервис", "Атлант Телеком","Космос ТВ *в планах*","НТВ-Плюс *в планах*","Триколор *в планах*","Zala Базовый","Zala Все включено"};
    String[][] favoritProgram={
            /* 0 МТИС Цифровой*/ {"8 Канал","БелМузТВ (Беларусь)","Беларусь 1","Беларусь 2","Беларусь 3","Беларусь 5","ВТВ (Первый музыкальный)","Дом кино","Домашний","EuroNews","Europa Plus TV","Fox","Galaxy TV","Gulli","HD Life","History","IQ HD","Карусель International","МИР","МИР HD","MTV Hits","Мульт","Моя Планета","National Geographic","Наука 2.0","НТВ Беларусь","ОНТ (Беларусь)","Paramount Comedy","РТР Беларусь","Русский Иллюзион","RU.TV","Сарафан ТВ","SONY Sci-Fi","Sony Turbo","СТВ","ТВ-3","24 Техно","ТНТ","ТРО","TV1000 Comedy HD","TV1000 Русское кино","TV1000","TV1000 Action","Viasat Explorer","Viasat History","Viasat Nature CEE","Авто Плюс","РБК","Домашние животные","Киномикс","КХЛ HD","Ля-Минор","Мужское кино","Наш Футбол HD","Охота и рыбалка","Россия Культура","Союз","Усадьба"},
            /* 1 Аксиома-сервис */ {"+TV Belviasat","365 Дней","8 канал (Беларусь)","CNN International","Discovery Channel","Euronews","Eurosport","Eurosport 2","JimJam","Mezzo","National Geographic","Nickelodeon","RTVi","TV1000",
            "TV1000 Action","TV1000 Русское кино","VH1 European","Viasat Explorer","Viasat History","VViasat Nature CEE","БелМузТВ (Беларусь)","Беларусь 1","Беларусь 2","Беларусь 3","Беларусь 5","РБК","ВТВ (Первый музыкальный)",
            "Детский канал","Детский мир","Иллюзион +","Киномикс","МИР","НТВ Беларусь","Наука 2.0","РТВ - Любимое кино","Ностальгия","ОНТ (Беларусь)","Охота и рыбалка","РТР Беларусь","Россия Культура","СТВ","Сетанта спорт","Союз",
            "ТВ-3","ТВ-3 Беларусь","TV XXI (TV21)","ТНТ","Телемагазин","Усадьба","Шансон ТВ"},

            /* 2 Атлант Телеком */ {"24 Техно","Канал 2x2","8 канал (Беларусь)","Amedia Hit","Amedia Premium HD","English Club TV","Euronews","Europa Plus TV","Fox HD","Fox Life HD","HD Life","History","History HD","MCM Top","MTV Россия",
            "MTV Dance","MTV Live HD","MTV Rocks","Mezzo","Mezzo Live HD","Nat Geo Wild HD","National Geographic","National Geographic HD","Nick Jr","Nickelodeon","Nickelodeon HD","Paramount Comedy","RTVi","TV1000","TV1000 Action",
            "TV1000 Русское кино","Tiji","Travel","Travel Channel HD","Travel+Adventure HD","VH1 European","VH1 Classic","Viasat Explorer","Viasat History","Viasat Nature CEE","Viasat Nature/History HD","Авто Плюс","РБК",
            "Беларусь 1","Беларусь 2","Беларусь 3","Беларусь 5","БелМузТВ (Беларусь)","Русский бестселлер (международный)","ВТВ (Первый музыкальный)","Детский канал","Домашний","Драйв ТВ","Еврокино","Здоровое Телевидение","Зоопарк",
            "Иллюзион +","Индийское кино","КХЛ-ТВ","Карусель International","Кинозал 1","Кинозал 2","Кинозал HD","Киномикс","Кинопремьера","Киносемья","Кинохит","Россия Культура","Кухня ТВ","Мать и дитя","МИР","Моя Планета",
            "Мужское кино","Мульт","Мультимания","НТВ Беларусь","НТВ-ПЛЮС Наш Футбол","Настоящее Страшное ТВ","Наука 2.0","Ностальгия","ОНТ (Беларусь)","Оружие","Охота и рыбалка","Охотник и Рыболов HD","Первый Музыкальный HD","ПерецI",
            "РТР Беларусь","Ретро ТВ","Родное кино","Русский Экстрим","СТВ","Сарафан ТВ","Сетанта спорт","Сетанта Спорт+ HD","Совершенно Секретно","Союз","ТВ-3","ТНТ","Усадьба","Феникс+Кино","Шансон ТВ"},
            /* 3 Космос ТВ */ {"Беларусь 1","Беларусь 2","Беларусь 3","Беларусь 5"},
            /* 4 НТВ-Плюс */ {"Беларусь 1","Беларусь 2","Беларусь 3","Беларусь 5"},
            /* 5 Триколор */ {"Беларусь 1","Беларусь 2","Беларусь 3","Беларусь 5"},
            /* 6 Zala */ {"8 канал (Беларусь)","А2","Драйв ТВ","EuroNews","Europa Plus TV","History","MIНСК TV","Nick Jr","RTVi","RU.TV","SONY Sci-Fi","STV","Viasat Explorer","Viasat History","Viasat Nature CEE","TV1000","TV1000 Русское кино",
            "Беларусь 1","Беларусь 2","Беларусь 3","Беларусь 4","Беларусь 5","БелБизнесЧенел","РБК","БелМузТВ (Беларусь)","БУГ-ТВ","Варяг","ВТВ (Первый музыкальный)",
            "Гомель ТВ","Гродно Плюс","Детский мир","Домашний","Еврокино","Киномикс","Лида ТВ","РТВ - Любимое кино","МИР","Моя Планета","Мужское кино","Наука 2.0","НТВ Беларусь","ОНТ (Беларусь)","Охота и рыбалка",
            "Первый музыкальный канал","ПерецI","Пинск","Россия Культура",
            "РТР Беларусь","Русский Иллюзион","Сарафан ТВ","СТВ","TV XXI (TV21)","ТВ 3 Россия","ТВ-3","Телеканал «+TV»","Телеканал «СИТИ»","Телеканал «ТВТ»","Канал 2x2","Телемагазин","ТНТ","Усадьба","Шансон ТВ"},
            /* 7 Zala Все включено */ {"24 Техно","8 канал (Беларусь)","Animal Planet","Boomerang","Cartoon Network","Discovery Channel","Eureka HD","EuroNews","Europa Plus TV","Eurosport 2","Eurosport","Fine Living Network","Galaxy TV","Gulli","History","MIНСК TV","Nick Jr","RTVi","RU.TV","SONY Sci-Fi","STV","TV XXI (TV21)","TV1000 Action","TV1000 Русское кино","TV1000","Tiji","Travel","Travel+Adventure","Viasat Explorer","Viasat History","Viasat Nature CEE","А2","БУГ-ТВ","БелБизнесЧенел","БелМузТВ (Беларусь)","Беларусь 1","Беларусь 2","Беларусь 3","Беларусь 4","Беларусь 5","ВТВ (Первый музыкальный)","Варяг","Гомель ТВ","Гродно Плюс","Детский канал","Детский мир","Дом кино","Домашний","Драйв ТВ","Еврокино","Иллюзион +","Индийское кино","КХЛ-ТВ","Канал 2x2","Киномикс","Кинохит","Кухня ТВ","Лида ТВ","МИР","Моя Планета","Мужское кино","Мультимания","НТВ Беларусь","НТВ-ПЛЮС Наш Футбол","Наука 2.0","Наше Новое Кино","Ностальгия","ОНТ (Беларусь)","Оружие","Охота и рыбалка","Первый музыкальный канал","ПерецI","Пинск","РБК","РТВ - Любимое кино","РТВ - Любимое кино","РТР Беларусь","Россия Культура","Русский Иллюзион","Русский Экстрим","СТВ","Сарафан ТВ","Сетанта спорт","Совершенно Секретно","Союз","Спорт 1 (Украина)","Спорт 2 (Украина)","ТВ 3 Россия","ТВ-3","ТНТ Music","ТНТ","ТРО","Телеканал «+TV»","Телеканал «СИТИ»","Телеканал «ТВТ»","Телемагазин","Усадьба","Шансон ТВ","Эгоист ТВ","Карусель","Ретро ТВ",},
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // адаптер
        ArrayAdapter<String> sourceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, data);
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner sourceSpinner = (Spinner) findViewById(R.id.sourceSpinner);
        sourceSpinner.setAdapter(sourceAdapter);
        // заголовок
        sourceSpinner.setPrompt("Источник телепрограммы");
        // выделяем элемент
        int sourceSavedposition= Utils.ReadSharedPreference(Utils.ProgramProviderParamName,0);
        if (sourceSavedposition>data.length-1)
        {
            sourceSavedposition = 0;
        }
        sourceSpinner.setSelection(sourceSavedposition);
        // устанавливаем обработчик нажатия
        sourceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Utils.WriteSharedPreference(Utils.ProgramProviderParamName,position);
                findViewById(R.id.faforitSpinner).setEnabled(position>0);
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
        Spinner faforitSpinner = (Spinner) findViewById(R.id.faforitSpinner);
        faforitSpinner.setAdapter(favoritAdapter);
        faforitSpinner.setEnabled(sourceSavedposition>0);

        faforitSpinner.setPrompt("Набор каналов");

        int favoriteSavedposition= Utils.ReadSharedPreference(Utils.FavoritSetParamName,0);
        if (favoriteSavedposition>favoriteSet.length-1)
        {
            favoriteSavedposition = 0;
        }
        faforitSpinner.setSelection(favoriteSavedposition);
        // устанавливаем обработчик нажатия
        faforitSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Utils.WriteSharedPreference(Utils.FavoritSetParamName,position);


                SharedPreferences.Editor ed = MainActivity.favoriteChannelListPreference.edit();
                ed.clear();

                for (int i=0;i<favoritProgram[position].length; i=i+1){
                    ed.putBoolean(favoritProgram[position][i].toUpperCase(), true);
                }
                ed.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}

