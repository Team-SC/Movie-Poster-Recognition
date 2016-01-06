package coldmanck.ocr_chiou.Core.TessTool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import coldmanck.ocr_chiou.Core.Dialogs.ImageDialog;
import coldmanck.ocr_chiou.Core.Imaging.Tools;
import coldmanck.ocr_chiou.MainActivity;
import coldmanck.ocr_chiou.MovieSearch;


/**
 * Created by Fadi on 6/11/2014.
 */
public class TessAsyncEngine extends AsyncTask<Object, Void, String> {

    static final String TAG = "DBG_" + TessAsyncEngine.class.getName();

    private Bitmap bmp;

    private Activity context;

    private Context ctx;
    public TessAsyncEngine(Context c){this.ctx = c;}


    @Override
    protected String doInBackground(Object... params) {

        try {

            if(params.length < 2) {
                Log.e(TAG, "Error passing parameter to execute - missing params");
                return null;
            }

            if(!(params[0] instanceof Activity) || !(params[1] instanceof Bitmap)) {
                Log.e(TAG, "Error passing parameter to execute(context, bitmap)");
                return null;
            }

            context = (Activity)params[0];

            bmp = (Bitmap)params[1];

            if(context == null || bmp == null) {
                Log.e(TAG, "Error passed null parameter to execute(context, bitmap)");
                return null;
            }

            int rotate = 0;

            if(params.length == 3 && params[2]!= null && params[2] instanceof Integer){
                rotate = (Integer) params[2];
            }

            if(rotate >= -180 && rotate <= 180 && rotate != 0)
            {
                bmp = Tools.preRotateBitmap(bmp, rotate);
                Log.d(TAG, "Rotated OCR bitmap " + rotate + " degrees");
            }

            TessEngine tessEngine =  TessEngine.Generate(context);

            bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);

            String result = tessEngine.detectText(bmp);

            Log.d(TAG, result);

            return result;

        } catch (Exception ex) {
            Log.d(TAG, "Error: " + ex + "\n" + ex.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        if(s == null || bmp == null || context == null)
            return;
        /*
        ImageDialog.New()
                .addBitmap(bmp)
                .addTitle(s)
                .show(context.getFragmentManager(), TAG);
        */

        Intent intent = new Intent( ctx, MovieSearch.class);
        intent.putExtra("movie_term", s);
        context.startActivity(intent);
        //super.onPostExecute(s);


    }
}
