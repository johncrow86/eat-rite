package org.launchcode.eatrite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController extends AbstractController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(){
		return "home";
	}
	
	@RequestMapping(value = "/addfood", method = RequestMethod.GET)
	public String addFood(){
		return "addfood";
	}
	
}
