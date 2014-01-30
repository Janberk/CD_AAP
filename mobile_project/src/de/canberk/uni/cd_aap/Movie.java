package de.canberk.uni.cd_aap;

public class Movie {

	private int id;
	private String title;
	private String genre;

	public Movie(String title) {
		setTitle(title);
		setGenre("");
	}

	// getters and setters
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getGenre() {
		return this.genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
//	@Override
//	public String toString() {
//		StringBuffer sb = new StringBuffer();
//		
//		sb.append("ID: " + getId() + "\n");
//		sb.append("Title: " + getTitle() + "\n");
//		sb.append("Genre: " + getGenre() + "\n");
//		
//		return sb.toString();
//	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getTitle());
		
		return sb.toString();
	}

}
