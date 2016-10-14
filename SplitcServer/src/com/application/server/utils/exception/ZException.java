package com.application.server.utils.exception;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;

import com.application.server.utils.CommonLib;
import com.application.server.utils.mailer.EmailModel;
import com.application.server.utils.mailer.EmailUtil;

public class ZException extends Exception implements Serializable {

	private static final long serialVersionUID = 1169426381288170661L;

	public ZException() {
		super();
	}

	public ZException(String msg) {
		super(msg);
		triggerMapper(msg);
	}

	public ZException(String msg, Exception e) {
		super(msg, e);
		triggerMapper(e);
	}

	public ZException(Exception e) {
		super(e);
		e.printStackTrace();
		triggerMapper(e);
	}

	public void triggerMapper(Exception e) {

		if (CommonLib.LIVE_BUILD) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			printWriter.flush();

			String stackTrace = writer.toString();

			// System.out.println("came here");
			EmailModel developerMail = new EmailModel();
			ArrayList<String> developers = new ArrayList<String>();
			developers.add("pratik@zapplon.com");
			developers.add("hello@zapplon.com");
			developerMail.setSenders(developers);
			developerMail.setFrom(CommonLib.ZAPP_ID);
			developerMail.setSubject("v2.4" + ": Server Exception occured !!");
			developerMail
					.setContent("Hey, " + "\n\n" + " An exception occured whose strack trace is:-\n " + stackTrace);
			EmailUtil.getInstance().sendEmail(developerMail);
		}

	}
	
	public void triggerMapper(String msg) {

		if (CommonLib.LIVE_BUILD) {

			// System.out.println("came here");
			EmailModel developerMail = new EmailModel();
			ArrayList<String> developers = new ArrayList<String>();
			developers.add("pratik@zapplon.com");
			developers.add("hello@zapplon.com");
			developerMail.setSenders(developers);
			developerMail.setFrom(CommonLib.ZAPP_ID);
			developerMail.setSubject("v2.4" + ": Server Exception occured !!");
			developerMail
					.setContent("Hey, " + "\n\n" + " An exception occured whose strack trace is:-\n " + msg);
			EmailUtil.getInstance().sendEmail(developerMail);
		}

	}

}

