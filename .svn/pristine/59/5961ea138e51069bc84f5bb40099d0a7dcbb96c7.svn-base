package com.protect7.crossrequestchecker.util;

import java.util.HashMap;

import burp.IBurpExtenderCallbacks;

public class CurrentConfig {

	private static CurrentConfig mInstance = new CurrentConfig();
	private IBurpExtenderCallbacks callbacks;
	
	
	HashMap<Constants, String> configMap = new HashMap<>();
	
	public void init(IBurpExtenderCallbacks callbacks) {
		this.callbacks = callbacks;
		for(Constants constant : Constants.values()) {
			configMap.put(constant, null);
		}
		loadPersistentConfig();
	}

	public static synchronized CurrentConfig getCurrentConfig(){
		  return mInstance;
	}
	
	public synchronized void setConfig(Constants configName, String configValue) {
		configMap.put(configName, configValue);
	}
	
	public synchronized String getConfig(Constants configName) {
		return configMap.get(configName);
	}
	
	public void persistConfig() {
		for(Constants config : configMap.keySet()) {
			if(getConfig(config) != null) {
				callbacks.saveExtensionSetting(config.toString(), getConfig(config));
			}
		}
	}
	
	public synchronized void loadPersistentConfig() {
		HashMap<Constants,String> tempMap = new HashMap<>();
		for(Constants config : configMap.keySet()) {		
			tempMap.put(config, callbacks.loadExtensionSetting(config.toString()));
		}
		configMap.clear();
		configMap = tempMap;
	}
	
}
