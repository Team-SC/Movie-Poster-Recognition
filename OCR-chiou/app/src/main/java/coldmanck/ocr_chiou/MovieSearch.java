package coldmanck.ocr_chiou;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import coldmanck.ocr_chiou.Parse.MovieData;
import coldmanck.ocr_chiou.Parse.Parse2;
import coldmanck.ocr_chiou.Parse.Parse;
import coldmanck.ocr_chiou.Parse.ShowImage;

public class MovieSearch extends Activity {

    private EditText editTextInput;
    private TextView textView;

    public static ImageView imageView_pos;
    private TextView textView_rate;
    private TextView textView_des;
    private TextView textView_director;
    private TextView textView_writer;
    private TextView textView_actor;
    private TextView textView_trailer;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    String term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        Intent intent = getIntent();
        term = intent.getStringExtra("movie_term");

        editTextInput = (EditText) findViewById(R.id.editTextM);
        Button button = (Button)findViewById(R.id.buttonM);
        button.setOnClickListener(buttonEvent);
        button.setTag(1);
        textView = (TextView)findViewById(R.id.textViewM);

        imageView_pos = (ImageView)findViewById(R.id.imageView_poster);
        textView_rate = (TextView)findViewById(R.id.textView_rate);
        textView_des = (TextView)findViewById(R.id.textView_des);
        textView_director = (TextView)findViewById(R.id.textView_director);
        textView_writer = (TextView)findViewById(R.id.textView_writer);
        textView_actor = (TextView)findViewById(R.id.textView_actor);
        textView_trailer = (TextView)findViewById(R.id.textView_trailer);
        textView_trailer.setAutoLinkMask(Linkify.ALL);

        textView1 = (TextView)findViewById(R.id.textView1M);
        textView2 = (TextView)findViewById(R.id.textView2M);
        textView3 = (TextView)findViewById(R.id.textView3M);
        textView4 = (TextView)findViewById(R.id.textView4M);

        textView_rate.setVisibility(View.INVISIBLE);
        textView_des.setVisibility(View.INVISIBLE);
        textView_director.setVisibility(View.INVISIBLE);
        textView_writer.setVisibility(View.INVISIBLE);
        textView_actor.setVisibility(View.INVISIBLE);
        textView_trailer.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        textView1.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.INVISIBLE);
        textView4.setVisibility(View.INVISIBLE);

        textView_des.setMovementMethod(ScrollingMovementMethod.getInstance());
        editTextInput.setText(term);

        String IMDb_TAG = "http://www.imdb.com/find?ref_=nv_sr_fn&q=";
        String term = editTextInput.getText().toString();
        term = term.replaceAll(" ","+");
        String url = IMDb_TAG + term;

        GetHttp task = new GetHttp();
        task.execute(url);
    }

    private View.OnClickListener buttonEvent = new View.OnClickListener() {
        public void onClick(View v) {

            if( (Integer)v.getTag() == 1 ) {
                String IMDb_TAG = "http://www.imdb.com/find?ref_=nv_sr_fn&q=";
                String term = editTextInput.getText().toString();
                term = term.replaceAll(" ","+");
                String url = IMDb_TAG + term;

                GetHttp task = new GetHttp();
                task.execute(url);
            }
        }
    };

    private class GetHttp extends AsyncTask<String , Integer , String> {

        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定(可用UI)
            super.onPreExecute();
            textView.setText("Loading...");
            textView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            //執行中 在背景做事情(不可用UI)
            String url = params[0];
            try {
                String next_url = new Parse(url).Showimgurl();
                return next_url;
            }catch (Exception e) {
                Log.e("bad", "bad!");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //執行中 可以在這邊告知使用者進度(可用UI)
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            //執行後 完成背景任務(可用UI)
            super.onPostExecute(result);
            /*
            Uri uri = Uri.parse(result);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            */
            if(result.compareTo("no result")!=0)
                new GetMovieData().execute(result);
            else {
                //Toast.makeText(MovieSearch.this, result, Toast.LENGTH_SHORT).show();
                textView.setText("Finish!");
                popOutAlertDialog("No result found.\n Term: " + term );
            }
        }
    }


    private class GetMovieData extends AsyncTask<String , Integer , MovieData>{

        String url;

        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定(可用UI)
            super.onPreExecute();
        }

        @Override
        protected MovieData doInBackground(String... params) {
            //執行中 在背景做事情(不可用UI)

            MovieData d = new MovieData();
            try {
                url = params[0];
                d = new Parse2(params[0]).ShowData();
                return d;
            }catch (Exception e) { Log.e("bad2", "bad!"); e.printStackTrace();}
            return d;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //執行中 可以在這邊告知使用者進度(可用UI)
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(MovieData data) {
            //執行後 完成背景任務(可用UI)
            super.onPostExecute(data);

            if(data.director.get(0).compareTo("no result") == 0 &&
                    data.actor.get(0).compareTo("no result") == 0 &&
                    data.star.get(0).compareTo("no result") == 0 &&
                    data.writer.get(0).compareTo("no result") == 0) {        // bad name list, try again
                textView.setText("Reparsing...");
                new GetMovieData().execute(url);
            }
            else // show movie's data
            {
                textView.setText("Finish!");
                textView_rate.setVisibility(View.VISIBLE);
                textView_des.setVisibility(View.VISIBLE);
                textView_director.setVisibility(View.VISIBLE);
                textView_writer.setVisibility(View.VISIBLE);
                textView_actor.setVisibility(View.VISIBLE);
                textView_trailer.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);


                if (data.posterUrl.compareTo("no poster") != 0)
                    new ShowImage().execute(data.posterUrl);
                else
                    imageView_pos.setImageBitmap(null);
                textView_rate.setText(data.rating);
                textView_des.setText(data.description);


                String str;
                if (data.director.size() > 0) {
                    str = "  " + data.director.get(0);
                    if (data.director.size() > 1) {
                        for (int i = 1; i < data.director.size(); i++)
                            str = str + ", " + data.director.get(i);
                    }
                    textView_director.setText(str);
                }

                if (data.writer.size() > 0) {
                    str = "  " + data.writer.get(0);
                    if (data.writer.size() > 1) {
                        for (int i = 1; i < data.writer.size(); i++)
                            str = str + ", " + data.writer.get(i);
                    }
                    textView_writer.setText(str);
                }

                if (data.actor.size() > 0) {
                    str = "  " + data.actor.get(0);
                    if (data.actor.size() > 1) {
                        for (int i = 1; i < data.actor.size(); i++)
                            str = str + ", " + data.actor.get(i);
                    }
                    textView_actor.setText(str);
                }
                else if(data.actor.size() > 0) {
                    str = "  " + data.star.get(0);
                    if (data.star.size() > 1) {
                        for (int i = 1; i < data.star.size(); i++)
                            str = str + ", " + data.star.get(i);
                    }
                    textView_actor.setText(str);

                }

                SpannableString sp = new SpannableString(data.trailerUrl);
                sp.setSpan(new URLSpan(data.trailerUrl), 0, data.trailerUrl.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_trailer.setText(sp);
            }

        }
    }

    private void popOutAlertDialog(String errorMessage) {
        // pop out alert dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(MovieSearch.this);
        dialog.setTitle("No result");
        dialog.setMessage(errorMessage);
        dialog.setPositiveButton(R.string.ok_label,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        finish();
                    }
                });
        dialog.show();
    }

























    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
