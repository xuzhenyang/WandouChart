package model;

import java.io.Serializable;
import java.sql.Date;

public class TbMessage implements Serializable
{
	private String fromUserId;
	private String toUserId;
	private String message;
	private Date sendTime;
	
	public String getFromUserId()
	{
		return fromUserId;
	}
	public void setFromUserId(String fromUserId)
	{
		this.fromUserId = fromUserId;
	}
	public String getToUserId()
	{
		return toUserId;
	}
	public void setToUserId(String toUserId)
	{
		this.toUserId = toUserId;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public Date getSendTime()
	{
		return sendTime;
	}
	public void setSendTime(Date sendTime)
	{
		this.sendTime = sendTime;
	}
	
	
}
