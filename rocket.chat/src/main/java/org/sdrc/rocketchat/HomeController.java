package org.sdrc.rocketchat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.sdrc.rocketchat.model.RegisterUserModel;
import org.sdrc.rocketchat.model.UserModel;
import org.sdrc.rocketchat.services.UserService;
import org.sdrc.rocketchat.util.Constants;
import org.sdrc.rocketchat.util.StateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	private final String USER_AGENT = "Mozilla/5.0";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ServletContext context;
	
	private final StateManager stateManager;
	
	@Autowired
	public HomeController(StateManager stateManager){
		this.stateManager = stateManager;
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/","home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@ResponseBody
	@RequestMapping(value = "getUserStatus", method = RequestMethod.GET, produces = "application/json")
	public String getUserStatus(ServletResponse res)
			throws Exception {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		// read script file
		engine.eval(Files.newBufferedReader(Paths.get(context.getRealPath
				("/resources/js/token.js")), StandardCharsets.UTF_8));

		Invocable inv = (Invocable) engine;
		// call function from script file
		Object returnObj = inv.invokeFunction("getToken");
		System.out.println(returnObj);
//		String token = (String) stateManager.getValue(Constants.TOKEN);
		
		return returnObj.toString();

	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/plain")
	public String login(HttpServletRequest request,
			RedirectAttributes redirectAttributes,
			@RequestParam("username") String username,
			@RequestParam("pwd") String pwd) throws Exception {
		List<UserModel> users = userService.getUserList();
		String status = "failed";
		for (UserModel userModel : users) {
			if (userModel.getUserName().equals(username)
					&& userModel.getPassword().equals(pwd)) {
				status = username;
				stateManager.setValue(Constants.USER_PRINCIPAL, userModel);

//				(String) ((HashMap) json.get("data")).get("authToken")
				Object tokens = sendPost(username, pwd);
				Map<String, String> keyValMap = new HashMap<String, String>();
				keyValMap.put("loginToken", (String) ((HashMap) tokens).get("authToken"));
				keyValMap.put("userId", (String) ((HashMap) tokens).get("userId"));
				
				String authTokenJson = new ObjectMapper().writeValueAsString(keyValMap);
				stateManager.setValue(Constants.TOKEN, keyValMap.get("loginToken"));
				
//				String userIdJson = new ObjectMapper().writeValueAsString(keyValMap);
				stateManager.setValue(Constants.USERID, keyValMap.get("userId"));
				
//				request.setAttribute("token", json);
				return authTokenJson;
			}
		}

		return status;
	}

	// HTTP POST request
	@SuppressWarnings("restriction")
	private Object sendPost(String username, String pwd) throws Exception {

		String url = "http://192.168.1.95:3000/api/v1/login";
		URL obj = new URL(null, "http://192.168.1.95:3000/api/v1/login",
				new sun.net.www.protocol.http.Handler());
		;

		// String url = "http://localhost:3000/api/v1/login";
		// URL obj = new URL(null, "http://localhost:3000/api/v1/login", new
		// sun.net.www.protocol.http.Handler());;
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		// String urlParameters =
		// "username=biswabhusan.pradhan&password=pass@123";
		String urlParameters = "username=" + username + "&password=" + pwd;

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println("*****************************"
				+ response.toString() + "********************");
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(response.toString());

//		return (String) ((HashMap) json.get("data")).get("authToken");
		return json.get("data");
	}

	@RequestMapping(value = "/chatPage")
	public String getChatPage() {
		return "chat";
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestParam("name") String name,
			@RequestParam("username") String userName,
			@RequestParam("newemail") String email,
			@RequestParam("newpwd") String pwd) throws Exception {
		// System.out.println(userService.register(name,email,pwd).getUserId());
		userService.register(name, userName, email, pwd);
		
		String url = "http://192.168.1.95:3000/api/v1/users.register";
		RegisterUserModel ru = new RegisterUserModel();
		ru.setEmail(email);
		ru.setName(name);
		ru.setUsername(userName);
		ru.setPass(pwd);
		ru.setApplicationCode("demo");
		
//		String urlParameters = "username=" + userName+ "&email="+email
//				+ "&pass=" + pwd +"&name="+name;
		
		RestTemplate rs = new RestTemplate();
		ResponseEntity<String> response = rs.postForEntity(url, ru, String.class);
		HttpStatus status = response.getStatusCode();
		String restCall = response.getBody();
		System.out.println(status);
		return "success";
	}


	@RequestMapping(value = "/rocketChat")
	public String getRocketChat() {
		return "rocketChat";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse resp, 
			RedirectAttributes redirectAttributes) {
		HttpSession session=request.getSession(false);
		if(session !=null){
			stateManager.setValue(Constants.USER_PRINCIPAL, null);
			request.getSession().setAttribute(Constants.USER_PRINCIPAL, null);
			request.getSession().invalidate();
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			attr.getRequest().getSession(true).removeAttribute(Constants.USER_PRINCIPAL);
			attr.getRequest().getSession(true).invalidate();
	
			/*try {
				request.logout();
				
				HttpHeaders headers = new HttpHeaders();
				headers.add("X-Auth-Token", (String) stateManager.getValue(Constants.TOKEN));
				headers.add("X-User-Id", (String) stateManager.getValue(Constants.USERID));
				
				HttpEntity<String> entity = new HttpEntity<String>(headers);
				
				RestTemplate restTemplate = new RestTemplate();
				Object obj = restTemplate.postForObject
						("http://192.168.1.226:3000/api/v1/logout", entity, Object.class); 
				
//				ResponseEntity<Object> response = restTemplate.exchange
//						("http://192.168.1.226:3000/api/v1/logout", HttpMethod.GET, entity, Object.class);
				System.out.println(obj.toString());
			} catch (ServletException e) {
				e.printStackTrace();
			}*/
	
			List<String> errMessgs = new ArrayList<>();
			
			errMessgs.add("Successfully logged out!!");
			redirectAttributes.addFlashAttribute("formError", errMessgs);
			redirectAttributes.addFlashAttribute("className","alert alert-success");
			return "redirect:/";
		}
		else{
			request.getSession().invalidate();
			return "redirect:/";
		}
	}
}
