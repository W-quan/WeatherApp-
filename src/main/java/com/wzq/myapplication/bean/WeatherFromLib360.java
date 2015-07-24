package com.wzq.myapplication.bean;

import java.util.List;

public class WeatherFromLib360 {
	private List<Data> data;
	private List<Data24> data24;
	private DataNow datanow;
	
	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public List<Data24> getData24() {
		return data24;
	}

	public void setData24(List<Data24> data24) {
		this.data24 = data24;
	}

	public DataNow getDatanow() {
		return datanow;
	}

	public void setDatanow(DataNow datanow) {
		this.datanow = datanow;
	}

	public static class Data{
		private String Weather;
		private String TempMax;
		private String TempMin;
		public String getWeather() {
			return Weather;
		}
		public void setWeather(String weather) {
			Weather = weather;
		}
		public String getTempMax() {
			return TempMax;
		}
		public void setTempMax(String TempMax) {
			this.TempMax = TempMax;
		}
		public String getTempMin() {
			return TempMin;
		}
		public void setTempMin(String tempMin) {
			TempMin = tempMin;
		}
		
		
	}
	
	public static class Data24{
		private String BeginHour;
		private String EndHour;
		private String TempMax;
		private String TempMin;
		public String getBeginHour() {
			return BeginHour;
		}
		public void setBeginHour(String beginHour) {
			BeginHour = beginHour;
		}
		public String getEndHour() {
			return EndHour;
		}
		public void setEndHour(String endHour) {
			EndHour = endHour;
		}
		public String getTempMax() {
			return TempMax;
		}
		public void setTempMax(String tempMax) {
			TempMax = tempMax;
		}
		public String getTempMin() {
			return TempMin;
		}
		public void setTempMin(String tempMin) {
			TempMin = tempMin;
		}
		
	}
	
	public static class DataNow{
		private String BeginHour;
		private String EndHour;
		private String TempMax;
		private String TempMin;
		public String getBeginHour() {
			return BeginHour;
		}
		public void setBeginHour(String beginHour) {
			BeginHour = beginHour;
		}
		public String getEndHour() {
			return EndHour;
		}
		public void setEndHour(String endHour) {
			EndHour = endHour;
		}
		public String getTempMax() {
			return TempMax;
		}
		public void setTempMax(String tempMax) {
			TempMax = tempMax;
		}
		public String getTempMin() {
			return TempMin;
		}
		public void setTempMin(String tempMin) {
			TempMin = tempMin;
		}
	}
}
