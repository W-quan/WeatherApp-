package com.wzq.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wzq.myapplication.activity.UpdatedActivity;
import com.wzq.myapplication.bean.WeatherFromLib360;

public class TodayChartView extends View{
	private Paint paintA;
	private Paint paintB;
	private Paint paintC;

	private int viewWidth;
	private int viewHight;

	private float Xstartx, Xstopx, Xy;
	private float Yx, Ystarty, Ystopy;
	private float Xlen, Ylen;
	
	private float[] tempMax;
	private float[] tempMin;
	
	private int i = 1;
	private int j = 0;
	public TodayChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public TodayChartView(Context context) {
		super(context);
	}
	public TodayChartView(Context context, AttributeSet attrs) {
		super(context, attrs);

		WeatherFromLib360 weatherFromLib360 = UpdatedActivity.weatherFromLib360;
		
		tempMax = new float[8];
		tempMin = new float[8];
		
		if (!isInEditMode()) {
			for(int i = 0; i < 8; i++){
				tempMax[i] = Float.parseFloat(weatherFromLib360.getData24().get(i).getTempMax());
				tempMin[i] = Float.parseFloat(weatherFromLib360.getData24().get(i).getTempMin());
			}
		}
		
		paintA = new Paint();
		paintA.setAntiAlias(true);
		paintA.setStrokeWidth(4);
		paintA.setTextSize(30);
		
		paintB = new Paint();
		paintB.setAntiAlias(false);
		paintB.setStrokeWidth(6);
		paintB.setColor(0xeeFF9900);
		paintB.setTextSize(30);
		
		paintC = new Paint();
		paintC.setAntiAlias(true);
		paintC.setStrokeWidth(6);
		paintC.setColor(0xee3333CC);
		paintC.setTextSize(30);
		

		System.out.println("gouzhao");
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		viewWidth = measureSize(widthMeasureSpec);
		viewHight = measureSize(heightMeasureSpec);
		System.out.println(viewWidth);
		System.out.println(viewHight);

		Xstartx = viewWidth / 10; Xstopx = viewWidth * 9/10; Xy = viewHight *9/10;
		Ystarty = viewHight / 10; Ystopy = Xy; Yx = Xstartx;

//		Xlen = Xstopx - Xstartx;
//		Ylen = Ystopy - Ystarty;

		Xlen = viewWidth *4/5;
		Ylen = viewHight *4/5;
	}

	private int measureSize(int measureSpec) {
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		// Default size if no limits are specified.
		int result = 500;
		if (specMode == MeasureSpec.AT_MOST) {
			// Calculate the ideal size of your control within this maximum size.
			// If your control fills the available space return the outer bound.
			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY) {
			// If your control can fit within these bounds return that value.
			result = specSize;
		}
		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		System.out.println("ondraw");
		//画坐标系
		//X、Y轴
		canvas.drawLine(Yx, Ystarty, Yx, Ystopy, paintA);
		canvas.drawLine(Xstartx, Xy, Xstopx, Xy, paintA);
		
		canvas.drawLine(Xstartx + Xlen/10 *8, Ystarty, Xstartx + Xlen/10 *9, Ystarty, paintB);
		canvas.drawText("Max", Xstartx + Xlen/10 *9 + 5, Ystarty + 10, paintB);
		canvas.drawLine(Xstartx + Xlen/10 *8, Ystarty + 40, Xstartx + Xlen/10 *9, Ystarty + 40, paintC);
		canvas.drawText("Min", Xstartx + Xlen/10 *9 + 5, Ystarty + 50, paintC);
		
		//Y轴上的单位线
		float sl = 20;
		for(int i = 0; i < 10; i++){
			canvas.drawLine(Yx, Ystarty + Ylen/10 *i, Yx + sl, Ystarty + Ylen/10 * i, paintA);
		}
		
		canvas.drawText("5", Xstartx - 25, Ystopy - Ylen/10 + 10, paintA);
		for(int i = 2; i < 10; i++){
			canvas.drawText("" + i*5, Xstartx - 35, Ystopy - Ylen/10 * (i) + 10, paintA);
		}
		canvas.drawText("温度/℃", Xstartx - 25, Ystopy - Ylen/10 * 10 - 20, paintA);
		
		//X轴上的单位线
		for(int i = 1; i < 10; i++){
			canvas.drawLine(Xstartx + Xlen/10 * i, Xy - sl, Xstartx + Xlen/10 * i, Xy, paintA);
		}
		
		for(int i = 1; i < 9; i++){
			canvas.drawText("" + (2+(i-1)*3), Xstartx + Xlen/10 * i - 10, Xy + sl + 5, paintA);
		}
		canvas.drawText("2", Xstartx + Xlen/10 * 9 - 10, Xy + sl + 5, paintA);
		
		canvas.drawText("时间/h", Xstartx + Xlen/10 * 9 - 5, Xy + sl + 35, paintA);
		
		//画温度线
		for(int i = 0; i < j; i++){
			canvas.drawLine(Xstartx + Xlen/10 * (i+1), (float) (Xy - (tempMax[i]/ 50.0 * Ylen)),
					Xstartx + Xlen/10 * (i+2), (float) (Xy - (tempMax[i + 1]/ 50.0 * Ylen)), paintB);
		}
		for(int i = 0; i < j; i++){
			canvas.drawLine(Xstartx + Xlen/10 * (i+1), (float) (Xy - (tempMin[i]/ 50.0 * Ylen)),
					Xstartx + Xlen/10 * (i+2), (float) (Xy - (tempMin[i + 1]/ 50.0 * Ylen)), paintC);
		}
		
		float xstart = Xstartx + Xlen/10 * (j+1);
		float xstop = Xstartx + Xlen/10 * (j+2);
		float ystart1 = (float) (Xy - (tempMax[j]/ 50.0 * Ylen));
		float ystop1 = (float) (Xy - (tempMax[j+1]/ 50.0 * Ylen));
		float ystart2 = (float) (Xy - (tempMin[j]/ 50.0 * Ylen));
		float ystop2 = (float) (Xy - (tempMin[j+1]/ 50.0 * Ylen));
		canvas.drawLine(xstart, ystart1,  xstart + (xstop - xstart)/5 * i, ystart1 + (ystop1 - ystart1)/5 * i , paintB);
		canvas.drawLine(xstart, ystart2,  xstart + (xstop - xstart)/5 * i, ystart2 + (ystop2 - ystart2)/5 * i , paintC);
		if (i < 6) {
			i++;
			invalidate();
		}
		if (j < 6 && i == 6) {
			j++;
			i = 0;
			invalidate();
		}
	}
}
