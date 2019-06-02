package tk.worldtechq.wally.Utils;

import android.content.Context;
import android.util.AttributeSet;

public class SquareImages extends android.support.v7.widget.AppCompatImageView {
    public SquareImages(Context context) {
        super(context);
    }

    public SquareImages(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImages(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth());
    }
}
