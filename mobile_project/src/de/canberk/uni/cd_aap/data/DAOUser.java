package de.canberk.uni.cd_aap.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;
import de.canberk.uni.cd_aap.model.User;
import de.canberk.uni.cd_aap.util.UtilMethods;

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
		putValues(user, values);

		return sqliteDb.insert(ProjectConstants.TABLE_USERS, null, values);
	}

	// get user
	public User findUserById(int id) {
		User user = null;
		Cursor cursor = null;
		String selectQuery = "SELECT * FROM " + ProjectConstants.TABLE_USERS
				+ " WHERE " + ProjectConstants.ID + "=" + '"' + id + '"';

		try {
			cursor = sqliteDb.rawQuery(selectQuery, null);

			if (cursor != null) {
				cursor.moveToFirst();
				user = createUserFromTableValues(cursor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return user;
	}

	public User findUserByLogin(String email, String password) {
		User user = null;
		Cursor cursor = null;
		String selectQuery = "SELECT * FROM " + ProjectConstants.TABLE_USERS
				+ " WHERE " + ProjectConstants.EMAIL + "='" + email + "' AND "
				+ ProjectConstants.PASSWORD + "='" + password + "'";

		try {
			cursor = sqliteDb.rawQuery(selectQuery, null);

			if (cursor != null) {
				cursor.moveToFirst();
				user = createUserFromTableValues(cursor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return user;
	}

	// get all users
	public ArrayList<User> getAllUsers() {
		ArrayList<User> userList = new ArrayList<User>();
		Cursor cursor = null;
		String selectQuery = "SELECT * FROM " + ProjectConstants.TABLE_USERS;

		try {
			cursor = sqliteDb.rawQuery(selectQuery, null);

			if (cursor != null) {

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					User user = createUserFromTableValues(cursor);

					userList.add(0, user);
				}
			}
		} catch (Exception e) {
			userList = new ArrayList<User>();
		} finally {
			cursor.close();
		}
		return userList;
	}

	// update user
	public boolean updateUser(User user) {
		ContentValues values = new ContentValues();
		putValues(user, values);

		return sqliteDb.update(ProjectConstants.TABLE_USERS, values,
				ProjectConstants.ID + " = " + user.getId(), null) > 0;
	}

	// delete user
	public boolean deleteUser(User user) {
		return sqliteDb.delete(ProjectConstants.TABLE_USERS,
				ProjectConstants.ID + " = " + user.getId(), null) > 0;
	}

	// get item count
	public int getItemCount() {
		int count = -1;
		Cursor cursor = null;
		String countQuery = "SELECT  * FROM " + ProjectConstants.TABLE_USERS;
		try {
			cursor = sqliteDb.rawQuery(countQuery, null);
			count = cursor.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return count;
	}

	public User createUserFromTableValues(Cursor cursor) {
		int iRow = cursor.getColumnIndex(ProjectConstants.ID);
		int iFirstName = cursor.getColumnIndex(ProjectConstants.FIRST_NAME);
		int iLastName = cursor.getColumnIndex(ProjectConstants.LAST_NAME);
		int iUserName = cursor.getColumnIndex(ProjectConstants.USER_NAME);
		int iEmail = cursor.getColumnIndex(ProjectConstants.EMAIL);
		int iPassword = cursor.getColumnIndex(ProjectConstants.PASSWORD);
		int iCreationDate = cursor
				.getColumnIndex(ProjectConstants.CREATION_DATE);
		int iDeleted = cursor.getColumnIndex(ProjectConstants.DELETED);

		int id = Integer.parseInt(cursor.getString(iRow));
		String firstName = cursor.getString(iFirstName);
		String lastName = cursor.getString(iLastName);
		String userName = cursor.getString(iUserName);
		String email = cursor.getString(iEmail);
		String password = cursor.getString(iPassword);
		String creationDate = cursor.getString(iCreationDate);
		int deletedAsInt = (Integer.parseInt(cursor.getString(iDeleted)));

		User user = new User(id, firstName, lastName, userName, email, password);
		user.setCreationDate(UtilMethods
				.setCreationDateFromString(creationDate));
		user.setDeleted(UtilMethods.isTrue(deletedAsInt));

		return user;
	}

	public void putValues(User user, ContentValues values) {
		values.put(ProjectConstants.FIRST_NAME, user.getFirstname());
		values.put(ProjectConstants.LAST_NAME, user.getLastname());
		values.put(ProjectConstants.USER_NAME, user.getUsername());
		values.put(ProjectConstants.EMAIL, user.getEmail());
		values.put(ProjectConstants.PASSWORD, user.getPassword());
		values.put(ProjectConstants.CREATION_DATE, UtilMethods
				.dateToFormattedStringConverter(user.getCreationDate()));
		if (user.getDeletionDate() != null) {
			values.put(ProjectConstants.DELETION_DATE, UtilMethods
					.dateToFormattedStringConverter(user.getDeletionDate()));
		}
		values.put(ProjectConstants.DELETED,
				UtilMethods.isTrueAsInt(user.isDeleted()));
	}

	// check already existing users
	public boolean alreadyExists(String email, String username) {
		Cursor cursorEmail = sqliteDb.query(ProjectConstants.TABLE_USERS,
				new String[] { ProjectConstants.ID, ProjectConstants.EMAIL },
				ProjectConstants.EMAIL + "='" + email + "'", null, null, null,
				null);
		Cursor cursorUserName = sqliteDb
				.query(ProjectConstants.TABLE_USERS, new String[] {
						ProjectConstants.ID, ProjectConstants.USER_NAME },
						ProjectConstants.USER_NAME + "='" + username + "'",
						null, null, null, null);
		if (cursorEmail == null || cursorUserName == null) {
			Toast.makeText(context, "Database query error", Toast.LENGTH_SHORT)
					.show();
		} else {
			if (cursorEmail.getCount() > 0 || cursorUserName.getCount() > 0) {
				cursorEmail.close();
				return true;
			}
		}
		return false;
	}

}