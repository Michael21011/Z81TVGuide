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
    String[] data = {"МТИС", "Programtv.ru"};
    String[] favoriteSet = {"МТИС",  "Аксиома-сервис", "Атлант Телеком","Космос ТВ","НТВ-Плюс","Триколор","Zala"};
    String[][] favoritProgram={
            /* 0 МТИС */ {"Охота и рыбалка"},
            /* 1 Аксиома-сервис */ {"Беларусь-1","ТВ-3","EuroNews","Усадьба","Культура","TV1000 Action","Охота и рыбалка","МИР","Шансон-TB","Mezzo","Nickelodeon"},
            /* 1 Атлант Телеком */ {},
            /* 1 Космос ТВ */ {},
            /* 1 НТВ-Плюс */ {},
            /* 1 Триколор */ {},
            /* 1 Zala */ {},
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
                    ed.putBoolean(favoritProgram[position][i], true);
                }
                ed.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}

