package id.egifcb.cataloguemovie.ui.fragment.nowplayingmovie;

import android.os.Bundle;
import android.os.Parcelable;
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
import id.egifcb.cataloguemovie.adapter.MovieAdapter;
import id.egifcb.cataloguemovie.model.Movie;

public class NowPlayingFragment extends Fragment implements NowPlayingView {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private NowPlayingPresenter nowPlayingPresenter;
    private ArrayList<Movie> listNowPlaying;
    private MovieAdapter movieAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    loadNowPlaying();
                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadNowPlaying();
                }
            });
        }
        if (savedInstanceState != null && savedInstanceState.containsKey("data_result")) {
            listNowPlaying = savedInstanceState.getParcelableArrayList("data_result");
            swipeRefreshLayout.setRefreshing(false);
            loadNowPlaying();

        } else if (savedInstanceState != null && savedInstanceState.containsKey("data_recycler_view")) {
            Parcelable parcelable = savedInstanceState.getParcelable("data_recycler_view");
            swipeRefreshLayout.setRefreshing(false);
            recyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (listNowPlaying != null) {
            outState.putParcelableArrayList("data_result", listNowPlaying);
        }
        if (recyclerView.getLayoutManager() != null) {
            outState.putParcelable("data_recycler_view", recyclerView.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        listNowPlaying = new ArrayList<>();
        nowPlayingPresenter = new NowPlayingPresenter(this);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showList(ArrayList<Movie> list) {
        listNowPlaying.clear();
        listNowPlaying.addAll(list);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNotList(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void loadNowPlaying() {
        nowPlayingPresenter.getListNowPlaying();
        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.setListMovie(listNowPlaying);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(movieAdapter);
    }
}
