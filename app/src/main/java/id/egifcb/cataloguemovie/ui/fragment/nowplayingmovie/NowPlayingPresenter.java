package id.egifcb.cataloguemovie.ui.fragment.nowplayingmovie;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import id.egifcb.cataloguemovie.BuildConfig;
import id.egifcb.cataloguemovie.api.ApiConfig;
import id.egifcb.cataloguemovie.api.ApiInterface;
import id.egifcb.cataloguemovie.model.Value;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingPresenter {
    private NowPlayingView nowPlayingView;
    private Context context;

    NowPlayingPresenter(NowPlayingView nowPlayingView, Context context) {
        this.nowPlayingView = nowPlayingView;
        this.context = context;
    }

    public void getListNowPlaying() {
        nowPlayingView.showLoading();

        ApiInterface api = ApiConfig.getNework().create(ApiInterface.class);
        Call<Value> call = api.getNowPlaying(BuildConfig.API_KEY, Locale.getDefault().toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                nowPlayingView.hideLoading();

                if (response.isSuccessful()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        nowPlayingView.showList(Objects.requireNonNull(response.body()).getResult());
                    }
                } else {
                    Toast.makeText(context, "Maaf Terdapat Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                nowPlayingView.hideLoading();

                Toast.makeText(context, "Jaringan Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
