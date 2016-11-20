package com.application.server.controller;

import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.application.server.model.Ride;
import com.application.server.model.User;
import com.application.server.model.UserRide;
import com.application.server.utils.DBUtil;
import com.application.server.utils.exception.ZException;

public class UserRideDao extends BaseDao {

	public static final String LOGGER = "UserRideDao.class";

	public UserRideDao() {
		super(UserRideDao.LOGGER);
	}

	// add your ride
	public UserRide addRide(UserRide newRide) {
		UserRide ride = null;
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

	// get the accepted users for a ride
	public ArrayList<User> getRidePeople(int rideId) {
		ArrayList<User> rides = new ArrayList<User>();
		Session session = null;
		info("getMyRides enter");
		try {
			session = DBUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();

			String sql = "SELECT * FROM User WHERE UserId in (Select TravellerId from UserRide Where RideId = :rideId)";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Ride.class);
			query.setParameter("rideId", rideId);
			java.util.List results = (java.util.List) query.list();

			for (Iterator iterator = ((java.util.List) results).iterator(); iterator.hasNext();) {
				User currentRide = (User) (iterator.next());
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

}
