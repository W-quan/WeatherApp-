package com.wzq.myapplication.activity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.wzq.myapplication.R;
import com.wzq.myapplication.bean.TUserInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements Runnable {
	private EditText userName;
	private EditText userPassword;
	private EditText repeatPassword;
	private EditText gender;
	private EditText birthday;
	private EditText phone;
	private EditText email;
	private Button sumbit;
	private Button cancel;
	
	private Result result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);

		userName = (EditText) findViewById(R.id.register_user_name_edittext);
		userPassword = (EditText) findViewById(R.id.register_user_password_edittext);
		repeatPassword = (EditText) findViewById(R.id.register_repeat_user_password_edittext);
		gender = (EditText) findViewById(R.id.register_gender_edittext);
		birthday = (EditText) findViewById(R.id.register_birthday_edittext);
		phone = (EditText) findViewById(R.id.register_phone_edittext);
		email = (EditText) findViewById(R.id.register_email_edittext);
		sumbit = (Button) findViewById(R.id.register_sumbit_button);
		cancel = (Button) findViewById(R.id.register_cercel_button);

		sumbit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!userPassword.getText().equals("")/* && userPassword.getText().equals(repeatPassword.getText())*/) {
					System.out.println(userName.getText());
					System.out.println(userPassword.getText());
					System.out.println(repeatPassword.getText());
				}
				new Thread(RegisterActivity.this).start();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
				finish();
			}
			else if(msg.what == 0){
				Toast.makeText(RegisterActivity.this, "注册失败," + result.message, Toast.LENGTH_SHORT).show();
			}
		};
	};

	@Override
	public void run() {
		String path = "http://172.17.149.137:8080/WeatherStatistics/Register?userName=" + userName.getText() + "&password="
				+ userPassword.getText() + "&gender=" + gender.getText() + "&birthday=" + birthday.getText() 
				+ "&phone=" + phone.getText() + "&email=" + email.getText();

		System.out.println(path);
		try {
			URL url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = new BufferedInputStream(httpURLConnection.getInputStream());
				result = new Result();
				result = new Gson().fromJson(UpdatedActivity.readStream(is), Result.class);
				if (result.success == true) {
					handler.sendEmptyMessage(1);
				} else {
					handler.sendEmptyMessage(0);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private class Result {
		public boolean success;
		public String message;
		public TUserInfo data;
	}
}
