package com.protect7.crossrequestchecker.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.protect7.crossrequestchecker.util.Constants;
import com.protect7.crossrequestchecker.util.CurrentConfig;

public class ConfigurationPanel extends JPanel {

	private static final long serialVersionUID = -4278008236240529083L;
	CurrentConfig config = CurrentConfig.getCurrentConfig();
	JCheckBox onlyInScopeCheckBox;
	JCheckBox noCorsRequestsCheckBox;
	JCheckBox excludeSameSecondLevelDomains;
	JLabel labelSaveSettings;

	public ConfigurationPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		onlyInScopeCheckBox = new JCheckBox("Only In Scope Requests");
		onlyInScopeCheckBox.setToolTipText("Only In Requests Items are checked");
		noCorsRequestsCheckBox = new JCheckBox("No CORS Requests");
		noCorsRequestsCheckBox.setToolTipText("Requests with \"x-requested-with: XMLHttpRequest\" Header are not Checked");
		excludeSameSecondLevelDomains = new JCheckBox("Exclude if same second level domain");
		
		
		labelSaveSettings = new JLabel("", JLabel.RIGHT);
		JButton saveButton = new JButton("Save Settings");
		saveButton.addActionListener(e -> saveSettings());
		
		panel.add(onlyInScopeCheckBox);
		panel.add(noCorsRequestsCheckBox);
		panel.add(excludeSameSecondLevelDomains);
		panel.add(saveButton);
		panel.add(labelSaveSettings);
		add(panel);
		loadSettings();
	}

	private void saveSettings() {
		config.setConfig(Constants.CHECK_ONLY_INSCOPE, Boolean.toString(onlyInScopeCheckBox.isSelected()));
		config.setConfig(Constants.DO_NOT_CHECK_CORS_REQUESTS, Boolean.toString(noCorsRequestsCheckBox.isSelected()));
		config.setConfig(Constants.DO_NOT_CHECK_SAME_SECOND_LEVEL_DOMAIN, Boolean.toString(excludeSameSecondLevelDomains.isSelected()));
		config.persistConfig();
		labelSaveSettings.setText("<html><p style='color:green;font-weight:bold;'>Successfully Saved</p></html>");
		Timer timer = new Timer(3000, e -> {
			labelSaveSettings.setText("");
		});
		timer.setRepeats(false);
		timer.start();
	}

	private void loadSettings() {
		onlyInScopeCheckBox.setSelected(Boolean.parseBoolean(config.getConfig(Constants.CHECK_ONLY_INSCOPE)));
		noCorsRequestsCheckBox.setSelected(Boolean.parseBoolean(config.getConfig(Constants.DO_NOT_CHECK_CORS_REQUESTS)));
		excludeSameSecondLevelDomains.setSelected(Boolean.parseBoolean(config.getConfig(Constants.DO_NOT_CHECK_SAME_SECOND_LEVEL_DOMAIN)));
	}
}
