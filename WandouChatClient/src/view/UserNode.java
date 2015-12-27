package view;

import model.TbUser;

public class UserNode
{
	String id;
	String nickname;
	String image;

	public UserNode(TbUser user)
	{
		this.id = user.getUserId();
		this.nickname = user.getUserName();
	}

	public UserNode(String id, String nickname, String image)
	{
		this.id = id;
		this.nickname = nickname;
		this.image = image;
	}

	public UserNode(String id, String nickname)
	{
		this.id = id;
		this.nickname = nickname;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

}
