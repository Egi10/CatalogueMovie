package id.egifcb.cataloguemovie.ui.activity.main;

import java.util.ArrayList;

import id.egifcb.cataloguemovie.model.Movie;

interface MainView {
    void showLoading();
    void hideLoading();
    void showList(ArrayList<Movie> list);
}
