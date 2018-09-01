package id.egifcb.cataloguemovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION =1;
    private static final String SQL_CRATE_TABLE_MOVIE = String.format("CREATE TABLE %s" +
            " (%s INTEGER PRIMARY KEY," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.MovieColums._ID,
            DatabaseContract.MovieColums.TITLE,
            DatabaseContract.MovieColums.VOTE_COUNT,
            DatabaseContract.MovieColums.VOTE_AVERAGE,
            DatabaseContract.MovieColums.POPULARITY,
            DatabaseContract.MovieColums.OVERVIERW,
            DatabaseContract.MovieColums.BACKDROP_PATH,
            DatabaseContract.MovieColums.POSTER_PATH,
            DatabaseContract.MovieColums.RELEASE_DATE);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CRATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIE);
        onCreate(sqLiteDatabase);
    }
}
