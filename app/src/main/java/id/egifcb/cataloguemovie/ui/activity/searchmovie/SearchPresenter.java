package id.egifcb.cataloguemovie.ui.activity.searchmovie;

import java.util.Locale;

import id.egifcb.cataloguemovie.BuildConfig;
import id.egifcb.cataloguemovie.api.ApiConfig;
import id.egifcb.cataloguemovie.api.ApiInterface;
import id.egifcb.cataloguemovie.model.Value;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter {
    private SearchView searchView;

    SearchPresenter(SearchView searchView) {
        this.searchView = searchView;
    }

    public void getList(String query) {
        searchView.showLoading();

        ApiInterface api = ApiConfig.getNework().create(ApiInterface.class);
        Call<Value> call = api.getSearchMovie(BuildConfig.API_KEY, Locale.getDefault().toString(), query);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResult().size() <= 0) {
                        searchView.showListEmpty();
                    } else {
                        searchView.showList(response.body().getResult());
                        searchView.hideLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                searchView.hideLoading();
                searchView.showFailure(t.getMessage());
            }
        });
    }
}
