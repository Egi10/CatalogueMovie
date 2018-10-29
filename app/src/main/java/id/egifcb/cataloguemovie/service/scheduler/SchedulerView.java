package id.egifcb.cataloguemovie.service.scheduler;

import java.util.ArrayList;

import id.egifcb.cataloguemovie.model.Movie;

public interface SchedulerView {
    void showLoading();
    void hideLoading();
    void showList(ArrayList<Movie> list);
    void showListEmpty();
    void showFailure(String message);
}
