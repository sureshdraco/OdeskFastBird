package appinventor.ai_sameh.FastBird.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.util.TypefaceCache;

public class TextViewPlus extends TextView {
	private static final String TAG = TextView.class.getSimpleName();

	public TextViewPlus(Context context) {
		super(context);
	}

	public TextViewPlus(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, attrs);
	}

	public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont(context, attrs);
	}

	private void setCustomFont(Context ctx, AttributeSet attrs) {
		TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
		String customFont = a.getString(R.styleable.TextViewPlus_font);
		setCustomFont(customFont);
		a.recycle();
	}

	public boolean setCustomFont(String asset) {
		if (!isInEditMode()) {
			Typeface tf = TypefaceCache.getTypeface(asset);
			if (tf != null) {
				setTypeface(tf);
			}
		}
		return true;
	}

}
