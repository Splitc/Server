package com.application.server.resource;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.application.server.controller.UserDao;
import com.application.server.controller.UserSessionDao;
import com.application.server.model.User;
import com.application.server.model.pojo.Location;
import com.application.server.utils.CommonLib;
import com.application.server.utils.JsonUtil;
import com.application.server.utils.exception.ZException;
import com.application.server.utils.mailer.EmailModel;
import com.application.server.utils.mailer.EmailUtil;

@Path("/auth")
public class UserSession extends BaseResource {

	public static final String LOGGER = "UserSession.class";

	public UserSession() {
		super(UserSession.LOGGER);
	}

	/**
	 * Operations - User login using Facebook Operations, Add a new user, Send
	 * an email to the new user Generates a session for the user
	 * 
	 * @author apoorvarora
	 */
	@Path("/login")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject authorization(@DefaultValue("images/default.jpg") @FormParam("profile_pic") String profilePic,
			@FormParam("app_type") String appType, @FormParam("client_id") String clientId,
			@FormParam("user_name") String userName, @FormParam("email") String email, @FormParam("phone") String phone,
			@FormParam("bio") String bio, @FormParam("registration_id") String regId,
			@FormParam("latitude") double latitude, @FormParam("longitude") double longitude,
			@FormParam("fbid") String fbId, @FormParam("fbdata") String fbData, @FormParam("fb_token") String fbToken,
			@FormParam("fb_permission") String fb_permissions, @Context HttpServletRequest requestContext

	) {
		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		if (fbToken == null || fbToken.isEmpty())
			return CommonLib.getResponseString("Invalid params", "", CommonLib.RESPONSE_INVALID_PARAMS);

		if (email == null || email.isEmpty())
			return CommonLib.getResponseString("Invalid email", "", CommonLib.RESPONSE_INVALID_PARAMS);

		UserDao userDao = new UserDao();
		User user = null;

		// create user if it does not exist, else generate the access token
		user = userDao.getUserDetails(fbId);

		if ((user == null || user.getUserId() <= 0))
			user = userDao.getUserDetailsFromEmail(email);

		if (user == null || user.getUserId() <= 0) {
			User userToAdd = new User();
			userToAdd.setProfilePic(profilePic);
			userToAdd.setUserName(userName);
			userToAdd.setEmail(email);
			userToAdd.setPhone(phone);
			userToAdd.setBio(bio);
			userToAdd.setFacebookId(fbId);
			userToAdd.setFacebookData(fbData);
			userToAdd.setFbPermission(fb_permissions);
			userToAdd.setCreated(System.currentTimeMillis());
			userToAdd.setModified(0);

			userToAdd.setFacebookToken(fbToken);

			user = userDao.addUserDetails(userToAdd);

			if (user != null) {
				new Thread(new Runnable() {

					@Override
					public void run() {

						EmailModel eModel = new EmailModel();
						eModel.setFrom(CommonLib.ZAPP_ID);
						ArrayList<String> senders = new ArrayList<String>();
						senders.add(email);
						eModel.setSenders(senders);
						eModel.setSubject(EmailUtil.USER_SIGNUP_SUBJECT);

						String htmlMail = null;
						try {

							htmlMail = CommonLib.readFile(getClass().getResourceAsStream(
									"/com/application/server/util/mailer/welcome/welcomeMail.html"));

						} catch (Exception e) {
							try {
								throw new ZException("Error", e);
							} catch (ZException e1) {
								e1.printStackTrace();
							}
						}
						eModel.setContent(htmlMail);
						EmailUtil.getInstance().sendHtmlEmail(eModel);

					}
				}).start();
			}

		} else {
			if (user != null) {

				boolean isValueChanged = false;

				if (user.getFacebookData() != null && fbData != null && !user.getFacebookData().equals(fbData)) {
					user.setFacebookData(fbData);
					isValueChanged = true;
				}
				if (user.getFacebookToken() != null && fbToken != null && !user.getFacebookToken().equals(fbToken)) {
					user.setFacebookToken(fbToken);
					isValueChanged = true;
				}
				if (user.getFbPermission() != null && fb_permissions != null
						&& !user.getFbPermission().equals(fb_permissions)) {
					user.setFbPermission(fb_permissions);
					isValueChanged = true;
				}
				if (user.getFacebookId() != null && fbId != null && !user.getFacebookId().equals(fbId)) {
					user.setFacebookId(fbId);
					isValueChanged = true;
				}
				if (user.getProfilePic() != null && profilePic != null && !user.getProfilePic().equals(profilePic)) {
					user.setProfilePic(profilePic);
					isValueChanged = true;
				}
				if (isValueChanged) {
					final User newUser = user;
					Runnable runnable = new Runnable() {
						public void run() {
							UserDao newDao = new UserDao();
							newDao.updateUserDetails(newUser);
						}
					};
					Thread newThread = new Thread(runnable);
					newThread.start();
				}

			}
		}

		if (user == null || user.getUserId() <= 0)
			return CommonLib.getResponseString("Error", "Some error occured", CommonLib.RESPONSE_INVALID_PARAMS);

		int status = CommonLib.RESPONSE_SUCCESS;
		Location location = new Location(latitude, longitude);
		UserSessionDao userSessionDao = new UserSessionDao();
		// Generate Access Token
		Object[] tokens = userSessionDao.generateAccessToken(user.getUserName(), user.getUserId(), "", regId, location);
		String accessToken = (String) tokens[0];
		boolean exists = (Boolean) tokens[1];

		boolean sessionAdded = false;
		if (!exists) {
			sessionAdded = userSessionDao.addSession(user.getUserId(), accessToken, regId, location, "");
		} else {
			sessionAdded = true;
		}
		if (sessionAdded) {
			JSONObject responseObject = new JSONObject();
			try {
				responseObject.put("access_token", accessToken);
				responseObject.put("user_id", user.getUserId());
				responseObject.put("email", user.getEmail());
				responseObject.put("profile_pic", user.getProfilePic());
				responseObject.put("username", user.getUserName());
				responseObject.put("user", JsonUtil.getUserJson(user));

			} catch (JSONException e) {
				try {
					throw new ZException("Error", e);
				} catch (ZException e1) {
					e1.printStackTrace();
				}
				error("Jersey exception: " + e.getMessage());
			}
			return CommonLib.getResponseString(responseObject.toString(), "", status);
		} else
			return CommonLib.getResponseString("failed", "", status);

	}

	/**
	 * Logout
	 * 
	 * @author apoorvarora
	 */
	@Path("/logout")
	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public JSONObject userLogout(@FormParam("access_token") String accessToken, @FormParam("client_id") String clientId,
			@FormParam("app_type") String appType) {

		// null checks, invalid request
		if (clientId == null || appType == null)
			CommonLib.getResponseString("failure", "invalid params", CommonLib.RESPONSE_INVALID_PARAMS);

		String clientCheck = super.clientCheck(clientId, appType);
		if (clientCheck != null && !clientCheck.equals("success"))
			CommonLib.getResponseString("failure", "invalid client", CommonLib.RESPONSE_INVALID_CLIENT_ID);

		UserDao userDao = new UserDao();
		User user = userDao.userActive(accessToken);
		boolean returnValue = false;

		if (user != null && user.getUserId() > 0) {
			UserSessionDao sessionDao = new UserSessionDao();
			returnValue = sessionDao.nullifyAccessToken(user.getUserId(), accessToken);
		}

		if (accessToken != null && !returnValue)
			CommonLib.getResponseString("failure", "", CommonLib.RESPONSE_FAILURE);

		return CommonLib.getResponseString("success", "", CommonLib.RESPONSE_SUCCESS);
	}

}
