package com.application.server.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.application.server.model.pojo.Location;
import com.application.server.utils.DBUtil;
import com.application.server.utils.exception.ZException;

public class UserSessionDao extends BaseDao {

	public static final String LOGGER = "UserSessionDao.class";

	public UserSessionDao() {
		super(UserSessionDao.LOGGER);
	}

	/**
	 * Utility method to generate an accessToken.
	 */
	public Object[] generateAccessToken(String userName, int userId, String deviceId, String regId, Location location) {
		Object[] tokens = new Object[2];
		String accessToken = "";
		Transaction transaction = null;
		Session session = null;
		boolean retType = false;

		try {

			session = DBUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			String sql = "SELECT * FROM Session WHERE UserId = :user_id and DeviceId = :device_id";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(com.application.server.model.Session.class);
			query.setParameter("user_id", userId);
			query.setParameter("device_id", deviceId);
			java.util.List results = (java.util.List) query.list();

			if (results != null && results.size() > 0) {
				for (Iterator iterator = ((java.util.List) results).iterator(); iterator.hasNext();) {
					retType = true;
					com.application.server.model.Session sessionToken = (com.application.server.model.Session) iterator
							.next();
					accessToken = sessionToken.getAccessToken();
					boolean shouldUpdate = false;
					if (location != null) {
						sessionToken.setLocation(location);
						shouldUpdate = true;
					}
					if (regId != null && !regId.isEmpty()) {
						sessionToken.setPushId(regId);
						shouldUpdate = true;
					}

					if (shouldUpdate)
						session.update(sessionToken);

					break;
				}
			} else {
				String keySource = userName + String.valueOf(System.currentTimeMillis() * 1000)
						+ String.valueOf((int) (Math.random() * 1000 * 1000));
				byte[] tokenByte = Base64.encodeBase64(keySource.getBytes());
				accessToken = new String(tokenByte);
			}

			transaction.commit();
			session.close();

		} catch (HibernateException e) {
			transaction.rollback();
			try{
				throw new ZException("Error",e);
			}
			catch(ZException e1){
				e1.printStackTrace();
			}
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		tokens[0] = accessToken;
		tokens[1] = retType;
		return tokens;
	}

	public boolean addSession(int userId, String accessToken, String registrationId, Location location,
			String deviceId) {
		Session session = null;
		info("addSession enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();
			com.application.server.model.Session loginSession = new com.application.server.model.Session();
			loginSession.setUserId(userId);

			loginSession.setAccessToken(accessToken);
			loginSession.setLocation(location);
			loginSession.setPushId(registrationId);
			loginSession.setCreated(System.currentTimeMillis());
			loginSession.setModified(0);
			loginSession.setDeviceId(deviceId);

			session.save(loginSession);

			transaction.commit();
			session.close();

		} catch (HibernateException e) {
			try{
				throw new ZException("Error",e);
			}
			catch(ZException e1){
				e1.printStackTrace();
			}
			error("Hibernate exception: " + e.getMessage());
			return false;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		info("addSession exit");
		return true;
	}

	public boolean updateRegistratonId(String pushId, String accessToken) {

		Session session = null;

		try {

			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM Session WHERE AccessToken = :access_token";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(com.application.server.model.Session.class);
			query.setParameter("access_token", accessToken);
			java.util.List results = (java.util.List) query.list();
			com.application.server.model.Session currentSession = (com.application.server.model.Session) results
					.get(0);
			if (currentSession != null) {
				currentSession.setPushId(pushId);
				currentSession.setModified(System.currentTimeMillis());
				session.update(currentSession);
			}

			transaction.commit();
			session.close();
		} catch (HibernateException e) {
			try{
				throw new ZException("Error",e);
			}
			catch(ZException e1){
				e1.printStackTrace();
			}
			return false;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return true;

	}

	public boolean updateLocation(double lat, double lon, String accessToken) {

		Session session = null;

		try {

			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM Session WHERE AccessToken = :access_token";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(com.application.server.model.Session.class);
			query.setParameter("access_token", accessToken);
			java.util.List results = (java.util.List) query.list();
			com.application.server.model.Session currentSession = (com.application.server.model.Session) results
					.get(0);
			if (currentSession != null) {
				Location location = new Location(lat, lon);
				currentSession.setLocation(location);
				currentSession.setModified(System.currentTimeMillis());
				session.update(currentSession);
			}

			transaction.commit();
			session.close();
		} catch (HibernateException e) {
			try{
				throw new ZException("Error",e);
			}
			catch(ZException e1){
				e1.printStackTrace();
			}
			return false;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return true;

	}

	/**
	 * Delete an accessToken for a particular user
	 */
	public boolean nullifyAccessToken(int userId, String accessToken) {

		Session session = null;
		info("nullifyAccessToken enter");

		try {

			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "DELETE FROM Session WHERE AccessToken = :access_token && UserId = :user_id";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(com.application.server.model.Session.class);
			query.setParameter("access_token", accessToken);
			query.setParameter("user_id", userId);
			int result = query.executeUpdate();
			transaction.commit();
			session.close();
			return true;

		} catch (HibernateException e) {
			try{
				throw new ZException("Error",e);
			}
			catch(ZException e1){
				e1.printStackTrace();
			}
			error("Hibernate exception: " + e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		info("nullifyAccessToken exit");
		return false;
	}

	public ArrayList<String> getUserPushIds(int userId) {
		ArrayList<String> pushIds = new ArrayList<String>();
		Session session = null;
		info("getStore enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT PushId FROM Session WHERE UserId = :userId";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("userId", userId);
			java.util.List results = (java.util.List) query.list();

			for (Iterator iterator = ((java.util.List) results).iterator(); iterator.hasNext();) {
				String pushId = String.valueOf(iterator.next());
				pushIds.add(pushId);
			}

			transaction.commit();
			session.close();

		} catch (HibernateException e) {
			try{
				throw new ZException("Error",e);
			}
			catch(ZException e1){
				e1.printStackTrace();
			}
			error("Hibernate exception: " + e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		info("getStore exit");
		return pushIds;
	}

	public ArrayList<String> getAllPushIds(int start, int count) {
		ArrayList<String> pushIds = new ArrayList<String>();
		Session session = null;
		info("getStore enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT PushId FROM Session LIMIT :start, :count";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("start", start);
			query.setParameter("count", count);
			java.util.List results = (java.util.List) query.list();

			for (Iterator iterator = ((java.util.List) results).iterator(); iterator.hasNext();) {
				String pushId = String.valueOf(iterator.next());
				pushIds.add(pushId);
			}

			transaction.commit();
			session.close();

		} catch (HibernateException e) {
			try{
				throw new ZException("Error",e);
			}
			catch(ZException e1){
				e1.printStackTrace();
			}
			error("Hibernate exception: " + e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		info("getStore exit");
		return pushIds;
	}
	
	public int getPushIdsCount() {
		int size = 0;
		Session session = null;
		info("getStore enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT count(*) FROM Session";
			SQLQuery query = session.createSQLQuery(sql);
			java.util.List results = (java.util.List) query.list();

			if (results != null && results.size() > 0) {
				Object resultValue = results.get(0);
				if (resultValue instanceof BigInteger)
					size = ((BigInteger) results.get(0)).intValue();
				else
					size = 0;
			}

			transaction.commit();
			session.close();

		} catch (HibernateException e) {
			try{
				throw new ZException("Error",e);
			}
			catch(ZException e1){
				e1.printStackTrace();
			}
			error("Hibernate exception: " + e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		info("getStore exit");
		return size;
	}

}
