package com.application.server.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.application.server.model.RideRequest;
import com.application.server.utils.CommonLib;
import com.application.server.utils.DBUtil;
import com.application.server.utils.exception.ZException;

public class RideRequestDao extends BaseDao {

	public static final String LOGGER = "RideRequestDao.class";

	public RideRequestDao() {
		super(RideRequestDao.LOGGER);
	}

	// add your ride
	public RideRequest addRideRequest(RideRequest newRide) {
		RideRequest ride = null;
		Session session = null;
		info("addSession enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();
			session.save(newRide);
			ride = newRide;
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
		info("addRide exit");
		return ride;
	}

	// view your rides
	public ArrayList<RideRequest> getMyRides(int userId, int start, int count) {
		ArrayList<RideRequest> rides = new ArrayList<RideRequest>();
		Session session = null;
		info("getMyRides enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM RideRequest WHERE UserId = :userId order by Created LIMIT :start, :count";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(RideRequest.class);
			query.setParameter("userId", userId);
			query.setParameter("start", start);
			query.setParameter("count", count);
			java.util.List results = (java.util.List) query.list();

			for (Iterator iterator = ((java.util.List) results).iterator(); iterator.hasNext();) {
				RideRequest currentRide = (RideRequest) (iterator.next());
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

			String sql = "SELECT COUNT(*) FROM RideRequest WHERE UserId = :userid";
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
	public ArrayList<RideRequest> getFeedRides(int userId, double startLatitude, double startLongitude,
			String startGooglePlaceId, double endLatitude, double endLongitude, String endGooglePlaceId, int start,
			int count) {
		// iterating over all the current rides which fall under the specified
		// radius
		ArrayList<RideRequest> rides = new ArrayList<RideRequest>();
		Session session = null;
		try {
			session = DBUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM RideRequest WHERE UserId <> :userid and Status in (:status1, :status2) order by Created LIMIT :start, :count";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(RideRequest.class);
			query.setParameter("userid", userId);
			query.setParameter("status1", CommonLib.RIDE_STATUS_CREATED);
			query.setParameter("status2", CommonLib.RIDE_STATUS_ACCEPTED);
			query.setParameter("start", start);
			query.setParameter("count", count);
			java.util.List results = (java.util.List) query.list();
			for (Iterator iterator = ((java.util.List) results).iterator(); iterator.hasNext();) {
				RideRequest currentRide = (RideRequest) (iterator.next());
				rides.add(currentRide);
			}

			transaction.commit();
			session.close();
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			System.out.println("error");
			return rides;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		// check if any ride is traveling to the similar distance

		// return that ride

		info("getMyRides exit");
		return rides;
	}

	// view active rides nearby count
	public int getFeedRidesCount(int userId, double startLatitude, double startLongitude, String startGooglePlaceId,
			double endLatitude, double endLongitude, String dropGooglePlaceId) {
		int size = 0;
		Session session = null;
		try {
			session = DBUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			String sql = "SELECT Count(*) FROM RideRequest WHERE UserId <> :userid and Status in (:status1, :status2)";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("userid", userId);
			query.setParameter("status1", CommonLib.RIDE_STATUS_CREATED);
			query.setParameter("status2", CommonLib.RIDE_STATUS_ACCEPTED);
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
			size = 0;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		// check if any ride is traveling to the similar distance

		// return that ride

		info("getMyRides exit");
		return size;
	}

	// delete your ride which are not finished and which doesn't
	public synchronized void updateOlderRides() {
		Session session = null;
		info("getMyRides enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM RideRequest WHERE Status in (:status1, :status2) and Created < :threshold";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(RideRequest.class);
			query.setParameter("status1", CommonLib.RIDE_STATUS_ACCEPTED);
			query.setParameter("status2", CommonLib.RIDE_STATUS_CREATED);
			query.setParameter("threshold", System.currentTimeMillis() - CommonLib.THRESHOLD_DELETION);

			java.util.List results = (java.util.List) query.list();

			for (Iterator iterator = ((java.util.List) results).iterator(); iterator.hasNext();) {
				RideRequest currentRide = (RideRequest) (iterator.next());
				currentRide.setStatus(CommonLib.RIDE_STATUS_EXPIRED);
				session.update(currentRide);
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
	}

	public RideRequest getRide(int rideId) {
		RideRequest currentRide = null;
		Session session = null;
		info("getMyRides enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM RideRequest WHERE RideId = :rideId";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(RideRequest.class);
			query.setParameter("rideId", rideId);
			java.util.List results = (java.util.List) query.list();

			if(results != null && results.size() > 0)
				currentRide = (RideRequest) (results.get(0));

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
		return currentRide;
	}
	
	public void updateRide(RideRequest ride) {
		Session session = null;
		info("getMyRides enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();
			session.update(ride);

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
	}

}