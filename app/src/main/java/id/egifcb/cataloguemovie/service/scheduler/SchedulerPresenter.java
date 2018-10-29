package id.egifcb.cataloguemovie.service.scheduler;

import java.util.Locale;

import id.egifcb.cataloguemovie.BuildConfig;
import id.egifcb.cataloguemovie.api.ApiConfig;
import id.egifcb.cataloguemovie.api.ApiInterface;
import id.egifcb.cataloguemovie.model.Value;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulerPresenter {
    private SchedulerView schedulerView;

    public SchedulerPresenter(SchedulerView schedulerView) {
        this.schedulerView = schedulerView;
    }

    public void getListUpComing() {
        schedulerView.showLoading();

        ApiInterface api = ApiConfig.getNework().create(ApiInterface.class);
        Call<Value> call = api.getUpComing(BuildConfig.API_KEY, Locale.getDefault().toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                schedulerView.hideLoading();

                if (response.isSuccessful()) {
                    schedulerView.showList(response.body().getResult());
                } else {
                    schedulerView.showListEmpty();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                schedulerView.hideLoading();
                schedulerView.showFailure(t.getMessage());
            }
        });
    }
}
