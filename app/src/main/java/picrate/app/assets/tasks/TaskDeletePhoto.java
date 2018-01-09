package picrate.app.assets.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import picrate.app.DB.DAO.PhotoDAO;
import picrate.app.DB.DAO.implementations.PhotoDAO_DB_impl;
import picrate.app.DB.Objects.Photo;
import picrate.app.R;
import picrate.app.assets.objects.MyApp;

/**
 * Created by miki4 on 21/12/2017.
 */

public class TaskDeletePhoto extends AsyncTask<Void,Void,Boolean> {
    private PhotoDAO dao;
    private Photo photo;

    public TaskDeletePhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    protected void onPreExecute() {
        dao = new PhotoDAO_DB_impl();
        dao.open();
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean result = dao.deletePhoto(photo);
        return result;
    }
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        dao.close();
        if(!result)
            Toast.makeText(MyApp.getAppContext(), R.string.delete_photo_error, Toast.LENGTH_SHORT).show();
    }
}
