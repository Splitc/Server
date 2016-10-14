package com.application.server.utils.mailer;

import java.util.ArrayList;

public class EmailModel {

	private String from;
	private String subject;
	private String content;
	private ArrayList<String> senders;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<String> getSenders() {
		return senders;
	}

	public void setSenders(ArrayList<String> senders) {
		this.senders = senders;
	}
	
}