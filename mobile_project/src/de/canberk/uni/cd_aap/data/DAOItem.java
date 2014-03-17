package de.canberk.uni.cd_aap.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import de.canberk.uni.cd_aap.model.Book;
import de.canberk.uni.cd_aap.model.Item;
import de.canberk.uni.cd_aap.model.Movie;
import de.canberk.uni.cd_aap.model.MusicAlbum;
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
				item.getCreationDateAsString(item.getCreationDate()));

		return sqliteDb.insert(ProjectConstants.TABLE_ITEMS, null, values);
	}

	public Item getItem(int id) {
		Item item = null;
		// Cursor cursor = sqliteDb
		// .query(ProjectConstants.TABLE_ITEMS,
		// new String[] { ProjectConstants.ID,
		// ProjectConstants.USER,
		// ProjectConstants.TITLE,
		// ProjectConstants.TYPE,
		// ProjectConstants.GENRE,
		// ProjectConstants.FAVORITE,
		// ProjectConstants.IN_POSSESSION,
		// ProjectConstants.DELETED },
		// ProjectConstants.ID + "=?",
		// new String[] { String.valueOf(id) }, null, null,
		// null, null);
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
		values.put(ProjectConstants.USER, item.getUser());
		values.put(ProjectConstants.TITLE, item.getTitle());
		values.put(ProjectConstants.TYPE, item.getType().toString());
		values.put(ProjectConstants.GENRE, item.getGenre());
		values.put(ProjectConstants.IN_POSSESSION,
				item.isInPossessionAsInteger());
		values.put(ProjectConstants.FAVORITE, item.isFavoriteAsInteger());
		values.put(ProjectConstants.DELETED, item.isDeletedAsInteger());
		values.put(ProjectConstants.DELETION_DATE, item.isDeletedAsInteger());
		values.put(ProjectConstants.ORIGINAL_TITLE, item.isDeletedAsInteger());
		values.put(ProjectConstants.COUNTRY, item.isDeletedAsInteger());
		values.put(ProjectConstants.YEAR_PUBLISHED, item.isDeletedAsInteger());
		values.put(ProjectConstants.CONTENT, item.isDeletedAsInteger());
		values.put(ProjectConstants.RATING, item.isDeletedAsInteger());
		values.put(ProjectConstants.PRODUCER, item.isDeletedAsInteger());
		values.put(ProjectConstants.DIRECTOR, item.isDeletedAsInteger());
		values.put(ProjectConstants.SCRIPT, item.isDeletedAsInteger());
		values.put(ProjectConstants.ACTORS, item.isDeletedAsInteger());
		values.put(ProjectConstants.MUSIC, item.isDeletedAsInteger());
		values.put(ProjectConstants.LENGTH, item.isDeletedAsInteger());
		values.put(ProjectConstants.LABEL, item.isDeletedAsInteger());
		values.put(ProjectConstants.STUDIO, item.isDeletedAsInteger());
		values.put(ProjectConstants.ARTIST, item.isDeletedAsInteger());
		values.put(ProjectConstants.FORMAT, item.isDeletedAsInteger());
		values.put(ProjectConstants.TITLE_COUNT, item.isDeletedAsInteger());
		values.put(ProjectConstants.EDITION, item.isDeletedAsInteger());
		values.put(ProjectConstants.PUBLISHING_HOUSE, item.isDeletedAsInteger());
		values.put(ProjectConstants.AUTHOR, item.isDeletedAsInteger());
		values.put(ProjectConstants.ISBN, item.isDeletedAsInteger());
	}

}