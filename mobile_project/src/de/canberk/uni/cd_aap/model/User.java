package de.canberk.uni.cd_aap.model;

import java.util.Calendar;
import java.util.Date;

public class User {

	private int id;
	private Date creationDate;
	private boolean deleted;
	private Date deletionDate;

	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;

	public User() {
		creationDate = new Date();
		setDeleted(false);
	}

	public User(String firstname, String lastname, String username,
			String email, String password) {
		creationDate = new Date();
		setFirstname(firstname);
		setLastname(lastname);
		setUsername(username);
		setEmail(email);
		setPassword(password);
		setDeleted(false);
	}

	public User(int id, String firstname, String lastname, String username,
			String email, String password) {
		setId(id);
		creationDate = new Date();
		setFirstname(firstname);
		setLastname(lastname);
		setUsername(username);
		setEmail(email);
		setPassword(password);
		setDeleted(false);
	}

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDateAsString() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(creationDate);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		return day + "." + month + "." + year;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("ID: " + getId() + "\n");
		sb.append("Creation date: " + getDateAsString() + "\n");
		sb.append("First name: " + getFirstname() + "\n");
		sb.append("Last name: " + getLastname() + "\n");
		sb.append("User name: " + getUsername() + "\n");
		sb.append("E-mail address: " + getEmail() + "\n");
		sb.append("Password: " + getPassword() + "\n");

		return sb.toString();
	}

}