package com.application.server.utils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.application.server.model.Ride;
import com.application.server.model.RideRequest;
import com.application.server.model.User;
import com.application.server.model.pojo.Feed;

public class JsonUtil {

	public static JSONObject getUserJson(User user) throws JSONException {
		if (user == null)
			return null;
		JSONObject userJsonObject = new JSONObject();

		JSONObject userJson = new JSONObject();
		userJsonObject.put("email", user.getEmail());
		userJsonObject.put("user_id", user.getUserId() + "");
		userJsonObject.put("description", user.getBio());
		userJsonObject.put("phone", user.getPhone());
		userJsonObject.put("profile_pic", user.getProfilePic());
		userJsonObject.put("user_name", CommonLib.getUserName(user));
		
		return userJsonObject;
	}

	public static JSONObject getRideJson(Ride ride) throws JSONException {
		if (ride == null)
			return null;
		JSONObject userJsonObject = new JSONObject();

		userJsonObject.put("fromAddress", ride.getFromAddress());
		userJsonObject.put("startLat", ride.getStartLat() + "");
		userJsonObject.put("startLon", ride.getStartLon());
		userJsonObject.put("startGooglePlaceId", ride.getStartGooglePlaceId());
		userJsonObject.put("toAddress", ride.getToAddress());
		userJsonObject.put("dropLat", ride.getDropLat());
		userJsonObject.put("dropLon", ride.getDropLon());
		userJsonObject.put("dropGooglePlaceId", ride.getDropGooglePlaceId());
		userJsonObject.put("status", ride.getStatus());
		userJsonObject.put("rideId", ride.getRideId());

		userJsonObject.put("created", ride.getCreated());
		userJsonObject.put("startTime", ride.getStartTime());
		userJsonObject.put("requiredPersons", ride.getRequiredPersons());
		userJsonObject.put("description", ride.getDescription());

		userJsonObject.put("userId", ride.getUserId());

		return userJsonObject;
	}
	
	public static JSONObject getRideRequestJson(RideRequest ride) throws JSONException {
		if (ride == null)
			return null;
		JSONObject userJsonObject = new JSONObject();

		userJsonObject.put("fromAddress", ride.getFromAddress());
		userJsonObject.put("startLat", ride.getStartLat() + "");
		userJsonObject.put("startLon", ride.getStartLon());
		userJsonObject.put("startGooglePlaceId", ride.getStartGooglePlaceId());
		userJsonObject.put("toAddress", ride.getToAddress());
		userJsonObject.put("dropLat", ride.getDropLat());
		userJsonObject.put("dropLon", ride.getDropLon());
		userJsonObject.put("dropGooglePlaceId", ride.getDropGooglePlaceId());
		userJsonObject.put("status", ride.getStatus());
		userJsonObject.put("rideRequestId", ride.getRideRequestId());

		userJsonObject.put("created", ride.getCreated());
		userJsonObject.put("startTime", ride.getStartTime());
		userJsonObject.put("requiredPersons", ride.getPersons());
		userJsonObject.put("description", ride.getDescription());

		userJsonObject.put("userId", ride.getUserId());

		return userJsonObject;
	}
	
	public static JSONObject getFeedJson(Feed feedItem) throws JSONException {
		if (feedItem == null)
			return null;
		JSONObject userJsonObject = new JSONObject();

		userJsonObject.put("fromAddress", feedItem.getFromAddress());
		userJsonObject.put("startLat", feedItem.getStartLat() + "");
		userJsonObject.put("startLon", feedItem.getStartLon());
		userJsonObject.put("startGooglePlaceId", feedItem.getStartGooglePlaceId());
		userJsonObject.put("toAddress", feedItem.getToAddress());
		userJsonObject.put("dropLat", feedItem.getDropLat());
		userJsonObject.put("dropLon", feedItem.getDropLon());
		userJsonObject.put("dropGooglePlaceId", feedItem.getDropGooglePlaceId());
		userJsonObject.put("status", feedItem.getStatus());
		userJsonObject.put("rideId", feedItem.getFeedId());
		userJsonObject.put("type", feedItem.getFeedType());

		userJsonObject.put("created", feedItem.getCreated());
		userJsonObject.put("startTime", feedItem.getStartTime());
		userJsonObject.put("requiredPersons", feedItem.getRequiredPersons());
		userJsonObject.put("description", feedItem.getDescription());

		userJsonObject.put("userId", feedItem.getUserId());

		return userJsonObject;
	}
}
