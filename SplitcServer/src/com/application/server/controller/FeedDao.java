package com.application.server.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.application.server.model.Ride;
import com.application.server.model.RideRequest;
import com.application.server.model.pojo.Feed;
import com.application.server.utils.CommonLib;
import com.application.server.utils.DBUtil;

public class FeedDao extends BaseDao {

	public static final String LOGGER = "FeedDao.class";

	public FeedDao() {
		super(FeedDao.LOGGER);
	}

	// view active rides nearby
	public ArrayList<Feed> getFeedRides(int userId, double startLatitude, double startLongitude,
			String startGooglePlaceId, double endLatitude, double endLongitude, String endGooglePlaceId, int start,
			int count, int filterOptions) {
		// iterating over all the current rides which fall under the specified
		// radius
		ArrayList<Feed> feedItems = new ArrayList<Feed>();
		Session session = null;
		try {
			session = DBUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			// Fetch Rides
			String sql = "SELECT * FROM Ride WHERE UserId <> :userid and Status in (:status1, :status2) order by Created"
					+ " LIMIT :start, :count";
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
				feedItems.add(CommonLib.getFeedItemFromRide(currentRide));
			}

			// Fetch ride requests
			ArrayList<RideRequest> ridesRequests = new ArrayList<RideRequest>();
			String sqlRequest = "SELECT * FROM RideRequest WHERE UserId <> :userid and Status in (:status1, :status2) order by Created"
					+ " LIMIT :start, :count";
			SQLQuery queryRequest = session.createSQLQuery(sqlRequest);
			queryRequest.addEntity(RideRequest.class);
			queryRequest.setParameter("userid", userId);
			queryRequest.setParameter("status1", CommonLib.RIDE_STATUS_CREATED);
			queryRequest.setParameter("status2", CommonLib.RIDE_STATUS_ACCEPTED);
			queryRequest.setParameter("start", start);
			queryRequest.setParameter("count", count);
			java.util.List resultsRequest = (java.util.List) queryRequest.list();
			for (Iterator iterator = ((java.util.List) resultsRequest).iterator(); iterator.hasNext();) {
				RideRequest currentRide = (RideRequest) (iterator.next());
				feedItems.add(CommonLib.getFeedItemFromRideRequest(currentRide));
			}

			transaction.commit();
			session.close();
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			System.out.println("error");
			return feedItems;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		// check if any ride is traveling to the similar distance

		// return that ride

		info("getMyRides exit");
		return feedItems;
	}

	// view active rides nearby count
	public int getFeedRidesCount(int userId, double startLatitude, double startLongitude, String startGooglePlaceId,
			double endLatitude, double endLongitude, String dropGooglePlaceId, int filter) {
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

			String sqlRequest = "SELECT Count(*) FROM RideRequest WHERE UserId <> :userid and Status in (:status1, :status2)";
			SQLQuery queryRequest = session.createSQLQuery(sqlRequest);
			queryRequest.setParameter("userid", userId);
			queryRequest.setParameter("status1", CommonLib.RIDE_STATUS_CREATED);
			queryRequest.setParameter("status2", CommonLib.RIDE_STATUS_ACCEPTED);
			java.util.List resultsRequest = (java.util.List) queryRequest.list();
			Object resultValueRequest = resultsRequest.get(0);
			if (resultValueRequest instanceof BigInteger)
				size += ((BigInteger) resultsRequest.get(0)).intValue();

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
}
