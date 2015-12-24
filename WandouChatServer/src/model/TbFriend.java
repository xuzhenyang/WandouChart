package model;

import java.sql.Date;

public class TbFriend
{

	private String userId;
	private String friendId;
	private Date addDate;

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getFriendId()
	{
		return friendId;
	}

	public void setFriendId(String friendId)
	{
		this.friendId = friendId;
	}

	public Date getAddDate()
	{
		return addDate;
	}

	public void setAddDate(Date addDate)
	{
		this.addDate = addDate;
	}

}
