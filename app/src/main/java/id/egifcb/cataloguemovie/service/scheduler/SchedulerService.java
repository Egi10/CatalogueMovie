package id.egifcb.cataloguemovie.service.scheduler;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import id.egifcb.cataloguemovie.R;
import id.egifcb.cataloguemovie.broadcast.AlarmReceiver;
import id.egifcb.cataloguemovie.model.Movie;

public class SchedulerService extends JobService implements SchedulerView {
    private SchedulerPresenter schedulerPresenter;
    private AlarmReceiver alarmReceiver = new AlarmReceiver();

    @Override
    public boolean onStartJob(JobParameters job) {
        schedulerPresenter = new SchedulerPresenter(this);
        schedulerPresenter.getListUpComing();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    @Override
    public void showLoading() {
        Log.d("showloading", "Loading");
    }

    @Override
    public void hideLoading() {
        Log.d("hideLoading", "Hide Loading");

    }

    @Override
    public void showList(ArrayList<Movie> list) {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateDay = dateFormat.format(currentDate.getTime());
        for (int i = 0; i < list.size(); i++) {
            String date = list.get(i).getReleaseDate();
            String title = list.get(i).getTitle();
            String deskripsi = list.get(i).getOverview();

            if (dateDay.equals(date)) {
                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME, date, "08:00", title, deskripsi);
            }
        }
    }

    @Override
    public void showListEmpty() {
        Log.d("showListEmpty", "showListEmpty");
    }

    @Override
    public void showFailure(String message) {
        Log.d("showFailure", "showFailure");
    }
}
