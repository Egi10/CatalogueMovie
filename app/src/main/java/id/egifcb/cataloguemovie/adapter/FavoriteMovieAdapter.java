package id.egifcb.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.egifcb.cataloguemovie.BuildConfig;
import id.egifcb.cataloguemovie.R;
import id.egifcb.cataloguemovie.model.Movie;
import id.egifcb.cataloguemovie.ui.activity.detailmovie.DetailMovieActivity;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {
    private Cursor listMovie;
    private Context context;

    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public void setListMovie(Cursor listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = getItem(position);

        Glide.with(context)
                .load(BuildConfig.IMG_URL+""+movie.getPosterPath())
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).centerCrop())
                .into(holder.ivPosterPath);

        holder.cardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity._ID, movie.getId());
                intent.putExtra(DetailMovieActivity.TITLE, movie.getTitle());
                intent.putExtra(DetailMovieActivity.VOTE_COUNT, movie.getVoteCount());
                intent.putExtra(DetailMovieActivity.VOTE_AVERAGE, movie.getVoteAverage());
                intent.putExtra(DetailMovieActivity.POPULARITY, movie.getPopularity());
                intent.putExtra(DetailMovieActivity.OVERVIEW, movie.getOverview());
                intent.putExtra(DetailMovieActivity.BACKDROP_PATH, movie.getBackdropPath());
                intent.putExtra(DetailMovieActivity.POSTER_PATH, movie.getPosterPath());
                intent.putExtra(DetailMovieActivity.RELEASE_DATE, movie.getReleaseDate());
                context.startActivity(intent);
            }
        });
    }

    private Movie getItem(int position) {
        if (!listMovie.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(listMovie);
    }

    @Override
    public int getItemCount() {
        if (listMovie == null) {
            return 0;
        }
        return listMovie.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPosterPath;
        private CardView cardList;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPosterPath = itemView.findViewById(R.id.iv_poster_path);
            cardList = itemView.findViewById(R.id.card_list);
        }
    }
}
