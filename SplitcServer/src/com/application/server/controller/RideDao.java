package com.application.server.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.omg.CORBA.COMM_FAILURE;

import com.application.server.model.Ride;
import com.application.server.utils.CommonLib;
import com.application.server.utils.DBUtil;
import com.application.server.utils.exception.ZException;

import gnu.io.CommPortOwnershipListener;

public class RideDao extends BaseDao {

	public static final String LOGGER = "RideDao.class";

	public RideDao() {
		super(RideDao.LOGGER);
	}

	// add your ride
	public Ride addRide(Ride newRide) {
		Ride ride = null;
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
	public ArrayList<Ride> getMyRides(int userId, int start, int count) {
		ArrayList<Ride> rides = new ArrayList<Ride>();
		Session session = null;
		info("getMyRides enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM Ride WHERE UserId = :userId order by Created LIMIT :start, :count";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Ride.class);
			query.setParameter("userId", userId);
			query.setParameter("start", start);
			query.setParameter("count", count);
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
	public ArrayList<Ride> getFeedRides(int userId, double startLatitude, double startLongitude,
			String startGooglePlaceId, double endLatitude, double endLongitude, String endGooglePlaceId, int start,
			int count) {
		// iterating over all the current rides which fall under the specified
		// radius
		ArrayList<Ride> rides = new ArrayList<Ride>();
		Session session = null;
		try {
			session = DBUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM Ride WHERE UserId <> :userid and Status in (:status1, :status2) order by Created LIMIT :start, :count";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Ride.class);
			query.setParameter("userid", userId);
			query.setParameter("status1", CommonLib.RIDE_STATUS_CREATED);
			query.setParameter("status2", CommonLib.RIDE_STATUS_ACCEPTED);
			query.setParameter("start", start);
			query.setParameter("count", count);
			java.util.List results = (java.util.List) query.list();
			for (Iterator iterator = ((java.util.List) results).iterator(); iterator.hasNext();) {
				Ride currentRide = (Ride) (iterator.next());
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

			String sql = "SELECT Count(*) FROM Ride WHERE UserId <> :userid and Status in (:status1, :status2)";
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

			String sql = "SELECT * FROM Ride WHERE Status in (:status1, :status2) and Created < :threshold";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Ride.class);
			query.setParameter("status1", CommonLib.RIDE_STATUS_ACCEPTED);
			query.setParameter("status2", CommonLib.RIDE_STATUS_CREATED);
			query.setParameter("threshold", System.currentTimeMillis() - CommonLib.THRESHOLD_DELETION);

			java.util.List results = (java.util.List) query.list();

			for (Iterator iterator = ((java.util.List) results).iterator(); iterator.hasNext();) {
				Ride currentRide = (Ride) (iterator.next());
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

	public Ride getRide(int rideId) {
		Ride currentRide = null;
		Session session = null;
		info("getMyRides enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM Ride WHERE RideId = :rideId";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Ride.class);
			query.setParameter("rideId", rideId);
			java.util.List results = (java.util.List) query.list();

			if(results != null && results.size() > 0)
				currentRide = (Ride) (results.get(0));

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
	
	public void updateRide(Ride ride) {
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