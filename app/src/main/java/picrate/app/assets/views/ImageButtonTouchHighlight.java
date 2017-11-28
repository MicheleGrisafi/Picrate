package androidlab.app.assets.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class ImageButtonTouchHighlight extends ImageButton {

    private Drawable mForegroundDrawable;
    private Rect mCachedBounds = new Rect();

    public ImageButtonTouchHighlight(Context context) {
        super(context);
        init();
    }

    public ImageButtonTouchHighlight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageButtonTouchHighlight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // Reset default ImageButton background and padding.
        setBackgroundColor(0);
        setPadding(0, 0, 0, 0);

        TypedArray a = getContext()
                .obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        mForegroundDrawable = a.getDrawable(0);
        mForegroundDrawable.setCallback(this);
        a.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (mForegroundDrawable.isStateful()) {
            mForegroundDrawable.setState(getDrawableState());
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mForegroundDrawable.setBounds(mCachedBounds);
        mForegroundDrawable.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Cache the view bounds.
        mCachedBounds.set(0, 0, w, h);
    }
}
