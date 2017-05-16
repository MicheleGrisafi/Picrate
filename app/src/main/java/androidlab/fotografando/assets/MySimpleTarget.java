package androidlab.fotografando.assets;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by Michele Grisafi on 16/05/2017.
 */

public class MySimpleTarget<Bitmap> extends SimpleTarget<Bitmap> {

    private LinearLayout myTarget;
    public MySimpleTarget() {
        super();
    }

    public MySimpleTarget(int width, int height) {
        super(width, height);
    }

    public MySimpleTarget(int width, int height, int target, View root) {
        super(width, height);
        myTarget = (LinearLayout) root.findViewById(target);

    }

    @Override
    public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
        BitmapDrawable ob = new BitmapDrawable(MyApp.getAppContext().getResources(), (android.graphics.Bitmap)resource);
        myTarget.setBackground(ob);
        myTarget.getBackground().setAlpha(120);
    }


}
