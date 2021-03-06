package com.protect7.crossrequestchecker.controller;

import java.net.MalformedURLException;
import java.net.URL;
import com.protect7.crossrequestchecker.entities.CrossRequestIssue;
import com.protect7.crossrequestchecker.entities.CustomHttpService;
import com.protect7.crossrequestchecker.gui.IssueTableModel;
import com.protect7.crossrequestchecker.util.Constants;
import com.protect7.crossrequestchecker.util.CurrentConfig;
import com.protect7.crossrequestchecker.util.Helper;
import burp.BurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.IHttpListener;
import burp.IHttpRequestResponse;
import burp.IRequestInfo;
import burp.IScanIssue;

public class HttpListener implements IHttpListener {

	private final IssueController issueController;
	private final CurrentConfig config;

	public HttpListener(IssueTableModel tableModel) {
		this.config = CurrentConfig.getCurrentConfig();
		this.issueController = new IssueController(tableModel);
	}


	public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {
		if(toolFlag == IBurpExtenderCallbacks.TOOL_PROXY && !messageIsRequest) {
			URL refererUrl = null;
			String referer = Helper.getReferer(new String(messageInfo.getRequest()));
			if (referer != null) {
				try {
					refererUrl = new URL(referer);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			if(refererUrl != null) {
				boolean checkOnlyInScope = Boolean.parseBoolean(config.getConfig(Constants.CHECK_ONLY_INSCOPE));
				boolean isInScope = BurpExtender.callbacks.isInScope(refererUrl);
				if(!checkOnlyInScope || (checkOnlyInScope && isInScope)) {
					boolean doNotCheckCorsRequests = Boolean.parseBoolean(config.getConfig(Constants.DO_NOT_CHECK_CORS_REQUESTS));
					boolean isCorsRequest = new String(messageInfo.getRequest()).toLowerCase().contains("x-requested-with: xmlhttprequest");
					if(!doNotCheckCorsRequests || (doNotCheckCorsRequests && !isCorsRequest)) {
						IRequestInfo requestInfo = BurpExtender.callbacks.getHelpers().analyzeRequest(messageInfo);
						boolean excludeSameSecondLevelDomain = Boolean.parseBoolean(config.getConfig(Constants.DO_NOT_CHECK_SAME_SECOND_LEVEL_DOMAIN));
						boolean isSameSecondLevelDomain = isSameSecondLevelDomain(refererUrl.getHost(), requestInfo.getUrl().getHost());
						if(!excludeSameSecondLevelDomain || !isSameSecondLevelDomain) {
							if (!refererUrl.getHost().equals(requestInfo.getUrl().getHost())) {
								IHttpRequestResponse[] messageArray = {messageInfo};
								// Scan issue must be generated for refer URL
								int port = refererUrl.getPort();
								if(port == -1) {
									if(refererUrl.getProtocol().equals("http")) {
										port = 80;
									}
									else {
										port = 443;
									}
								}
								CustomHttpService httpService = new CustomHttpService(refererUrl.getHost(), port, refererUrl.getProtocol());
								IScanIssue issue = new CrossRequestIssue(refererUrl.getHost(), refererUrl, messageArray, 
										httpService, port, refererUrl.getProtocol());
								issueController.addIssue(issue);
							}
						}
					}
				}
			}
		}
	}
	
	private boolean isSameSecondLevelDomain(String host1, String host2) {
		String[] host1Split = host1.split("\\.");
		String[] host2Split = host2.split("\\.");
		if(host1Split.length > 1 && host2Split.length > 1) {
			String topLevelHost1 = host1Split[host1Split.length-1];
			String secondLevelHost1 = host1Split[host1Split.length-2];
			String topLevelHost2 = host2Split[host2Split.length-1];
			String secondLevelHost2 = host2Split[host2Split.length-2];
			if(topLevelHost1.equals(topLevelHost2) && secondLevelHost1.equals(secondLevelHost2)) {
				return true;
			}
		}
		return false;
	}
}
