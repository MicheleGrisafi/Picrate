package picrate.app.assets.objects;

import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by Michele Grisafi on 16/05/2017.
 */

/** Classe di sostegno a Glide **/
public class MySimpleTarget<Bitmap> extends SimpleTarget<Bitmap> {

    private ConstraintLayout myTarget;

    public MySimpleTarget(int width, int height) {
        super(width, height);
    }

    public MySimpleTarget(int width, int height, int target, View root) {
        this(width, height);
        myTarget = (ConstraintLayout) root.findViewById(target);
    }
    public MySimpleTarget(int width, int height, ConstraintLayout target) {
        this(width, height);
        myTarget = target;
    }

    @Override
    public void onResourceReady(Object resource, GlideAnimation glideAnimation) {
        BitmapDrawable ob = new BitmapDrawable(MyApp.getAppContext().getResources(), (android.graphics.Bitmap)resource);
        myTarget.setBackground(ob);
        myTarget.getBackground().setAlpha(90);
    }


}
