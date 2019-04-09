package com.test.woloszkiewicz.entity;

public class ReCaptchaResponse {
	
	private boolean success;
	private String callenge_ts;
	private String hostname;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCallenge_ts() {
		return callenge_ts;
	}
	public void setCallenge_ts(String callenge_ts) {
		this.callenge_ts = callenge_ts;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	
}
