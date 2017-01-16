package org.launchcode.eatrite.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "food")
public class Food extends AbstractEntity{

	private String name;
	private double calories;
	private double fats;
	private double carbohydrates;
	private double proteins;
	
	public Food() {}
	
	public Food(String name, double calories, double fats, double carbohydrates, double proteins) {
		
		super();
		
		this.name = name;
		this.calories = calories;
		this.fats = fats;
		this.carbohydrates = carbohydrates;
		this.proteins = proteins;
		
	}
		
		@NotNull
	    @Column(name = "name", unique = true)
		public String getName() {
			return name;
		}

		@SuppressWarnings("unused")
		private void setName(String name) {
			this.name = name;
		}
		
		@NotNull
		@Column(name = "calories")
		public double getCalories() {
			return calories;
		}
		
		public void setCalories(double cals) {
			this.calories = cals;
		}
		
		@NotNull
		@Column(name = "fats")
		public double getFats() {
			return fats;
		}
		
		public void setFats(double fats) {
			this.fats = fats;
		}
		
		@NotNull
		@Column(name = "carbohydrates")
		public double getCarbohydrates() {
			return carbohydrates;
		}
		
		public void setCarbohydrates(double carbs) {
			this.carbohydrates = carbs;
		}
		
		@NotNull
		@Column(name = "proteins")
		public double getProteins() {
			return proteins;
		}
		
		public void setProteins(double prot) {
			this.proteins = prot;
		}
}
