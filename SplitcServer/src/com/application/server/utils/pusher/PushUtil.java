package com.application.server.utils.pusher;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jivesoftware.smack.XMPPException;

import com.application.server.utils.CommonLib;
import com.application.server.utils.exception.ZException;


public class PushUtil {
	private static volatile PushUtil sInstance;
	private static ExecutorService executorService;

	public static final String NOTIFICATION_TYPE_PROMOTIONAL = "NOTIFICATION_TYPE_PROMOTIONAL";
	
	public static final String NOTIFICATION_TYPE_CASHBACK_POINTS_ADDED = "NOTIFICATION_TYPE_CASHBACK_POINTS_ADDED";
	public static final String NOTIFICATION_TYPE_REFERRAL_POINTS_ADDED = "NOTIFICATION_TYPE_REFERRAL_POINTS_ADDED";
	
	public static final String NOTIFICATION_TYPE_BOOKING_CONFIRMED = "NOTIFICATION_TYPE_BOOKING_CONFIRMED";
	public static final String NOTIFICATION_TYPE_BOOKING_CANCELLED = "NOTIFICATION_TYPE_BOOKING_CANCELLED";

	/**
	 * Empty constructor to prevent multiple objects in memory
	 */
	private PushUtil() {
	}

	/**
	 * Implementation of double check'd locking scheme.
	 */
	public static PushUtil getInstance() {

		if (sInstance == null) {
			synchronized (PushUtil.class) {
				if (sInstance == null) {
					sInstance = new PushUtil();
					executorService = Executors.newFixedThreadPool(10);
				}
			}
		}
		return sInstance;
	}

	public void sendPush(final PushModel pushModel) {

		Runnable runnable = new Runnable() {
			public void run() {

				GCM ccsClient = new GCM();
				String userName = CommonLib.projectId + "@gcm.googleapis.com";
				String password = CommonLib.apiKey;
				try {
					ccsClient.connect(userName, password);
				} catch (XMPPException e) {
					try{throw new ZException("Error",e);}catch(ZException e1){e1.printStackTrace();}
				}
				String messageId = ccsClient.getRandomMessageId();

				Map<String, Object> payload = new HashMap<String, Object>();
				payload.put("command", pushModel.getCommand());
				payload.put("type", pushModel.getType());
				payload.put("Notification", pushModel.getObject());
				payload.put("EmbeddedMessageId", messageId);
				Long timeToLive = 10000L;
				Boolean delayWhileIdle = false;

				for (String user : pushModel.getPushIds()) {
					ccsClient.send(GCM.createJsonMessage(user, messageId, payload, null, timeToLive, delayWhileIdle));
				}
				ccsClient.disconnect();
			}
		};
		executorService.submit(runnable);
	}
}
