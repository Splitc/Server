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
import com.application.server.utils.mailer.EmailModel;
import com.application.server.utils.mailer.EmailUtil;

@Path("/user")
public class UserResource extends BaseResource {

	public static final String LOGGER = "UserResource.class";

	public UserResource() {
		super(UserResource.LOGGER);
	}

	@Path("/feedback")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject userSignup(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("log") String log, @FormParam("message") String title,
			@FormParam("access_token") String accessToken) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao userDao = new UserDao();

		// access token validity
		User user = userDao.userActive(accessToken);

		if (user != null && user.getUserId() > 0) {
			EmailModel eModel = new EmailModel();
			eModel.setContent(EmailUtil.getInstance().getFeedbackContent(user.getUserName(), title, log));
			eModel.setSubject(EmailUtil.USER_FEEDBACK_SUBJECT);
			eModel.setFrom(user.getEmail());
			ArrayList<String> senders = new ArrayList<String>();
			senders.add(CommonLib.ZAPP_ID);
			eModel.setSenders(senders);
			EmailUtil.getInstance().sendEmail(eModel);
			return CommonLib.getResponseString("success", "success", CommonLib.RESPONSE_SUCCESS);
		} else
			return CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);
	}

	@Path("/registrationId")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject registerPushId(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("access_token") String accessToken, @FormParam("pushId") String pushId) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao dao = new UserDao();

		User user = dao.userActive(accessToken);

		UserSessionDao userSessionDao = new UserSessionDao();
		if (user != null && user.getUserId() > 0 && userSessionDao.updateRegistratonId(pushId, accessToken))
			return CommonLib.getResponseString("success", "success", CommonLib.RESPONSE_SUCCESS);

		return CommonLib.getResponseString("", "", CommonLib.RESPONSE_FAILURE);
	}

	@Path("/location")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject registerPushId(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("access_token") String accessToken, @FormParam("latitude") double lat,
			@FormParam("longitude") double lon) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao dao = new UserDao();

		User user = dao.userActive(accessToken);
		UserSessionDao userSessionDao = new UserSessionDao();
		if (user != null && user.getUserId() > 0 && userSessionDao.updateLocation(lat, lon, accessToken))
			return CommonLib.getResponseString("success", "success", CommonLib.RESPONSE_SUCCESS);

		return CommonLib.getResponseString("", "", CommonLib.RESPONSE_FAILURE);
	}

	@Path("/details")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject getUserDetails(@FormParam("client_id") String clientId, @FormParam("app_type") String appType,
			@FormParam("access_token") String accessToken, @FormParam("userId") int userId) {

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao dao = new UserDao();

		User user = dao.userActive(accessToken);
		if (user != null && user.getUserId() > 0) {
			User otherUser = dao.getUserDetailsFromUserId(userId);
			try {
				return CommonLib.getResponseString(JsonUtil.getUserJson(otherUser), "success",
						CommonLib.RESPONSE_SUCCESS);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return CommonLib.getResponseString("", "", CommonLib.RESPONSE_FAILURE);
	}
}
