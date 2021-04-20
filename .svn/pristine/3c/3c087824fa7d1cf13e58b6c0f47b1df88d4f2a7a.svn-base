package com.protect7.crossrequestchecker.entities;

import burp.IHttpService;

public class CustomHttpService  implements IHttpService {

	final String serviceHost;
	final int servicePort;
	final String serviceProtocol;
	
	public CustomHttpService(String serviceHost, int servicePort, String serviceProtocol) {
		this.serviceHost = serviceHost;
		this.servicePort = servicePort;
		this.serviceProtocol = serviceProtocol;
	}
	
	public String getHost() {
		return serviceHost;
	}

	public int getPort() {
		return servicePort;
	}

	public String getProtocol() {
		return serviceProtocol;
	}
}
