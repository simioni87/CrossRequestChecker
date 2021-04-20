package burp;

import java.awt.Component;
import com.protect7.crossrequestchecker.controller.HttpListener;
import com.protect7.crossrequestchecker.gui.IssueTableModel;
import com.protect7.crossrequestchecker.gui.MainPanel;
import com.protect7.crossrequestchecker.util.CurrentConfig;

public class BurpExtender implements IBurpExtender, ITab { 

	private final CurrentConfig config = CurrentConfig.getCurrentConfig();
	private final IssueTableModel tableModel = new IssueTableModel();
	private MainPanel panel;
	public static IBurpExtenderCallbacks callbacks;



	public String getTabCaption() {
		return "Cross Request Checker";
	}

	public Component getUiComponent() {
		return panel;
	}

	public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
		BurpExtender.callbacks = callbacks;
		System.out.println("Cross Request Checker started");
		callbacks.setExtensionName("Cross Request Checker");
		config.init(callbacks);
		panel = new MainPanel(tableModel);
		callbacks.addSuiteTab(this);
		callbacks.registerHttpListener(new HttpListener(tableModel));
		callbacks.printOutput("Cross Request Checker successfully started");
		callbacks.printOutput("Version 0.1.4");
        callbacks.printOutput("Created by Simon Reinhart");
        callbacks.printOutput("Protect7 GmbH");
	}
}