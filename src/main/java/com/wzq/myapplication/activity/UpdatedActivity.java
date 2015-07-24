package com.wzq.myapplication.activity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wzq.myapplication.R;
import com.wzq.myapplication.bean.WeatherBeanFomeBaidu;
import com.wzq.myapplication.bean.WeatherBeanFomeCn;
import com.wzq.myapplication.bean.WeatherFromCnAtad;
import com.wzq.myapplication.bean.WeatherFromLib360;
import com.wzq.myapplication.util.City;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class UpdatedActivity extends Activity implements Runnable{
	private SharedPreferences setting;
	public static WeatherBeanFomeBaidu weatherFromBaidu;
	public static WeatherBeanFomeCn weatherFomeCnAdat;
	public static WeatherFromCnAtad weatherFromCnAtad;
	public static WeatherFromLib360 weatherFromLib360;
	
	private TextView updateingTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updated_activity);
		
		updateingTextView = (TextView) findViewById(R.id.updateing);
		
		Animation animation = AnimationUtils.loadAnimation(this,R.anim.update_translate_in);
		updateingTextView.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationStart(Animation animation) {
				setting = getSharedPreferences("setting_info", 0);
				new Thread(UpdatedActivity.this).start();
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				handler.sendEmptyMessage(2);
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
	}
	
	@SuppressLint(value = "HandlerLeak")
	Handler handler = new Handler(){
		Boolean one = false;
		Boolean two = false;
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				one = true;
				if (two) {
					goToMainActivity();
				}
			}
			if (msg.what == 2) {
				two = true;
				if (one) {
					goToMainActivity();
				}
			}
		}
	};
	
	private void goToMainActivity() {
		Animation animation = AnimationUtils.loadAnimation(this,R.anim.update_translate_out);
		animation.setFillAfter(true);
		updateingTextView.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(UpdatedActivity.this, MainActivity.class);
				UpdatedActivity.this.startActivity(intent);
				UpdatedActivity.this.finish();
			}
		});
		
	}


	@Override
	public void run() {
		//从百度api获取天气信息
		String weatherJson = getWeather(setting.getString("localCity", "广州"), "baidu");
		Gson gson = new Gson();
		weatherFromBaidu = gson.fromJson(weatherJson, new TypeToken<WeatherBeanFomeBaidu>(){}.getType());
		
		//从中国气象局里获得天气信息
		
		//从 assets/city/city.json文件中查阅城市代码
		String localcityCode = "";
		try {
			InputStream inputStream = getResources().getAssets().open("city/city.json");
			int lenght = inputStream.available();
			byte[] buffer = new byte[lenght];
			inputStream.read(buffer);
			String cityJson = new String(buffer);
			List<City> citylist = gson.fromJson(cityJson, new TypeToken<List<City>>(){}.getType());
			for(int i = 0; i < citylist.size(); i++){
				if (citylist.get(i).getProvince().equals(setting.getString("province", "广东"))) {
					for(int j = 0; j < citylist.get(i).getCityname().size(); j++){
						if(citylist.get(i).getCityname().get(j).getName()
								.equals(setting.getString("localCity", "广州"))){
							localcityCode = citylist.get(i).getCityname().get(j).getCode();
							break;
						}
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//气象局接口1
		weatherJson = getWeather(localcityCode, "http://www.weather.com.cn/adat/sk/");
		weatherFomeCnAdat = gson.fromJson(weatherJson, new TypeToken<WeatherBeanFomeCn>(){}.getType());
		
		//气象局接口2
		weatherJson = getWeather(localcityCode, "http://m.weather.com.cn/atad/");
		weatherFromCnAtad = gson.fromJson(weatherJson, WeatherFromCnAtad.class);
		
		//中lib360获取天气信息
		weatherJson = getWeather(setting.getString("localCity", "广州"), "http://api.lib360.net/open/weather.json?city=");
		weatherFromLib360 = gson.fromJson(weatherJson, WeatherFromLib360.class);
		
		if (weatherJson != null) {
			handler.sendEmptyMessage(1);
			System.out.println("get resource success!");
		}else {
			handler.sendEmptyMessage(-1);
		}
	}

	@SuppressWarnings("deprecation")
	private String getWeather(String location, String fromwhere) {
		URL url;
		String urlpath = null;
		switch (fromwhere) {
			case "baidu":
				urlpath = "http://api.map.baidu.com/telematics/v3/weather?location="
						+ location + "&output=json&ak=XjuUgHe846mW243Qwzt59akK";        //百度天气接口

				break;
			case "http://www.weather.com.cn/adat/sk/":
				urlpath = fromwhere + location + ".html";                                    //中国气象局接口1

				break;
			case "http://m.weather.com.cn/atad/":
				urlpath = fromwhere + location + ".html";                                    //中国气象局接口2

				break;
			case "http://api.lib360.net/open/weather.json?city=":
				urlpath = fromwhere + URLEncoder.encode(location);
				break;
		}
		
		String weatherJson = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(urlpath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = new BufferedInputStream(conn.getInputStream());
				weatherJson = readStream(is);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			assert conn != null;
			conn.disconnect();
		}
		return weatherJson;
	}

	public static String readStream(InputStream in) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		in.close();
		return new String(data);
	}
}
