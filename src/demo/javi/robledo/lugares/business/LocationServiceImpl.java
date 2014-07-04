package demo.javi.robledo.lugares.business;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import demo.javi.robledo.lugares.bean.Location;
import demo.javi.robledo.lugares.business.ws.GeocodingAPIHandler;
import demo.javi.robledo.lugares.business.ws.GetLocationDetailsResponseParser;
import demo.javi.robledo.lugares.dao.LocationDao;
import demo.javi.robledo.lugares.dao.LocationDaoDB;

public class LocationServiceImpl implements LocationService {

	private LocationDao locationDao;
	private GeocodingAPIHandler geocodingAPI;
	private Context context;
	
	public LocationServiceImpl(Context context) {
		this.context = context;
		locationDao = new LocationDaoDB(this.context);
		geocodingAPI = new GeocodingAPIHandler();
	}
	@Override
	public void addLocation(Location location) {
		locationDao.addLocation(location);
	}

	@Override
	public List<Location> getLocations() {
		return locationDao.listLocations();
	}
	@Override
	public Location getLocationDetails(String partialName) {
		JSONObject response = geocodingAPI.getLocationDetails(partialName);
		GetLocationDetailsResponseParser parser = new GetLocationDetailsResponseParser();
		return parser.parseResponse(response);
	}
	
	@Override
	public void removeLocation(Location location) {
		locationDao.deleteLocation(location);
	}

}
