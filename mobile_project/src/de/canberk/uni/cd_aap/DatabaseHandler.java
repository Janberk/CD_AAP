package de.canberk.uni.cd_aap;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// database details
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "android_db";
	public static final String USERS = "users";
	
	// table column names
	private static final String ID = "id";
	private static final String FIRST_NAME = "firstname";
	private static final String LAST_NAME = "lastname";
	private static final String USER_NAME = "username";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	
	// constructor
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
	// creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USERS = "CREATE TABLE " + USERS + "(" 
        		+ ID + " INTEGER PRIMARY KEY," 
        		+ FIRST_NAME + " TEXT," 
        		+ LAST_NAME + " TEXT," 
        		+ USER_NAME + " TEXT," 
        		+ EMAIL + " TEXT," 
        		+ PASSWORD + " TEXT" 
        		+ ")";
        db.execSQL(CREATE_TABLE_USERS);
    }    

	// upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// drop older table if exists
		db.execSQL("DROP TABLE IF EXISTS " + USERS);

		// create tables again
		onCreate(db);
	}
	
	// adding new user
	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FIRST_NAME, user.getFirstname());
		values.put(LAST_NAME, user.getLastname());
		values.put(USER_NAME, user.getUsername());
		values.put(EMAIL, user.getEmail());
		values.put(PASSWORD, user.getPassword());

		db.insert(USERS, null, values);
		db.close();
	}
	
	// get user
	public User getUser(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(USERS, new String[] { ID, FIRST_NAME,
				LAST_NAME, USER_NAME, EMAIL, PASSWORD }, ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		User user = new User(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5));
		return user;
	}
	
	// get all users
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		// select all query
		String selectQuery = "SELECT * FROM " + USERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setId(Integer.parseInt(cursor.getString(0)));
				user.setFirstname(cursor.getString(1));
				user.setLastname(cursor.getString(2));
				user.setUsername(cursor.getString(3));
				user.setEmail(cursor.getString(4));
				user.setPassword(cursor.getString(5));
				// adding contact to list
				userList.add(user);
			} while (cursor.moveToNext());
		}

		// return contact list
		return userList;
	}

	// get users count
	public int getUsersCount() {
		String countQuery = "SELECT  * FROM " + USERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
	
	// update user
	public int updateUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(FIRST_NAME, user.getFirstname());
		values.put(LAST_NAME, user.getLastname());
		values.put(USER_NAME, user.getUsername());
		values.put(EMAIL, user.getEmail());
		values.put(PASSWORD, user.getPassword());

		// updating row
		return db.update(USERS, values, ID + " = ?",
				new String[] { String.valueOf(user.getId()) });
	}
	
	// delete user
	public void deleteUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(USERS, ID + " = ?",
				new String[] { String.valueOf(user.getId()) });
		db.close();
	}

}
