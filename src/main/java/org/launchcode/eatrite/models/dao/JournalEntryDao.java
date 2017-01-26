package org.launchcode.eatrite.models.dao;

import java.util.List;

import org.launchcode.eatrite.models.JournalEntry;
import org.launchcode.eatrite.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface JournalEntryDao extends CrudRepository<JournalEntry, Integer> {
	
	List<JournalEntry> findByOwner(User user);
	List<JournalEntry> findAll();
	
	@Query(SELECT  u.userName FROM  User u 
			  INNER JOIN Area a ON a.idUser = u.idUser
			  WHERE
			  a.idArea = :idArea)
			List<User> findByIdarea(@Param("idArea") Long idArea);
	
	@Query("SELECT je FROM JournalEntry journal WHERE je.owner = :id AND a.id = :addressId")
		Employee findByIdAndAddress(@Param("id") Long id, @Param("addressId") Long addressId);

}