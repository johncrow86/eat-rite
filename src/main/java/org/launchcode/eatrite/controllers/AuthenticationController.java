package org.launchcode.eatrite.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.eatrite.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		
		boolean hasError = false;
		
		if (!User.isValidUsername(username)){
			hasError = true;
			model.addAttribute("username_error", "Invalid Username");
		}

		List<User> users = userDao.findAll();
		for (User u : users){
			if (u.getUsername().equals(username)){
				hasError = true;
				model.addAttribute("username_error", "Username already exists");
			}
		}
		
		if (!User.isValidPassword(password)){
			hasError = true;
			model.addAttribute("password_error", "Invalid Password");
		} else if (!password.equals(verify)){
			hasError = true;
			model.addAttribute("verify_error", "Passwords do not match");
		}
		
		if (hasError == true)
			return "signup";
		
		User u = new User(username, password);
		userDao.save(u);
		setUserInSession(request.getSession(), u);
			
;		return "redirect:home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User u = userDao.findByUsername(username);
		
		if (u == null){
			model.addAttribute("error", "User does not exist");
			return "login";
		} else if (!u.isMatchingPassword(password)){
			model.addAttribute("error", "Incorrect Password");
			return "login";
		}
		
		setUserInSession(request.getSession(), u);
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
