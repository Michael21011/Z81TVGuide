package info.z81.z81tvguide;


        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.URL;
        import java.net.URLEncoder;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.ImageView;


public class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
    private final static String TAG = "AsyncTaskLoadImage";
    private ImageView imageView;
    public AsyncTaskLoadImage(ImageView imageView) {
       // this.imageView = imageView;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        String s="";
        try {


            URL url = new URL(String.format(params[0], URLEncoder.encode(params[1], "UTF-8").replace("+","%20")));
            s = url.toString();
            InputStream  is=(InputStream)url.getContent();
            OutputStream output = new FileOutputStream(Z81TVGuide.getAppContext().getFilesDir() + "/" + params[1]);
            byte[] buffer = new byte[1024]; // Adjust if you want
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1)
            {
                output.write(buffer, 0, bytesRead);
            }
            output.flush();
            output.close();
            bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            Log.d(TAG,String.format("Picture not found %s link %s",params[1], s));
        }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        //imageView.setImageBitmap(bitmap);
    }
}