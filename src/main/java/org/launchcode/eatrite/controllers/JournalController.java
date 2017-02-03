package org.launchcode.eatrite.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.eatrite.models.JournalEntry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class JournalController extends AbstractController {

	@RequestMapping(value = "/myjournal", method = RequestMethod.GET)
	public String myJournalForm(HttpServletRequest request, Model model) {
		
		Date today = new Date();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		String todayString = df.format(today);
		
		List<JournalEntry> journal = journalEntryDao.findByOwnerAndCreatedString(getUserFromSession(request.getSession()), todayString);
		model.addAttribute("journal", journal);
		
		
		double[] macroTotals = totalMacros(journal);
		model.addAttribute("macroTotals", macroTotals);
		
		return "myjournal";
	}
	
	@RequestMapping(value = "/myjournal", method = RequestMethod.POST)
	public String myJournal(HttpServletRequest request, Model model) {
		
		String entry = request.getParameter("entry");
		double calories = Double.valueOf(request.getParameter("calories"));
		double fats = Double.valueOf(request.getParameter("fats"));
		double carbohydrates = Double.valueOf(request.getParameter("carbohydrates"));
		double proteins = Double.valueOf(request.getParameter("proteins"));
		
		JournalEntry je = new JournalEntry(getUserFromSession(request.getSession()), entry, calories, fats, carbohydrates, proteins);
		journalEntryDao.save(je);
		
		List<JournalEntry> journal = journalEntryDao.findByOwner(getUserFromSession(request.getSession()));
		model.addAttribute("journal", journal);
		
		double[] macroTotals = totalMacros(journal);
		model.addAttribute("macroTotals", macroTotals);
		
		return "myjournal";
	}
	
	@RequestMapping(value = "/myjournal/all", method = RequestMethod.GET)
	public String myJournalAll(HttpServletRequest request, Model model) {
		
		List<JournalEntry> journal = journalEntryDao.findByOwner(getUserFromSession(request.getSession()));
		model.addAttribute("journal", journal);
		
		
		double[] macroTotals = totalMacros(journal);
		model.addAttribute("macroTotals", macroTotals);
		
		return "myjournal";
	}
	
	@RequestMapping(value = "/myjournal/{createdString}", method = RequestMethod.GET)
	public String myJournalCreatedForm(@PathVariable String createdString, HttpServletRequest request, Model model) {
		
		List<JournalEntry> journal = journalEntryDao.findByOwnerAndCreatedString(getUserFromSession(request.getSession()), createdString);
		model.addAttribute("journal", journal);
		
		double[] macroTotals = totalMacros(journal);
		model.addAttribute("macroTotals", macroTotals);
		
		return "myjournal";
	}
	
	@RequestMapping(value = "/myjournal/{createdString}", method = RequestMethod.POST)
	public String myJournalCreated(HttpServletRequest request, Model model) {
		
		String day = request.getParameter("day");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		
		String createdString = month + "-" + day + "-" + year;
		
		List<JournalEntry> journal = journalEntryDao.findByOwnerAndCreatedString(getUserFromSession(request.getSession()), createdString);
		model.addAttribute("journal", journal);
		
		double[] macroTotals = totalMacros(journal);
		model.addAttribute("macroTotals", macroTotals);
		
		return "myjournal";
	}
	
	private double[] totalMacros (List<JournalEntry> journal) {

		double calories = 0;
		double fats = 0;
		double carbohydrates = 0;
		double proteins = 0;
		
		/*
		 * index[0] = calories
		 * index[1] = fats
		 * index[2] = carbohydrates
		 * index[3] = proteins
		 */
		double[] macroTotals = new double[4];
		
		for (JournalEntry je : journal) {
			calories += je.getCalories();
			fats += je.getFats();
			carbohydrates += je.getCarbohydrates();
			proteins += je.getProteins();
		}
		
		macroTotals[0] = calories;
		macroTotals[1] = fats;
		macroTotals[2] = carbohydrates;
		macroTotals[3] = proteins;
		
		return macroTotals;
	}
	
}
