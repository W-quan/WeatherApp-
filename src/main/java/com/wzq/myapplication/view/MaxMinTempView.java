package com.wzq.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MaxMinTempView extends View{
	private int maxT = 35;
	private int minT = 25;
	private int aT = 0;
	private int iT = 0;
	
	private Paint paintA;
	private Paint paintB;
	private Paint paintC;

	private int yStart = 150;
	private int yMin = 150;
	private int yMax = 150;
	
	public MaxMinTempView(Context context) {
		super(context);
	}

	public MaxMinTempView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MaxMinTempView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		paintA = new Paint();
		paintA.setAntiAlias(true);
		paintA.setStrokeWidth(3);
		paintA.setTextSize(25);
		
		paintB = new Paint();
		paintB.setAntiAlias(true);
		paintB.setColor(0xff000000);
		paintB.setStrokeWidth((float) 0.5);
		
		paintC = new Paint();
		paintC.setAntiAlias(true);
		paintC.setStrokeWidth(50);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawLine(20, 150, 320, 150, paintA);
		canvas.drawLine(110, 40, 110, 250, paintB);
		canvas.drawLine(170, 40, 170, 250, paintB);
		
//		canvas.drawLine(85, 120, 85, 150, paintC);
//		canvas.drawLine(195, 80, 195, 150, paintC);
		
		canvas.drawLine(85, yMin , 85, 150, paintC);
		canvas.drawLine(195, yMax , 195, 150, paintC);
		
		canvas.drawText("Min" + iT + "°", 50, yMin - 10, paintA);
		canvas.drawText("Max" + aT + "°", 160, yMax - 10, paintA);
		
		if (minT > 0) {
			if (yMin > yStart - minT * 2.5) {
				yMin -= 2;
			}
			if (iT < minT) {
				iT++;
			}
		}else {
			if (yMin < yStart + minT * 2.5) {
				yMin += 2;
			}
			if (iT > minT) {
				iT--;
			}
		}
		
		if (maxT > 0) {
			if (yMax > yStart - maxT * 2.5) {
				yMax -= 2;
			}
			if (aT < maxT) {
				aT++;
			}
		}else {
			if (yMax < yStart + maxT * 2.5) {
				yMax += 2;
			}
			if (aT > maxT) {
				aT--;
			}
		}
		
		if (yMin > yStart - minT * 2.5 || yMin < yStart + minT * 2.5
				|| yMax > yStart - maxT * 2.5 || yMax < yStart + maxT * 2.5
				|| iT < minT || iT > minT || aT < maxT || aT > maxT) {
			invalidate();
		}
	}
	
	public void setTemp(String temp) {
		if (temp.length() > 6) {
			String[] a = temp.split(" ");
			this.maxT = Integer.parseInt(a[0]);
			String[] b = a[2].split("℃");
			this.minT = Integer.parseInt(b[0]);
		}
	}
}
