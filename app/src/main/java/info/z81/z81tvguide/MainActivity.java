package info.z81.z81tvguide;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static ChannelList channelList;
    public static SharedPreferences favoriteChannelListPreference;
    public static Resources MyResources;
    private static String FileURL = "http://mtis.by/program_xml.zip";
    private static String FilePreviousURL = "http://mtis.by/program_xml_old.zip";
    private static String WWWFileName = "program_xml.zip";
    public NowList nowList;
    ProgressDialog mProgressDialog;
    private Boolean IsForceUnzipFile = false;
    private Document d;
    private int XMLLoadProgress = 20;
    private Boolean ShowOnlyFavorites = true;
    private Boolean NeedRefreshList = false;
    private Boolean NeedRebuildList = true;
    private Boolean NeedDownloadFuture = false;
    private Boolean NeedDownloadPast = false;
    public static TVProgram tvProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.task_download_file));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);

        MyResources = getResources();
        nowList = new NowList();
        channelList = new ChannelList();
        favoriteChannelListPreference = getPreferences(MODE_PRIVATE);
        tvProgram = new TVProgram();


        setContentView(R.layout.activity_main);
        showContentInBackground(null);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        if (NeedRefreshList) {
            NeedRefreshList = false;
            showContentInBackground(null);
        }

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

    protected void SetListViewListeners() {
        ListView list = (ListView) findViewById(R.id.listView1);


        list.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tw = (TextView) findViewById(R.id.channel);
                Toast.makeText(getBaseContext(), tw.getText(), Toast.LENGTH_LONG).show();

                return true;
            }
        });

        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                NowItem ni = (NowItem) nowList.GetItem(position);
                Toast.makeText(getBaseContext(), ni.ChannelName, Toast.LENGTH_LONG).show();
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
                SwithShowOnlyFavorites(item);
                return true;
            case R.id.action_search:
                showContentInBackground(null);
                return true;
            case R.id.action_refresh:
                showContentInBackground(null);
                return true;
         /*   case R.id.action_settings:
                sendMessage(null);
                return true;
          */
          /*  case R.id.action_download:
                downloadList(false);
                return true;
            case R.id.action_download_previous:
                downloadList(true);
                return true;

            case R.id.action_test:
                sortListViewByTime();
                return true;
                */
            case R.id.action_channellist:
                openChannelListActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SwithShowOnlyFavorites(MenuItem item) {
        ShowOnlyFavorites = !ShowOnlyFavorites;
        if (ShowOnlyFavorites) {
            item.setIcon(R.drawable.ic_layout_star_selected);
        } else item.setIcon(R.drawable.ic_layout_star);
        showContentInBackground(null);
    }

    private void openChannelListActivity() {
        Intent intent = new Intent(this, ChannelListActivity.class);
        NeedRefreshList = true;
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


    private void showContentInBackground(Object object) {
        // execute this when the downloader must be fired

        String DownloadFileName = GetDownloadedFileName();
        File myF = new File(DownloadFileName);
        if ((tvProgram.ChannelCount() > 0) & !NeedRebuildList) {
            final UpdateProgramTask updateProgramTask = new UpdateProgramTask(this);
            updateProgramTask.execute(this.getFilesDir().getPath());

            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    updateProgramTask.cancel(true);
                }
            });
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
            } finally {
                //  wl.release();
            }
            return null;
        }
    }

    private class ParseFileTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private String[] catnames = new String[1000];

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
                        });
                    } else if (val == 1) {
                        if (NeedDownloadPast) {
                            mProgressDialog.dismiss();
                            Utils.ShowMessage(MainActivity.this, getResources().getString(R.string.app_name), getResources().getString(R.string.needclosebecausenocurrent));
                        }
                        NeedDownloadPast = true;
                        downloadList(true);
                    } else if (val == -1) {
                        if (NeedDownloadFuture) {
                            mProgressDialog.dismiss();
                            Utils.ShowMessage(MainActivity.this, getResources().getString(R.string.app_name), getResources().getString(R.string.needclosebecausenocurrent));
                        }
                        NeedDownloadFuture = true;
                        downloadList(false);
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
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            //  PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            //  PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,  getClass().getName());
            //  wl.acquire();

            try {

                if ((IsForceUnzipFile) || (d == null)) {
                    NeedRebuildList = true;
                    publishProgress((int) (1));
                    String ResultFile = "";
                    ResultFile = unpackZip(Params[0], WWWFileName, ResultFile);
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
                        d = db.parse(inputStream);

                    } catch (Exception e) {
                        Log.e("tag", e.getMessage());
                    } finally {

                    }
                }

                publishProgress(XMLLoadProgress);
                boolean FavoritesSelected = getFavoritesSelected();
                String currentDate = "";
                String maxDate = "";
                nowList.Clear();
                channelList.Clear();
                Calendar c = Calendar.getInstance();
                String CurrentTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime());
                Date today = new Date();
                today.setTime(c.getTimeInMillis() + (1000 * 5 * 60));
                String TimeForProgramCompare = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(today.getTime());
                String currentText = "";
                NodeList programlist = d.getElementsByTagName("programme");
                currentText = "";
                maxDate = "";
                String channelid = "";
                String channelName = "";
                Properties pro = new Properties();
                Properties cha = new Properties();

                DateFormat df = new DateFormat();
                String currentTitleDate = (String) df.format("yyyy-MM-dd hh:mm:ss", new Date());

				/* new algoritm for list */
                tvProgram.Clear();
                /* look throw all channel list*/
                NodeList channelNodeList = d.getElementsByTagName("channel");
                for (int j = 0; j < channelNodeList.getLength(); j++) {
                    Node channelNode = channelNodeList.item(j);
                    Element channelElement = (Element) channelNode;
                    tvProgram.AddChannel(channelElement.getAttributes().getNamedItem("id").getNodeValue(), channelElement.getElementsByTagName("display-name").item(0).getTextContent());
                }
                for (int j = 0; j < programlist.getLength(); j++) {
                    publishProgress((int) (XMLLoadProgress + j * (100 - XMLLoadProgress) / programlist.getLength()));
                    Node programNode = programlist.item(j);
                    Element program = (Element) programNode;
                    channelid = program.getAttributes().getNamedItem("channel").getNodeValue();

                    currentDate = program.getAttributes().getNamedItem("start").getNodeValue();
                    tvProgram.AddProgram(channelid, program.getElementsByTagName("title").item(0).getTextContent(), Utils.StringToDate(currentDate));
                }
                NeedRebuildList = false;

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
                channelList.Clear();
                Calendar c = Calendar.getInstance();
                String CurrentTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime());
                Date today = new Date();
                today.setTime(c.getTimeInMillis() + (1000 * 5 * 60));
                String TimeForProgramCompare = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(today.getTime());
                String currentText = "";
                NodeList programlist = d.getElementsByTagName("programme");
                currentText = "";
                maxDate = "";
                String channelid = "";
                String channelName = "";
                Properties pro = new Properties();
                Properties cha = new Properties();

                DateFormat df = new DateFormat();
                String currentTitleDate = (String) df.format("yyyy-MM-dd hh:mm:ss", new Date());

                for (int i = 0; i < tvProgram.ChannelCount(); i++) {
                    ProgramList pl = tvProgram.GetItem(i);
                    if (!FavoritesSelected || !ShowOnlyFavorites || (favoriteChannelListPreference.getBoolean(pl.ChannelName, false))) {
                        ProgramItem pi = null;
                        for (int j = 0; j < pl.Count(); j++) {
                            Date programDate = pl.GetItem(j).DateStart;
                            if (pi != null) {

                                if (((programDate.compareTo(pi.DateStart) > 0) & (programDate.compareTo(today) <= 0))) {
                                    pi = pl.GetItem(j);

                                }
                            } else pi = pl.GetItem(j);
                        }
                        if (pi != null) {
                            nowList.Add(pl.ChannelId, pl.ChannelName, pi.Title, pi.DateStart);
                        }
                    }
                }
                nowList.sort(1);

            } finally {
                //    wl.release();
            }
            return null;
        }
    }


}

