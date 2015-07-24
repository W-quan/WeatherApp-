package com.wzq.myapplication.bean;

public class TUserInfo {
	private Long id;
	private String name;
	private String passwork;
	private String gender;
	private String Birthday;
	private Byte[] photo;
	private String phone;
	private String email;
	private String interests;
	
	private Long optlockversion;
	
	public Long getVersion() {
		return optlockversion;
	}
	
	public void setVersion(Long v) {
	   	optlockversion = v;
	}	
	public String getBirthday() {
		return Birthday;
	}

	public void setBirthday(String birthday) {
		Birthday = birthday;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswork() {
		return passwork;
	}
	public void setPasswork(String passwork) {
		this.passwork = passwork;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String usr_gender) {
		this.gender = usr_gender;
	}
	
	public Byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(Byte[] photo) {
		this.photo = photo;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
}
