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

import com.application.server.controller.RideRequestDao;
import com.application.server.controller.UserDao;
import com.application.server.controller.UserRideDao;
import com.application.server.model.RideRequest;
import com.application.server.model.User;
import com.application.server.utils.CommonLib;
import com.application.server.utils.JsonUtil;
import com.application.server.utils.exception.ZException;

public class RideRequestResource extends BaseResource {

	public static final String LOGGER = "RideRequestResource.class";

	public RideRequestResource() {
		super(RideRequestResource.LOGGER);
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
			@FormParam("dropGooglePlaceId") String dropGooglePlaceId, @FormParam("persons") int persons,
			@FormParam("description") String description, @FormParam("startTime") long startTime) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao userDao = new UserDao();

		// access token validity
		User user = userDao.userActive(accessToken);

		if (user != null && user.getUserId() > 0) {
			RideRequest newRide = new RideRequest();
			newRide.setFromAddress(fromAddress);
			newRide.setCreated(System.currentTimeMillis());
			newRide.setStartTime(startTime);
			newRide.setDescription(description);
			newRide.setDropGooglePlaceId(dropGooglePlaceId);
			newRide.setDropLat(dropLat);
			newRide.setDropLon(dropLon);
			newRide.setFromAddress(fromAddress);
			newRide.setPersons(persons);
			newRide.setStartGooglePlaceId(startGooglePlaceId);
			newRide.setStartLat(startLat);
			newRide.setStartLon(startLon);
			newRide.setStatus(CommonLib.RIDE_STATUS_CREATED);
			newRide.setToAddress(toAddress);
			newRide.setUserId(user.getUserId());

			RideRequestDao rideDao = new RideRequestDao();
			newRide = rideDao.addRideRequest(newRide);
			if (newRide != null && newRide.getRideRequestId() > 0) {
				try {
					return CommonLib.getResponseString(JsonUtil.getRideRequestJson(newRide), "Invalid user",
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
			RideRequestDao rideDao = new RideRequestDao();
			List<RideRequest> rides = rideDao.getMyRides(user.getUserId(), start, count);
			int size = rideDao.getMyRidesCount(user.getUserId());
			JSONObject returnObject = new JSONObject();
			try {
				JSONArray jsonArr = new JSONArray();
				for (RideRequest wish : rides) {
					JSONObject wishJson = JsonUtil.getRideRequestJson(wish);

					JSONArray userArr = new JSONArray();
					UserRideDao userRideDao = new UserRideDao();
					List<User> acceptedUsers = userRideDao.getRidePeople(wish.getRideRequestId());
					for (User acceptedUser : acceptedUsers) {
						userArr.put(JsonUtil.getUserJson(acceptedUser));
					}
					wishJson.put("users", userArr);
					wishJson.put("user", JsonUtil.getUserJson(user));
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
}
