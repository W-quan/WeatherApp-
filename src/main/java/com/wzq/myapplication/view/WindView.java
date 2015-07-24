package com.wzq.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class WindView extends View{
	private Paint paintA;
	
	public WindView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public WindView(Context context) {
		super(context);
	}
	public WindView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		paintA = new Paint();
		paintA.setAntiAlias(true);
		paintA.setTextSize(25);
		paintA.setStrokeWidth(10);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawLine(0, 0, 0, 180, paintA);
	}
}
