package com.wzq.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import com.wzq.myapplication.R;

public class TodayWeatherActivity extends Activity implements OnTouchListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.today_weather_activity);
		
		RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.today_chart_layout);
		rLayout.setOnTouchListener(this);
	}

	private float downY = 0, upY = 0;
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.performClick();
		int actionType = event.getActionMasked();
		switch (actionType) {
			case MotionEvent.ACTION_DOWN:
				downY = event.getY();
				upY = downY;
				return true;
			case MotionEvent.ACTION_UP:
				upY = event.getY();
				if (upY - downY> 100) {
					finish();
					overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
				}
				return true;
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.in_from_up, R.anim.out_to_down);
	}

}
