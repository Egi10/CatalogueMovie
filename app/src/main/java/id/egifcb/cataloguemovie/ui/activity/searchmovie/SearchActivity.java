package id.egifcb.cataloguemovie.ui.activity.searchmovie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import id.egifcb.cataloguemovie.R;
import id.egifcb.cataloguemovie.adapter.MovieAdapter;
import id.egifcb.cataloguemovie.model.Movie;
import id.egifcb.cataloguemovie.ui.activity.main.MainActivity;

public class SearchActivity extends AppCompatActivity implements SearchView {
    SwipeRefreshLayout swipeRefresh;
    RecyclerView recyclerView;
    private ArrayList<Movie> listMovie;
    private MovieAdapter movieAdapter;
    SearchPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }

        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        listMovie = new ArrayList<>();
        mainPresenter = new SearchPresenter(this);
        final String query = getIntent().getStringExtra("keyword");

        if (savedInstanceState != null && savedInstanceState.containsKey("data_result")) {
            setTitle(query);
            listMovie = savedInstanceState.getParcelableArrayList("data_result");
            swipeRefresh.setRefreshing(false);
            loadMovie();

        } else if (savedInstanceState != null && savedInstanceState.containsKey("data_recycler_view")) {
            setTitle(query);
            Parcelable parcelable = savedInstanceState.getParcelable("data_recycler_view");
            swipeRefresh.setRefreshing(false);
            recyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
        }

        if (savedInstanceState == null) {
            setTitle(query);

            swipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    mainPresenter.getList(query);
                }
            });

            loadMovie();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (listMovie != null) {
            outState.putParcelableArrayList("data_result", listMovie);
        }
        if (recyclerView.getLayoutManager() != null) {
            outState.putParcelable("data_recycler_view", recyclerView.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showList(ArrayList<Movie> list) {
        listMovie.clear();
        listMovie.addAll(list);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListEmpty() {
        Toast.makeText(getBaseContext(), R.string.messageEmpty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailure(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void loadMovie() {
        movieAdapter = new MovieAdapter(this);
        movieAdapter.setListMovie(listMovie);
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
