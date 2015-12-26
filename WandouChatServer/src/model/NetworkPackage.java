package model;
import java.io.Serializable;

public class NetworkPackage implements Serializable
{
	private String commandName;
	private Object param;

	public String getCommandName()
	{
		return commandName;
	}

	public void setCommandName(String commandName)
	{
		this.commandName = commandName;
	}

	public Object getParam()
	{
		return param;
	}

	public void setParam(Object param)
	{
		this.param = param;
	}

}
