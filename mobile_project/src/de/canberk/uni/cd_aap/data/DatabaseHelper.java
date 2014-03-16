package de.canberk.uni.cd_aap.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// database details
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "cdaap_android_db.db";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// creating tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ProjectConstants.CREATE_TABLE_ITEMS);
		db.execSQL(ProjectConstants.CREATE_TABLE_USERS);
	}

	// upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + ProjectConstants.TABLE_ITEMS);
		db.execSQL("DROP TABLE IF EXISTS " + ProjectConstants.TABLE_USERS);

		onCreate(db);
	}

}