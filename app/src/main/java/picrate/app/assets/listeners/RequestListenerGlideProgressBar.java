package picrate.app.assets.listeners;

import android.widget.ProgressBar;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.net.URL;

/**
 * Created by miki4 on 24/09/2017.
 */

public class RequestListenerGlideProgressBar implements RequestListener<URL, GlideDrawable> {
    private ProgressBar progressBar;

    public RequestListenerGlideProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public boolean onException(Exception e, URL model, Target<GlideDrawable> target, boolean isFirstResource) {
        progressBar.setVisibility(ProgressBar.GONE);
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, URL model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        progressBar.setVisibility(ProgressBar.GONE);
        return false;
    }
}
