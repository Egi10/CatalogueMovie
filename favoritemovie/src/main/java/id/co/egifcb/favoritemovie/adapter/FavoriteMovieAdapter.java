package id.co.egifcb.favoritemovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import id.co.egifcb.favoritemovie.BuildConfig;
import id.co.egifcb.favoritemovie.R;

import static id.co.egifcb.favoritemovie.db.DatabaseContract.MovieColums.POSTER_PATH;
import static id.co.egifcb.favoritemovie.db.DatabaseContract.getColumnString;

public class FavoriteMovieAdapter extends CursorAdapter {
    public FavoriteMovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_movie, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            ImageView imageView = view.findViewById(R.id.iv_poster_path);

            Glide.with(context)
                    .load(BuildConfig.IMG_URL+""+getColumnString(cursor, POSTER_PATH))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).centerCrop())
                    .into(imageView);
        }

    }
}
