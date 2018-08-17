package id.egifcb.cataloguemovie.api;

import id.egifcb.cataloguemovie.model.Value;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Routes {
    @GET("search/movie")
    Call<Value> getSearchMovie(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query);
}
