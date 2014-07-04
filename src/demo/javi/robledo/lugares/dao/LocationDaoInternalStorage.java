package demo.javi.robledo.lugares.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import demo.javi.robledo.lugares.bean.Location;

public class LocationDaoInternalStorage implements LocationDao {

	private Context context;
	private static final String FILE_PATH = "locations.dat";
	private static final String TEMPORARY_FILE_PATH = "locations.dat.tmp";
	private static final String FIELD_SEPARATOR = ";";
	private static final String RECORD_SEPARATOR = "!";

	public LocationDaoInternalStorage(Context context) {
		this.context = context;
	}

	@Override
	public void addLocation(Location location) {
		FileOutputStream outputStream;
		try {
			outputStream = context.openFileOutput(FILE_PATH,
					Context.MODE_APPEND);
			OutputStreamWriter writer = new OutputStreamWriter(outputStream);
			writer.write(toFileFormat(location));
			writer.write("\n");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Location> listLocations() {
		List<Location> result = new ArrayList<Location>();
		try {
			FileInputStream is = context.openFileInput(FILE_PATH);
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);

			String line;
			while ((line = br.readLine()) != null) {
				Location location = fromFileLine(line);
				result.add(location);
			}
		} catch (IOException e) {
			// TODO Gestionar error
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void deleteLocation(Location locationToDelete) {
		try {
			FileInputStream is = context.openFileInput(FILE_PATH);						
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);
			
			FileOutputStream outputStream = context.openFileOutput(TEMPORARY_FILE_PATH,
					Context.MODE_APPEND);
			OutputStreamWriter writer = new OutputStreamWriter(outputStream);

			String line;
			while ((line = br.readLine()) != null) {
				Location locationFromFile = fromFileLine(line);
				if(!locationFromFile.getFormattedAddres().equals(locationToDelete.getFormattedAddres())) {
					writer.write(line);
					writer.write("\n");
				}				
			}
			writer.flush();
			
			context.deleteFile(FILE_PATH);
			
			renameTemporalFile();
			
		} catch (IOException e) {
			// TODO Gestionar error
			e.printStackTrace();
		}
		
	}

	private String toFileFormat(Location location) {
		return location.getFormattedAddres() + FIELD_SEPARATOR
				+ Double.toString(location.getLatitude()) + FIELD_SEPARATOR
				+ Double.toString(location.getLongitude()) + RECORD_SEPARATOR;
	}

	private Location fromFileLine(String fileLine) {
		fileLine = fileLine.replace(RECORD_SEPARATOR, "");
		String[] fields = fileLine.split(FIELD_SEPARATOR);

		Location location = new Location();
		location.setFormattedAddres(fields[0]);
		location.setLatitude(Double.parseDouble(fields[1]));
		location.setLongitude(Double.parseDouble(fields[2]));

		return location;
	}

	private void renameTemporalFile() {
		File privateDir = context.getFilesDir();
		File fileToRename = new File(privateDir, TEMPORARY_FILE_PATH);
		File finalFile = new File(privateDir, FILE_PATH);
		fileToRename.renameTo(finalFile);
	}

}
