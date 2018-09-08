package id.egifcb.cataloguemovie.ui.activity.searchmovie;

import java.util.ArrayList;

import id.egifcb.cataloguemovie.model.Movie;

interface SearchView {
    void showLoading();
    void hideLoading();
    void showList(ArrayList<Movie> list);
    void showListEmpty();
    void showFailure(String message);
}
