package de.canberk.uni.cd_aap.model;

public class Movie extends Item {

	private String producer;
	private String director;
	private String script;
	private String actors;
	private String music;
	private int length; // in minutes

	public Movie() {
		super();
	}

	public Movie(String user, String title, String type, String genre,
			boolean favorite) {
		super(user, title, type, genre, favorite);
	}

	public Movie(int id, String user, String title, String type, String genre,
			boolean favorite) {
		super(id, user, title, type, genre, favorite);
	}

	// getters and setters
	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("ID: " + getId() + "\n");
		sb.append("Creation date: " + getCreationDateAsString() + "\n");
		sb.append("Deletion date: " + getDeletionDate() + "\n");
		sb.append("Created by: " + getUser() + "\n");
		sb.append("Title: " + getTitle() + "\n");
		sb.append("Original title: " + getOriginalTitle() + "\n");
		sb.append("Country: " + getCountry() + "\n");
		sb.append("Year published: " + getYearPublished() + "\n");
		sb.append("Type: " + getType() + "\n");
		sb.append("Genre: " + getGenre() + "\n");
		sb.append("Producer: " + getProducer() + "\n");
		sb.append("Director: " + getDirector() + "\n");
		sb.append("Actors: " + getActors() + "\n");
		sb.append("Script: " + getScript() + "\n");
		sb.append("Music: " + getMusic() + "\n");
		sb.append("Length: " + getLength() + "\n");
		sb.append("Content: " + getContent() + "\n");
		sb.append("Favorite?: " + isFavorite() + "\n");
		sb.append("Rating: " + getRating() + "\n");
		sb.append("In Possession?: " + isInPossession() + "\n");
		sb.append("Deleted: " + isDeleted() + "\n");

		return sb.toString();
	}

}