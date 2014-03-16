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
		values.put(ProjectConstants.FIRST_NAME, user.getFirstname());
		values.put(ProjectConstants.LAST_NAME, user.getLastname());
		values.put(ProjectConstants.USER_NAME, user.getUsername());
		values.put(ProjectConstants.EMAIL, user.getEmail());
		values.put(ProjectConstants.PASSWORD, user.getPassword());

		return sqliteDb.insert(ProjectConstants.TABLE_USERS, null, values);

	}

	// get user
	public User getUser(int id) {
		User user = null;

		try {
			Cursor cursor = sqliteDb.query(ProjectConstants.TABLE_USERS,
					new String[] { ProjectConstants.ID,
							ProjectConstants.FIRST_NAME,
							ProjectConstants.LAST_NAME,
							ProjectConstants.USER_NAME, ProjectConstants.EMAIL,
							ProjectConstants.PASSWORD }, ProjectConstants.ID
							+ "=?", new String[] { String.valueOf(id) }, null,
					null, null, null);

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
		String selectQuery = "SELECT * FROM " + ProjectConstants.TABLE_USERS;

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
		values.put(ProjectConstants.FIRST_NAME, user.getFirstname());
		values.put(ProjectConstants.LAST_NAME, user.getLastname());
		values.put(ProjectConstants.USER_NAME, user.getUsername());
		values.put(ProjectConstants.EMAIL, user.getEmail());
		values.put(ProjectConstants.PASSWORD, user.getPassword());

		return sqliteDb.update(ProjectConstants.TABLE_USERS, values,
				ProjectConstants.ID + " = " + user.getId(), null) > 0;
	}

	// delete user
	public boolean deleteUser(User user) {
		return sqliteDb.delete(ProjectConstants.TABLE_USERS,
				ProjectConstants.ID + " = " + user.getId(), null) > 0;
	}

	// get user count
	public int getUserCount() {
		int count;
		String countQuery = "SELECT  * FROM " + ProjectConstants.TABLE_USERS;
		Cursor cursor = sqliteDb.rawQuery(countQuery, null);
		count = cursor.getCount();
		cursor.close();

		return count;
	}

	public User createUser(Cursor cursor) {
		int iRow = cursor.getColumnIndex(ProjectConstants.ID);
		int iFirstName = cursor.getColumnIndex(ProjectConstants.FIRST_NAME);
		int iLastName = cursor.getColumnIndex(ProjectConstants.LAST_NAME);
		int iUserName = cursor.getColumnIndex(ProjectConstants.USER_NAME);
		int iEmail = cursor.getColumnIndex(ProjectConstants.EMAIL);
		int iPassword = cursor.getColumnIndex(ProjectConstants.PASSWORD);

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