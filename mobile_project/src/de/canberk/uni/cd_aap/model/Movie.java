package de.canberk.uni.cd_aap.model;

public class Movie extends Item {

	private String director;
	private String country;
	private int year;

	public Movie() {
		super();
	}

	public Movie(String title, String type, String genre, boolean favorite) {
		super(title, type, genre, favorite);
	}

	public Movie(int id, String title, String type, String genre,
			boolean favorite) {
		super(id, title, type, genre, favorite);
	}

	// getters and setters
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("ID: " + getId() + "\n");
		sb.append("Title: " + getTitle() + "\n");
		sb.append("Type: " + getType() + "\n");
		sb.append("Genre: " + getGenre() + "\n");
		sb.append("Favorite: " + isFavorite() + "\n");

		return sb.toString();
	}

}