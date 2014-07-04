package demo.javi.robledo.lugares.business;

import java.util.List;

import demo.javi.robledo.lugares.bean.Location;
import demo.javi.robledo.lugares.business.ws.LocationException;


public interface LocationService {

	public Location getLocationDetails(String partialName) throws LocationException;
	
	public void addLocation(Location location) throws LocationException;
	
	public List<Location> getLocations();
	
	public void removeLocation(Location location);
}
