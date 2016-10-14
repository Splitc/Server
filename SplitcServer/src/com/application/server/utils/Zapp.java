package com.application.server.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Zapp implements ServletContextListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		zappObject = null;
		if (archiveOtpService != null) {
			archiveOtpService.shutdown();
		}
	}

	private static ScheduledExecutorService archiveOtpService;
	private static Zapp zappObject;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		archiveOtpService = Executors.newScheduledThreadPool(1);
		
		if (zappObject == null)
			zappObject = new Zapp();

		archiveOtpService.scheduleAtFixedRate(new Runnable() {
			public void run() {
//				zappObject.phoneVerification();
//				zappObject.oauthVerification();
			}
		}, 0, 3, TimeUnit.MINUTES);

	}

}