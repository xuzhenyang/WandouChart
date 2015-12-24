package model;

import java.io.Serializable;

public class TbUser implements Serializable
{
	private String userId;
	private String userPwd;
	private String userName;

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String id)
	{
		this.userId = id;
	}

	public String getUserPwd()
	{
		return userPwd;
	}

	public void setUserPwd(String pwd)
	{
		this.userPwd = pwd;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String toString()
	{
		return "UserId : " + userId + " UserPwd : " + userPwd + " UserName : "
				+ userName;
	}

}
