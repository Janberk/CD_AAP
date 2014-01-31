package de.canberk.uni.cd_aap;

public class Movie extends Item {

	public Movie(String title) {
		super(title);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("ID: " + getId() + "\n");
		sb.append("Title: " + getTitle() + "\n");
		sb.append("Type: " + getType() + "\n");
		sb.append("Genre: " + getGenre() + "\n");

		return sb.toString();
	}

}