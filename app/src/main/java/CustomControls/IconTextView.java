package CustomControls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class IconTextView extends TextView {

    public IconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeFace(context);
    }
    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeFace(context);
    }
    public IconTextView(Context context) {
        super(context);
        setTypeFace(context);
    }
    private void setTypeFace(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(),
            "fonts/icomoon.ttf"));
    }
}
