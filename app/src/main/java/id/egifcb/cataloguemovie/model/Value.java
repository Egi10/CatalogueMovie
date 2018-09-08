package id.egifcb.cataloguemovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Value {
    @SerializedName("results")
    private ArrayList<Movie> result;

    public ArrayList<Movie> getResult() {
        return result;
    }
}
