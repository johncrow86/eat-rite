package org.launchcode.eatrite.models.dao;

import java.util.List;

import org.launchcode.eatrite.models.JournalEntry;
import org.launchcode.eatrite.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface JournalEntryDao extends CrudRepository<JournalEntry, Integer> {
	
	List<JournalEntry> findByOwner(User user);
	List<JournalEntry> findAll();

}