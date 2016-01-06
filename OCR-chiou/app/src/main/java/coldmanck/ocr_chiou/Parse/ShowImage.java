package coldmanck.ocr_chiou.Parse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

import coldmanck.ocr_chiou.MovieSearch;


/**
 * Created by Nai-Jack Li on 2016/1/5.
 */
public class ShowImage extends AsyncTask<String , Integer , Bitmap> {
    @Override
    protected void onPreExecute() {
        //執行前 設定可以在這邊設定(可用UI)
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        //執行中 在背景做事情(不可用UI)
        try {
            Bitmap bmp = BitmapFactory.decodeStream(new URL(params[0]).openConnection().getInputStream());
            return  bmp;
        }catch (IOException e) {e.printStackTrace();}
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //執行中 可以在這邊告知使用者進度(可用UI)
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        //執行後 完成背景任務(可用UI)
        MovieSearch.imageView_pos.setImageBitmap(bmp);
    }
}
