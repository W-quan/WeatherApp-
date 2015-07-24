package com.wzq.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class HumidityView extends View {
	private int h;
	private int t = 0;

	private Paint paintA;
	private Paint paintB;
	private Paint paintC;

	private RectF oval;

	private int dStart = 0;
	private int dEndA = 2;
	private int dEndB = 2;

	public HumidityView(Context context) {
		super(context);
	}

	public HumidityView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public HumidityView(Context context, AttributeSet attrs) {
		super(context, attrs);

		paintA = new Paint();
		paintA.setAntiAlias(true);
		paintA.setColor(0xffCBCBCB);
		paintA.setStyle(Style.STROKE);
		paintA.setStrokeWidth(4);

		paintB = new Paint();
		paintB.setAntiAlias(true);
		paintB.setColor(0xff000000);
		paintB.setStyle(Style.STROKE);
		paintB.setStrokeWidth(4);

		paintC = new Paint();
		paintC.setAntiAlias(true);
		paintC.setColor(0xff000000);
		paintC.setTextSize(70);

		if (!isInEditMode()) {
			Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
					"fonts/ITC Avant Garde Gothic LT Extra Light.ttf");
			paintC.setTypeface(typeFace);
		}

		oval = new RectF(40, 30, 240, 230);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawArc(oval, dStart, dEndA, false, paintA);
		canvas.drawArc(oval, dStart, dEndB, false, paintB);
		canvas.drawText(Integer.toString(t) + "%", 80, 160, paintC);

		if (t < h) {
			t++;
		}

		if (dStart < 40) {
			dStart += 2;
		}
		if (dEndB < (float) h / 100.0 * 280) {
			dEndB += 2;
//			System.out.println(dEndB);
		}
		if (dEndA < 280) {
			dEndA += 6;
		}

		if (dStart < 40 || dEndA < 280 || dEndB < (float) h / 100.0 * 280 || t < h) {
			invalidate();
		}
	}

	public void setHumidity(String humidity) {
		if (humidity != null) {
			String[] t = humidity.split("%");
			h = Integer.parseInt(t[0]);
		} else {
			h = 0;
		}
	}
}
