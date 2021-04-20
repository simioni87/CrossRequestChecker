package com.protect7.crossrequestchecker.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.IHttpService;
import burp.IMessageEditor;
import burp.IMessageEditorController;
import burp.IScanIssue;

public class CenterPanel extends JPanel {

	private static final long serialVersionUID = 8472627619821851125L;
	private final JTabbedPane tabbedPane = new JTabbedPane();

	public CenterPanel(final IssueTableModel tableModel) {
		setLayout(new BorderLayout());

		final JTable table = new JTable();
		table.setModel(tableModel);
		add(new JScrollPane(table));
		table.setAutoCreateRowSorter(true);

		tabbedPane.add("Request", new JPanel());
		tabbedPane.add("Response", new JPanel());

		add(tabbedPane);
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				if(table.getSelectedRow() != -1) {
					changeRequestResponseView(table, tableModel);
				}
			}
		});

		JSplitPane  splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(table), tabbedPane);
		splitPane.setResizeWeight(0.5);
		splitPane.setDividerSize(5);
		add(splitPane, BorderLayout.CENTER);

	}

	private void changeRequestResponseView(JTable table, IssueTableModel tableModel) {
		IScanIssue issue = tableModel.getScanIssueAtRow(table.convertRowIndexToModel(table.getSelectedRow()));
		IHttpRequestResponse messageInfo = issue.getHttpMessages()[0];
		if(messageInfo != null) {
			IMessageEditorController controller = new IMessageEditorController() {
				
				@Override
				public byte[] getResponse() {
					return messageInfo.getResponse();
				}
				
				@Override
				public byte[] getRequest() {
					return messageInfo.getRequest();
				}
				
				@Override
				public IHttpService getHttpService() {
					return messageInfo.getHttpService();
				}
			};
			IMessageEditor messageEditorRequest = BurpExtender.callbacks.createMessageEditor(controller, false);
			messageEditorRequest.setMessage(messageInfo.getRequest(), true);
			tabbedPane.setComponentAt(0, messageEditorRequest.getComponent());
			
			IMessageEditor messageEditorResponse = BurpExtender.callbacks.createMessageEditor(controller, false);
			messageEditorResponse.setMessage(messageInfo.getResponse(), false);
			tabbedPane.setComponentAt(1, messageEditorResponse.getComponent());
		}
	}
}