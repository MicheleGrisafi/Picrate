package androidlab.fotografando.assets.tasks;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.Objects.Photo;
import androidlab.fotografando.assets.objects.AppInfo;

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
