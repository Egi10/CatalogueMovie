package id.egifcb.cataloguemovie.model;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import static id.egifcb.cataloguemovie.db.DatabaseContract.getColumnInt;
import static id.egifcb.cataloguemovie.db.DatabaseContract.getColumnString;
import static id.egifcb.cataloguemovie.ui.activity.detailmovie.DetailMovieActivity.BACKDROP_PATH;
import static id.egifcb.cataloguemovie.ui.activity.detailmovie.DetailMovieActivity.OVERVIEW;
import static id.egifcb.cataloguemovie.ui.activity.detailmovie.DetailMovieActivity.POPULARITY;
import static id.egifcb.cataloguemovie.ui.activity.detailmovie.DetailMovieActivity.POSTER_PATH;
import static id.egifcb.cataloguemovie.ui.activity.detailmovie.DetailMovieActivity.RELEASE_DATE;
import static id.egifcb.cataloguemovie.ui.activity.detailmovie.DetailMovieActivity.TITLE;
import static id.egifcb.cataloguemovie.ui.activity.detailmovie.DetailMovieActivity.VOTE_AVERAGE;
import static id.egifcb.cataloguemovie.ui.activity.detailmovie.DetailMovieActivity.VOTE_COUNT;
import static id.egifcb.cataloguemovie.ui.activity.detailmovie.DetailMovieActivity._ID;

public class Movie {
    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("id")
    private int id;
    @SerializedName("video")
    private String video;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("title")
    private String title;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("originalTitle")
    private String originalTitle;
    @SerializedName("genre_ids")
    private String[] genreIds;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("adult")
    private String adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(String[] genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Movie(Cursor cursor) {
        this.voteCount = getColumnString(cursor, VOTE_COUNT);
        this.id = getColumnInt(cursor, _ID);
        this.voteAverage = getColumnString(cursor, VOTE_AVERAGE);
        this.title = getColumnString(cursor, TITLE);
        this.popularity = getColumnString(cursor, POPULARITY);
        this.posterPath = getColumnString(cursor, POSTER_PATH);
        this.backdropPath = getColumnString(cursor, BACKDROP_PATH);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.releaseDate = getColumnString(cursor, RELEASE_DATE);
    }


}
