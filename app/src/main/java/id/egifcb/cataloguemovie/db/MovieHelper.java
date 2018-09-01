package id.egifcb.cataloguemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import id.egifcb.cataloguemovie.model.Movie;

import static android.provider.BaseColumns._ID;
import static id.egifcb.cataloguemovie.db.DatabaseContract.MovieColums.BACKDROP_PATH;
import static id.egifcb.cataloguemovie.db.DatabaseContract.MovieColums.OVERVIERW;
import static id.egifcb.cataloguemovie.db.DatabaseContract.MovieColums.POPULARITY;
import static id.egifcb.cataloguemovie.db.DatabaseContract.MovieColums.POSTER_PATH;
import static id.egifcb.cataloguemovie.db.DatabaseContract.MovieColums.RELEASE_DATE;
import static id.egifcb.cataloguemovie.db.DatabaseContract.MovieColums.TITLE;
import static id.egifcb.cataloguemovie.db.DatabaseContract.MovieColums.VOTE_AVERAGE;
import static id.egifcb.cataloguemovie.db.DatabaseContract.MovieColums.VOTE_COUNT;
import static id.egifcb.cataloguemovie.db.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        sqLiteDatabase.close();
    }

//    public ArrayList<Movie> query() {
//        ArrayList<Movie> arrayList = new ArrayList<>();
//        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null,
//                null, null, null, _ID + " DESC", null);
//        cursor.moveToFirst();
//        Movie movie;
//
//        if (cursor.getCount() > 0) {
//            do {
//                movie = new Movie();
//                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
//                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
//                movie.setVoteCount(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_COUNT)));
//                movie.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
//                movie.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULARITY)));
//                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIERW)));
//                movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)));
//                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
//                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
//
//                arrayList.add(movie);
//                cursor.moveToNext();
//            } while (!cursor.isAfterLast());
//        }
//        return arrayList;
//    }
//
//    public long insert(Movie movie) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(_ID, movie.getId());
//        contentValues.put(TITLE, movie.getTitle());
//        contentValues.put(VOTE_COUNT, movie.getVoteCount());
//        contentValues.put(VOTE_AVERAGE, movie.getVoteAverage());
//        contentValues.put(POPULARITY, movie.getPopularity());
//        contentValues.put(OVERVIERW, movie.getOverview());
//        contentValues.put(BACKDROP_PATH, movie.getBackdropPath());
//        contentValues.put(POSTER_PATH, movie.getPosterPath());
//        contentValues.put(RELEASE_DATE, movie.getReleaseDate());
//
//        return sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
//    }
//
//    public Cursor queryById(String id) {
//        return sqLiteDatabase.query(DATABASE_TABLE, null, _ID + " = ?", new String[]{id},
//                null, null, null, null);
//    }
//
//    public int delete(int id) {
//        return sqLiteDatabase.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
//    }

    public Cursor queryByIdProvider(String id) {
        return sqLiteDatabase.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return sqLiteDatabase.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues contentValues) {
        return sqLiteDatabase.insert(DATABASE_TABLE,null,contentValues);
    }

    public int updateProvider(String id, ContentValues contentValues) {
        return sqLiteDatabase.update(DATABASE_TABLE,contentValues,_ID +" = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return sqLiteDatabase.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
