package id.egifcb.cataloguemovie.ui.activity.detailmovie;

import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import id.egifcb.cataloguemovie.BuildConfig;
import id.egifcb.cataloguemovie.R;
import id.egifcb.cataloguemovie.ui.activity.main.MainActivity;

public class DetailMovieActivity extends AppCompatActivity {
    TextView tvVoteCount, tvVoteAverage, tvPopularity, tvOverview, tvTitle, tvReleaseDate;
    ImageView ivBackdropPath, ivPosterPath;

    public static String TITLE = "title";
    public static String VOTE_COUNT = "vote_count";
    public static String VOTE_AVERAGE = "vote_average";
    public static String POPULARITY = "popularity";
    public static String OVERVIEW = "overview";
    public static String BACKDROP_PATH = "backdrop_path";
    public static String POSTER_PATH = "poster_path";
    public static String RELEASE_DATE = "release_date";

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

        tvVoteCount = findViewById(R.id.tv_vote_count);
        tvVoteAverage = findViewById(R.id.tv_vote_average);
        tvPopularity = findViewById(R.id.tv_popularity);
        tvOverview = findViewById(R.id.tv_overview);
        ivBackdropPath = findViewById(R.id.iv_backdrop_path);
        ivPosterPath = findViewById(R.id.iv_poster_path);
        tvTitle = findViewById(R.id.tv_title);
        tvReleaseDate = findViewById(R.id.tv_release_date);

        Intent intent = getIntent();
        if (intent != null) {
            setTitle(intent.getStringExtra(TITLE));
            tvVoteCount.setText(intent.getStringExtra(VOTE_COUNT));
            tvVoteAverage.setText(intent.getStringExtra(VOTE_AVERAGE));
            tvPopularity.setText(intent.getStringExtra(POPULARITY));
            tvOverview.setText(intent.getStringExtra(OVERVIEW));
            Glide.with(getBaseContext())
                    .load(BuildConfig.IMG_URL+""+intent.getStringExtra(BACKDROP_PATH))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).centerCrop())
                    .into(ivBackdropPath);
            Glide.with(getBaseContext())
                    .load(BuildConfig.IMG_URL+""+intent.getStringExtra(POSTER_PATH))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).centerCrop())
                    .into(ivPosterPath);
            tvTitle.setText(intent.getStringExtra(TITLE));

            String dateInput = intent.getStringExtra(RELEASE_DATE);
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
        }

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, tvTitle.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
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
