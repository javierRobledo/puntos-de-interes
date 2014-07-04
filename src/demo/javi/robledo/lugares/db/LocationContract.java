package demo.javi.robledo.lugares.db;

import android.provider.BaseColumns;

public final class LocationContract {
	private LocationContract() {
	}
	
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String REAL_TYPE = " REAL";
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";

	
	public static abstract class LocationEntry implements BaseColumns {
		public static final String TABLE_NAME = "location";
		public static final String COLUMN_NAME_ADDRESS = "address";
		public static final String COLUMN_NAME_LATITUDE = "latitude";
		public static final String COLUMN_NAME_LONGITUDE = "longitude";
		
		public static final String SQL_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
				+ _ID + INTEGER_TYPE + " PRIMARY KEY "+COMMA_SEP
				+ COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA_SEP
				+ COLUMN_NAME_LATITUDE + REAL_TYPE + COMMA_SEP
				+ COLUMN_NAME_LONGITUDE + REAL_TYPE
				+")";
		
		public static final String SQL_DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME; 
	}
}
