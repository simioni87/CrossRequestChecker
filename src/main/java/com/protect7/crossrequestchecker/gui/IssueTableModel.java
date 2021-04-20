package com.protect7.crossrequestchecker.gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.IRequestInfo;
import burp.IScanIssue;

public class IssueTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private ArrayList<IScanIssue> issueList = new ArrayList<>();
	final Class<?>[] columnClasses = new Class<?>[] {String.class, String.class, String.class};

	public void setIssueList(ArrayList<IScanIssue> issueList) {
		this.issueList = issueList;
		fireTableDataChanged();
	}
	
	public IScanIssue getScanIssueAtRow(int row) {
		//Verify if table Sort is implemented 
		return issueList.get(row);
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return issueList.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object ret = null;
		if (issueList.size() > row) {
			IScanIssue issue = issueList.get(row);
			IHttpRequestResponse messageInfo = issue.getHttpMessages()[0];
			IRequestInfo requestInfo = BurpExtender.callbacks.getHelpers().analyzeRequest(messageInfo);
			switch (column) {
			case 0:
				ret = issue.getUrl().toString();
				break;
			case 1:
				ret = requestInfo.getUrl().toString();
				break;
			default:
				throw new IndexOutOfBoundsException("Column index out of bounds: " + column);
			}
		}
		return ret;
	}

	@Override
	public String getColumnName(int column) {
		String columnName;
		switch (column) {
		case 0:
			columnName = "Origin URL";
			break;
		case 1:
			columnName = "Cross URL";
			break;
		default:
			throw new IndexOutOfBoundsException("Column index out of bounds: " + column);
		}
		return columnName;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClasses[columnIndex];
	}

}
