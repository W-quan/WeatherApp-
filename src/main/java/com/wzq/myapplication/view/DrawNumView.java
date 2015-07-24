package com.wzq.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class DrawNumView extends View{
	private int p;
	
	private Paint paint;
	
	private int t = 0;
	
	public DrawNumView(Context context) {
		super(context);
	}
	
	public DrawNumView(Context context, AttributeSet attrs, int defStyl) {
		super(context,attrs, defStyl);
	}
	
	public DrawNumView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		paint = new Paint();
		
		paint.setAntiAlias(true);
		paint.setColor(0xbbFF9966);
		paint.setTextSize(150);
		
		if (!isInEditMode()) {
			Typeface typeFace =Typeface.createFromAsset(context.getAssets(),
					"fonts/ITC Avant Garde Gothic LT Extra Light.ttf");
			paint.setTypeface(typeFace);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawText(Integer.toString(t), 0, 150, paint);
		if (t < p) {
			t++;
			invalidate();
		}
	}
	
	public void setPm25Value(String pm25) {
		if (pm25 != null) {
			p = Integer.parseInt(pm25);
		}else {
			p = 0;
		}
	}
}
