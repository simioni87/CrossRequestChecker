package com.protect7.crossrequestchecker.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -8438576029794021570L;

	public MainPanel(IssueTableModel tableModel) {
		setLayout(new BorderLayout(10, 10));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		ConfigurationPanel configurationPanel = new ConfigurationPanel();
		CenterPanel tablePanel = new CenterPanel(tableModel);
		
		add(configurationPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		
	}
}
