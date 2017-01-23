package org.launchcode.eatrite.controllers;

import javax.servlet.http.HttpSession;

import org.launchcode.eatrite.models.User;
import org.launchcode.eatrite.models.dao.FoodDao;
import org.launchcode.eatrite.models.dao.JournalEntryDao;
import org.launchcode.eatrite.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

	@Autowired
    protected UserDao userDao;
	
	@Autowired
	protected JournalEntryDao journalEntryDao;
	
	@Autowired
	protected FoodDao foodDao;
	
	public static final String userSessionKey = "user_id";
	
	protected User getUserFromSession(HttpSession session) {
    	
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findByUid(userId);
    }
    
    protected void setUserInSession(HttpSession session, User user) {
    	session.setAttribute(userSessionKey, user.getUid());
    }
}
