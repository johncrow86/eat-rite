package org.launchcode.eatrite.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.eatrite.models.Food;
import org.launchcode.eatrite.models.JournalEntry;
import org.launchcode.eatrite.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController extends AbstractController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(){
		return "home";
	}
	
	@RequestMapping(value = "/addfood", method = RequestMethod.GET)
	public String addFoodForm(){
		return "addfood";
	}
	
	@RequestMapping(value = "/addfood", method = RequestMethod.POST)
	public String addFood(HttpServletRequest request, Model model) {

		String foodname = request.getParameter("foodname");
		double calories = Double.valueOf(request.getParameter("calories"));
		double fats = Double.valueOf(request.getParameter("fats"));
		double carbohydrates = Double.valueOf(request.getParameter("carbohydrates"));
		double proteins = Double.valueOf(request.getParameter("proteins"));
		
		boolean hasError = false;

		List<Food> food = foodDao.findAll();
		
		for (Food f : food){
			if (f.getName().equals(foodname)){
				hasError = true;
				model.addAttribute("foodname_error", "Food already exists");
			}
		}
		
		if (hasError == true)
			return "addfood";
		
		Food f = new Food(foodname, calories, fats, carbohydrates, proteins);
		foodDao.save(f);
			
		return "redirect:/";
	}
	
}
