package id.egifcb.cataloguemovie.db;

import android.database.Cursor;
import android.net.Uri;
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

    public static final String AUTHORITY = "id.egifcb.cataloguemovie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
