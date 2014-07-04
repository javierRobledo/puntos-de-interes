package demo.javi.robledo.lugares.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import demo.javi.robledo.lugares.bean.Location;
import demo.javi.robledo.lugares.db.LocationContract;
import demo.javi.robledo.lugares.db.LocationSQLiteHelper;

public class LocationDaoDB implements LocationDao {

	private LocationSQLiteHelper dbHelper;
	private SQLiteDatabase db;

	public LocationDaoDB(Context context) {
		dbHelper = new LocationSQLiteHelper(context);				
	}
	
		
	@Override
	public void addLocation(Location location) {		
		ContentValues values = new ContentValues();
		values.put(LocationContract.LocationEntry.COLUMN_NAME_ADDRESS,
				location.getFormattedAddres());
		values.put(LocationContract.LocationEntry.COLUMN_NAME_LATITUDE,
				location.getLatitude());
		values.put(LocationContract.LocationEntry.COLUMN_NAME_LONGITUDE,
				location.getLongitude());

		db = dbHelper.getWritableDatabase();
		long rowId = db.insert(LocationContract.LocationEntry.TABLE_NAME, null,
				values);
		db.close();
	}

	@Override
	public List<Location> listLocations() {
		String projection[] = {
				LocationContract.LocationEntry.COLUMN_NAME_ADDRESS,
				LocationContract.LocationEntry.COLUMN_NAME_LATITUDE,
				LocationContract.LocationEntry.COLUMN_NAME_LONGITUDE };

		db = dbHelper.getReadableDatabase();
		Cursor c = db.query(LocationContract.LocationEntry.TABLE_NAME,
				projection, null, null, null, null, null);		
		
		c.moveToFirst();

		List<Location> result = new ArrayList<Location>();
		while (!c.isAfterLast()) {
			Location location = new Location();
			location.setFormattedAddres(c.getString(c
					.getColumnIndex(LocationContract.LocationEntry.COLUMN_NAME_ADDRESS)));
			location.setLatitude(c.getDouble(c
					.getColumnIndex(LocationContract.LocationEntry.COLUMN_NAME_LATITUDE)));
			location.setLongitude(c.getDouble(c
					.getColumnIndex(LocationContract.LocationEntry.COLUMN_NAME_LONGITUDE)));
			result.add(location);
			c.moveToNext();
		}
		db.close();
		return result;
	}

	@Override
	public void deleteLocation(Location location) {
		String selection = LocationContract.LocationEntry.COLUMN_NAME_ADDRESS + " = ?";
		String[] selectionArgs = {location.getFormattedAddres()};
		
		db = dbHelper.getWritableDatabase();
		db.delete(LocationContract.LocationEntry.TABLE_NAME, selection, selectionArgs);
		db.close();
	}

}
