package de.canberk.uni.cd_aap.data;

public class ProjectConstants {

	// table names
	public static final String TABLE_USERS = "users";
	public static final String TABLE_ITEMS = "items";

	// item table column names
	// common
	public static final String ID = "_id";
	public static final String TITLE = "title";
	public static final String USER = "created_by";
	public static final String TYPE = "type";
	public static final String GENRE = "genre";
	public static final String FAVORITE = "favorite";
	public static final String CREATION_DATE = "creation_date"; // also for user
	public static final String DELETED = "deleted"; // also for user
	public static final String DELETION_DATE = "deletion_date"; // also for user
	public static final String IN_POSSESSION = "in_possession";
	public static final String ORIGINAL_TITLE = "original_title";
	public static final String COUNTRY = "country";
	public static final String YEAR_PUBLISHED = "year_published";
	public static final String CONTENT = "content";
	public static final String RATING = "rating";
	// movie
	public static final String PRODUCER = "producer"; // also for albums
	public static final String DIRECTOR = "director";
	public static final String SCRIPT = "script";
	public static final String ACTORS = "actors";
	public static final String MUSIC = "music";
	public static final String LENGTH = "length";
	// album
	public static final String LABEL = "label";
	public static final String STUDIO = "studio";
	public static final String ARTIST = "artist";
	public static final String FORMAT = "format";
	public static final String TITLE_COUNT = "title_count";
	// books
	public static final String EDITION = "edition";
	public static final String PUBLISHING_HOUSE = "publishing_house";
	public static final String AUTHOR = "author";
	public static final String ISBN = "isbn";

	// user table column names
	public static final String FIRST_NAME = "firstname";
	public static final String LAST_NAME = "lastname";
	public static final String USER_NAME = "username";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	
	// sql statement items table
	public static final String CREATE_TABLE_ITEMS = "CREATE TABLE "
			+ TABLE_ITEMS + "("
			+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ USER + " TEXT, "
			+ TITLE + " TEXT, "
			+ TYPE + " TEXT, "
			+ GENRE + " TEXT, "
			+ FAVORITE + " INTEGER, "			
			+ CREATION_DATE + " TEXT, "
			+ DELETED + " INTEGER, "
			+ DELETION_DATE + " TEXT, "
			+ IN_POSSESSION + " INTEGER, "
			+ ORIGINAL_TITLE + " TEXT, "
			+ COUNTRY + " TEXT, "
			+ YEAR_PUBLISHED + " TEXT, "
			+ CONTENT + " TEXT, "
			+ RATING + " TEXT, "
			+ PRODUCER + " TEXT, "
			+ DIRECTOR + " TEXT, "
			+ SCRIPT + " TEXT, "
			+ ACTORS + " TEXT, "
			+ MUSIC + " TEXT, "
			+ LENGTH + " INTEGER, "
			+ LABEL + " TEXT, "
			+ STUDIO + " TEXT, "
			+ ARTIST + " TEXT, "
			+ FORMAT + " TEXT, "
			+ TITLE_COUNT + " INTEGER, "
			+ EDITION + " INTEGER, "
			+ PUBLISHING_HOUSE + " TEXT, "
			+ AUTHOR + " TEXT, "
			+ ISBN + " TEXT"
			+ ")";
	
	// sql statement table users
	public static final String CREATE_TABLE_USERS = "CREATE TABLE "
			+ TABLE_USERS + "("
			+ ID + " INTEGER PRIMARY KEY,"
			+ CREATION_DATE + " TEXT,"
			+ DELETED + " INTEGER,"
			+ DELETION_DATE + " TEXT,"
			+ FIRST_NAME + " TEXT,"
			+ LAST_NAME + " TEXT,"
			+ USER_NAME + " TEXT,"
			+ EMAIL + " TEXT,"
			+ PASSWORD + " TEXT"
			+ ")";
	
}