package de.canberk.uni.cd_aap.model;

import de.canberk.uni.cd_aap.util.DateUtil;

public class MusicAlbum extends Item {

	private String label;
	private String studio;
	private String producer;
	private String artist;
	private String format;
	private String titleCount;

	public MusicAlbum() {
		super();
	}

	public MusicAlbum(String user, String title, String type, String genre,
			boolean favorite) {
		super(user, title, type, genre, favorite);
	}

	public MusicAlbum(int id, String user, String title, String type,
			String genre, boolean favorite) {
		super(id, user, title, type, genre, favorite);
	}

	// getters and setters
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getTitleCount() {
		return titleCount;
	}

	public void setTitleCount(String titleCount) {
		this.titleCount = titleCount;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("ID: " + getId() + "\n");
		sb.append("Creation date: "
				+ DateUtil.dateToFormattedStringConverter(getCreationDate())
				+ "\n");
		sb.append("Deletion date: " + getDeletionDate() + "\n");
		sb.append("Created by: " + getUser() + "\n");
		sb.append("Title: " + getTitle() + "\n");
		sb.append("Original title: " + getOriginalTitle() + "\n");
		sb.append("Artist: " + isDeleted() + "\n");
		sb.append("Country: " + getCountry() + "\n");
		sb.append("Year published: " + getYearPublished() + "\n");
		sb.append("Type: " + getType() + "\n");
		sb.append("Genre: " + getGenre() + "\n");
		sb.append("Label: " + isDeleted() + "\n");
		sb.append("Studio: " + isDeleted() + "\n");
		sb.append("Producer: " + getProducer() + "\n");
		sb.append("Format: " + isDeleted() + "\n");
		sb.append("Title count: " + isDeleted() + "\n");
		sb.append("Content: " + getContent() + "\n");
		sb.append("Favorite?: " + isFavorite() + "\n");
		sb.append("Rating: " + getRating() + "\n");
		sb.append("In Possession?: " + isInPossession() + "\n");
		sb.append("Deleted: " + isDeleted() + "\n");

		return sb.toString();
	}

}