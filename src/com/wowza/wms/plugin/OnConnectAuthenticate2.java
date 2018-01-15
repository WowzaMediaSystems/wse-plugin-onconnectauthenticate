/*
 * This code and all components (c) Copyright 2006 - 2018, Wowza Media Systems, LLC. All rights reserved.
 * This code is licensed pursuant to the Wowza Public License version 1.0, available at www.wowza.com/legal.
 */
package com.wowza.wms.plugin;

import java.io.File;

import com.wowza.wms.amf.AMFDataList;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.application.WMSProperties;
import com.wowza.wms.authentication.IAuthenticateUsernamePasswordProvider;
import com.wowza.wms.authentication.file.AuthenticationPasswordFiles;
import com.wowza.wms.client.IClient;
import com.wowza.wms.module.ModuleBase;
import com.wowza.wms.request.RequestFunction;
import com.wowza.wms.util.AuthenticationUtils;

public class OnConnectAuthenticate2 extends ModuleBase
{
	public static final String AUTHPASSWORDFILEPATH = "${com.wowza.wms.context.VHostConfigHome}/conf/connect.password";
	private File passwordFile = null;
	private String usernamePasswordProviderClass = null;

	public void onAppStart(IApplicationInstance appInstance)
	{
		WMSProperties props = appInstance.getProperties();

		String passwordFileStr = props.getPropertyStr("rtmpAuthenticateFile", AUTHPASSWORDFILEPATH);
		usernamePasswordProviderClass = props.getPropertyStr("usernamePasswordProviderClass", usernamePasswordProviderClass);
		if (passwordFileStr != null)
		{
			passwordFileStr = appInstance.decodeStorageDir(passwordFileStr);
			passwordFile = new File(passwordFileStr);
		}

		if (passwordFile != null)
			getLogger().info("ModuleOnConnectAuthenticate: Authorization password file: " + passwordFile.getAbsolutePath());
		if (usernamePasswordProviderClass != null)
			getLogger().info("ModuleOnConnectAuthenticate: Authorization password class: " + usernamePasswordProviderClass);
	}

	public void onConnect(IClient client, RequestFunction function, AMFDataList params)
	{
		boolean isAuthenticated = false;

		String username = null;
		String password = null;

		try
		{
			while (true)
			{
				getLogger().info("size: " + params.size());

				String[] auth = client.getQueryStr().split("&");

				username = auth[0];
				password = auth[1];

				if (username == null || password == null)
					break;

				IAuthenticateUsernamePasswordProvider filePtr = null;
				if (usernamePasswordProviderClass != null)
					filePtr = AuthenticationUtils.createUsernamePasswordProvider(usernamePasswordProviderClass);
				else if (passwordFile != null)
					filePtr = AuthenticationPasswordFiles.getInstance().getPasswordFile(passwordFile);

				if (filePtr == null)
					break;

				filePtr.setClient(client);

				String userPassword = filePtr.getPassword(username);
				if (userPassword == null)
					break;

				if (!userPassword.equals(password))
					break;

				isAuthenticated = true;
				break;
			}
		}
		catch (Exception e)
		{
			getLogger().error("ModuleOnConnectAuthenticate.onConnect: " + e.toString());
			isAuthenticated = false;
		}

		if (!isAuthenticated)
			client.rejectConnection("Authentication Failed[" + client.getClientId() + "]: " + username);
		else
			client.acceptConnection();
	}
}
