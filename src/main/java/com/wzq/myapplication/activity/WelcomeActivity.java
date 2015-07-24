package com.wzq.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.wzq.myapplication.R;

public class WelcomeActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		
		AlphaAnimation alpha = new AlphaAnimation((float) 0.1, 1);
		alpha.setDuration(3000);
		ImageView iv = (ImageView)findViewById(R.id.welcome_Img);
		iv.startAnimation(alpha);
		alpha.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(WelcomeActivity.this, UpdatedActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

}
