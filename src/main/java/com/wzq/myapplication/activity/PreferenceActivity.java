package com.wzq.myapplication.activity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.wzq.myapplication.R;
import com.wzq.myapplication.bean.TUserInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PreferenceActivity extends Activity {
	private SharedPreferences settings;
	private EditText localProvinceEditText;
	private EditText localCityEditText;
	private Result result;
	private EditText userName;
	private EditText userPassword;
	private TextView password;
	
	private Button loginButton;
	private Button registerButton;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preference_activity);

		settings = getSharedPreferences("setting_info", 0);
		
		localProvinceEditText = (EditText) findViewById(R.id.province_edit_text);
		localProvinceEditText.setText(settings.getString("province", "广东"));
		
		localCityEditText = (EditText) findViewById(R.id.local_city_edit_text);
		localCityEditText.setText(settings.getString("localCity", "广州"));
		
		userName = (EditText)findViewById(R.id.user_name_edittext);
		userPassword = (EditText)findViewById(R.id.user_password_edittext);
		password = (TextView)findViewById(R.id.user_password_text);
		
		//注册
		registerButton = (Button) findViewById(R.id.register_button);
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PreferenceActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		//登陆
		loginButton = (Button)findViewById(R.id.login_button);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String path = "http://172.17.149.137:8080/WeatherStatistics/Login?" + "userName=" + userName.getText()
										+ "&password=" + userPassword.getText();
						System.out.println(path);
						try {
							URL url = new URL(path);
							HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
							if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
								InputStream is = new BufferedInputStream(httpURLConnection.getInputStream());
								result = new Result();
								result = new Gson().fromJson(UpdatedActivity.readStream(is), Result.class);
								if (result.success == true) {
									handler.sendEmptyMessage(1);
								}else {
									handler.sendEmptyMessage(0);
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				userName.setEnabled(false);
				password.setVisibility(View.INVISIBLE);
				userPassword.setVisibility(View.INVISIBLE);
				registerButton.setVisibility(View.INVISIBLE);
				loginButton.setText("退出登陆");
			}
			if (msg.what == 0) {
				Toast.makeText(PreferenceActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	@Override
	protected void onPause() {
		super.onPause();
		settings.edit()
				.putString("province", localProvinceEditText.getText().toString())
				.putString("localCity", localCityEditText.getText().toString())
				.commit();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.in_from_lefe, R.anim.out_to_right);
	}

	@SuppressWarnings("unused")
	private class Result {
		public boolean success;
		public String message;
		public TUserInfo data;
	}
}
