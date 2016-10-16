package com.application.server.utils;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.dom4j.Branch;

import com.application.server.model.User;
import com.application.server.utils.exception.ZException;

public class CommonLib {

	public static final String ANDROID_CLIENT_ID = "splitc_android_client";
	public static final String ANDROID_APP_TYPE = "splitc_android";


	public static final String ANDROID_PUSH_CLIENT_ID = "zapp_android_push_client";
	public static final String ANDROID_PUSH_APP_TYPE = "zapp_android_push";

	public static final String ANDROID_MERCHANT_CLIENT_ID = "bt_merchant_android_client";

	public static final String APP_CONFIG_KEY_USER_POINTS_REFERRAL = "USER_POINTS_REFERRAL";
	public static final String APP_CONFIG_KEY_USER_POINTS_NEW_JOINEE = "USER_POINTS_NEW_JOINEE";
	public static final String APP_CONFIG_KEY_ZAPP_VALUE = "ZAPP_VALUE";
	public static final String APP_CONFIG_KEY_OLA_CASHBACK_INR = "OLA_CASHBACK_INR";
	public static final String APP_CONFIG_KEY_MEGA_CASHBACK_INR = "MEGA_CASHBACK_INR";
	public static final String APP_CONFIG_KEY_EASY_CASHBACK_INR = "EASY_CASHBACK_INR";
	public static final String APP_CONFIG_KEY_JUGNOO_CASHBACK_INR = "JUGNOO_CASHBACK_INR";
	public static final String APP_CONFIG_KEY_SHOW_APPCONFIG_DIALOG = "SHOW_APPCONFIG_DIALOG";
	public static final String APP_CONFIG_KEY_DEVICE_ID_CHECK = "DEVICE_ID_CHECK";
	public static final String APP_CONFIG_LEADERBOARD_FLAG = "LEADERBOARD_FLAG";
	public static final String APP_CONFIG_LEADERBOARD_NAME = "LEADERBOARD_NAME";
	public static final String APP_CONFIG_LEADERBOARD_IMAGE_URL = "LEADERBOARD_IMAGE_URL";
	public static final String APP_CONFIG_LEADERBOARD_USER_POINTS = "LEADERBOARD_USER_POINTS";
	public static final String APP_CONFIG_CANCELLATION_REASON = "CANCELLATION_REASON";
	public static final String APP_CONFIG_KEY_LOCAL_ADDRESS_VERSION = "LOCAL_ADDRESS_VERSION";
	public static final String APP_CONFIG_KEY_RIDZ_CASHBACK_INR = "RIDZ_CASHBACK_INR";
	public static final String APP_CONFIG_INTERCITY_CITIES = "INTERCITY_CITIES";
	public static final String APP_CONFIG_INTERCITY_COMMISION_PERCENTAGE = "INTERCITY_COMMISION_PERCENTAGE";

	public static final String WEB_CLIENT_ID = "zapp_web_client";
	public static final String WEB_APP_TYPE = "zapp_web";

	public static final String VERSION = "v1";

	public static final boolean ZLOG = false;
	public static final boolean LIVE_BUILD = false;

	public static final String ZAPP_ID = "";
	public static final String ZAPP_PWD = "";

	public static final int APP_REFER_STATUS_POINTS_REDEEMED = 1;
	public static final int APP_REFER_STATUS_POINTS_NOT_REDEEMED = 0;

	public static final double PERCENTAGE_ZAPP = 0.8;

	public static final int DEAL_TYPE_1 = 1;
	public static final int DEAL_TYPE_2 = 2;
	public static final int DEAL_TYPE_3 = 3;
	public static final int DEAL_TYPE_4 = 4; // Product deal tied up when the
												// merchant is tied up.

	public static final int INTERCITY = 901;
	public static final int INTRACITY = 902;
	public static final int SELFDRIVE = 903;

	// Cab Types
	public static final int CAB_SEDAN = 13;
	public static final int CAB_COMPACT = 14;
	public static final int CAB_LUXURY = 15;
	public static final int BIKE = 16;
	public static final int AUTO = 17;
	public static final int CAB_SUV = 18;
	public static final int TEMPO = 19;

	// Cab Structure
	public static final int ONLINE_CAB = 21;
	public static final int OFFLINE_CAB = 22;
	public static final int OFFLINE_CAB_VERSION = 23;

	// Modes
	public static final int ONE_WAY = 31;
	public static final int ROUND_TRIP = 32;
	public static final int MULTI_CITY = 33;
	public static final int SHARING = 34;
	public static final int PACKAGE = 35;
	public static final int PLACE_TO_PLACE = 36;

	public static final int USER_NOT_VERIFIED = 0;
	public static final int USER_PHONE_VERIFIED = 1;

	public static final int DP_ACTION_SLOW = 1;
	public static final int DP_ACTION_MODERATE = 2;
	public static final int DP_ACTION_FAST = 3;
	public static final int DP_ACTION_FULL = 4;

	public static final int SORT_INVALID = 0;
	public static final int SORT_ASC = 1;
	public static final int SORT_DESC = 2;

	public static final int TYPE_NOT_DEFINED = 0;
	public static final int TYPE_UBER = 5;
	public static final int TYPE_OLA = 6;
	public static final int TYPE_EASY = 7;
	public static final int TYPE_JUGNOO = 8;
	public static final int TYPE_MEGA = 9;
	public static final int TYPE_RIDZ = 10;

	// intercity
	public static final int TYPE_AHA = 31;
	public static final int TYPE_RODER = 32;
	public static final int TYPE_MYTAXI = 33;
	public static final int TYPE_GETMECAB = 34;
	public static final int TYPE_SAVAARI = 35;

	// selfdrive
	public static final int TYPE_MYLES = 51;
	public static final String TYPE_MYLES_DISPLAY_NAME = "MYLES";
	public static final String TYPE_MYLES_TEST_SERVER_URL = "http://119.226.59.187:8081/service.asmx";
	public static final String TYPE_MYLES_TEST_CLIENT_ID = "2205";

	public static final int TYPE_AVAILABILITY = 70;
	public static final int TYPE_BOOKING = 71;
	public static final int TYPE_CANCEL = 72;
	public static final int TYPE_OTHER = 73;

	public static final String TYPE_EASY_CABS_DISPLAY_NAME = "Easy Cabs";
	public static final String TYPE_MEGA_CABS_DISPLAY_NAME = "Mega Cabs";
	public static final String TYPE_JUGNOO_AUTO_DISPLAY_NAME = "Jugnoo Auto";

	public static final String TYPE_UBER_LOGO_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/logo/cab/uber.png";
	public static final String TYPE_OLA_LOGO_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/logo/cab/olaicon.png";
	public static final String TYPE_EASYCABS_LOGO_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/logo/cab/easycabs_logo.png";
	public static final String TYPE_JUGNOO_LOGO_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/logo/auto/jugnoo.png";

	public static final String TYPE_AHA_LOGO_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/aha_taxi_logo.png";
	public static final String TYPE_RODER_LOGO_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/logo/auto/jugnoo.png";
	public static final String TYPE_AHA_DISPLAY_NAME = "Aha";
	public static final String TYPE_AHA_TOS = "Zapplon only acts as a facilitator between the End-user and the Cab Services Provider. Any deficiency in the Cab services shall be the sole responsibility of the respective service provider.<br />"

	+ "Service tax (at 6%) is included in the fare.<br />"

	+ "State tax,Toll and parking charges if applicable to be paid extra.<br />"

	+ "Driver allowance is included in the fare. This includes driver's food and stay.<br />"
			+ "- Free KMs: 1250 KMs included in price\n" + "- Advance Payment to be made online - 20%<br />"
			+ "- Balance Payment to driver in cash - 80%<br />"
			+ "- Each day will be counted from midnight 12 to midnight 12<br />"
			+ "- Vehicle, fuel charges included in price<br />"

	+ "Outstation cabs should not be put to local usage in origin city.<br />"

	+ "The final amount will depend on actual travelled distance and days.<br />"

	+ "The total distance is only an estimate and the final bill will only depend on the net kilometres travelled.<br />"

	+ "For multi city travel the total distance includes the return distance to the origin city.";

	public static final String TYPE_RODER_DISPLAY_NAME = "Roder";

	public static final String TYPE_OLA_BRAND = "Ola";
	public static final String TYPE_UBER_BRAND = "Uber";
	public static final String TYPE_JUGNOO_BRAND = "Jugnoo";

	public static final String CAB_SEDAN_IMAGE_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/indigo.png";
	public static final String CAB_COMPACT_IMAGE_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/indica.png";
	public static final String CAB_LUXURY_IMAGE_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/innova.png";
	public static final String BIKE_IMAGE_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/logo/auto/jugnoo.png";
	public static final String AUTO_IMAGE_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/logo/auto/jugnoo.png";
	public static final String CAB_SUV_IMAGE_URL = "https://s3-ap-southeast-1.amazonaws.com/zimage.com/cab_icons/innova.png";

	// type jugnoo
	public static final String TYPE_JUGNOO_BUSINESS_ID = "1016";
	public static final String TYPE_JUGNOO_BUSINESS_TOKEN = "44073c56dfa4215e47580a313f6c5436a85319a8b644bed4151397aad7074376";
	public static final String TYPE_JUGNOO_TEST_URL = "https://test.jugnoo.in:7012";
	public static final String TYPE_JUGNOO_LIVE_BUSINESS_TOKEN = "a5b4f3270505634a15dd693bcdcdcf99e8f7123d59471432d4a5c9d8fc20ce80";
	public static final String TYPE_JUGNOO_LIVE_URL = "https://prod-opr.jugnoo.in:4013";

	// default user details for MegaCabs
	public static final String TYPE_MEGA_CLIENT_CODE = "ZAPLON";
	public static final String TYPE_MEGA_PASS_CODE = "ZAPLON_123";
	public static final String TYPE_MEGA_OPR_VALUE = "opr";
	public static final String TYPE_MEGA_CIRCLE_VALUE = "circl";

	// Fare details for MegaCabs
	public static final String TYPE_MEGA_BASE_FARE = "69";
	public static final String TYPE_MEGA_RATE_PER_KM = "23";
	public static final String TYPE_MEGA_NIGHT_START_TIME = "23";
	public static final String TYPE_MEGA_NIGHT_END_TIME = "5";

	// type ridz
	public static final String TYPE_RIDZ_SERVER_URL = "http://api.ridz.in:4522/testapi/public/api/v1/quik/";
	public static final String TYPE_RIDZ_MID = "$2y$10$RQmSk2WZzokdewgQHA/YAW7.mKLqDbrVimc/HdjNgjyngyhSXkWM";
	public static final String TYPE_RIDZ_PWD = "$2y$10$RQo7dObghty56bgh7u6AmSk2WZQHA/YAW7.mcm0Vimc/ngh56fgh56fgh4fy";
	public static final String TYPE_RIDZ_DISPLAY_NAME = "Ridz";
	public static final int TYPE_RIDZ_MINI = 1;
	public static final int TYPE_RIDZ_SEDAN = 2;

	// AhaTaxis credentials
	public static final String TYPE_AHA_API_KEY = "zapplonOutstationCab";
	public static final String TYPE_AHA_AVAILABILITY_KEY = "klfsMrtf5767#hawqj@P";
	public static final String TYPE_AHA_BOOKING_KEY = "4wovc@SJghj956#ewvs1";
	public static final String TYPE_AHA_CANCELLATION_KEY = "DFhj13@ghyb86#hhtf";
	public static final String TYPE_AHA_SERVER_URL = "https://ahataxis.com/";
	public static final String TYPE_AHA_TEST_SERVER_URL = "http://dev.ahataxis.com/";

	public static final int TYPE_AHA_BUSINESS = 41;
	public static final int TYPE_AHA_PREMIUM = 42;
	public static final int TYPE_AHA_ECONOMY = 43;
	public static final int TYPE_MYTAXI_INDIGO = 44;
	public static final int TYPE_MYTAXI_INNOVA = 45;
	public static final int TYPE_MYTAXI_TEMPO_12 = 46;
	public static final int TYPE_MYTAXI_TEMPO_15 = 47;
	public static final int TYPE_RODER_SUV_7 = 48;
	public static final int TYPE_RODER_SUV_8 = 49;
	public static final int TYPE_RODER_SEDAN = 50;
	public static final int TYPE_RODER_HATCHBACK = 51;
	public static final int TYPE_GETMECAB_SEDAN = 52;
	public static final int TYPE_GETMECAB_SUV = 53;
	public static final int TYPE_SAVAARI_INDIGO = 56;
	public static final int TYPE_SAVAARI_INDICA = 57;

	// Payment creds
	public static final String TYPE_ZAPPLON_PAYMENT_URL = "https://test.payu.in/_payment"; // live:
																							// https://secure.payu.in/_payment,
																							// dev:https://test.payu.in/_payment
	public static final String ZAPPLON_PAYMENT_KEY = "dRQuiA"; // live:
																// 3s6sTaSM,
																// dev: fB7m8s
	public static final String ZAPPLON_PAYMENT_SALT = "teEkuVg2"; // live:
																	// 1ec1B3P2r7,
																	// dev:
																	// eRis5Chv
	public static final String ZAPPLON_PAYMENT_MERCHANT_ID = "4928174"; // live:
																		// 5611077,
																		// dev:4928174
	public static final String ZAPPLON_PAYMENT_SUCCESS_URL = "http://zapplon.com/intercity/";
	public static final String ZAPPLON_PAYMENT_FAILURE_URL = "http://zapplon.com/intercity/";

	public static final String TYPE_AHA_PAYMENT_URL = "https://secure.payu.in/_payment";
	public static final String TYPE_AHA_TEST_PAYMENT_URL = "https://test.payu.in/_payment";

	public static final int TYPE_PAYMENT_PENDING = 21;
	public static final int TYPE_PAYMENT_FAILED = 22;
	public static final int TYPE_PAYMENT_DONE = 23;

	// MyTaxiIndia
	public static final String TYPE_MYTAXI_DISPLAY_NAME = "MyTaxiIndia";
	public static final String TYPE_MYTAXI_SERVER_URL = "http://api.mytaxiindia.com/";
	public static final String TYPE_MYTAXI_TEST_SERVER_URL = "http://52.33.249.28/";
	public static final String TYPE_MYTAXI_USERID = "shiwang@zapplon.com";
	public static final String TYPE_MYTAXI_PASSWORD = "9958835333";
	public static final String TYPE_MYTAXI_MID = "47";
	public static final String TYPE_MYTAXI_AUTH = "eb09b71df542b0729d5f2255848b6675cf133306c8b73306902156c8b0f29bba0fd944ddf2ddfa886645536e38590bc442d17404d3169d42b77938ff171e71ac";

	// GetMeCab
	public static final String TYPE_GETMECAB_DISPLAY_NAME = "GetMeCab";
	public static final String TYPE_GETMECAB_SERVER_URL = "http://52.74.19.64:85/";
	public static final String TYPE_GETMECAB_USERID = "apoorv@zapplon.com";
	public static final String TYPE_GETMECAB_PASSWORD = "zapplon1!";
	public static final String TYPE_GETMECAB_TEST_USERID = "sumed@getmecab.com";
	public static final String TYPE_GETMECAB_TEST_PASSWORD = "sumed123";

	// Roder credentials
	public static final String TYPE_RODER_TOKEN = "9c3005e1d250677c46c4dbf05b8f84f08e164c14";

	// Savaari
	public static final String TYPE_SAVAARI_SERVER_URL = "http://api.betasavaari.com/partner_client/";
	public static final String TYPE_SAVAARI_TOKEN = "";

	// public static final int BILLING_STATUS_WISHED = 101;
	// public static final int BILLING_STATUS_BILLED = 102;

	// billing status
	public static final int BILLING_STATUS_BOOKED = 101;
	public static final int BILLING_STATUS_VERIFIED = 102;
	public static final int BILLING_STATUS_RATED = 103;

	// voucher status
	public static final int VOUCHER_STATUS_ACTIVE = 1;
	public static final int VOUCHER_STATUS_INACTIVE = 0;

	// TABLE BOOKING
	public static final int BILLING_STATUS_TABLE_BOOKED = 201;
	public static final int BILLING_STATUS_MERCHANT_ACTION_TAKEN = 202;
	public static final int BILLING_STATUS_USER_RATED = 204;
	public static final int TABLE_BOOKING_ACTION_ACCEPT = 205;
	public static final int TABLE_BOOKING_ACTION_REJECT = 206;

	public static final int DEAL_THREE_COUNT = 2;

	public static final int RESPONSE_FAILURE = 200;
	public static final int RESPONSE_SUCCESS = 201;
	public static final int RESPONSE_INVALID_USER = 202;
	public static final int RESPONSE_INVALID_PARAMS = 203;
	public static final int RESPONSE_INVALID_APP_TYPE = 204;
	public static final int RESPONSE_INVALID_CLIENT_ID = 205;
	public static final int RESPONSE_INVALID_MERCHANT = 206;

	public static final int STATUS_DELETED = 0;
	public static final int STATUS_ACTIVE = 1;

	public static final int STATUS_UNAVAILABLE = 0;
	public static final int STATUS_AVAILABLE = 1;

	public static final int TYPE_MERCHANT = 2;
	public static final int TYPE_USER = 1;

	public static final int MESSAGE_STATUS_INACTIVE = 0;
	public static final int MESSAGE_STATUS_ARCHIVE_FROM = 1;
	public static final int MESSAGE_STATUS_ARCHIVE_TO = 2;
	public static final int MESSAGE_STATUS_ACTIVE = 3;

	public static final int STORE_ITEM_STATUS_ACTIVE = 1;
	public static final int STORE_ITEM_STATUS_ARCHIVE = 2;
	public static final int STORE_ITEM_STATUS_DE_PRIORITIZED = 3;

	public static final int STORE_ITEM_ACTION_ADD = 101;
	public static final int STORE_ITEM_ACTION_EDIT = 102;
	public static final int STORE_ITEM_ACTION_DELETE = 103;

	/**
	 * Different store types
	 */
	public static final int STORE_TYPE_SALON_SPA = 1;
	public static final int STORE_TYPE_RESTAURANT = 2;
	public static final int STORE_TYPE_SPA = 3;

	public static final String UBER_CLIENT_ID = "YA36hEXTQ3o4SVWI9yADTXv9wb2J0GyQ";
	public static final String UBER_GRANT_TYPE = "authorization_code";
	public static final String UBER_CLIENT_SECRET = "WyinwR4ZQgwXyQgYMpLj9jwkvEcKaiC2uLxHWUT5";
	public static final String UBER_SERVER = "https://api.uber.com/";
	public static final String UBER_SERVER_TOKEN = "VexZiPPs0esJHZmGRBYtw9ZwtJZYfChTNXsAMdIa";

	public static final String OLA_SERVER = "https://devapi.olacabs.com/";
	public static final String OLA_X_APP_TOKEN = "ee54f086ee2f4ea8a62baa338a6ab8f4";
	public static final String OLA_AUTH_URL = "https://devapi.olacabs.com/oauth2/authorize?response_type=token&client_id=ODA4OGRkZDUtM2EzZS00YWQ5LTlmOTktNDI2MTg1NTU1YTQz&redirect_uri=http://zapplon.com/&scope=profile%20booking&state=state123";

	public static final String EASY_SERVER = "http://tms.easycabs.com:8084/service.asmx";
	public static final String EASY_USER_ID = "Zapplon";
	public static final String EASY_PASSWD = "Zapplon@123#";

	public static final String JUGNOO_SERVER = "https://dev.jugnoo.in:4012/";
	public static final String JUGNOO_ACCESS_TOKEN = "a5b4f3270505634a15dd693bcdcdcf99e8f7123d59471432d4a5c9d8fc20ce7f";

	public static final String RODER_SERVER = "https://api.roder.in/";

	/**
	 * Push notifiation types
	 */
	public static final String MERCHANT_GCM_TABLE_BOOKING = "merchant_gcm_table_booking";

	/**
	 * Android API constants
	 */
	public static String projectId = "996855199819"; 
	public static String apiKey = "AIzaSyBzi76XfJsZGSk-jHY0Wgr2XHecirwTiJY	";
	public static final String GCM_SERVER = "gcm.googleapis.com";
	public static final int GCM_PORT = 5235;
	public static final String GCM_ELEMENT_NAME = "gcm";
	public static final String GCM_NAMESPACE = "google:mobile:data";

	public static final int ACTION_ACCEPT_WISH = 1;
	public static final int ACTION_DECLINE_WISH = 2;

	public static final int CURRENT_USER_WISH_ACCEPTED = 1;
	public static final int WISH_ACCEPTED_CURRENT_USER = 2;

	public static final int ADDRESS_TYPE_HOME = 201;
	public static final int ADDRESS_TYPE_WORK = 202;

	public static JSONObject getResponseString(Object responseJson, String errorMessage, int status) {
		JSONObject responseObject = new JSONObject();
		switch (status) {
		case RESPONSE_SUCCESS:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "success");
				responseObject.put("errorCode", "0");
				responseObject.put("errorMessage", "");
			} catch (JSONException e) {
				try {
					throw new ZException("Error", e);
				} catch (ZException e1) {
					e1.printStackTrace();
				}
			}
			break;

		case RESPONSE_INVALID_USER:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "failure");
				responseObject.put("errorCode", RESPONSE_INVALID_USER);
				responseObject.put("errorMessage", errorMessage);
			} catch (JSONException e) {
				try {
					throw new ZException("Error", e);
				} catch (ZException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case RESPONSE_INVALID_PARAMS:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "failure");
				responseObject.put("errorCode", RESPONSE_INVALID_PARAMS);
				responseObject.put("errorMessage", errorMessage);
			} catch (JSONException e) {
				try {
					throw new ZException("Error", e);
				} catch (ZException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case RESPONSE_INVALID_APP_TYPE:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "failure");
				responseObject.put("errorCode", RESPONSE_INVALID_APP_TYPE);
				responseObject.put("errorMessage", errorMessage);
			} catch (JSONException e) {
				try {
					throw new ZException("Error", e);
				} catch (ZException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case RESPONSE_INVALID_CLIENT_ID:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "failure");
				responseObject.put("errorCode", RESPONSE_INVALID_CLIENT_ID);
				responseObject.put("errorMessage", errorMessage);
			} catch (JSONException e) {
				try {
					throw new ZException("Error", e);
				} catch (ZException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case RESPONSE_FAILURE:
			try {
				responseObject.put("response", responseJson);
				responseObject.put("status", "failure");
				responseObject.put("errorCode", RESPONSE_FAILURE);
				responseObject.put("errorMessage", errorMessage);
			} catch (JSONException e) {
				try {
					throw new ZException("Error", e);
				} catch (ZException e1) {
					e1.printStackTrace();
				}
			}
			break;
		default:
			break;
		}

		return responseObject;
	}

	public static String getIntercityCabType(int cabType) {
		String taxiType = "";
		if (cabType == CommonLib.TYPE_AHA_BUSINESS) {
			taxiType = "AHABusiness";
		} else if (cabType == CommonLib.TYPE_AHA_PREMIUM) {
			taxiType = "AHAPremium";
		} else if (cabType == CommonLib.TYPE_AHA_ECONOMY) {
			taxiType = "AHAEconomy";
		} else if (cabType == CommonLib.TYPE_MYTAXI_INDIGO) {
			taxiType = "MTI INDIGO_OR_EQUIVALENT";
		} else if (cabType == CommonLib.TYPE_MYTAXI_INNOVA) {
			taxiType = "MTI INNOVA_OR_EQUIVALENT";
		} else if (cabType == CommonLib.TYPE_MYTAXI_TEMPO_12) {
			taxiType = "MTI TEMPO_TRAVELER_12_SEATER";
		} else if (cabType == CommonLib.TYPE_MYTAXI_TEMPO_15) {
			taxiType = "MTI TEMPO_TRAVELER_15_SEATER";
		} else if (cabType == CommonLib.TYPE_RODER_SUV_7) {
			taxiType = "Roder TYPE_RODER_SUV_7";
		} else if (cabType == CommonLib.TYPE_RODER_SUV_8) {
			taxiType = "Roder TYPE_RODER_SUV_8";
		} else if (cabType == CommonLib.TYPE_RODER_SEDAN) {
			taxiType = "Roder TYPE_RODER_SEDAN";
		} else if (cabType == CommonLib.TYPE_RODER_HATCHBACK) {
			taxiType = "Roder TYPE_RODER_HATCHBACK";
		} else if (cabType == CommonLib.TYPE_GETMECAB_SEDAN) {
			taxiType = "Getmecab TYPE_GETMECAB_SEDAN";
		} else if (cabType == CommonLib.TYPE_GETMECAB_SUV) {
			taxiType = "Getmecab TYPE_GETMECAB_SUV";
		} else if (cabType == CommonLib.TYPE_SAVAARI_INDIGO) {
			taxiType = "Savaari TYPE_SAVAARI_INDIGO";
		} else if (cabType == CommonLib.TYPE_SAVAARI_INDICA) {
			taxiType = "Savaari TYPE_SAVAARI_INDICA";
		}

		return taxiType;
	}

	public static String getUserKey() {
		String key = "null";
		try {
			key = UUID.randomUUID().toString().substring(0, 5);
		} catch (Exception e) {
			try {
				throw new ZException("Error", e);
			} catch (ZException e1) {
				e1.printStackTrace();
			}
		}
		return key;
	}

	public static String getMerchantPin() {
		String pin = "null";
		try {
			pin = UUID.randomUUID().toString().substring(0, 5);
		} catch (Exception e) {
			try {
				throw new ZException("Error", e);
			} catch (ZException e1) {
				e1.printStackTrace();
			}
		}
		return pin;
	}

	public static final String welcomeEmailTextFirst = "Hello Sir,\n" + "Greetings from Zapplon !\n\n"
			+ "We welcome you onboard on Zapplon. \n";
	public static final String welcomeEmailSecond = "\n Please download the application from the url : https://play.google.com/store/apps/details?id=com.application.zapplonmerchant";

	public static final String welcomeEmailThird = "\nZapplon is fully committed to drive your sales and increase revenues by giving you a platform to connect with on demand customers taking into account your yield and demand and supply conditions.\n"
			+ "Zapplon also takes care of your marketing to all the nearby online customers by giving you an effective visibility on various channels and social media platforms.\n"
			+ "You can also market your new and slow moving products to get more customers in real time.\n"
			+ "So why wait, start using Zapplon and drive up your sales in a Zapp !!!\n"
			+ "PS:- The services on Zapplon are free for first 3 months. Terms and conditions apply.\n"
			+ "Regards,\n Apoorv Arora\nCo Founder\nZapplon";

	/**
	 * Algorithm to compute the current discount based on the parameters Maximum
	 * Discount Minimum Discount Current people in the store Maximum occupancy
	 * of the store
	 */
	public static int getCurrentDiscountAmount(int maximumDiscount, int mimnimumDiscount, int currentCrowd,
			int maximumOccupancy) {
		return mimnimumDiscount
				+ (((maximumOccupancy - currentCrowd) * (maximumDiscount - mimnimumDiscount)) / maximumOccupancy);
	}

	public static final String otpText = " is your Zapplon verification code.";

	public static String getOTP() {
		return (int) (Math.random() * 5000) + 1000 + "";
	}

	public static String getCabCompanyName(int type) {
		String retValue;
		switch (type) {
		case CommonLib.TYPE_OLA:
			retValue = "OLA";
			break;
		case CommonLib.TYPE_UBER:
			retValue = "uber";
			break;
		case CommonLib.TYPE_JUGNOO:
			retValue = "Jugnoo";
			break;
		case CommonLib.TYPE_MEGA:
			retValue = "Mega";
			break;
		case CommonLib.TYPE_EASY:
			retValue = "Easy";
			break;
		case CommonLib.TYPE_AHA:
			retValue = "Aha";
			break;

		default:
			retValue = "";
		}
		return retValue;
	}

	public static String getUserName(User user) {
		if (user == null)
			return "";
		String userName = "";
		if (user.getUserName() == null || user.getUserName().equals("") || user.getUserName().equals("null")) {
			try {
				JSONObject data = new JSONObject(user.getFacebookData());
				if (data.has("name")) {
					String name = String.valueOf(data.get("name"));
					name = name.split(" ")[0];
					userName = name;
				}
			} catch (JSONException e) {
				try {
					throw new ZException("Error", e);
				} catch (ZException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				try {
					throw new ZException("Error", e);
				} catch (ZException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			String name = user.getUserName();
			name = name.split(" ")[0];
			userName = name;
		}
		return userName;
	}

	static ArrayList<Branch> branches = null;

	/** Method used to read contents of html/text/XML file */
	public static String readFile(InputStream stream) {
		// NB: does not close inputStream, you can use IOUtils.closeQuietly for
		// that
		java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";

	}

	public static Double deg2rad(Double deg) {
		return deg * (Math.PI / 180);
	}

	public static int getTimeEstimate(double distance) {

		double speed = 40;// in km/hr
		double time = (distance / speed) * 60 * 60;

		return (int) Math.ceil(time);
	}

	public static int getTimeEstimate(double startLatitude, double startLongitude, double endLatitude,
			double endLongitude) {

		double latitudeDiff = deg2rad(Math.abs(endLatitude - startLatitude));
		double longitudeDiff = deg2rad(Math.abs(endLongitude - startLongitude));

		double A = (Math.pow(Math.sin(latitudeDiff / 2), 2) + Math.cos(deg2rad(startLatitude))
				* Math.cos(deg2rad(endLatitude)) * Math.pow(Math.sin(longitudeDiff / 2), 2));

		double C = 2 * Math.atan2(Math.sqrt(A), Math.sqrt(1 - A));

		double distance = 6371 * C;

		double speed = 40;
		double time = (distance / speed) * 60 * 60;
		return (int) Math.ceil(time);
	}

	public static double getDistanceFromLatLong(double startLatitude, double startLongitude, double endLatitude,
			double endLongitude) {

		double latitudeDiff = deg2rad(Math.abs(endLatitude - startLatitude));
		double longitudeDiff = deg2rad(Math.abs(endLongitude - startLongitude));

		double A = (Math.pow(Math.sin(latitudeDiff / 2), 2) + Math.cos(deg2rad(startLatitude))
				* Math.cos(deg2rad(endLatitude)) * Math.pow(Math.sin(longitudeDiff / 2), 2));

		double C = 2 * Math.atan2(Math.sqrt(A), Math.sqrt(1 - A));

		double distance = 6371 * C;

		return distance;
	}

	public static String generateHash(int userId, String bookingId) {
		return (userId + "" + bookingId + System.currentTimeMillis()).hashCode() + "";
	}

	public static String hashCal(String str) {
		byte[] hashseq = str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();

			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1)
					hexString.append("0");
				hexString.append(hex);
			}

		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}

		return hexString.toString();
	}

	public static String getHiddenFieldForm(String name, String value) {
		return "<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\" />";
	}

	public static String getDistanceQuery(double myLatitude, String locationLatitude, double myLongitude,
			String locationLongitude) {
		return "3956 * 2 * ASIN(SQRT(POWER(SIN((" + myLatitude + " - " + locationLatitude
				+ ") * pi()/180 / 2), 2) + COS(" + myLatitude + " * pi()/180) * COS(" + locationLatitude
				+ " * pi()/180) * POWER(SIN((" + myLongitude + " - " + locationLongitude + ") * pi()/180 / 2), 2)))";
	}

}
