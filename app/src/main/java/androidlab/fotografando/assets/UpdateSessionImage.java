package androidlab.fotografando.assets;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidlab.DB.Objects.Photo;

/**
 * Created by miki4 on 19/05/2017.
 */

public class UpdateSessionImage extends AsyncTask<Void,Void,Void> {
    private Photo foto;
    private ImageView imageView;
    private Context context;
    public UpdateSessionImage(Photo foto, ImageView imageView, Context context){
        this.foto = foto;
        this.imageView = imageView;
        this.context = context;
    }
    @Override
    protected Void doInBackground(Void... params) {
        Glide.with(context).load(foto.getImage()).into(imageView);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
