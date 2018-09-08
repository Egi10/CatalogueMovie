package id.egifcb.cataloguemovie.ui.fragment.upcomingmovie;

import java.util.Locale;
import id.egifcb.cataloguemovie.BuildConfig;
import id.egifcb.cataloguemovie.api.ApiConfig;
import id.egifcb.cataloguemovie.api.ApiInterface;
import id.egifcb.cataloguemovie.model.Value;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingPresenter {
    private UpComingView upComingView;

    public UpComingPresenter(UpComingView upComingView) {
        this.upComingView = upComingView;
    }

    public void getListUpComing() {
        upComingView.showLoading();

        ApiInterface api = ApiConfig.getNework().create(ApiInterface.class);
        Call<Value> call = api.getUpComing(BuildConfig.API_KEY, Locale.getDefault().toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                upComingView.hideLoading();

                if (response.isSuccessful()) {
                    upComingView.showList(response.body().getResult());
                } else {
                    upComingView.showNotList(response.message());
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                upComingView.hideLoading();
                upComingView.showFailure(t.getMessage());
            }
        });
    }
}
