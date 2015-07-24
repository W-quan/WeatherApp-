package com.wzq.myapplication.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wzq.myapplication.R;
import com.wzq.myapplication.bean.WeatherBeanFomeBaidu;
import com.wzq.myapplication.bean.WeatherBeanFomeCn;
import com.wzq.myapplication.util.GetImgIdByWeather;
import com.wzq.myapplication.view.DrawNumView;
import com.wzq.myapplication.view.HumidityView;
import com.wzq.myapplication.view.MaxMinTempView;
import com.wzq.myapplication.view.TempView;

public class MainActivity extends Activity implements OnTouchListener {
	private SharedPreferences setting;

	private TextView cityName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		WeatherBeanFomeBaidu weatherFromBaidu = UpdatedActivity.weatherFromBaidu;
		WeatherBeanFomeCn weatherFomeCnAdat = UpdatedActivity.weatherFomeCnAdat;

		setting = getSharedPreferences("setting_info", 0);

		cityName = (TextView) findViewById(R.id.cityName_text);
		cityName.setText(setting.getString("localCity", "广州"));

		TextView date = (TextView) findViewById(R.id.date_text);
		TextView time = (TextView) findViewById(R.id.time_text);

		if (weatherFromBaidu == null) {
			Toast.makeText(getApplicationContext(), "更新天气失败，请检查网络", Toast.LENGTH_SHORT).show();
			date.setText(setting.getString("updatedDate", "2015-6-25"));
			time.setText(setting.getString("updatedTime", "8:00 更新"));
		}

		Date local_date = new Date();
		SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		dfDate.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		date.setText(dfDate.format(local_date));

		SimpleDateFormat dfTime = new SimpleDateFormat("H:mm", Locale.getDefault());
		dfTime.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		time.setText(dfTime.format(local_date) + "更新");

		setting.edit().putString("updatedDate", dfDate.format(local_date))
				.putString("updatedTime", dfTime.format(local_date) + "更新").apply();

		TextView weatherText = (TextView) findViewById(R.id.weather_text);
		ImageView weatherImg = (ImageView) findViewById(R.id.weather_Img);
		weatherImg.setImageResource(
				new GetImgIdByWeather().getDrawable(UpdatedActivity.weatherFromLib360.getData().get(0).getWeather()));

		TextView humidityText = (TextView) findViewById(R.id.humidity_text);
		HumidityView humidityView = (HumidityView) findViewById(R.id.humidity_view);
		humidityView.setHumidity(weatherFomeCnAdat.getWeatherinfo().getSD());

		TextView minmaxTempText = (TextView) findViewById(R.id.minmaxTemp_text);
		MaxMinTempView minmaxTempView = (MaxMinTempView) findViewById(R.id.minmaxTemp_view);
		assert weatherFromBaidu != null;
		minmaxTempView.setTemp(weatherFromBaidu.getResults().get(0).getWeatherDatas().get(0).getTemperature());

		ImageButton menuButton = (ImageButton) findViewById(R.id.menuButton);
		menuButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, PreferenceActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
		});

		TextView tempText = (TextView) findViewById(R.id.temp_text);
		TempView tempView = (TempView) findViewById(R.id.temp_View);
		tempView.setTemp(weatherFromBaidu.getResults().get(0).getWeatherDatas().get(0).getDate());

		TextView pm25Text = (TextView) findViewById(R.id.pm25_text);
		DrawNumView pm25Show = (DrawNumView) findViewById(R.id.pm25_Show);
		pm25Show.setPm25Value(weatherFromBaidu.getResults().get(0).getPm25());

		TextView windText = (TextView) findViewById(R.id.wind_text);
		ImageView windfenche = (ImageView) findViewById(R.id.wind_fenche);

		TextView windValueText = (TextView) findViewById(R.id.wind_value_text);
		windValueText.setText("风力:" + weatherFomeCnAdat.getWeatherinfo().getWSE());

		Animation fencheRotate = AnimationUtils.loadAnimation(this, R.anim.fengche_rotate);
		fencheRotate.setInterpolator(new LinearInterpolator());
		windfenche.startAnimation(fencheRotate);

		Animation translatein = AnimationUtils.loadAnimation(this, R.anim.weather_icon_down);
		weatherImg.startAnimation(translatein);

		translatein = AnimationUtils.loadAnimation(this, R.anim.main_activity_text_translate_right);
		cityName.startAnimation(translatein);
		weatherText.startAnimation(translatein);
		humidityText.startAnimation(translatein);
		minmaxTempText.startAnimation(translatein);
		tempText.startAnimation(translatein);
		pm25Text.startAnimation(translatein);
		windText.startAnimation(translatein);

		translatein = AnimationUtils.loadAnimation(this, R.anim.main_activity_translate_lefe);
		tempView.startAnimation(translatein);
		pm25Show.startAnimation(translatein);
		menuButton.startAnimation(translatein);
		humidityView.startAnimation(translatein);

		translatein = AnimationUtils.loadAnimation(this, R.anim.date_time_translate_down);
		date.startAnimation(translatein);
		time.startAnimation(translatein);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_main_Layout);
		linearLayout.setOnTouchListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!cityName.getText().equals(setting.getString("localCity", "广州"))) {
			Intent intent = new Intent(MainActivity.this, UpdatedActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private float downY = 0;
	private float downX = 0;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.performClick();
		int actionType = event.getActionMasked();
		switch (actionType) {
		case MotionEvent.ACTION_DOWN:
			downY = event.getY();
			downX = event.getX();
			float upY;
			float upX;
			return true;
		case MotionEvent.ACTION_UP:
			upY = event.getY();
			upX = event.getX();
			if (downY - upY > 100) {
				Intent intent = new Intent(MainActivity.this, Next7DayWeatherActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
			}
			if (downX - upX > 100) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, PreferenceActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
			return true;
		}
		return false;
	}
}
