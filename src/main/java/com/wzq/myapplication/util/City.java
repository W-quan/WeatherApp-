package com.wzq.myapplication.util;

import java.util.List;

public class City {
	private String 省;
	private List<CityName> 市;
	
	public String getProvince() {
		return 省;
	}
	public void setProvince(String province) {
		this.省 = province;
	}
	public List<CityName> getCityname() {
		return 市;
	}
	public void setCityname(List<CityName> cityname) {
		this.市 = cityname;
	}
	 
	public static class CityName{
		private String 市名;
		private String 编码;
		
		public String getName() {
			return 市名;
		}
		public void setName(String name) {
			this.市名 = name;
		}
		public String getCode() {
			return 编码;
		}
		public void setCode(String code) {
			this.编码 = code;
		}
		
	}
}


