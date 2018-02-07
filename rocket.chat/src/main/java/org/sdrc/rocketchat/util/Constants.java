package org.sdrc.rocketchat.util;

import org.springframework.stereotype.Component;

@Component
public class Constants {
	public static final String USER_PRINCIPAL = "UserPrincipal";
	public static final String TOKEN = "Token";
	public static final String REFERER = "referer";
	public static final String REDIRECT = "redirect:";
	public static final String USER_PROFILE_OBJECT = "userProfileServiceImpl";
	public static final String ERROR_LIST = "errorList";
	public static final String LOGIN_META_ID = "loginMetaId";
	public static final String USERID = "UserId";


	public static class RocketChatAPIs {
		public static final String LOGIN_REST_API_URL = "api/v1/login";
		public static final String LOGOUT_REST_API_URL = "api/v1/logout";
		public static final String REGISTER_REST_API_URL = "api/v1/users.register";

	}
}
