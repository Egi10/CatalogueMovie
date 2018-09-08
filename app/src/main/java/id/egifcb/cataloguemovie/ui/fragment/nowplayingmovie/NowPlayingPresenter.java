package id.egifcb.cataloguemovie.ui.fragment.nowplayingmovie;

import java.util.Locale;
import id.egifcb.cataloguemovie.BuildConfig;
import id.egifcb.cataloguemovie.api.ApiConfig;
import id.egifcb.cataloguemovie.api.ApiInterface;
import id.egifcb.cataloguemovie.model.Value;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingPresenter {
    private NowPlayingView nowPlayingView;

    public NowPlayingPresenter(NowPlayingView nowPlayingView) {
        this.nowPlayingView = nowPlayingView;
    }

    public void getListNowPlaying() {
        nowPlayingView.showLoading();

        ApiInterface api = ApiConfig.getNework().create(ApiInterface.class);
        Call<Value> call = api.getNowPlaying(BuildConfig.API_KEY, Locale.getDefault().toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                nowPlayingView.hideLoading();

                if (response.isSuccessful()) {
                    nowPlayingView.showList(response.body().getResult());
                } else {
                    nowPlayingView.showNotList(response.message());
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                nowPlayingView.hideLoading();
                nowPlayingView.showFailure(t.getMessage());
            }
        });
    }
}
