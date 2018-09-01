package id.egifcb.cataloguemovie.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static id.egifcb.cataloguemovie.db.DatabaseContract.AUTHORITY;
import static id.egifcb.cataloguemovie.db.DatabaseContract.CONTENT_URI;

public class NoteProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE, MOVIE);

        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE + "/#", MOVIE_ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;

        switch (URI_MATCHER.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;

            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;

        switch (URI_MATCHER.match(uri)) {
            case MOVIE:
                added = movieHelper.insertProvider(contentValues);
                break;

            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;

        switch (URI_MATCHER.match(uri)) {
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int update;

        switch (URI_MATCHER.match(uri)) {
            case MOVIE_ID:
                update = movieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;

            default:
                update = 0;
                break;
        }

        if (update > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return update;
    }
}
