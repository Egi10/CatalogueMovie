package id.egifcb.cataloguemovie.ui.activity.detailmovie;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import id.egifcb.cataloguemovie.BuildConfig;
import id.egifcb.cataloguemovie.R;
import id.egifcb.cataloguemovie.db.MovieHelper;
import id.egifcb.cataloguemovie.model.Movie;
import id.egifcb.cataloguemovie.ui.activity.main.MainActivity;

import static id.egifcb.cataloguemovie.db.DatabaseContract.CONTENT_URI;

public class DetailMovieActivity extends AppCompatActivity {
    TextView tvVoteCount, tvVoteAverage, tvPopularity, tvOverview, tvTitle, tvReleaseDate;
    ImageView ivBackdropPath, ivPosterPath;

    public static String _ID = "_id";
    public static String TITLE = "title";
    public static String VOTE_COUNT = "vote_count";
    public static String VOTE_AVERAGE = "vote_average";
    public static String POPULARITY = "popularity";
    public static String OVERVIEW = "overview";
    public static String BACKDROP_PATH = "backdrop_path";
    public static String POSTER_PATH = "poster_path";
    public static String RELEASE_DATE = "release_date";

    private String title, voteCount, voteAverange, popularity, overview, backdropPath, posterPath, releaseDate;
    private int id;

    private MovieHelper movieHelper;

    FloatingActionButton floatingActionButton;

    private boolean favorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.BOTTOM);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        floatingActionButton = findViewById(R.id.fab);
        tvVoteCount = findViewById(R.id.tv_vote_count);
        tvVoteAverage = findViewById(R.id.tv_vote_average);
        tvPopularity = findViewById(R.id.tv_popularity);
        tvOverview = findViewById(R.id.tv_overview);
        ivBackdropPath = findViewById(R.id.iv_backdrop_path);
        ivPosterPath = findViewById(R.id.iv_poster_path);
        tvTitle = findViewById(R.id.tv_title);
        tvReleaseDate = findViewById(R.id.tv_release_date);

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null
                    , null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    Movie movie = new Movie(cursor);
                }
                cursor.close();
            }
        }

        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra(TITLE);
            voteCount = intent.getStringExtra(VOTE_COUNT);
            voteAverange = intent.getStringExtra(VOTE_AVERAGE);
            popularity = intent.getStringExtra(POPULARITY);
            overview = intent.getStringExtra(OVERVIEW);
            backdropPath = intent.getStringExtra(BACKDROP_PATH);
            posterPath = intent.getStringExtra(POSTER_PATH);
            releaseDate = intent.getStringExtra(RELEASE_DATE);
            id = intent.getIntExtra(_ID, 0);
        }

        setTitle(title);
        tvVoteCount.setText(voteCount);
        tvVoteAverage.setText(voteAverange);
        tvPopularity.setText(popularity);
        tvOverview.setText(overview);
        Glide.with(getBaseContext())
                .load(BuildConfig.IMG_URL + "" + backdropPath)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).centerCrop())
                .into(ivBackdropPath);
        Glide.with(getBaseContext())
                .load(BuildConfig.IMG_URL + "" + posterPath)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).centerCrop())
                .into(ivPosterPath);
        tvTitle.setText(title);

        String dateInput = releaseDate;
        SimpleDateFormat simpleDateFormatInput = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat simpleDateFormatOutput = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
        Date date;
        String dateOutput = null;
        try {
            date = simpleDateFormatInput.parse(dateInput);
            dateOutput = simpleDateFormatOutput.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvReleaseDate.setText(dateOutput);

        int cekFavorite = movieHelper.queryByIdProvider(String.valueOf(id)).getCount();

        if (cekFavorite > 0) {
            favorite = true;
            floatingActionButton.setImageResource(R.drawable.ic_favorite);
        } else {
            favorite = false;
            floatingActionButton.setImageResource(R.drawable.ic_no_favorite);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favorite) {
                    floatingActionButton.setImageResource(R.drawable.ic_favorite);
                    ContentValues values = new ContentValues();
                    values.put(_ID, id);
                    values.put(TITLE, title);
                    values.put(VOTE_COUNT, voteCount);
                    values.put(VOTE_AVERAGE, voteAverange);
                    values.put(POPULARITY, popularity);
                    values.put(OVERVIEW, overview);
                    values.put(BACKDROP_PATH, backdropPath);
                    values.put(POSTER_PATH, posterPath);
                    values.put(RELEASE_DATE, releaseDate);
                    getContentResolver().insert(CONTENT_URI, values);

                    Toast.makeText(getBaseContext(), R.string.sukses_favorite, Toast.LENGTH_SHORT).show();
                } else {
                    floatingActionButton.setImageResource(R.drawable.ic_no_favorite);
                    movieHelper.deleteProvider(String.valueOf(id));

                    Toast.makeText(getBaseContext(), R.string.hapus_favorite, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieHelper != null) {
            movieHelper.close();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(DetailMovieActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
