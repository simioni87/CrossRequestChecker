package com.protect7.crossrequestchecker.util;

public class Helper {

	public static String getReferer(String httpRequest) {
		String[] headers = httpRequest.split("\n");
		for(String header : headers) {
			if(header.startsWith("Referer:")) {
				String[] refererSplit = header.split("Referer:");
				if(refererSplit.length>1) {
					return refererSplit[1].trim();
				}			
				break;
			}
		}
		return null;
	}

	public static String getDate(String httpRequest) {
		String[] headers = httpRequest.split("\n");
		for(String header : headers) {
			if(header.startsWith("Date:")) {
				String[] refererSplit = header.split("Date:");
				if(refererSplit.length>1) {
					return refererSplit[1].trim();
				}			
				break;
			}
		}
		return null;
	}

	
}
