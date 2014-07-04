package demo.javi.robledo.lugares.dao;

import java.util.List;

import demo.javi.robledo.lugares.bean.Location;

public interface LocationDao {

	public void addLocation(Location location);
	
	public List<Location> listLocations();
	
	public void deleteLocation(Location location);
}
