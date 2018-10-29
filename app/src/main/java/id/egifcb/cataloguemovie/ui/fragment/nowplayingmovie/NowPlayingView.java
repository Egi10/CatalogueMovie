package id.egifcb.cataloguemovie.ui.fragment.nowplayingmovie;

import java.util.ArrayList;

import id.egifcb.cataloguemovie.model.Movie;

public interface NowPlayingView {
    void showLoading();
    void hideLoading();
    void showList(ArrayList<Movie> list);
    void showNotList(String message);
    void showFailure(String message);
}
