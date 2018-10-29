package id.egifcb.cataloguemovie.ui.fragment.upcomingmovie;

import java.util.ArrayList;

import id.egifcb.cataloguemovie.model.Movie;

interface UpComingView {
    void showLoading();
    void hideLoading();
    void showList(ArrayList<Movie> list);
    void showNotList(String message);
    void showFailure(String message);
}
