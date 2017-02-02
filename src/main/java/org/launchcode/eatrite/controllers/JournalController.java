package org.launchcode.eatrite.controllers;

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
		
		List<JournalEntry> journal = journalEntryDao.findByOwner(getUserFromSession(request.getSession()));
		model.addAttribute("journal", journal);
		
		return "myjournal";
	}
	
	@RequestMapping(value = "/myjournal", method = RequestMethod.POST)
	public String myJournal(HttpServletRequest request, Model model) {
		
		String entry = request.getParameter("entry");
		double calories = Double.valueOf(request.getParameter("calories"));
		double fats = Double.valueOf(request.getParameter("fats"));
		double carbohydrates = Double.valueOf(request.getParameter("carbohydrates"));
		double proteins = Double.valueOf(request.getParameter("proteins"));
		
		List<JournalEntry> journal = journalEntryDao.findByOwner(getUserFromSession(request.getSession()));
		model.addAttribute("journal", journal);
		
		JournalEntry je = new JournalEntry(getUserFromSession(request.getSession()), entry, calories, fats, carbohydrates, proteins);
		journalEntryDao.save(je);
		
		return "redirect:myjournal";
	}
	
	@RequestMapping(value = "/myjournal/{createdString}", method = RequestMethod.GET)
	public String myJournalCreatedForm(@PathVariable String createdString, HttpServletRequest request, Model model) {
		
		List<JournalEntry> journal = journalEntryDao.findByOwnerAndCreatedString(getUserFromSession(request.getSession()), createdString);
		model.addAttribute("journal", journal);
		
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
		
		return "myjournal";
	}
	
}
