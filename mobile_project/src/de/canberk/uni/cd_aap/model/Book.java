package de.canberk.uni.cd_aap.model;

public class Book extends Item {

	private int edition;
	private String publishingHouse;
	private String author;
	private String isbn;	

	public Book() {
		super();
	}

	public Book(String user, String title, String type, String genre, boolean favorite) {
		super(user, title, type, genre, favorite);
	}

	public Book(int id, String user, String title, String type, String genre,
			boolean favorite) {
		super(id, user, title, type, genre, favorite);
	}

	// getters and setters
	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	public String getPublishingHouse() {
		return publishingHouse;
	}

	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("ID: " + getId() + "\n");
		sb.append("Created by: " + getUser() + "\n");
		sb.append("Title: " + getTitle() + "\n");
		sb.append("Type: " + getType() + "\n");
		sb.append("Genre: " + getGenre() + "\n");
		sb.append("Favorite: " + isFavorite() + "\n");

		return sb.toString();
	}

}