package picrate.app.assets.tasks;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import picrate.app.DB.DAO.PhotoDAO;
import picrate.app.DB.DAO.implementations.PhotoDAO_DB_impl;
import picrate.app.DB.Objects.Photo;
import picrate.app.assets.objects.AppInfo;

/**
 * Created by miki4 on 27/09/2017.
 */

public class AsyncTaskLoaderLoadRatings extends AsyncTaskLoader<List<Photo>> {
    public AsyncTaskLoaderLoadRatings(Context context) {
        super(context);
    }

    @Override
    public List<Photo> loadInBackground() {
        PhotoDAO dao = new PhotoDAO_DB_impl();
        dao.open();
        ArrayList<Photo> result = dao.getRatingPhotos(AppInfo.getUtente());
        dao.close();
        return result;
    }
}
