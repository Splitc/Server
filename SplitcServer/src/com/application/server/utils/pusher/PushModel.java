package com.application.server.utils.pusher;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONObject;

public class PushModel {
	private ArrayList<String> pushIds;
	private String type;
	private String command;
	private JSONObject object;

	public PushModel() {
	}

	public ArrayList<String> getPushIds() {
		return pushIds;
	}

	public void setPushIds(ArrayList<String> pushIds) {
		this.pushIds = pushIds;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public JSONObject getObject() {
		return object;
	}

	public void setObject(JSONObject object) {
		this.object = object;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
