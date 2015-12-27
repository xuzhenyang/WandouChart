package model;

import java.io.Serializable;
import java.util.Date;

public class TbUser implements Serializable
{
	private String userId;
	private String userPwd;
	private String userName;
	private String ip;
	private String port;
	private Date recentLoginDate;
	private Date recentLogoutDate;
	private String state;

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

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

	public Date getRecentLoginDate()
	{
		return recentLoginDate;
	}

	public void setRecentLoginDate(Date recentLoginDate)
	{
		this.recentLoginDate = recentLoginDate;
	}

	public Date getRecentLogoutDate()
	{
		return recentLogoutDate;
	}

	public void setRecentLogoutDate(Date recentLogoutDate)
	{
		this.recentLogoutDate = recentLogoutDate;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String toString()
	{
		return "UserId : " + userId + " UserPwd : " + userPwd + " UserName : "
				+ userName;
	}

}
