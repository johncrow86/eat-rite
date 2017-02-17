package org.launchcode.eatrite.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.eatrite.models.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface FoodDao extends CrudRepository<Food, Integer> {

	Food findByUid(int uid);
	Food findByName(String name);
	
	List<Food> findAll();
	List<Food> findAllByOrderByName();
}
