package coldmanck.ocr_chiou.Parse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nai-Jack Li on 2016/1/5.
 */
public class MovieData {

        public String name;
        public String posterUrl;
        public String rating;
        public String description;
        public String trailerUrl;
        public List<String> director;
        public List<String> writer;
        public List<String> actor;
        public List<String> star;

        public MovieData(){
            name = "";
            posterUrl = "";
            rating = "";
            description = "";
            trailerUrl = ";";
            director = new ArrayList<String>();
            writer = new ArrayList<String>();
            actor = new ArrayList<String>();
            star = new ArrayList<String>();
        }
    }
