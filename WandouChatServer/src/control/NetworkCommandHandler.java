package control;

import model.NetworkPackage;
import exception.BusinessException;
import exception.DBOperatorException;
import exception.SystemException;

public abstract class NetworkCommandHandler
{
	public NetworkCommandHandler()
	{

	}

	public abstract Object handleCommand(NetworkPackage networkPackage)
			throws BusinessException, SystemException, DBOperatorException;
}
