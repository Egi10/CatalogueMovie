package id.co.egifcb.favoritemovie.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import id.co.egifcb.favoritemovie.R;
import id.co.egifcb.favoritemovie.adapter.FavoriteMovieAdapter;
import id.co.egifcb.favoritemovie.db.DatabaseContract;
import static id.co.egifcb.favoritemovie.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    private FavoriteMovieAdapter favoriteMovieAdapter;
    private final int LOAD_NOTES_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        favoriteMovieAdapter = new FavoriteMovieAdapter(this, null, true);
        listView.setAdapter(favoriteMovieAdapter);

        listView.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        favoriteMovieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        favoriteMovieAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_NOTES_ID);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = (Cursor) favoriteMovieAdapter.getItem(i);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColums.TITLE));
        String voteCount = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColums.VOTE_COUNT));
        String voteAverage = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColums.VOTE_AVERAGE));
        String popularity = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColums.POPULARITY));
        String overview = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColums.OVERVIERW));
        String backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColums.BACKDROP_PATH));
        String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColums.POSTER_PATH));
        String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColums.RELEASE_DATE));

        Intent intent = new Intent(getBaseContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.TITLE, title);
        intent.putExtra(DetailActivity.VOTE_COUNT, voteCount);
        intent.putExtra(DetailActivity.VOTE_AVERAGE, voteAverage);
        intent.putExtra(DetailActivity.POPULARITY, popularity);
        intent.putExtra(DetailActivity.OVERVIEW, overview);
        intent.putExtra(DetailActivity.BACKDROP_PATH, backdropPath);
        intent.putExtra(DetailActivity.POSTER_PATH, posterPath);
        intent.putExtra(DetailActivity.RELEASE_DATE, releaseDate);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.exit);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
