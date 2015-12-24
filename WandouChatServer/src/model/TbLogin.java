package model;

import java.util.Date;

public class TbLogin
{

	private String ip;
	private String port;
	private String userId;
	private Date recentLoginDate;
	private Date recentLogoutDate;
	private String state;

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

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
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

}
