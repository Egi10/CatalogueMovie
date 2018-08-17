package id.egifcb.cataloguemovie.ui.activity.main;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Locale;

import id.egifcb.cataloguemovie.BuildConfig;
import id.egifcb.cataloguemovie.api.Network;
import id.egifcb.cataloguemovie.api.Routes;
import id.egifcb.cataloguemovie.model.Value;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainView mainView;

    MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void getList(String query) {
        mainView.showLoading();

        Routes api = Network.getNework().create(Routes.class);
        Call<Value> call = api.getSearchMovie(BuildConfig.API_KEY, Locale.getDefault().toString(), query);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(@NonNull Call<Value> call, @NonNull Response<Value> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResult().size() <= 0) {
                        Log.d("Presenter", "Tidak Ada Movie");
                    } else {
                        mainView.showList(response.body().getResult());
                        mainView.hideLoading();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Value> call, @NonNull Throwable t) {
                mainView.hideLoading();
            }
        });
    }
}
