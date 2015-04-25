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
	private static String FileURL = "http://mtis.by/program_xml.zip";
    private static String FilePreviousURL = "http://mtis.by/program_xml_old.zip";
	//private static String FileURL = "http://mail.lewis.com.au/public/xmltv/xmltv.zip";
	private static String WWWFileName = "program_xml.zip";
	private Boolean IsForceUnzipFile = false;
	public NowList nowList;
    public static ChannelList channelList;
	private Document d;
	private int XMLLoadProgress = 20;

    public static SharedPreferences favoriteChannelListPreference;

    public static Resources MyResources;
	
	// declare the dialog as a member field of your activity
	ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		// instantiate it within the onCreate method
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage(getResources().getString(R.string.download_message));
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(false);

        MyResources = getResources();
		nowList = new NowList();
        channelList = new ChannelList();
        favoriteChannelListPreference = getPreferences(MODE_PRIVATE);
		

	           
		
        setContentView(R.layout.activity_main);
        showContentInBackground(null);
        
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }


    protected void updateListView()    {
    	
        final ListView lv1 = (ListView)findViewById(R.id.listView1);
        NowAdapter adapter = new NowAdapter(this, nowList);
        lv1.setAdapter(adapter);
        SetListViewListeners();
    }


    protected void sortListViewByTime() {
    	nowList.sort(1);
    	updateListView();
    }
    protected void SetListViewListeners()    {
    	 ListView list = (ListView)findViewById(R.id.listView1);


 			list.setOnItemLongClickListener(new OnItemLongClickListener() {
 				@Override
 		        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
 					TextView tw = (TextView)findViewById(R.id.channel);
 					  Toast.makeText(getBaseContext(), tw.getText(), Toast.LENGTH_LONG).show();
 		                // ���������� "������", ����� ��������� ������� �����, �����
 		                // onListItemClick ������ �� ���������
 		                return true;
 		        }
 		    });
 			
 			list.setOnItemClickListener(new OnItemClickListener() {
 			      public void onItemClick(AdapterView<?> parent, View view,
 			          int position, long id) {
 			    	  NowItem ni = (NowItem)nowList.GetItem(position);
 			    	  Toast.makeText(getBaseContext(),ni.ChannelName, Toast.LENGTH_LONG).show();
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
            case R.id.action_search:
            	showContentInBackground(null);
                return true;
            case R.id.action_refresh:  
            	showContentInBackground(null);
                return true;
         /*   case R.id.action_settings:
            	sendMessage(null);
                return true;
          */  case R.id.action_download:
            	refreshList(false);
                return true;
            case R.id.action_download_previous:
                refreshList(true);
                return true;

            case R.id.action_test:
            	sortListViewByTime();
                return true;
            case R.id.action_channellist:
                openChannelListActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openChannelListActivity() {
        Intent intent = new Intent(this, ChannelListActivity.class);
        //	EditText editText = (EditText) findViewById(R.id.edit_message);
        //	String message = editText.getText().toString();
      //  intent.putExtra("channel", channelList.);
        startActivity(intent);
    }

    private void doTest(Object object) {
    	
    	DateFormat df = new DateFormat();
    	String d=(String) df.format("yyyy-MM-dd hh:mm:ss", new Date());
    	String ss= String.format("Time: %1s",d);
    	Toast.makeText(getBaseContext(), ss, Toast.LENGTH_LONG).show();
    	 String dd = "20140510124500";
     	int year = Integer.parseInt(dd.subSequence(0, 4).toString());
     	int month = Integer.parseInt(dd.subSequence(4, 6).toString());
     	int day = Integer.parseInt(dd.subSequence(6, 8).toString());
     	int hour = Integer.parseInt(dd.subSequence(8, 10).toString());	    		        	
     	int min = Integer.parseInt(dd.subSequence(10, 12).toString());	    		        	
     	Calendar StartDate = Calendar.getInstance();
     	
     	StartDate.set(year, month-1, day, hour, min);

     	Calendar c = Calendar.getInstance();
     	
   //  String rr=c.getTime().toString();
   //  String rr2=StartDate.getTime().toString();
 	//    long diffMin = (c.getTimeInMillis()-StartDate.getTimeInMillis())/(1000*60);

 	  // Toast.makeText(getBaseContext(),rr+"\n"+rr2, Toast.LENGTH_LONG).show();
 	   
 	   
	}


	private void showContentInBackground(Object object) {
    	// execute this when the downloader must be fired

        String DownloadFileName=GetDownloadedFileName();
        File myF = new File(DownloadFileName);
        if(myF.exists())
        {

            final ParseFileTask parseFileTask = new ParseFileTask(this);
    			parseFileTask.execute(this.getFilesDir().getPath());

    			mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
    			    @Override
    			    public void onCancel(DialogInterface dialog) {
    			    	parseFileTask.cancel(true);
    			    }
    			    }
    			);
        }
        else
            downloadFile(FileURL);
    	
    }
    private String GetDownloadedFileName()    {
        return this.getFilesDir().getPath() +"/"+ WWWFileName;
    }

	private void showContent(Object object) {
		String ResultFile = "";
		ResultFile = unpackZip(this.getFilesDir().getPath(),WWWFileName, ResultFile);
	//	EditText editText = (EditText) findViewById(R.id.edit_message);
	//	editText.setText(ResultFile);
		

		// Load XML for parsing.
	/*	File f = new File("/data/data/com.example.firstapp/files");
		String[] sss = f.list();
		f = new File("/data/com.example.firstapp/files");
		 sss = f.list();
	*/	
		 
try
{
		        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        dbf.setNamespaceAware(false);
		        dbf.setValidating(false);
		        DocumentBuilder db = dbf.newDocumentBuilder();
		        InputStream inputStream = new FileInputStream(ResultFile);
		        Document d=db.parse(inputStream);


		        String currentDate="";
		        String maxDate="";
		       // String CurrentTime = (String) DateFormat.format("yyyyMMddhhmmss", new java.util.Date());
		        Calendar c = Calendar.getInstance();
                Date today = c.getTime();
                today.setTime(c.getTimeInMillis()+(1000*60*60));
                c.setTime(today);

		        String CurrentTime=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime());
		        String currentText="";
		        NodeList programlist = d.getElementsByTagName("programme");
	        	currentText="";
	        	maxDate="";
	        	String channelid="";
	        	Properties pro= new Properties();
	        	Properties cha= new Properties();
	        	for (int j = 0; j < programlist.getLength(); j++) {
	        		Node programNode  = programlist.item(j);
	        		Element program=(Element)programNode;
	        		channelid = program.getAttributes().getNamedItem("channel").getNodeValue();
	        		if (pro.containsKey(channelid))
	        		{
	        			maxDate=pro.getProperty(channelid, "");
	        		}
	        		else
	        		{
	        			pro.put(channelid, "");
	        			maxDate="";
	        		}
	        		


	        			currentDate = program.getAttributes().getNamedItem("start").getNodeValue();	
	        			if ((currentDate.compareTo(maxDate)>0) & (currentDate.compareTo(CurrentTime)<=0))
	        			{
	        				currentText = program.getElementsByTagName("title").item(0).getTextContent();
	        				pro.put(channelid, currentDate);
	        				cha.put(channelid, currentText);
	        			}
        			
	        		
                
	        	}
		        NodeList channellist = d.getElementsByTagName("channel");
	        	String[] catnames = new String[channellist.getLength()];
	        	
		        for (int i = 0; i < channellist.getLength(); i++) {
		        	Node node = channellist.item(i);
		        	Element fstElmnt = (Element) node;
		        	//NodeList nameList = fstElmnt.getElementsByTagName("display-name");
		        	/*Element nameElement = (Element) nameList.item(0);
		        	nameList = nameElement.getChildNodes();
		        	name[i].setText("Name = "
		        	+ ((Node) nameList.item(0)).getNodeValue());
		        	*/
		        	channelid = channellist.item(i).getAttributes().getNamedItem("id").getNodeValue();

		        	catnames[i] = String.format("%s: %s %s %s", fstElmnt.getElementsByTagName("display-name").item(0).getTextContent(), pro.getProperty(channelid,""),cha.getProperty(channelid,"Unknown"),CurrentTime);
			       //TextView tv = (TextView)findViewById(R.id.textView2);
		        	ListView lv=(ListView)findViewById(R.id.listView1);
       	
		        	

		        		// ���������� ������� ������
		        		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1, catnames);

		        		lv.setAdapter(adapter);
		        		

		        //	lv.a
	        //	tv.append(fstElmnt.getElementsByTagName("display-name").item(0).getTextContent()+": pro.getProperty(channelid,"")+":"+cha.getProperty(channelid,"Unknown")+"\n");
					
				}
}
 catch (Exception e){Log.e("tag", e.getMessage());}
		 
       /* AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
        	inputStream= new FileInputStream(ResultFile);//  getApplicationContext().getAssets().open("/data/data/com.example.firstapp/files/program_xml.xml", 1);
          //  inputStream = assetManager.open(ResultFile);
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

       String s = readTextFile(inputStream);
       // editText.setText(s);
        TextView tv = (TextView)findViewById(R.id.textView2);
        tv.setText(s.substring(20, 100));
		*/
	}

	private String readTextFile(InputStream inputStream) {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    byte buf[] = new byte[100024];
	    int len;
	    try {
	        while (((len = inputStream.read(buf)) != -1) )  {
	            outputStream.write(buf, 0, len);
	        }
	        outputStream.close();
	        inputStream.close();
	    } catch (IOException e) {

	    }
	    return outputStream.toString();
	}

	private void refreshList(boolean old) {
	//	EditText editText = (EditText) findViewById(R.id.edit_message);

//		editText.setText("Time: "+DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()));
        if (old)
          downloadFile(FilePreviousURL);
        else
            downloadFile(FileURL);

	}
	
	private boolean downloadFile(String URL)	{
		




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
	
	private String unpackZip(String path, String zipname, String  ResultFile)	{
	     InputStream is;
	     ZipInputStream zis;
	     try 
	     {
	         String filename;
	         is = new FileInputStream(path +"/"+ zipname);
	         zis = new ZipInputStream(new BufferedInputStream(is));          
	         ZipEntry ze;
	         byte[] buffer = new byte[1024];
	         int count;

	         while ((ze = zis.getNextEntry()) != null) 
	         {
	             // zapis do souboru
	             filename = ze.getName();

	             // Need to create directories if not exists, or
	             // it will generate an Exception...
	             if (ze.isDirectory()) {
	                File fmd = new File(path + filename);
	                fmd.mkdirs();
	                continue;
	             }
	             File  outputfile= new File(path, filename);
	             FileOutputStream fout = new FileOutputStream(outputfile);

	             // cteni zipu a zapis
	             while ((count = zis.read(buffer)) != -1) 
	             {
	                 fout.write(buffer, 0, count);             
	             }

	             fout.close();               
	             zis.closeEntry();
	             //ResultFile = path +'/'+ filename;
	             ResultFile = outputfile.getAbsolutePath();
	         }

	         zis.close();
	     } 
	     catch(IOException e)
	     {
	         e.printStackTrace();
	         return "";
	     }

	    return ResultFile;
	}
	
	// usually, subclasses of AsyncTask are declared inside the activity class.
	// that way, you can easily modify the UI thread from here
	private class DownloadTask extends AsyncTask<String, Integer, String> {

	    private Context context;

	    public DownloadTask(Context context) {
	        this.context = context;
			mProgressDialog.setMessage(getString(R.string.download_message));
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
                mProgressDialog.dismiss();
	        if (result != null)
	            Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
	        else
	        {   Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
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
	                output = new FileOutputStream(context.getFilesDir()+"/"+WWWFileName);

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
	                } 
	                catch (IOException ignored) { }

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
	    private String[] catnames=new String[1000];

	    public ParseFileTask(Context context) {
	        this.context = context;
			mProgressDialog.setMessage(getString(R.string.update_list));  }

	    @Override
	    protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(MainActivity.this,"",getString(R.string.update_list), false,false);
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
	            Toast.makeText(context,"Parse file error: "+result, Toast.LENGTH_LONG).show();
	        else
	        {   
	        	 runOnUiThread(new Runnable(){
	        	        public void run(){
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
	 
	        	if ((IsForceUnzipFile) || (d==null))
	        	{
               	publishProgress((int) (1));
	        	String ResultFile = "";
	    		ResultFile = unpackZip(Params[0],WWWFileName, ResultFile);
                    if (ResultFile=="")
                    {
                       // refreshList(null);
                        return null;
                    }
	    		IsForceUnzipFile = false;
	    		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    		try
		    		{
 		        dbf.setNamespaceAware(false);
 		        dbf.setValidating(false);
 		        
 		        DocumentBuilder db = dbf.newDocumentBuilder();
 		        InputStream inputStream = new FileInputStream(ResultFile);
 		        d=db.parse(inputStream);

	    		}

	    		 catch (Exception e){Log.e("tag", e.getMessage());}
	    		 
		           
	            
		         finally {
		
		        }
	        	}

	    		publishProgress(XMLLoadProgress);
                boolean FavoritesSelected = getFavoritesSelected();
	    		        String currentDate="";
	    		        String maxDate="";
	    		        nowList.Clear();
                        channelList.Clear();
	    		        // String CurrentTime = (String) DateFormat.format("yyyyMMddhhmmss", new java.util.Date());
	    		        Calendar c = Calendar.getInstance();
	    		        String CurrentTime=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime());
                        Date today = new Date();
                        today.setTime(c.getTimeInMillis()+(1000*5*60));
                        String TimeForProgramCompare = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(today.getTime());
	    		        String currentText="";
	    		        NodeList programlist = d.getElementsByTagName("programme");
	    	        	currentText="";
	    	        	maxDate="";
	    	        	String channelid="";
                        String channelName="";
	    	        	Properties pro= new Properties();
	    	        	Properties cha= new Properties();
	    	        	
	    	        	DateFormat df = new DateFormat();
	    	        	String currentTitleDate=(String) df.format("yyyy-MM-dd hh:mm:ss", new Date());
	    	       	 // setTitle(String.format(getString(R.string.now_title),currentTitleDate));
	    	       	  
	    	        	for (int j = 0; j < programlist.getLength(); j++) {
	    	        		publishProgress((int) (XMLLoadProgress+j * (100-XMLLoadProgress) / programlist.getLength()));
	    	        		Node programNode  = programlist.item(j);
	    	        		Element program=(Element)programNode;
	    	        		channelid = program.getAttributes().getNamedItem("channel").getNodeValue();
	    	        		if (pro.containsKey(channelid))
	    	        		{
	    	        			maxDate=pro.getProperty(channelid, "");
	    	        		}
	    	        		else
	    	        		{
	    	        			pro.put(channelid, "");
	    	        			maxDate="0";
	    	        		}

	    	        			currentDate = program.getAttributes().getNamedItem("start").getNodeValue();

	    	        			if ((currentDate.compareTo(maxDate)>0) & (currentDate.compareTo(TimeForProgramCompare)<=0))
	    	        			{
	    	        				currentText = program.getElementsByTagName("title").item(0).getTextContent();
	    	        				pro.put(channelid, currentDate);
	    	        				cha.put(channelid, currentText);
	    	        			}
	    	        	}
	    		        NodeList channellist = d.getElementsByTagName("channel");

	    		        for (int i = 0; i < channellist.getLength(); i++) {
	    		        	Node node = channellist.item(i);
	    		        	Element fstElmnt = (Element) node;

	    		        	channelid = channellist.item(i).getAttributes().getNamedItem("id").getNodeValue();
                            channelName = fstElmnt.getElementsByTagName("display-name").item(0).getTextContent();
                            channelList.Add(channelid, channelName);

                            if (!FavoritesSelected || (favoriteChannelListPreference.getBoolean(channelName, false ))) {
                                String dd = pro.getProperty(channelid, "");
                                nowList.Add(channelid, fstElmnt.getElementsByTagName("display-name").item(0).getTextContent(), cha.getProperty(channelid, "Unknown"), dd);
                             }
	    }
                nowList.sort(1);
	        }
	        finally {
	        //    wl.release();
	        }
	        return null;
	    }
	
	    
	}

    private boolean getFavoritesSelected() {
       Map<String, ?> favorites = favoriteChannelListPreference.getAll();
        return favorites.size()>0;
    }


}
