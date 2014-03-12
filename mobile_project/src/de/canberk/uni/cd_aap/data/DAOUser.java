package de.canberk.uni.cd_aap.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import de.canberk.uni.cd_aap.model.User;

public class DAOUser {

	private Context context;
	private SQLiteDatabase sqliteDb;
	private DatabaseHelper dbHelper;

	// table column names
	private static final String ID = "_id";
	private static final String FIRST_NAME = "firstname";
	private static final String LAST_NAME = "lastname";
	private static final String USER_NAME = "username";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";

	// table name
	public static final String TABLE_USERS = "users";

	// sql statement
	public static final String CREATE_TABLE_USERS = "CREATE TABLE "
			+ TABLE_USERS + "(" + ID + " INTEGER PRIMARY KEY," + FIRST_NAME
			+ " TEXT," + LAST_NAME + " TEXT," + USER_NAME + " TEXT," + EMAIL
			+ " TEXT," + PASSWORD + " TEXT" + ")";

	// constructor
	public DAOUser(Context context) {
		this.context = context;
	}

	public DAOUser open() throws SQLiteException {
		dbHelper = new DatabaseHelper(context);
		sqliteDb = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	// adding new user
	public long addUser(User user) {
		ContentValues values = new ContentValues();
		values.put(FIRST_NAME, user.getFirstname());
		values.put(LAST_NAME, user.getLastname());
		values.put(USER_NAME, user.getUsername());
		values.put(EMAIL, user.getEmail());
		values.put(PASSWORD, user.getPassword());

		return sqliteDb.insert(TABLE_USERS, null, values);

	}

	// get user
	public User getUser(int id) {
		User user = null;

		try {
			Cursor cursor = sqliteDb.query(TABLE_USERS, new String[] { ID,
					FIRST_NAME, LAST_NAME, USER_NAME, EMAIL, PASSWORD }, ID
					+ "=?", new String[] { String.valueOf(id) }, null, null,
					null, null);

			if (cursor != null) {
				cursor.moveToFirst();
				user = createUser(cursor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	// get all users
	public ArrayList<User> getAllUsers() {
		ArrayList<User> userList = new ArrayList<User>();
		String selectQuery = "SELECT * FROM " + TABLE_USERS;

		try {
			Cursor cursor = sqliteDb.rawQuery(selectQuery, null);

			if (cursor != null) {

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					User user = createUser(cursor);

					userList.add(0, user);
				}
				cursor.close();
			}
		} catch (Exception e) {
			userList = new ArrayList<User>();
		}
		return userList;
	}

	// update user
	public boolean updateUser(User user) {
		ContentValues values = new ContentValues();
		values.put(FIRST_NAME, user.getFirstname());
		values.put(LAST_NAME, user.getLastname());
		values.put(USER_NAME, user.getUsername());
		values.put(EMAIL, user.getEmail());
		values.put(PASSWORD, user.getPassword());

		return sqliteDb.update(TABLE_USERS, values, ID + " = " + user.getId(),
				null) > 0;
	}

	// delete user
	public boolean deleteUser(User user) {
		return sqliteDb.delete(TABLE_USERS, ID + " = " + user.getId(), null) > 0;
	}

	// get user count
	public int getUserCount() {
		int count;
		String countQuery = "SELECT  * FROM " + TABLE_USERS;
		Cursor cursor = sqliteDb.rawQuery(countQuery, null);
		count = cursor.getCount();
		cursor.close();

		return count;
	}

	public User createUser(Cursor cursor) {
		int iRow = cursor.getColumnIndex(ID);
		int iFirstName = cursor.getColumnIndex(FIRST_NAME);
		int iLastName = cursor.getColumnIndex(LAST_NAME);
		int iUserName = cursor.getColumnIndex(USER_NAME);
		int iEmail = cursor.getColumnIndex(EMAIL);
		int iPassword = cursor.getColumnIndex(PASSWORD);

		int id = Integer.parseInt(cursor.getString(iRow));
		String firstName = cursor.getString(iFirstName);
		String lastName = cursor.getString(iLastName);
		String userName = cursor.getString(iUserName);
		String email = cursor.getString(iEmail);
		String password = cursor.getString(iPassword);

		User user = new User(id, firstName, lastName, userName, email, password);

		return user;
	}

}