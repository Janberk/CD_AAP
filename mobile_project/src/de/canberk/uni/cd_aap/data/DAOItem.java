package de.canberk.uni.cd_aap.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import de.canberk.uni.cd_aap.model.Book;
import de.canberk.uni.cd_aap.model.Item;
import de.canberk.uni.cd_aap.model.Movie;
import de.canberk.uni.cd_aap.model.MusicAlbum;
import de.canberk.uni.cd_aap.util.CoverUtil;
import de.canberk.uni.cd_aap.util.DateUtil;
import de.canberk.uni.cd_aap.util.ItemType;

public class DAOItem {

	private Context context;
	private SQLiteDatabase sqliteDb;
	private DatabaseHelper dbHelper;

	// constructor
	public DAOItem(Context context) {
		this.context = context;
	}

	public DAOItem open() throws SQLiteException {
		dbHelper = new DatabaseHelper(context);
		sqliteDb = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	// adding new item
	public long addItem(Item item) {
		ContentValues values = new ContentValues();
		putValues(item, values);
		values.put(ProjectConstants.CREATION_DATE,
				DateUtil.dateToFormattedStringConverter(item.getCreationDate()));

		return sqliteDb.insert(ProjectConstants.TABLE_ITEMS, null, values);
	}

	public Item getItem(int id) {
		Item item = null;
		String selectQuery = "SELECT * FROM " + ProjectConstants.TABLE_ITEMS
				+ " WHERE " + ProjectConstants.ID + "=" + '"' + id + '"';

		try {
			Cursor cursor = sqliteDb.rawQuery(selectQuery, null);

			if (cursor != null) {
				cursor.moveToFirst();
				item = createItemFromTableValues(cursor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	// get all items
	public ArrayList<Item> getAllItems(String user) {
		ArrayList<Item> itemList = new ArrayList<Item>();
		String selectQuery = "SELECT * FROM " + ProjectConstants.TABLE_ITEMS
				+ " WHERE " + ProjectConstants.USER + "=" + '"' + user + '"';

		try {
			Cursor cursor = sqliteDb.rawQuery(selectQuery, null);

			if (cursor != null) {

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					Item item = createItemFromTableValues(cursor);

					itemList.add(0, item);
				}
				cursor.close();
			}
		} catch (Exception e) {
			itemList = new ArrayList<Item>();
		}
		return itemList;
	}

	// update item
	public boolean updateItem(Item item) {
		ContentValues values = new ContentValues();
		putValues(item, values);

		return sqliteDb.update(ProjectConstants.TABLE_ITEMS, values,
				ProjectConstants.ID + " = " + item.getId(), null) > 0;
	}

	// delete item
	public boolean deleteItem(Item item) {
		return sqliteDb.delete(ProjectConstants.TABLE_ITEMS,
				ProjectConstants.ID + " = " + item.getId(), null) > 0;
	}

	// get item count
	public int getItemCount() {
		int count;
		String countQuery = "SELECT  * FROM " + ProjectConstants.TABLE_ITEMS;
		Cursor cursor = sqliteDb.rawQuery(countQuery, null);
		count = cursor.getCount();
		cursor.close();

		return count;
	}

	// get items by specified type
	public ArrayList<Item> getItemsByType(ItemType type, String user) {
		ArrayList<Item> pre = getAllItems(user);
		ArrayList<Item> result = new ArrayList<Item>();
		ItemType fetchedType = null;

		for (Item item : pre) {
			fetchedType = item.getType();
			if (fetchedType == type) {
				result.add(item);
			}
		}
		return result;
	}

	public Item createItemFromTableValues(Cursor cursor) {
		Item item = null;

		int iRow = cursor.getColumnIndex(ProjectConstants.ID);
		int iCover = cursor.getColumnIndex(ProjectConstants.COVER);
		int iCreationDate = cursor
				.getColumnIndex(ProjectConstants.CREATION_DATE);
		int iUser = cursor.getColumnIndex(ProjectConstants.USER);
		int iTitle = cursor.getColumnIndex(ProjectConstants.TITLE);
		int iType = cursor.getColumnIndex(ProjectConstants.TYPE);
		int iGenre = cursor.getColumnIndex(ProjectConstants.GENRE);
		int iFavorite = cursor.getColumnIndex(ProjectConstants.FAVORITE);
		int iInPossession = cursor
				.getColumnIndex(ProjectConstants.IN_POSSESSION);
		int iDeleted = cursor.getColumnIndex(ProjectConstants.DELETED);

		ItemType type = ItemType.valueOf(cursor.getString(iType));

		int favoriteAsInt = (Integer.parseInt(cursor.getString(iFavorite)));
		int inPossessionAsInt = (Integer.parseInt(cursor
				.getString(iInPossession)));
		int deletedAsInt = (Integer.parseInt(cursor.getString(iDeleted)));

		int id = Integer.parseInt(cursor.getString(iRow));
		byte[] bytes = cursor.getBlob(iCover);
		Bitmap cover = CoverUtil.getBitmap(bytes);
		String creationDate = cursor.getString(iCreationDate);
		String user = cursor.getString(iUser);
		String title = cursor.getString(iTitle);
		String itemType = cursor.getString(iType);
		String genre = cursor.getString(iGenre);

		switch (type) {
		case Album:
			item = new MusicAlbum(id, user, title, itemType, genre,
					isTrue(favoriteAsInt));
			break;
		case Book:
			item = new Book(id, user, title, itemType, genre,
					isTrue(favoriteAsInt));
			break;
		case Movie:
			item = new Movie(id, user, title, itemType, genre,
					isTrue(favoriteAsInt));
			break;
		default:
			break;
		}
		item.setCover(cover);
		item.setCreationDateFromString(creationDate);
		item.setInPossession(isTrue(inPossessionAsInt));
		item.setDeleted(isTrue(deletedAsInt));
		return item;
	}

	public boolean isTrue(int valueToCheck) {
		if (valueToCheck == 1) {
			return true;
		}
		return false;
	}

	public void putValues(Item item, ContentValues values) {
		byte[] bytes = CoverUtil.getByteArray(item.getCover());
		values.put(ProjectConstants.COVER, bytes);
		values.put(ProjectConstants.USER, item.getUser());
		values.put(ProjectConstants.TITLE, item.getTitle());
		values.put(ProjectConstants.TYPE, item.getType().toString());
		values.put(ProjectConstants.GENRE, item.getGenre());
		values.put(ProjectConstants.IN_POSSESSION,
				item.isTrueAsInt(item.isInPossession()));
		values.put(ProjectConstants.FAVORITE,
				item.isTrueAsInt(item.isFavorite()));
		values.put(ProjectConstants.DELETED, item.isTrueAsInt(item.isDeleted()));
		
		if (item.getDeletionDate() != null) {
			values.put(ProjectConstants.DELETION_DATE,
					DateUtil.dateToFormattedStringConverter(item.getDeletionDate()));
		}
		
		values.put(ProjectConstants.ORIGINAL_TITLE,
				item.isTrueAsInt(item.isDeleted()));
		values.put(ProjectConstants.COUNTRY, item.getCountry());
		values.put(ProjectConstants.YEAR_PUBLISHED, item.getYearPublished());
		values.put(ProjectConstants.CONTENT, item.getContent());
		values.put(ProjectConstants.RATING, item.getRating());

		if (item instanceof Movie) {
			values.put(ProjectConstants.PRODUCER, ((Movie) item).getProducer());
			values.put(ProjectConstants.DIRECTOR, ((Movie) item).getDirector());
			values.put(ProjectConstants.SCRIPT, ((Movie) item).getScript());
			values.put(ProjectConstants.ACTORS, ((Movie) item).getActors());
			values.put(ProjectConstants.MUSIC, ((Movie) item).getMusic());
			values.put(ProjectConstants.LENGTH, ((Movie) item).getLength());
		}

		if (item instanceof MusicAlbum) {
			values.put(ProjectConstants.LABEL, ((MusicAlbum) item).getLabel());
			values.put(ProjectConstants.STUDIO, ((MusicAlbum) item).getStudio());
			values.put(ProjectConstants.ARTIST, ((MusicAlbum) item).getArtist());
			values.put(ProjectConstants.FORMAT, ((MusicAlbum) item).getFormat());
			values.put(ProjectConstants.TITLE_COUNT,
					((MusicAlbum) item).getTitleCount());
		}

		if (item instanceof Book) {
			values.put(ProjectConstants.EDITION, ((Book) item).getEdition());
			values.put(ProjectConstants.PUBLISHING_HOUSE,
					((Book) item).getPublishingHouse());
			values.put(ProjectConstants.AUTHOR, ((Book) item).getAuthor());
			values.put(ProjectConstants.ISBN, ((Book) item).getIsbn());
		}

	}

}