package id.egifcb.cataloguemovie.api;

import com.google.gson.Gson;

import id.egifcb.cataloguemovie.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    public static Retrofit getNework() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
