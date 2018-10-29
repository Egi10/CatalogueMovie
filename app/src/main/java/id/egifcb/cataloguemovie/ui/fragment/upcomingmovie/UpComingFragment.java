package id.egifcb.cataloguemovie.ui.fragment.upcomingmovie;

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

public class UpComingFragment extends Fragment implements UpComingView {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private UpComingPresenter upComingPresenter;
    private ArrayList<Movie> listUpComing;
    private MovieAdapter movieAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_up_coming, container, false);
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
            listUpComing = savedInstanceState.getParcelableArrayList("data_result");
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
        if (listUpComing != null) {
            outState.putParcelableArrayList("data_result", listUpComing);
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

        listUpComing = new ArrayList<>();
        upComingPresenter = new UpComingPresenter(this);
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
        listUpComing.clear();
        listUpComing.addAll(list);
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
        upComingPresenter.getListUpComing();
        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.setListMovie(listUpComing);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(movieAdapter);
    }
}
