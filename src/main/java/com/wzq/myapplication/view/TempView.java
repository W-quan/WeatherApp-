package com.wzq.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class TempView extends View{
	public int temp;
	private int t = 0;
	private Paint paint;
	private Paint paintC;
	
	private RectF oval;
	
	public TempView(Context context) {
		super(context);
	}
	
	public TempView(Context context,AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public TempView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		paint = new Paint();
		paintC = new Paint();
		
		paint.setAntiAlias(true);
		paint.setColor(0xbb912AEA);
		paint.setTextSize(150);
		
		if (!isInEditMode()) {
			Typeface typeFace =Typeface.createFromAsset(context.getAssets(),
					"fonts/ITC Avant Garde Gothic LT Extra Light.ttf");
			paint.setTypeface(typeFace);
		}
		
		paintC.setColor(0xbb912AEA);
		paintC.setStrokeWidth(4);
		paintC.setAntiAlias(true);
		paintC.setStyle(Style.STROKE);
		oval = new RectF(180, 40, 225, 85);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawText(Integer.toString(t), 0, 150, paint);
		canvas.drawArc(oval, 2, 360, false, paintC);
		
		if (t < temp) {
			t++;
			invalidate();
		}
	}
	
	public void  setTemp(String temp) {
		String[] a = temp.split("：");
		a = a[1].split("℃");
		this.temp = Integer.parseInt(a[0]);
	}
}
