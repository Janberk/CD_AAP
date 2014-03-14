package de.canberk.uni.cd_aap.model;

import de.canberk.uni.cd_aap.util.ItemType;

public abstract class Item {

	private int id;
	private String user;
	private boolean favorite;

	private String title;
	private ItemType type;
	private String genre;

	public Item() {
	}

	public Item(String user, String title, String type, String genre, boolean favorite) {
		setUser(user);
		setTitle(title);
		setType(ItemType.valueOf(type));
		setGenre(genre);
		setFavorite(favorite);
	}

	public Item(int id, String user, String title, String type, String genre,
			boolean favorite) {
		setId(id);
		setUser(user);
		setTitle(title);
		setType(ItemType.valueOf(type));
		setGenre(genre);
		setFavorite(favorite);
	}

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public int isFavoriteAsInteger() {
		if (isFavorite()) {
			return 1;
		}
		return 0;
	}

	@Override
	public abstract String toString();

}