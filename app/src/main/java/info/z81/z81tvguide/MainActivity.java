package info.z81.z81tvguide;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static SharedPreferences digitalNumberPreference;
    public static SharedPreferences favoriteChannelListPreference;
    public static SharedPreferences knownChannelListPreference;
    public static Resources MyResources;
    private static String TutorialVideoURL="https://www.youtube.com/watch?v=kk3UyIk6sPM";
    private static String FileURL = "http://mtis.by/program_xml.zip";
    //  private static String FileURL = "http://www.teleguide.info/download/new3/xmltv.xml.gz";
    //   private static String FileURL = "https://onedrive.live.com/redir?resid=16289EE13DCB02CD!620&authkey=!ABE_0Cb3lnujxcE&ithint=file%2czip";

    public static String IconDownloadPathMask = "https://raw.githubusercontent.com/Michael21011/Z81TVGuide/RollBecasuLoop/Icons/%s.png";

    private static String FilePreviousURL = "http://mtis.by/program_xml_old.zip";
    private static String WWWFileName = "program_xml.zip";
    //private static String WWWFileName = "xmltv.xml.gz";
    private static String internalFilePath = "z81_program.txt";
    public final static String const_programListIndex = "ProgramListIndex";
    public final static String const_filterString = "FilterString";
    private Z81TVGuide myApplication;

    public NowList nowList;
    ProgressDialog mProgressDialog;
    private Boolean IsForceUnzipFile = false;

    private int XMLLoadProgress = 20;
    private Boolean ShowOnlyFavorites = true;
    private Boolean NeedRefreshList = false;
    private Boolean NeedRebuildList = true;
    private Boolean NeedDownloadFuture = false;
    private Boolean NeedDownloadPast = false;
    public static TVProgram tvProgram;
    private Tracker mTracker;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  if (NeedRefreshList) {
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.task_download_file));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);

        Z81TVGuide application = (Z81TVGuide) getApplication();
        mTracker = application.getDefaultTracker();

        MyResources = getResources();
        nowList = new NowList();
        favoriteChannelListPreference = getPreferences(MODE_PRIVATE);
        digitalNumberPreference = getSharedPreferences("digitalNumbers", MODE_PRIVATE);
        knownChannelListPreference = getSharedPreferences("knownChannel", MODE_PRIVATE);

        setContentView(R.layout.activity_main);
        myApplication = (Z81TVGuide) getApplication();
        if (myApplication.tvProgram == null) {
            myApplication.tvProgram = new TVProgram();
            ;
            tvProgram = myApplication.tvProgram;
            showContentInBackground(null);
        } else {
            tvProgram = myApplication.tvProgram;
            UpdateContentInBackground(this);
        }
        registerForContextMenu(findViewById(R.id.listView1));


        //}

    }

    @Override
    public void onPause() {
        super.onPause();
    /*    if (mProgressDialog != null)
            mProgressDialog.dismiss();
            */
    }

    @Override
    public void onResume() {
        super.onResume();
        /* if (mProgressDialog != null)
            mProgressDialog.dismiss();
            */
        if (NeedRefreshList) {
            NeedRefreshList = false;
            UpdateContentInBackground(this);
            //todo: needrefreshlis parameter for why? showContentInBackground(null);
        }
        AppRater.app_launched(this, mTracker);
        //Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Image~MainActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    protected void updateListView() {

        final ListView lv1 = (ListView) findViewById(R.id.listView1);
        NowAdapter adapter = new NowAdapter(this, nowList);
        lv1.setAdapter(adapter);
        SetListViewListeners();
    }


    protected void sortListViewByTime() {
        nowList.sort(1);
        updateListView();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //Do the initial here
        //menu = popupMenu;
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_popupmenu, menu);
        MenuItem mi = menu.findItem(R.id.menu_main_channel);
        ;
        String ChanellNumber = "";
        if (GetCurrentNowItem().DigitalNumber != -1)
            ChanellNumber = String.format(" Ц:%s", GetCurrentNowItem().DigitalNumber);
        mi.setTitle(String.format("%1$s%2$s", GetCurrentProgramList().ChannelName, ChanellNumber));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // AdapterViewCompat.AdapterContextMenuInfo info = (AdapterViewCompat.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_main_copy:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("ContextItem")
                        .setAction("CopyItem")
                        .build());
                CopyItem();
                return true;
            case R.id.menu_main_channel:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("ContextItem")
                        .setAction("ChannelItem")
                        .build());
                ChannelItem();
                return true;
            case R.id.menu_main_searchInInternet:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("ContextItem")
                        .setAction("SearchInInternetItem")
                        .build());
                SearchInInternetItem();
                return true;
            case R.id.menu_main_share:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("ContextItem")
                        .setAction("ShareItem")
                        .build());
                ShareItem();
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    private void ChannelItem() {

        int index = tvProgram.GetProgramListIndex(GetCurrentNowItem().ChannelId);

        openOneChannelProgram(index);
    }

    private void ShareItem() {

        final ListView lv1 = (ListView) findViewById(R.id.oneChannelProgramListView);

        ProgramItem pi = GetCurrentProgramItem();
        String t = String.format("Программа %1$s на канале %3$s. Начало %2$s", pi.Title, new SimpleDateFormat("dd.MM.yyyy в HH:mm").format(pi.DateStart), GetCurrentProgramList().ChannelName);
        // Toast.makeText(getBaseContext(), String.format("%s", t), Toast.LENGTH_LONG).show();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Телепередача  ");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, t);
        startActivity(Intent.createChooser(sharingIntent, "Отправить через"));
    }

    private ProgramItem GetCurrentProgramItem() {
        NowItem ni = GetCurrentNowItem();
        ProgramList pl = tvProgram.GetProgramList(ni.ChannelId);
        ProgramItem pi = pl.GetItem(ni.PositionInChannel);
        return pi;
    }

    private NowItem GetCurrentNowItem() {
        return (NowItem) nowList.GetItem(currentPosition);
    }

    private ProgramList GetCurrentProgramList() {
        ProgramList pl = tvProgram.GetProgramList(GetCurrentNowItem().ChannelId);
        return pl;
    }

    private void SearchInInternetItem() {

        final ListView lv1 = (ListView) findViewById(R.id.oneChannelProgramListView);


        ProgramItem pi = GetCurrentProgramItem();
        String t = String.format("%1$s", pi.Title, new SimpleDateFormat("dd.MM.yyyy в HH:mm").format(pi.DateStart), GetCurrentProgramList().ChannelName);
        // Toast.makeText(getBaseContext(), String.format("%s", t), Toast.LENGTH_LONG).show();

        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, t);
            startActivity(intent);
        } catch (Exception e) {
            try {
                Uri uri = Uri.parse("https://www.google.com/search?q=" + t);
                Intent gSearchIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(gSearchIntent);
            } catch (Exception e2) {
                ShowInfo(getResources().getString(R.string.error), getResources().getString(R.string.searchInInternetItemErrorDescription));
            }
        }


    }

    private void CopyItem() {

        final ListView lv1 = (ListView) findViewById(R.id.oneChannelProgramListView);


        ProgramItem pi = GetCurrentProgramItem();
        String t = String.format("Программа %1$s на канале %3$s. Начало %2$s", pi.Title, new SimpleDateFormat("dd.MM.yyyy в HH:mm").format(pi.DateStart), GetCurrentProgramList().ChannelName);
        // Toast.makeText(getBaseContext(), String.format("%s", t), Toast.LENGTH_LONG).show();
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip;
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            myClip = ClipData.newPlainText("text", t);
            myClipboard.setPrimaryClip(myClip);
        }
    }

    protected void SetListViewListeners() {
        ListView list = (ListView) findViewById(R.id.listView1);


        list.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("RowClick")
                        .setAction("onItemLongClick")
                        .build());
                currentPosition = position;
            /*    NowItem ni = (NowItem) nowList.GetItem(position);
                openChannelListActivity(ni.ChannelId);
                */


                return false;
            }
        });

        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                currentPosition = position;
                String ChanellNumber = "";
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("RowClick")
                        .setAction("onItemClick")
                        .build());
                NowItem ni = (NowItem) nowList.GetItem(position);
                if (ni.DigitalNumber != -1)
                    ChanellNumber = String.format(" Ц:%s", ni.DigitalNumber);
                Toast.makeText(getBaseContext(), String.format("%s%s", ni.ChannelName, ChanellNumber), Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_ShowOnlyFavorites:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("SwithShowOnlyFavorites")
                        .build());
                SwithShowOnlyFavorites(item);
                return true;
            case R.id.action_search:
                showContentInBackground(null);
                return true;
            case R.id.action_refresh:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Refresh")
                        .build());
                UpdateContentInBackground(this);
                return true;
         /*   case R.id.action_settings:
                sendMessage(null);
                return true;
          */
            case R.id.action_download:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Download")
                        .build());
                downloadList(false);
                return true;
            case R.id.action_channellist:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("OpenChannelList")
                        .build());
                openChannelListActivity(null);
                return true;
            case R.id.action_rate:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Rate")
                        .build());
                AppRater.showRateDialog(this, null, mTracker);
                return true;
            case R.id.action_tutorialVideo:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Tutorial")
                        .build());
                openTutorialVideo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openTutorialVideo() {
        try {
            Uri uri = Uri.parse(TutorialVideoURL);
            Intent tutorialVideIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(tutorialVideIntent);
        } catch (Exception e2) {
            ShowInfo(getResources().getString(R.string.error), getResources().getString(R.string.tutorialErrorDescription));
        }
    }

    private void SwithShowOnlyFavorites(MenuItem item) {
        ShowOnlyFavorites = !ShowOnlyFavorites;
        if (ShowOnlyFavorites) {
            item.setIcon(R.drawable.ic_layout_star_selected);
        } else item.setIcon(R.drawable.ic_layout_star);
        UpdateContentInBackground(this);
        //showContentInBackground(null);
    }

    private void openChannelListActivity(String ChannelName) {
        Intent intent = new Intent(this, ChannelListActivity.class);
        intent.putExtra(MainActivity.const_programListIndex, ChannelName);
        NeedRefreshList = true;
        startActivity(intent);
    }

    public void onChannelLogoClick(View view) {

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("RowClick")
                .setAction("ChannelLogoClick")
                .build());
        Integer tag1 = (Integer) view.getTag();
        String tag = tag1.toString();
        int index = tvProgram.GetProgramListIndex(tag);

        openOneChannelProgram(index);
    }

    public void openOneChannelProgram(int index) {
        Intent intent = new Intent(this, OneChannelProgramActivity.class);
        intent.putExtra(MainActivity.const_programListIndex, index);
        startActivity(intent);
    }


    private void doTest(Object object) {

        DateFormat df = new DateFormat();
        String d = (String) df.format("yyyy-MM-dd hh:mm:ss", new Date());
        String ss = String.format("Time: %1s", d);
        Toast.makeText(getBaseContext(), ss, Toast.LENGTH_LONG).show();
        String dd = "20140510124500";
        int year = Integer.parseInt(dd.subSequence(0, 4).toString());
        int month = Integer.parseInt(dd.subSequence(4, 6).toString());
        int day = Integer.parseInt(dd.subSequence(6, 8).toString());
        int hour = Integer.parseInt(dd.subSequence(8, 10).toString());
        int min = Integer.parseInt(dd.subSequence(10, 12).toString());
        Calendar StartDate = Calendar.getInstance();

        StartDate.set(year, month - 1, day, hour, min);

        Calendar c = Calendar.getInstance();


    }

    private void UpdateContentInBackground(Object object) {
        final UpdateProgramTask updateProgramTask = new UpdateProgramTask(this);
        updateProgramTask.execute(this.getFilesDir().getPath());

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                updateProgramTask.cancel(true);
            }
        });
    }

    private void showContentInBackground(Object object) {
        // execute this when the downloader must be fired

        String DownloadFileName = GetDownloadedFileName();
        File myF = new File(DownloadFileName);
        if ((tvProgram.ChannelCount() > 0) & !NeedRebuildList) {
            UpdateContentInBackground(this);
        } else if (myF.exists()) {

            final ParseFileTask parseFileTask = new ParseFileTask(this);
            parseFileTask.execute(this.getFilesDir().getPath());

            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                    @Override
                                                    public void onCancel(DialogInterface dialog) {
                                                        parseFileTask.cancel(true);
                                                    }
                                                }
            );
        } else
            downloadFile(FileURL);

    }

    private String GetDownloadedFileName() {
        return this.getFilesDir().getPath() + "/" + WWWFileName;
    }


    private void downloadList(boolean old) {
        if (old)
            downloadFile(FilePreviousURL);
        else
            downloadFile(FileURL);

    }

    private boolean downloadFile(String URL) {


        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(this);
        NeedRebuildList = true;
        downloadTask.execute(URL);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                @Override
                                                public void onCancel(DialogInterface dialog) {
                                                    downloadTask.cancel(true);
                                                }
                                            }
        );


        return true;

    }

    private String unpackGZIP(String path, String zipname, String ResultFile) {
        InputStream is;
        GZIPInputStream zis;
        try {
            String filename;
            is = new FileInputStream(path + "/" + zipname);
            zis = new GZIPInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[8196];
            int count;
            filename = "output.xml";

            // Need to create directories if not exists, or
            // it will generate an Exception...
            File fmd = new File(path + filename);
            File outputfile = new File(path, filename);
            FileOutputStream fout = new FileOutputStream(outputfile);

            while ((count = zis.read(buffer)) != -1) {
                fout.write(buffer, 0, count);
            }
            fout.close();
            ResultFile = outputfile.getAbsolutePath();

            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return ResultFile;
    }

    private String unpackZip(String path, String zipname, String ResultFile) {
        InputStream is;
        ZipInputStream zis;
        try {
            String filename;
            is = new FileInputStream(path + "/" + zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null) {
                // zapis do souboru
                filename = ze.getName();

                // Need to create directories if not exists, or
                // it will generate an Exception...
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }
                File outputfile = new File(path, filename);
                FileOutputStream fout = new FileOutputStream(outputfile);

                // cteni zipu a zapis
                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
                //ResultFile = path +'/'+ filename;
                ResultFile = outputfile.getAbsolutePath();
            }

            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return ResultFile;
    }

    private boolean getFavoritesSelected() {
        Map<String, ?> favorites = favoriteChannelListPreference.getAll();
        return favorites.size() > 0;
    }


    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;

        public DownloadTask(Context context) {
            this.context = context;
            tvProgram.Clear();
            mProgressDialog.setMessage(getString(R.string.task_download_file));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }


        @Override
        protected void onPostExecute(String result) {
            // Commented because empty string with it
            //   mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                IsForceUnzipFile = true;
                showContentInBackground(null);
            }
        }

        @Override
        protected String doInBackground(String... sUrl) {
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            // PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            // PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            //  wl.acquire();

            try {
                InputStream input = null;
                OutputStream output = null;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(sUrl[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    // expect HTTP 200 OK, so we don't mistakenly save error report
                    // instead of the file
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return "Server returned HTTP " + connection.getResponseCode()
                                + " " + connection.getResponseMessage();

                    // this will be useful to display download percentage
                    // might be -1: server did not report the length
                    int fileLength = connection.getContentLength();

                    // download the file
                    input = connection.getInputStream();
                    output = new FileOutputStream(context.getFilesDir() + "/" + WWWFileName);

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        // allow canceling with back button
                        if (isCancelled())
                            return null;
                        total += count;
                        // publishing the progress....
                        if (fileLength > 0) // only if total length is known
                            publishProgress((int) (total * 100 / fileLength));
                        output.write(data, 0, count);
                    }
                } catch (Exception e) {
                    return e.toString();
                } finally {
                    try {
                        if (output != null)
                            output.close();
                        if (input != null)
                            input.close();
                    } catch (IOException ignored) {
                    }

                    if (connection != null)
                        connection.disconnect();
                }
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Event")
                        .setAction("DownloadTask")
                        .build());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                //  wl.release();
            }
            return null;
        }
    }

    private class ParseFileTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private ArrayList<String> newChannelList = new ArrayList<String>();

        public ParseFileTask(Context context) {
            this.context = context;
            tvProgram.Clear();
            mProgressDialog.setMessage(getString(R.string.task_load_list));
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            //Commented because empty screen with it
            // mProgressDialog.dismiss();
            Boolean halt = false;
            if (result != null)
                Toast.makeText(context, "Parse file error: " + result, Toast.LENGTH_LONG).show();


            else {
                int val = tvProgram.IsCurrent();
                if (val == 0) {
                    NeedDownloadPast = false;
                    NeedDownloadFuture = false;
                    final UpdateProgramTask updateProgramTask = new UpdateProgramTask(context);
                    updateProgramTask.execute();

                    mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                            @Override
                                                            public void onCancel(DialogInterface dialog) {
                                                                updateProgramTask.cancel(true);
                                                            }
                                                        }

                    );
                    if (!newChannelList.isEmpty()) {
                        String message = "";

                        for (int j = 0; j < newChannelList.size() & j < 10; j++) {
                            message = message + "\t" + newChannelList.get(j) + "\n";
                            if (newChannelList.size() > 10 && j == 9)
                                message = message + "\t...\n";
                        }

                        message = String.format(getResources().getString(R.string.newchannelinfo), message);
                        ShowInfo(getResources().getString(R.string.app_name), message);
                    }
                    /* Show empty notification
                    else {ShowInfo(getResources().getString(R.string.app_name), "No new channel");
                    }
                    */
                } else if (val == 1) {
                    if (NeedDownloadPast) {
                        mProgressDialog.dismiss();
                        ShowAlertAndClose(getResources().getString(R.string.app_name), getResources().getString(R.string.needclosebecausenocurrent));
                        halt = true;
                        //  System.exit(0);
                    }
                    NeedDownloadPast = true;
                    downloadList(true);
                } else if (val == -1) {
                    if (NeedDownloadFuture) {
                        mProgressDialog.dismiss();
                        ShowAlertAndClose(getResources().getString(R.string.app_name), getResources().getString(R.string.needclosebecausenocurrent));
                        halt = true;
                        //System.exit(0);
                    }
                    NeedDownloadFuture = true;
                    if (!halt) {
                        downloadList(false);
                    }
                }


              /*  runOnUiThread(new Runnable() {
                    public void run() {
                        updateListView();
                    }
                });
                */
            }
        }

        @Override
        protected String doInBackground(String... Params) {
            // ParseFileTask
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            //  PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            //  PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,  getClass().getName());
            //  wl.acquire();
            Document document = null;
            try {

                if ((IsForceUnzipFile) || (document == null)) {
                    NeedRebuildList = true;
                    publishProgress((int) (1));
                    String ResultFile = "";
                    if (WWWFileName.contains("gz")) {
                        ResultFile = unpackGZIP(Params[0], WWWFileName, ResultFile);
                    } else {
                        ResultFile = unpackZip(Params[0], WWWFileName, ResultFile);
                    }
                    if (ResultFile.equals("")) {
                        // refreshList(null);
                        return null;
                    }
                    IsForceUnzipFile = false;

                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    try {
                        dbf.setNamespaceAware(false);
                        dbf.setValidating(false);

                        DocumentBuilder db = dbf.newDocumentBuilder();
                        InputStream inputStream = new FileInputStream(ResultFile);
                        // String fresult = getStringFromInputStream(inputStream);
                        // Toast.makeText(context, fresult, Toast.LENGTH_SHORT).show();
                        document = db.parse(inputStream);

                        NodeList checkChannelNodeList = document.getElementsByTagName("channel");
                        boolean wrongEncoding = true;
                        for (int j = 0; j < checkChannelNodeList.getLength(); j++) {
                            Node checkChannelNode = checkChannelNodeList.item(j);
                            Element checkElement = (Element) checkChannelNode;
                            String checkName = checkElement.getElementsByTagName("display-name").item(0).getTextContent();

                            if (checkName.contains("а") || checkName.contains("о") || checkName.contains("и")) {
                                wrongEncoding = false;
                                break;
                            }
                        }
                        if (wrongEncoding) {
                            DocumentBuilder db2 = dbf.newDocumentBuilder();
                            InputStream inputStream2 = new FileInputStream(ResultFile);

                            document = db2.parse(new InputSource(new InputStreamReader(inputStream2, "windows-1251")));
                        }

                    } catch (Exception e) {
                        Log.e("tag", e.getMessage());

                        File f = new File(Params[0] + "/" + WWWFileName);
                        try {
                            f.delete();
                        } catch (Exception ex) {
                            System.err.println(e.getMessage());
                        }
                        ShowAlertAndClose(getResources().getString(R.string.app_name), getResources().getString(R.string.needclosebecausewronhgxml));

                    } finally {

                    }
                }

                publishProgress(XMLLoadProgress);
                String currentDate = "";
                nowList.Clear();
                Calendar c = Calendar.getInstance();
                Date today = new Date();
                today.setTime(c.getTimeInMillis() + (1000 * 5 * 60));
                NodeList programlist = document.getElementsByTagName("programme");
                String channelid = "";
                String description = "";
                Properties pro = new Properties();


                DateFormat df = new DateFormat();


				/* new algoritm for list */
                tvProgram.Clear();
                /* look throw all channel list*/
                NodeList channelNodeList = document.getElementsByTagName("channel");
                String ChannelName = "";
                newChannelList.clear();
                for (int j = 0; j < channelNodeList.getLength(); j++) {
                    Node channelNode = channelNodeList.item(j);
                    Element channelElement = (Element) channelNode;
                    ChannelName = channelElement.getElementsByTagName("display-name").item(0).getTextContent();
                    tvProgram.AddChannel(channelElement.getAttributes().getNamedItem("id").getNodeValue(), ChannelName);
                    if (tvProgram.GetItem(j).IsNew()) {
                        newChannelList.add(ChannelName);
                        tvProgram.GetItem(j).SetKnown();
                    }
                }
                for (int j = 0; j < programlist.getLength(); j++) {
                    publishProgress((int) (XMLLoadProgress + j * (100 - XMLLoadProgress) / programlist.getLength()));
                    Node programNode = programlist.item(j);
                    Element program = (Element) programNode;
                    channelid = program.getAttributes().getNamedItem("channel").getNodeValue();

                    currentDate = program.getAttributes().getNamedItem("start").getNodeValue();
                    NodeList dlist = program.getElementsByTagName("desc");
                    if (dlist.getLength() == 0)
                        description = "";
                    else
                        description = dlist.item(0).getTextContent();

                    tvProgram.AddProgram(channelid, program.getElementsByTagName("title").item(0).getTextContent(), Utils.StringToDate(currentDate), description);
                }

                NeedRebuildList = false;
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Event")
                        .setAction("ParseFileTask")
                        .build());

            } catch (Exception e) {
                System.err.println(e.getMessage());

                File f = new File(Params[0] + "/" + WWWFileName);
                try {
                    f.delete();
                } catch (Exception ex) {
                    System.err.println(e.getMessage());
                }
                ShowAlertAndClose(getResources().getString(R.string.app_name), getResources().getString(R.string.needclosebecausewronhgxml));
            } finally {
                //    wl.release();
            }
            return null;
        }


    }

    private class UpdateProgramTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private String[] catnames = new String[1000];

        public UpdateProgramTask(Context context) {
            this.context = context;
            mProgressDialog.setMessage(getString(R.string.task_update_list));
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            mProgressDialog.dismiss();

            if (result != null)
                Toast.makeText(context, "Parse file error: " + result, Toast.LENGTH_LONG).show();
            else {

                runOnUiThread(new Runnable() {
                    public void run() {
                        updateListView();
                    }
                });
            }


        }


        @Override
        protected String doInBackground(String... Params) {
            // UpdateProgramTask
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            //  PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            //  PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,  getClass().getName());
            //  wl.acquire();

            try {

                boolean FavoritesSelected = getFavoritesSelected();
                String currentDate = "";
                String maxDate = "";
                nowList.Clear();
                Calendar c = Calendar.getInstance();
                Date today = new Date();
                today.setTime(c.getTimeInMillis() + (1000 * 5 * 60));

                for (int i = 0; i < tvProgram.ChannelCount(); i++) {
                    ProgramList pl = tvProgram.GetItem(i);
                    if (!FavoritesSelected || !ShowOnlyFavorites || (favoriteChannelListPreference.getBoolean(pl.ChannelName, false))) {
                        ProgramItem pi = null;
                        int CurrentItemIndex = pl.GetCurrentItemIndex(5);
                        if (CurrentItemIndex != -1) {
                            pi = pl.GetItem(CurrentItemIndex);
                        }

                        if (pi != null) {
                            nowList.Add(pl.ChannelId, pl.ChannelName, pi.Title, pi.DateStart, pi.Description, pl.DigitalNumber, pi.Position);
                        }
                    }
                }
                nowList.sort(1);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Event")
                        .setAction("UpdateProgramTask")
                        .build());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                //    wl.release();
            }
            return null;
        }
    }

    public void ShowAlertAndClose(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title)
                .setMessage(message)
                //.setIcon(R.drawable.ic_android_cat)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                System.exit(0);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void ShowInfo(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title)
                .setMessage(message)
                //.setIcon(R.drawable.ic_android_cat)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }


}

