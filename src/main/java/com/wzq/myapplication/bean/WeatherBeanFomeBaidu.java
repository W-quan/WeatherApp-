package com.wzq.myapplication.bean;

import java.util.List;

public class WeatherBeanFomeBaidu {
	
	
	private int error;
	private String status;
	private String date;
	private List<Results> results;

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}

	
	public static class Results{
		private String currentCity;
		private String pm25;
		private List<Index> index;
		private List<WeatherData> weather_data;
		
		public String getCurrentCity() {
			return currentCity;
		}

		public void setCurrentCity(String currentCity) {
			this.currentCity = currentCity;
		}

		public String getPm25() {
			return pm25;
		}

		public void setPm25(String pm25) {
			this.pm25 = pm25;
		}

		public List<Index> getIndexs() {
			return index;
		}

		public void setIndexs(List<Index> index) {
			this.index = index;
		}

		public List<WeatherData> getWeatherDatas() {
			return weather_data;
		}

		public void setWeatherDatas(List<WeatherData> weatherDatas) {
			this.weather_data = weatherDatas;
		}

		public static class Index {
			private String title;
			private String zs;
			private String tipt;
			private String des;
			
			public String getTitle() {
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
			}
			public String getZs() {
				return zs;
			}
			public void setZs(String zs) {
				this.zs = zs;
			}
			public String getTipt() {
				return tipt;
			}
			public void setTipt(String tipt) {
				this.tipt = tipt;
			}
			public String getDes() {
				return des;
			}
			public void setDes(String des) {
				this.des = des;
			}
			
		}
		
		public static class WeatherData{
			private String date;
			private String dayPictureUrl;
			private String nightPictureUrl;
			private String weather;
			private String wind;
			private String temperature;
			
			public String getDate() {
				return date;
			}
			public void setDate(String date) {
				this.date = date;
			}
			public String getDayPictureUrl() {
				return dayPictureUrl;
			}
			public void setDayPictureUrl(String dayPictureUrl) {
				this.dayPictureUrl = dayPictureUrl;
			}
			public String getNightPictureUrl() {
				return nightPictureUrl;
			}
			public void setNightPictureUrl(String nightPictureUrl) {
				this.nightPictureUrl = nightPictureUrl;
			}
			public String getWeather() {
				return weather;
			}
			public void setWeather(String weather) {
				this.weather = weather;
			}
			public String getWind() {
				return wind;
			}
			public void setWind(String wind) {
				this.wind = wind;
			}
			public String getTemperature() {
				return temperature;
			}
			public void setTemperature(String temperature) {
				this.temperature = temperature;
			}
		}
	}
}
