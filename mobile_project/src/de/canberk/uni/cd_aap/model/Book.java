package de.canberk.uni.cd_aap.model;

import de.canberk.uni.cd_aap.util.DateUtil;

public class Book extends Item {

	private String edition;
	private String publishingHouse;
	private String author;
	private String isbn;

	public Book() {
		super();
	}

	public Book(String user, String title, String type, String genre,
			boolean favorite) {
		super(user, title, type, genre, favorite);
	}

	public Book(int id, String user, String title, String type, String genre,
			boolean favorite) {
		super(id, user, title, type, genre, favorite);
	}

	// getters and setters
	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
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
		sb.append("Creation date: "
				+ DateUtil.dateToFormattedStringConverter(getCreationDate()) + "\n");
		sb.append("Deletion date: " + getDeletionDate() + "\n");
		sb.append("Created by: " + getUser() + "\n");
		sb.append("Title: " + getTitle() + "\n");
		sb.append("Original title: " + getOriginalTitle() + "\n");
		sb.append("Type: " + getType() + "\n");
		sb.append("Genre: " + getGenre() + "\n");
		sb.append("Edition: " + getEdition() + "\n");
		sb.append("Publishing house: " + getPublishingHouse() + "\n");
		sb.append("Country: " + getCountry() + "\n");
		sb.append("Year published: " + getYearPublished() + "\n");
		sb.append("Author: " + getAuthor() + "\n");
		sb.append("ISBN: " + getIsbn() + "\n");
		sb.append("Content: " + getContent() + "\n");
		sb.append("Favorite?: " + isFavorite() + "\n");
		sb.append("Rating: " + getRating() + "\n");
		sb.append("In Possession?: " + isInPossession() + "\n");
		sb.append("Deleted: " + isDeleted() + "\n");

		return sb.toString();
	}

}