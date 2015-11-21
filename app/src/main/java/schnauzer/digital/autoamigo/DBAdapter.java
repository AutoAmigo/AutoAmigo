// ------------------------------------ DBADapter.java ---------------------------------------------


package schnauzer.digital.autoamigo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String KEY_NAME = "name";
	public static final String KEY_LASTNAME = "lastname";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_CITY = "city";
	public static final String KEY_USER = "user";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_TYPE = "type";
	public static final String KEY_HANDICAPPED = "handicapped";
	public static final String KEY_AGE = "age";
	public static final String KEY_INSTITUTION = "institution";

	public static final String KEY_DATE = "date";
	public static final String KEY_TIME = "time";
	public static final String KEY_POST = "post";
	public static final String KEY_CURRENT_USER = "id_user";

	public static final String KEY_NAME_RIDE = "name_route";
	public static final String KEY_START_TIME = "start_time";
	public static final String KEY_FINISH_TIME = "finish_time";
	public static final String KEY_M = "monday";
	public static final String KEY_T = "tuesday";
	public static final String KEY_W = "wednesday";
	public static final String KEY_TD = "thursday";
	public static final String KEY_F = "friday";
	public static final String KEY_S= "saturday";
	public static final String KEY_SD = "sunday";
	public static final String KEY_DATE_RIDE = "date";








	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_ID= 0;
	public static final int COL_NAME = 1;
	public static final int COL_LASTNAME = 2;
	public static final int COL_EMAIL = 3;
	public static final int COL_CITY = 4;
	public static final int COL_USER = 5;
	public static final int COL_PASSWORD = 6;
	public static final int COL_TYPE = 7;
	public static final int COL_HANDICAPPED = 8;
	public static final int COL_AGE = 9;
	public static final int COL_INSTITUTION = 10;




	
	public static final String[] USER_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_LASTNAME, KEY_EMAIL,KEY_CITY,KEY_USER,KEY_PASSWORD,KEY_TYPE,KEY_HANDICAPPED,KEY_AGE,KEY_INSTITUTION};


	
	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "MyDb";
	public static final String DATABASE_TABLE_USER = "user";
	public static final String DATABASE_TABLE_POST = "post";
	public static final String DATABASE_TABLE_RIDE = "ride";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 2;	



	// TODO: Place your fields here!
	// + KEY_{...} + " {type} not null"
	//	- Key is the column name you created above.
	//	- {type} is one of: text, integer, real, blob
	//		(http://www.sqlite.org/datatype3.html)
	//  - "not null" means it is a required field (must be given a value).
	// NOTE: All must be comma separated (end of line!) Last one must have NO comma!!


	// Rest  of creation:
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE_USER
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
					+ KEY_NAME + " text not null, "
					+ KEY_LASTNAME + " text not null, "
					+ KEY_EMAIL + " email not null"
					+ KEY_CITY + " text not null"
					+ KEY_USER + " text not null"
					+ KEY_PASSWORD + " text not null"
					+ KEY_TYPE + " text not null"
					+ KEY_HANDICAPPED+ " text not null"
					+ KEY_AGE+ " text not null"
					+ KEY_INSTITUTION+ " text not null"+");"

			+"create table " + DATABASE_TABLE_POST
	        + " (" + KEY_ROWID + " integer primary key autoincrement, "
				+ KEY_CURRENT_USER + " integer not null, "
				+ KEY_DATE + " text not null"
				+ KEY_TIME + " text not null"
				+ KEY_POST + " text not null" +");"

			+"create table " + DATABASE_TABLE_RIDE
					+ " (" + KEY_ROWID + " integer primary key autoincrement, "
					+ KEY_CURRENT_USER + " integer not null, "
					+ KEY_DATE_RIDE + " text not null"
					+ KEY_START_TIME + " text not null"
					+ KEY_FINISH_TIME + " text not null"
					+ KEY_START_TIME + " text not null"
					+ KEY_M + " text not null"
					+ KEY_T + " text not null"
					+ KEY_W+ " text not null"
					+ KEY_TD + " text not null"
					+ KEY_F+ " text not null"
					+ KEY_S + " text not null"
					+ KEY_SD + " text not null"
					+ KEY_NAME_RIDE + " text not null" +");";
	
	// Context of application who uses us.
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to the database.
	public long addUser(String name, String lastname,String email,String city,String user,String password,String type,String handicapped,String age,String institution) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_LASTNAME, lastname);
		initialValues.put(KEY_EMAIL, email);
		initialValues.put(KEY_CITY, city);
		initialValues.put(KEY_USER, user);
		initialValues.put(KEY_LASTNAME, password);
		initialValues.put(KEY_LASTNAME, type);
		initialValues.put(KEY_LASTNAME, handicapped);
		initialValues.put(KEY_LASTNAME, age);
		initialValues.put(KEY_LASTNAME, institution);
		// Insert it into the database.
		return db.insert(DATABASE_TABLE_USER, null, initialValues);
	}
	public long addPost(Integer id_current_user, String date,String time,String post ) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_CURRENT_USER, id_current_user);
		initialValues.put(KEY_DATE, date);
		initialValues.put(KEY_TIME, time);
		initialValues.put(KEY_POST, post);

		// Insert it into the database.
		return db.insert(DATABASE_TABLE_POST, null, initialValues);
	}
	public long addRide(Integer id_current_user,String date,String start_time,String end_time,String monday,String tuesday,String wednesday, String thursday,String friday,String saturday,String sunday,String name_ride){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_CURRENT_USER, id_current_user);
		initialValues.put(KEY_DATE_RIDE, date);
		initialValues.put(KEY_START_TIME, start_time);
		initialValues.put(KEY_FINISH_TIME, end_time);
		initialValues.put(KEY_M, monday);
		initialValues.put(KEY_T, tuesday);
		initialValues.put(KEY_W, wednesday);
		initialValues.put(KEY_TD, thursday);
		initialValues.put(KEY_F, friday);
		initialValues.put(KEY_S, saturday);
		initialValues.put(KEY_SD, sunday);
		initialValues.put(KEY_NAME_RIDE, name_ride);


		// Insert it into the database.
		return db.insert(DATABASE_TABLE_RIDE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE_USER, where, null) != 0;
	}
	
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE_USER, USER_KEYS,
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE_USER, USER_KEYS,
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, String name, String lastname) {
		String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NAME, name);
		newValues.put(KEY_LASTNAME, lastname);

		
		// Insert it into the database.
		return db.update(DATABASE_TABLE_USER, newValues, where, null) != 0;
	}
	
	
	
	/////////////////////////////////////////////////////////////////////
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_USER);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
