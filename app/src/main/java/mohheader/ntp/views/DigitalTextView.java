package mohheader.ntp.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mohheader on 6/17/14.
 */
public class DigitalTextView extends TextView {
    /*
     * Caches typefaces based on their file path and name, so that they don't have to be created every time when they are referenced.
     */
    private static Typeface mTypeface;

    public DigitalTextView(final Context context) {
        this(context, null);
    }

    public DigitalTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DigitalTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/digital.otf");
        }
        setTypeface(mTypeface);
        setPaintFlags(Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
}
