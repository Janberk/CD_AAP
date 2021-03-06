package de.canberk.uni.cd_aap.model;

import java.util.Date;

import android.graphics.Bitmap;
import de.canberk.uni.cd_aap.util.CoverUtil;
import de.canberk.uni.cd_aap.util.ItemType;

public abstract class Item {

	private int id;
	private Bitmap cover = CoverUtil.createDefaultCover();
	private Date creationDate;
	private String user;
	private boolean deleted;
	private Date deletionDate;
	private boolean inPossession;
	private boolean favorite;

	private String title;
	private String originalTitle;
	private String country;
	private ItemType type;
	private String genre;
	private String yearPublished;
	private String content;
	private String rating;

	public Item() {
		setCreationDate(new Date());
		setCover(cover);
	}

	public Item(String user, String title, String type, String genre,
			boolean favorite) {
		setCreationDate(new Date());
		setCover(cover);
		setUser(user);
		setTitle(title);
		setType(ItemType.valueOf(type));
		setGenre(genre);
		setFavorite(favorite);
	}

	public Item(int id, String user, String title, String type, String genre,
			boolean favorite) {
		setId(id);
		setCreationDate(new Date());
		setCover(cover);
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

	public Bitmap getCover() {
		return cover;
	}

	public void setCover(Bitmap cover) {
		this.cover = cover;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}

	public boolean isInPossession() {
		return inPossession;
	}

	public void setInPossession(boolean inPossession) {
		this.inPossession = inPossession;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(String yearPublished) {
		this.yearPublished = yearPublished;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@Override
	public abstract String toString();

}