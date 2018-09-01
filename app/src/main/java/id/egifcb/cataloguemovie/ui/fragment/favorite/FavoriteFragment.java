package id.egifcb.cataloguemovie.ui.fragment.favorite;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import id.egifcb.cataloguemovie.R;
import id.egifcb.cataloguemovie.adapter.FavoriteMovieAdapter;
import id.egifcb.cataloguemovie.adapter.MovieAdapter;
import id.egifcb.cataloguemovie.db.MovieHelper;
import id.egifcb.cataloguemovie.model.Movie;

import static id.egifcb.cataloguemovie.db.DatabaseContract.CONTENT_URI;

public class FavoriteFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private Cursor listFavorite;
    private FavoriteMovieAdapter movieAdapter;
    private MovieHelper movieHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        movieHelper = new MovieHelper(getContext());
        movieHelper.open();

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                new loadMoviewAsync().execute();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new loadMoviewAsync().execute();
            }
        });

        movieAdapter = new FavoriteMovieAdapter(getContext());
        movieAdapter.setListMovie(listFavorite);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(movieAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    private class loadMoviewAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Cursor list) {
            super.onPostExecute(list);
            swipeRefreshLayout.setRefreshing(false);

            listFavorite = list;
            movieAdapter.setListMovie(listFavorite);
            movieAdapter.notifyDataSetChanged();

            if (listFavorite.getCount() == 0) {
                Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Ada Data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI, null, null,
                    null, null);
        }
    }
}
