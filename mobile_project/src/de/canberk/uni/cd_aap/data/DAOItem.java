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

	// table name
	public static final String TABLE_ITEMS = "items";

	// table column names
	private static final String ID = "_id";
	private static final String TITLE = "title";
	private static final String USER = "created_by";
	private static final String TYPE = "type";
	private static final String GENRE = "genre";
	private static final String FAVORITE = "favorite";

	// sql statement
	public static final String CREATE_TABLE_ITEMS = "CREATE TABLE "
			+ TABLE_ITEMS
			+ "("
			+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ USER	+ " TEXT, "
			+ TITLE	+ " TEXT, "
			+ TYPE + " TEXT, "
			+ GENRE + " TEXT, "
			+ FAVORITE + " INTEGER"
			+ ")";

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
		values.put(USER, item.getUser());
		values.put(TITLE, item.getTitle());
		values.put(TYPE, item.getType().toString());
		values.put(GENRE, item.getGenre());
		values.put(FAVORITE, item.isFavoriteAsInteger());

		return sqliteDb.insert(TABLE_ITEMS, null, values);

	}

	// get item
	public Item getItem(int id) {
		Item item = null;

		try {
			Cursor cursor = sqliteDb
					.query(TABLE_ITEMS, new String[] { ID, USER, TITLE, TYPE, GENRE,
							FAVORITE }, ID + "=?",
							new String[] { String.valueOf(id) }, null, null,
							null, null);

			if (cursor != null) {
				cursor.moveToFirst();
				item = createItem(cursor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	// get all items
	public ArrayList<Item> getAllItems(String user) {
		ArrayList<Item> itemList = new ArrayList<Item>();
		String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE " + USER + "=" + '"' + user + '"';

		try {
			Cursor cursor = sqliteDb.rawQuery(selectQuery, null);

			if (cursor != null) {

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					Item item = createItem(cursor);

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
		values.put(USER, item.getUser());
		values.put(TITLE, item.getTitle());
		values.put(TYPE, item.getType().toString());
		values.put(GENRE, item.getGenre());
		values.put(FAVORITE, item.isFavoriteAsInteger());

		return sqliteDb.update(TABLE_ITEMS, values, ID + " = " + item.getId(),
				null) > 0;
	}

	// delete item
	public boolean deleteItem(Item item) {
		return sqliteDb.delete(TABLE_ITEMS, ID + " = " + item.getId(), null) > 0;
	}

	// get item count
	public int getItemCount() {
		int count;
		String countQuery = "SELECT  * FROM " + TABLE_ITEMS;
		Cursor cursor = sqliteDb.rawQuery(countQuery, null);
		count = cursor.getCount();
		cursor.close();

		return count;
	}

	public Item createItem(Cursor cursor) {
		Item item = null;

		int iRow = cursor.getColumnIndex(ID);
		int iUser = cursor.getColumnIndex(USER);
		int iTitle = cursor.getColumnIndex(TITLE);
		int iType = cursor.getColumnIndex(TYPE);
		int iGenre = cursor.getColumnIndex(GENRE);
		int iFavorite = cursor.getColumnIndex(FAVORITE);

		ItemType type = ItemType.valueOf(cursor.getString(iType));

		int favoriteAsInt = (Integer.parseInt(cursor.getString(iFavorite)));

		int id = Integer.parseInt(cursor.getString(iRow));
		String user = cursor.getString(iUser);
		String title = cursor.getString(iTitle);
		String itemType = cursor.getString(iType);
		String genre = cursor.getString(iGenre);

		switch (type) {
		case Album:
			item = new MusicAlbum(id, user, title, itemType, genre,
					isFavorite(favoriteAsInt));
			break;
		case Book:
			item = new Book(id, user, title, itemType, genre,
					isFavorite(favoriteAsInt));
			break;
		case Movie:
			item = new Movie(id, user, title, itemType, genre,
					isFavorite(favoriteAsInt));
			break;
		default:
			break;
		}
		return item;
	}

	public boolean isFavorite(int favoriteAsInt) {
		if (favoriteAsInt == 1) {
			return true;
		}
		return false;
	}

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

}