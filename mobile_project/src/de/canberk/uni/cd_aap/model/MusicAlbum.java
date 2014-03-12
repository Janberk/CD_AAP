package de.canberk.uni.cd_aap.model;

public class MusicAlbum extends Item {

	private String artist;

	public MusicAlbum() {
		super();
	}

	public MusicAlbum(String title, String type, String genre, boolean favorite) {
		super(title, type, genre, favorite);
	}

	public MusicAlbum(int id, String title, String type, String genre,
			boolean favorite) {
		super(id, title, type, genre, favorite);
	}

	// getters and setters
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
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