package com.mycompany.garden.ajax.model.vo;

public class User {
	private String userId;
	private String userPwd;
	private String userName;
	private int age;
	private String email;
	
	// 딱히 안 만들어도 되지만 나는 그냥 만들었다.
	public User() {}

	public User(String userId, String userPwd, String userName, int age, String email) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.age = age;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
