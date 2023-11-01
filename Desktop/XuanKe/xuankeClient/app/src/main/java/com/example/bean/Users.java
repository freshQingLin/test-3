package com.example.bean;

public class Users {
	int uid; // 用户id
	String user; // 账号
	String pswd; // 密码
	String sex; // 性别（男 女）
	String name; // 名字
	int type;  //1 学生 2主讲老师 3主管老师

	public Users() {
		super();
	}



	public Users(int uid, String user, String pswd, String sex, String name,int type) {
		super();
		this.type = type;
		this.uid = uid;
		this.user = user;
		this.pswd = pswd;
		this.sex = sex;
		this.name = name;
	}
	
	



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}




	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
