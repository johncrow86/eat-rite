package org.launchcode.eatrite.models.dao;

import javax.transaction.Transactional;

import org.launchcode.eatrite.models.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface FoodDao extends CrudRepository<Food, Integer> {

	Food findByName(String name);
}
