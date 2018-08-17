package id.egifcb.cataloguemovie.ui.activity.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import id.egifcb.cataloguemovie.R;
import id.egifcb.cataloguemovie.adapter.MovieAdapter;
import id.egifcb.cataloguemovie.model.Movie;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView {
    private EditText etQuery;
    ImageView ivSearch;
    SwipeRefreshLayout swipeRefresh;
    RecyclerView recyclerView;
    private ArrayList<Movie> listMovie = null;
    private LinearLayout llEmpty;
    private MovieAdapter movieAdapter;
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etQuery = findViewById(R.id.et_query);
        ivSearch = findViewById(R.id.iv_search);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        llEmpty = findViewById(R.id.ll_empty);
        llEmpty.setVisibility(View.VISIBLE);

        listMovie = new ArrayList<>();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
            }
        });

        loadMovie();

        ivSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search :
                listMovie.clear();
                movieAdapter.notifyDataSetChanged();

                llEmpty.setVisibility(View.GONE);

                String query = etQuery.getText().toString();

                mainPresenter.getList(query);
                break;
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

    private void loadMovie() {
        movieAdapter = new MovieAdapter(this);
        movieAdapter.setListMovie(listMovie);
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        recyclerView.setAdapter(movieAdapter);

        mainPresenter = new MainPresenter(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to leave ?");
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    fileList();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
