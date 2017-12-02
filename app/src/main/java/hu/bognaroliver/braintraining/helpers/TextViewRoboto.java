package hu.bognaroliver.braintraining.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 *  Make a new TextView widget with Roboto font-family
 */
public class TextViewRoboto extends android.support.v7.widget.AppCompatTextView
{
	public TextViewRoboto(Context context)
	{
		super(context);
	}

	public TextViewRoboto(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public TextViewRoboto(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public void setTypeface(Typeface tf, int style)
	{
		switch (style)
		{
			case Typeface.BOLD:
				super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Regular.ttf"));
				break;

			case Typeface.ITALIC:
				super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-ThinItalic.ttf"));
				break;

			case Typeface.BOLD_ITALIC:
				super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-RegularItalic.ttf"));
				break;

			default:
				super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/Roboto-Thin.ttf"));
				break;
		}
	}


}
