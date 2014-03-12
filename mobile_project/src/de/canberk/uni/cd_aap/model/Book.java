package de.canberk.uni.cd_aap.model;

public class Book extends Item {

	private String author;

	public Book() {
		super();
	}

	public Book(String title, String type, String genre, boolean favorite) {
		super(title, type, genre, favorite);
	}

	public Book(int id, String title, String type, String genre,
			boolean favorite) {
		super(id, title, type, genre, favorite);
	}

	// getters and setters
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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