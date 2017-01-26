package org.launchcode.eatrite.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.launchcode.eatrite.models.User;

@Entity
@Table(name = "journalentry")
public class JournalEntry extends AbstractEntity{
	
	private String name;
	private double calories;
	private double fats;
	private double carbohydrates;
	private double proteins;
	private User owner;
	private Date created;
	private String createdString;
	
	private DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	
	public JournalEntry() {}
	
	public JournalEntry(User owner, String name, double calories, double fats, double carbohydrates, double proteins) {
		
		super();
		
		this.owner = owner;
		this.name = name;
		this.calories = calories;
		this.fats = fats;
		this.carbohydrates = carbohydrates;
		this.proteins = proteins;
		this.created = new Date();
		this.createdString = df.format(created);
		
		owner.addJournalEntry(this);
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "calories")
	public double getCalories() {
		return calories;
	}
	
	public void setCalories(double cals) {
		this.calories = cals;
	}
	
	@Column(name = "fats")
	public double getFats() {
		return fats;
	}
	
	public void setFats(double fats) {
		this.fats = fats;
	}
	
	@Column(name = "carbohydrates")
	public double getCarbohydrates() {
		return carbohydrates;
	}
	
	public void setCarbohydrates(double carbs) {
		this.carbohydrates = carbs;
	}
	
	@Column(name = "proteins")
	public double getProteins() {
		return proteins;
	}
	
	public void setProteins(double prot) {
		this.proteins = prot;
	}
	
	@NotNull
	@OrderColumn
	@Column(name = "created")
	public Date getCreated() {
		return created;
	}
	
	@SuppressWarnings("unused")
	private void setCreated(Date created) {
		this.created = created;
	}
	
	@NotNull
	@Column(name = "createdString")
	public String getCreatedString() {
		return createdString;
	}
	
	@SuppressWarnings("unused")
	private void setCreatedString(String createdString) {
		this.createdString = createdString;
	}
	
	@ManyToOne
	public User getOwner() {
		return owner;
	}
	
	@SuppressWarnings("unused")
	private void setOwner(User owner) {
		this.owner = owner;
	}

}
