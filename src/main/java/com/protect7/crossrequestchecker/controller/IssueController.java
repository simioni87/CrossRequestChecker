package com.protect7.crossrequestchecker.controller;

import java.util.ArrayList;

import com.protect7.crossrequestchecker.entities.CrossRequestIssue;
import com.protect7.crossrequestchecker.gui.IssueTableModel;
import com.protect7.crossrequestchecker.util.Helper;
import burp.BurpExtender;
import burp.IRequestInfo;
import burp.IScanIssue;

public class IssueController {
	
	private final IssueTableModel tableModel;
	private final ArrayList<IScanIssue> crossRequestIssues;
	
	public IssueController(IssueTableModel tableModel) {
		this.tableModel = tableModel;
		crossRequestIssues = new ArrayList<IScanIssue>();
		init();
	}

	private void init() {
		IScanIssue[] allScanIssues = BurpExtender.callbacks.getScanIssues(null);
		for(IScanIssue scanIssue : allScanIssues) {
			if(scanIssue.getIssueName().equals(CrossRequestIssue.ISSUE_NAME)) {
				crossRequestIssues.add(scanIssue);
			}
		}
		tableModel.setIssueList(crossRequestIssues);
	}
	
	public void addIssue(IScanIssue issue) {
		if(!alreadyReported(issue)) {
			crossRequestIssues.add(issue);
			BurpExtender.callbacks.addScanIssue(issue);
			tableModel.fireTableDataChanged();
		}
	}
	
	private boolean alreadyReported(IScanIssue issue) {
		IRequestInfo requestInfo = BurpExtender.callbacks.getHelpers().analyzeRequest(issue.getHttpMessages()[0]);
		String validationString = issue.getUrl().toString().split("\\?")[0] + requestInfo.getUrl().toString().split("\\?")[0];
		for(IScanIssue currentIssue : crossRequestIssues) {
			IRequestInfo requestInfoCurrentIssue = BurpExtender.callbacks.getHelpers().analyzeRequest(currentIssue.getHttpMessages()[0]);
			String referer = Helper.getReferer(new String(currentIssue.getHttpMessages()[0].getRequest()));
			String currentValidationString = referer.split("\\?")[0] + requestInfoCurrentIssue.getUrl().toString().split("\\?")[0];
			if(currentValidationString.equals(validationString)) {
				return true;
			}
		}
		return false;
	}
}
