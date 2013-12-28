package de.canberk.uni.cd_aap;

public class User {

	private int id;
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;

	public User(int id, String firstname, String lastname, String username,
			String email, String password) {
		setId(id);
		setFirstname(firstname);
		setLastname(lastname);
		setUsername(username);
		setEmail(email);
		setPassword(password);
	}
	
	public User(String firstname, String lastname, String username,
			String email, String password) {
		setFirstname(firstname);
		setLastname(lastname);
		setUsername(username);
		setEmail(email);
		setPassword(password);
	}

	public User(String email, String password) {
		setEmail(email);
		setPassword(password);
	}

	public User() {

	}

	// getters and setters
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("ID: " + getId() + "\n");
		sb.append("First name: " + getFirstname() + "\n");
		sb.append("Last name: " + getLastname() + "\n");
		sb.append("User name: " + getUsername() + "\n");
		sb.append("E-mail address: " + getEmail() + "\n");
		sb.append("Password: " + getPassword() + "\n");
		
		return sb.toString();
	}

}