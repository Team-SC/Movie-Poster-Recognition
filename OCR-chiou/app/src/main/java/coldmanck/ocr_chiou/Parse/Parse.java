package coldmanck.ocr_chiou.Parse;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

/**
 * Created by Nai-Jack Li on 2016/1/5.
 */
public class Parse {

    String path;

    public Parse(String t){
        path = t;
    }

    public String Showimgurl() throws Exception {

        URL url = new URL(path);
        Document xmlDoc =  Jsoup.parse(url, 3000);  //取得該網頁的document

        Elements title = xmlDoc.select("td[class=result_text] > a"); //設定找尋目標(正規表示法)

        String nextUrl;
        if(title.size()==0)
            nextUrl = "no result";
        else
            nextUrl = title.get(0).attr("abs:href"); //取得連結


        //if(!nextUrl.contains("title")) nextUrl = "no result";

        Log.e("Parse",path);
        Log.e("next_url",nextUrl);

        return nextUrl;
    }


}
