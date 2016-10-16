package com.application.server.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.application.server.model.Ride;
import com.application.server.utils.DBUtil;
import com.application.server.utils.exception.ZException;

public class RideDao extends BaseDao {

	public static final String LOGGER = "RideDao.class";

	public RideDao() {
		super(RideDao.LOGGER);
	}

	// add your ride
	public boolean addRide(Ride ride) {
		Session session = null;
		info("addSession enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();
			session.save(ride);

			transaction.commit();
			session.close();

		} catch (HibernateException e) {
			try {
				throw new ZException("Error", e);
			} catch (ZException e1) {
				e1.printStackTrace();
			}
			error("Hibernate exception: " + e.getMessage());
			return false;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		info("addRide exit");
		return true;
	}

	// view your rides
	public ArrayList<Ride> getMyRides(int userId) {
		ArrayList<Ride> rides = new ArrayList<Ride>();
		Session session = null;
		info("getMyRides enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM Ride WHERE UserId = :userId";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Ride.class);
			query.setParameter("userId", userId);
			java.util.List results = (java.util.List) query.list();

			for (Iterator iterator = ((java.util.List) results).iterator(); iterator.hasNext();) {
				Ride currentRide = (Ride) (iterator.next());
				rides.add(currentRide);
			}

			transaction.commit();
			session.close();

		} catch (HibernateException e) {
			try {
				throw new ZException("Error", e);
			} catch (ZException e1) {
				e1.printStackTrace();
			}
			error("Hibernate exception: " + e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		info("getMyRides exit");
		return rides;
	}

	// view your rides with count
	public int getMyRidesCount(int userId) {
		int size = 0;
		Session session = null;
		try {
			session = DBUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			String sql = "SELECT COUNT(*) FROM Ride WHERE UserId = :userid";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("userid", userId);

			java.util.List results = (java.util.List) query.list();
			Object resultValue = results.get(0);
			if (resultValue instanceof BigInteger)
				size = ((BigInteger) results.get(0)).intValue();
			else
				size = 0;

			transaction.commit();
			session.close();
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			System.out.println("error");
			return 0;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return size;
	}

	// view active rides nearby
	public ArrayList<Ride> getFeedRides(int userId, double startLatitude, double startLongitude, double endLatitude,
			double endLongitude) {
		ArrayList<Ride> rides = new ArrayList<Ride>();
		
		// iterating over all the current rides which fall under the specified radius
		
		// check if any ride is traveling to the similar distance
		
		// return that ride
		
		info("getMyRides exit");
		return rides;
	}

	// view active rides nearby count
	public int getFeedRidesCount(int userId, double startLatitude, double startLongitude, double endLatitude,
			double endLongitude) {
		int size = 0;
		return size;
	}

	// delete your ride which are not finished and which doesn't

}