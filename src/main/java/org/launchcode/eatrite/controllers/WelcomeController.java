package org.launchcode.eatrite.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public String welcome(Model model) {
		List<Food> food = foodDao.findAllByOrderByName();
		model.addAttribute("food", food);
		
		return "home";
	}
	
	@RequestMapping(value = "/addfood", method = RequestMethod.GET)
	public String addFoodForm() {
		return "addfood";
	}
	
	@RequestMapping(value = "/food/{foodName}", method = RequestMethod.GET)
	public String singleFood(@PathVariable String foodName, Model model) {
		
		Food food = foodDao.findByName(foodName);
		model.addAttribute(food);
		
		return "singlefood";
	}
	
	@RequestMapping(value = "/food/{foodName}", method = RequestMethod.POST)
	public String postSingleFood(HttpServletRequest request, Model model) {
		
		Integer foodId = Integer.valueOf(request.getParameter("foodId"));
		Food food = foodDao.findByUid(foodId);
		Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userSessionKey);
		User user;
		
		if (userId != null) {
        	user = userDao.findByUid(userId);
        	if (user != null) {
        		JournalEntry je = new JournalEntry(user, food);
        		journalEntryDao.save(je);
        	}
        } else {
        	model.addAttribute(food);
        	model.addAttribute("error", "Must be logged in to add");
        	return "singlefood";
        }
		
		return "redirect:/myjournal";
	}
	
	@RequestMapping(value = "/addfood", method = RequestMethod.POST)
	public String addFood(HttpServletRequest request, Model model) {

		//validate input
		boolean hasError = false;
		Pattern validDoublePattern = Pattern.compile("[0-9]+");
		Matcher calorieMatcher = validDoublePattern.matcher(request.getParameter("calories"));
		Matcher fatsMatcher = validDoublePattern.matcher(request.getParameter("fats"));
		Matcher carbohydratesMatcher = validDoublePattern.matcher(request.getParameter("carbohydrates"));
		Matcher proteinsMatcher = validDoublePattern.matcher(request.getParameter("proteins"));
		
		String foodname = request.getParameter("foodname");
		List<Food> food = foodDao.findAll();
		for (Food f : food){
			if (f.getName().equals(foodname)){
				hasError = true;
				model.addAttribute("foodname_error", "Food already exists");
			}
		}
		
		if (!calorieMatcher.matches()) {
			hasError = true;
			model.addAttribute("calorie_error", "Calories must be a number");
		}
		
		if (!fatsMatcher.matches()) {
			hasError = true;
			model.addAttribute("fats_error", "Fats must be a number");
		}
		
		if (!carbohydratesMatcher.matches()) {
			hasError = true;
			model.addAttribute("carbohydrates_error", "Carbohydrates must be a number");
		}
		
		if (!proteinsMatcher.matches()) {
			hasError = true;
			model.addAttribute("proteins_error", "Proteins must be a number");
		}
		
		//if invalid input reload page with errors
		if (hasError == true)
			return "addfood";
		
		//store validated input
		double calories = Double.valueOf(request.getParameter("calories"));
		double fats = Double.valueOf(request.getParameter("fats"));
		double carbohydrates = Double.valueOf(request.getParameter("carbohydrates"));
		double proteins = Double.valueOf(request.getParameter("proteins"));
		
		//save data entry
		Food f = new Food(foodname, calories, fats, carbohydrates, proteins);
		foodDao.save(f);
			
		return "redirect:/";
	}
	
}
