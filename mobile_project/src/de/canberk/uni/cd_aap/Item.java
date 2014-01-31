package de.canberk.uni.cd_aap;

public abstract class Item {

	private int id;
	private String title;
	private String type;
	private String genre;

	public Item(String title) {
		setTitle(title);
		setType("");
		setGenre("");
	}
	
	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	@Override
	public abstract String toString();

}
