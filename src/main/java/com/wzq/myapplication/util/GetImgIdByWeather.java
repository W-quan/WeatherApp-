package com.wzq.myapplication.util;


import com.wzq.myapplication.R;

public class GetImgIdByWeather {

	public int  getDrawable(String weather){
		int drawableID = 0;
		switch (weather) {
			case "晴":
				drawableID = R.drawable.qingtain2;
				break;
			case "多云":
				drawableID = R.drawable.duoyun;
				break;
			case "雷阵雨":
				drawableID = R.drawable.leizhenyu;
				break;
			case "阵雨":
				drawableID = R.drawable.xiaoyu;
			case "雷阵雨转阵雨" :
				drawableID = R.drawable.leizhenyu;
				break;
			case "小雨":
				drawableID = R.drawable.xiaoyu;
				break;
			case "中雨":
				drawableID = R.drawable.zhongyu;
				break;
			case "大雨":
				drawableID = R.drawable.dayu;
				break;
			case "暴雨":
				drawableID = R.drawable.baoyu;
				break;
			case "阴":
				drawableID = R.drawable.yingtian;
				break;
			case "阴转多云":
				drawableID = R.drawable.duoyun;
				break;
			case "多云转阴":
				drawableID = R.drawable.duoyun;
				break;
			case "晴转多云":
				drawableID = R.drawable.duoyun;
				break;
			case "雷阵雨转多云":
				drawableID = R.drawable.leizhenyu;
				break;
			case "中雨转阵雨":
				drawableID = R.drawable.zhongyu;
				break;
			case "小雨转晴":
				drawableID = R.drawable.xiaoyu;
				break;
			case "中到大雨转阵雨":
				drawableID = R.drawable.zhongyu;
				break;
			case "中到大雨":
				drawableID = R.drawable.zhongyu;
				break;
			case "阵雨转多云":
				drawableID = R.drawable.xiaoyu;
				break;
			case "多云转晴":
				drawableID = R.drawable.duoyun;
				break;
			default:
				break;
		}
		return drawableID;
	}
}
