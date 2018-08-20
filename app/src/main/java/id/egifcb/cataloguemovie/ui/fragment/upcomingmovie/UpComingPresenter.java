package id.egifcb.cataloguemovie.ui.fragment.upcomingmovie;

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

public class UpComingPresenter {
    private UpComingView upComingView;
    private Context context;

    public UpComingPresenter(UpComingView upComingView, Context context) {
        this.upComingView = upComingView;
        this.context = context;
    }

    public void getListUpComing() {
        upComingView.showLoading();

        ApiInterface api = ApiConfig.getNework().create(ApiInterface.class);
        Call<Value> call = api.getUpComing(BuildConfig.API_KEY, Locale.getDefault().toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                upComingView.hideLoading();

                if (response.isSuccessful()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        upComingView.showList(Objects.requireNonNull(response.body()).getResult());
                    }
                } else {
                    Toast.makeText(context, "Terdapat Kesalahan Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                upComingView.hideLoading();

                Toast.makeText(context, "Periksa Jaringan Internet Anda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
