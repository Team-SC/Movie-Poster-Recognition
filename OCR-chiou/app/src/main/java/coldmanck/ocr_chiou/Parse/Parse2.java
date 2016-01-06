package coldmanck.ocr_chiou.Parse;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

/**
 * Created by Nai-Jack Li on 2016/1/5.
 */
public class Parse2 {
    MovieData Data;
    String path;

    public Parse2(String t){
        path = t;
    }

    public MovieData ShowData() throws Exception {

        URL url = new URL(path); //http://www.imdb.com/title/tt1375666/?ref_=nv_sr_1  http://www.imdb.com/title/tt0241527/?ref_=fn_al_tt_1
        Document xmlDoc =  Jsoup.parse(url, 3000);
        /*Document xmlDoc =  Jsoup.connect(path)
                .timeout(3000)           // 设置连接超时时间
                .post();                 // 使用 POST 方法访问 URL
        */
        Data = new MovieData();

        //get Poster Url
        Elements poster = xmlDoc.select("img[title$=Poster]");
        if(poster.size()>0)
            Data.posterUrl = poster.get(0).attr("abs:src");
        else
            Data.posterUrl = "no poster";

        //get Trailer Url
        Elements trailer = xmlDoc.select("a[itemprop=trailer]");
        if(trailer.size()>0)
            Data.trailerUrl = trailer.get(0).attr("abs:href");
        else
            Data.trailerUrl = "no trailer";

        //get Rating
        Elements rating = xmlDoc.select("span[itemprop=ratingValue]");
        if(rating.size()>0)
            Data.rating = rating.get(0).text();
        else
            Data.rating = "NA";

        //get description
        Elements description = xmlDoc.select("div[itemprop=description]");
        if(description.size()>0)
            Data.description = description.get(0).text();
        else
            Data.description = "no description";

        //get director name
        Elements director = xmlDoc.select("div[class*=item] > span[itemprop=director] > a > span[itemprop=name]");
        if(director.size()>0)
            for(Element d : director)
                Data.director.add(d.text());
        else
            Data.director.add("no result");

        //get writer name
        Elements writer = xmlDoc.select("div[class*=item] > span[itemprop=creator] > a > span[itemprop=name]");
        if(writer.size()>0)
            for(Element d : writer)
                Data.writer.add(d.text());
        else
            Data.writer.add("no writer");

        //get writer name
        Elements actor = xmlDoc.select("div[class*=item] > span[itemprop=actors] > a > span[itemprop=name]");
        if(actor.size()>0)
            for(Element d : actor)
                Data.actor.add(d.text());
        else
            Data.actor.add("no actor");

        //get star name
        Elements star = xmlDoc.select("div[class*=item] > span[itemprop=actors] > a > span[itemprop=name]");
        if(star.size()>0)
            for(Element d : star)
                Data.star.add(d.text());
        else
            Data.star.add("no result");



        Log.e("Parse2", path);
        //Log.e("i", Integer.toString(i));
        //Log.e("next_url",imgurl.get(0));
        return Data;
    }
}
