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

import com.application.server.controller.FeedDao;
import com.application.server.controller.UserDao;
import com.application.server.model.User;
import com.application.server.model.pojo.Feed;
import com.application.server.utils.CommonLib;
import com.application.server.utils.JsonUtil;

@Path("/feed")
public class FeedResource extends BaseResource {

	public static final String LOGGER = "FeedResource.class";
	public static final int FILTER_SHOW_RIDES = 2;
	public static final int FILTER_SHOW_RIDE_REQUESTS = 1;
	public static final int FILTER_SHOW_ALL = 0;

	public FeedResource() {
		super(FeedResource.LOGGER);
	}

	@Path("/fetch")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject getFeedRides(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("access_token") String accessToken, @QueryParam("start") int start,
			@QueryParam("count") int count, @FormParam("startLatitude") double startLat,
			@FormParam("startLongitude") double startLon, @FormParam("startGooglePlaceId") String startGooglePlaceId,
			@FormParam("dropLatitude") double dropLat, @FormParam("dropLongitude") double dropLon,
			@FormParam("dropGooglePlaceId") String dropGooglePlaceId,
			@FormParam("filter_options") String filterOptions) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao userDao = new UserDao();

		// access token validity
		User user = userDao.userActive(accessToken);

		if (user != null && user.getUserId() > 0) {

			int filter = FILTER_SHOW_ALL;
			if (filterOptions != null && !filterOptions.isEmpty()) {
				filter = filterOptions.equalsIgnoreCase("ride") ? FILTER_SHOW_RIDES
						: filterOptions.equalsIgnoreCase("rideRequest") ? FILTER_SHOW_RIDE_REQUESTS : FILTER_SHOW_ALL;
			}

			FeedDao feedDao = new FeedDao();
			List<Feed> feedItems = feedDao.getFeedRides(user.getUserId(), startLat, startLon, startGooglePlaceId,
					dropLat, dropLon, dropGooglePlaceId, start, count, filter);

			int size = feedDao.getFeedRidesCount(user.getUserId(), startLat, startLon, startGooglePlaceId, dropLat,
					dropLon, dropGooglePlaceId, filter);

			JSONObject returnObject = new JSONObject();
			try {
				JSONArray ridesArr = new JSONArray();
				for (Feed wish : feedItems) {
					JSONObject wishJson = JsonUtil.getFeedJson(wish);
					wishJson.put("user", JsonUtil.getUserJson(user));
					ridesArr.put(wishJson);
				}
				returnObject.put("feedItems", ridesArr);
				returnObject.put("total", size);
			} catch (JSONException e) {

			}
			return CommonLib.getResponseString(returnObject, "success", CommonLib.RESPONSE_SUCCESS);
		} else
			return CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);
	}

}
