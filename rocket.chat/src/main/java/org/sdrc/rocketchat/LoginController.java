package org.sdrc.rocketchat;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.sdrc.rocketchat.model.UserModel;
import org.sdrc.rocketchat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController{

	@Autowired
	private UserService userService;
	
	
	/*@ResponseBody
	@RequestMapping(value="/login", method = RequestMethod.POST,produces="text/plain")
	public String login(HttpServletRequest request, RedirectAttributes redirectAttributes,@RequestParam("username")String username, @RequestParam("pwd")String pwd) {
		List<UserModel> users=userService.getUserList();
		String status="failed";
		for (UserModel userModel : users) {
			if(userModel.getUserName().equals(username) && userModel.getPassword().equals(pwd)) {
				status =  username;
			}
		}
		
		return status;
	}*/
}
