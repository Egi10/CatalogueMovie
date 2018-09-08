package id.egifcb.cataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

import id.egifcb.cataloguemovie.BuildConfig;
import id.egifcb.cataloguemovie.R;
import id.egifcb.cataloguemovie.model.Movie;

import static id.egifcb.cataloguemovie.db.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Cursor cursor;

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(CONTENT_URI, null, null,
                null, null);
    }

    @Override
    public void onDataSetChanged() {
        final long identify = Binder.clearCallingIdentity();

        cursor = context.getContentResolver().query(CONTENT_URI, null, null,
                null, null);

        Binder.restoreCallingIdentity(identify);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Movie movie = getItem(i);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget_item);
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context).asBitmap()
                    .load(BuildConfig.IMG_URL +""+ movie.getPosterPath())
                    .submit().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        remoteViews.setImageViewBitmap(R.id.imageView, bitmap);

        Intent fillIntent = new Intent();
        fillIntent.putExtra(FavoriteWidget.EXTRA_ITEM, movie.getTitle());
        fillIntent.putExtras(fillIntent);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return remoteViews;
    }

    private Movie getItem(int i) {
        if (!cursor.moveToPosition(i)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new Movie(cursor);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
