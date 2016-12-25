package com.application.server.resource;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.application.server.controller.UserDao;
import com.application.server.controller.UserSessionDao;
import com.application.server.model.User;
import com.application.server.utils.CommonLib;
import com.application.server.utils.JsonUtil;
import com.application.server.utils.pusher.PushModel;
import com.application.server.utils.pusher.PushUtil;

@Path("/message")
public class ChatResource extends BaseResource {

	public static final String LOGGER = "ChatResource.class";

	public ChatResource() {
		super(ChatResource.LOGGER);
	}

	@Path("/send")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject sendMessage(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("access_token") String accessToken, @FormParam("message") String message,
			@FormParam("userId") int userId, @FormParam("type") int type, @FormParam("feedId") int feedId) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao userDao = new UserDao();

		// access token validity
		User user = userDao.userActive(accessToken);

		if (user != null && user.getUserId() > 0) {
			User otherUser = userDao.getUserDetailsFromUserId(userId);

			// check the user
			if (otherUser == null || otherUser.getUserId() < 1)
				return CommonLib.getResponseString("invalid user", "failure", CommonLib.RESPONSE_FAILURE);

			// fetch all sessions
			UserSessionDao sessionDao = new UserSessionDao();
			ArrayList<String> pushIds = sessionDao.getUserPushIds(userId);

			// send the push in a separate thread
			PushModel pushModel = new PushModel();
			pushModel.setCommand(CommonLib.PUSH_COMMAND_MESSAGE);
			pushModel.setPushIds(pushIds);
			pushModel.setType(CommonLib.PUSH_TYPE_CHAT);
			JSONObject pushJson = null;
			try {
				pushJson = JsonUtil.getMessageJson(CommonLib.getUserName(user), user.getProfilePic(), user.getUserId(), userId, message, feedId, type);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			pushModel.setObject(pushJson);
			PushUtil.getInstance().sendPush(pushModel);

			// acknowledge the user
			return CommonLib.getResponseString(pushJson, "success", CommonLib.RESPONSE_SUCCESS);
		} else
			return CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);
	}

}