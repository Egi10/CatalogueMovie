package id.egifcb.cataloguemovie.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_MOVIE = "movie";

    static final class MovieColums implements BaseColumns {
        static String TITLE = "title";
        static String VOTE_COUNT = "vote_count";
        static String VOTE_AVERAGE = "vote_average";
        static String POPULARITY = "popularity";
        static String OVERVIERW = "overview";
        static String BACKDROP_PATH = "backdrop_path";
        static String POSTER_PATH = "poster_path";
        static String RELEASE_DATE = "release_date";
    }
}
