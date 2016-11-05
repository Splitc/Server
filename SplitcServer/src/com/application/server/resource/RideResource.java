package com.application.server.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.application.server.controller.RideDao;
import com.application.server.controller.UserDao;
import com.application.server.controller.UserRideDao;
import com.application.server.model.Ride;
import com.application.server.model.User;
import com.application.server.model.UserRide;
import com.application.server.utils.CommonLib;
import com.application.server.utils.JsonUtil;
import com.application.server.utils.exception.ZException;

@Path("/ride")
public class RideResource extends BaseResource {

	public static final String LOGGER = "RideResource.class";

	public RideResource() {
		super(RideResource.LOGGER);
	}

	@Path("/add")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject userSignup(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("access_token") String accessToken, @FormParam("fromAddress") String fromAddress,
			@FormParam("startLat") double startLat, @FormParam("startLon") double startLon,
			@FormParam("startGooglePlaceId") String startGooglePlaceId, @FormParam("toAddress") String toAddress,
			@FormParam("dropLat") double dropLat, @FormParam("dropLon") double dropLon,
			@FormParam("dropGooglePlaceId") String dropGooglePlaceId, @FormParam("requiredPersons") int requiredPersons,
			@FormParam("description") String description, @FormParam("startTime") long startTime) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao userDao = new UserDao();

		// access token validity
		User user = userDao.userActive(accessToken);

		if (user != null && user.getUserId() > 0) {
			Ride newRide = new Ride();
			newRide.setFromAddress(fromAddress);
			newRide.setCreated(System.currentTimeMillis());
			newRide.setStartTime(startTime);
			newRide.setDescription(description);
			newRide.setDropGooglePlaceId(dropGooglePlaceId);
			newRide.setDropLat(dropLat);
			newRide.setDropLon(dropLon);
			newRide.setFromAddress(fromAddress);
			newRide.setRequiredPersons(requiredPersons);
			newRide.setCurrentRequiredPersons(requiredPersons);
			newRide.setStartGooglePlaceId(startGooglePlaceId);
			newRide.setStartLat(startLat);
			newRide.setStartLon(startLon);
			newRide.setStatus(CommonLib.RIDE_STATUS_CREATED);
			newRide.setToAddress(toAddress);
			newRide.setUserId(user.getUserId());

			RideDao rideDao = new RideDao();
			newRide = rideDao.addRide(newRide);
			if (newRide != null && newRide.getRideId() > 0) {
				try {
					return CommonLib.getResponseString(JsonUtil.getRideJson(newRide), "Invalid user",
							CommonLib.RESPONSE_SUCCESS);
				} catch (JSONException e) {
					try {
						throw new ZException("Error", e);
					} catch (ZException e1) {
						e1.printStackTrace();
					}
				}
			}

			return CommonLib.getResponseString("success", "success", CommonLib.RESPONSE_SUCCESS);
		} else
			return CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);
	}

	@Path("/fetch")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject getMyRides(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("access_token") String accessToken, @QueryParam("start") int start,
			@QueryParam("count") int count) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao userDao = new UserDao();

		// access token validity
		User user = userDao.userActive(accessToken);

		if (user != null && user.getUserId() > 0) {
			RideDao rideDao = new RideDao();
			List<Ride> rides = rideDao.getMyRides(user.getUserId(), start, count);
			int size = rideDao.getMyRidesCount(user.getUserId());
			JSONObject returnObject = new JSONObject();
			try {
				JSONArray jsonArr = new JSONArray();
				for (Ride wish : rides) {
					JSONObject wishJson = JsonUtil.getRideJson(wish);

					JSONArray userArr = new JSONArray();
					UserRideDao userRideDao = new UserRideDao();
					List<User> acceptedUsers = userRideDao.getAcceptedUsers(wish.getRideId());
					for (User acceptedUser : acceptedUsers) {
						userArr.put(JsonUtil.getUserJson(acceptedUser));
					}
					wishJson.put("accepted_users", userArr);
					jsonArr.put(wishJson);
				}
				returnObject.put("rides", jsonArr);
				returnObject.put("total", size);
			} catch (JSONException e) {

			}
			return CommonLib.getResponseString(returnObject, "success", CommonLib.RESPONSE_SUCCESS);
		} else
			return CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);
	}

	@Path("/feed")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject getFeedRides(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("access_token") String accessToken, @QueryParam("start") int start,
			@QueryParam("count") int count, @FormParam("startLatitude") double startLat,
			@FormParam("startLongitude") double startLon, @FormParam("startGooglePlaceId") String startGooglePlaceId,
			@FormParam("dropLatitude") double dropLat, @FormParam("dropLongitude") double dropLon,
			@FormParam("dropGooglePlaceId") String dropGooglePlaceId) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao userDao = new UserDao();

		// access token validity
		User user = userDao.userActive(accessToken);

		if (user != null && user.getUserId() > 0) {
			RideDao rideDao = new RideDao();
			List<Ride> rides = rideDao.getFeedRides(user.getUserId(), startLat, startLon, startGooglePlaceId, dropLat,
					dropLon, dropGooglePlaceId, start, count);
			int size = rideDao.getFeedRidesCount(user.getUserId(), startLat, startLon, startGooglePlaceId, dropLat,
					dropLon, dropGooglePlaceId);
			JSONObject returnObject = new JSONObject();
			try {
				JSONArray jsonArr = new JSONArray();
				for (Ride wish : rides) {
					JSONObject wishJson = JsonUtil.getRideJson(wish);

					JSONArray userArr = new JSONArray();
					UserRideDao userRideDao = new UserRideDao();
					List<User> acceptedUsers = userRideDao.getAcceptedUsers(wish.getRideId());
					for (User acceptedUser : acceptedUsers) {
						userArr.put(JsonUtil.getUserJson(acceptedUser));
					}
					wishJson.put("accepted_users", userArr);
					jsonArr.put(wishJson);
				}
				returnObject.put("rides", jsonArr);
				returnObject.put("total", size);
			} catch (JSONException e) {

			}
			return CommonLib.getResponseString(returnObject, "success", CommonLib.RESPONSE_SUCCESS);
		} else
			return CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);
	}

	// TODO: if action is already taken for the particular ride, then reject
	// TODO: action for rating vs the new ride acceptance
	// TODO: action for cancellation
	// TODO: check for add vs update
	@Path("/action")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject updateUserRide(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("access_token") String accessToken, @FormParam("action") int action,
			@FormParam("rideId") int rideId, @FormParam("fromAddress") String fromAddress,
			@FormParam("startLat") double startLat, @FormParam("startLon") double startLon,
			@FormParam("startGooglePlaceId") String startGooglePlaceId, @FormParam("toAddress") String toAddress,
			@FormParam("dropLat") double dropLat, @FormParam("dropLon") double dropLon,
			@FormParam("dropGooglePlaceId") String dropGooglePlaceId, @FormParam("description") String description,
			@FormParam("rating") float rating) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao userDao = new UserDao();

		// access token validity
		User user = userDao.userActive(accessToken);

		if (user != null && user.getUserId() > 0) {
			RideDao rideDao = new RideDao();

			Ride currentRide = rideDao.getRide(rideId);

			int currentPersons = currentRide.getCurrentRequiredPersons();
			if (currentPersons < 1) {
				return CommonLib.getResponseString("failure", "already has succicient persons",
						CommonLib.RESPONSE_FAILURE);
			}

			UserRideDao userRideDao = new UserRideDao();

			UserRide userRide = new UserRide();
			userRide.setCreated(System.currentTimeMillis());
			userRide.setDescription(description);
			userRide.setDropGooglePlaceId(dropGooglePlaceId);
			userRide.setDropLat(dropLat);
			userRide.setDropLon(dropLon);
			userRide.setFromAddress(fromAddress);
			userRide.setRating(rating);
			userRide.setRideId(rideId);
			userRide.setStartGooglePlaceId(startGooglePlaceId);
			userRide.setStartLat(startLat);
			userRide.setTravellerId(user.getUserId());
			userRide.setToAddress(toAddress);
			userRide.setStartLon(startLon);
			userRide.setUserId(currentRide.getUserId());

			if (currentPersons < 2)
				userRide.setStatus(CommonLib.RIDE_STATUS_FULFILLED);
			else
				userRide.setStatus(CommonLib.RIDE_STATUS_ACCEPTED);

			userRide = userRideDao.addRide(userRide);

			if (userRide != null) {
				currentRide.setCurrentRequiredPersons(currentPersons - 1);
				rideDao.updateRide(currentRide);
			}
			return CommonLib.getResponseString(userRide, "success", CommonLib.RESPONSE_SUCCESS);
		} else
			return CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);
	}

}