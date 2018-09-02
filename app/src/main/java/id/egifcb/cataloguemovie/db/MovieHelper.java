package id.egifcb.cataloguemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
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
