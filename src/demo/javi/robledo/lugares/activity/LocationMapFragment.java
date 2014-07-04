package demo.javi.robledo.lugares.activity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import demo.javi.robledo.lugares.bean.Location;

public class LocationMapFragment extends MapFragment {
	
	public void addMarkerAndCenter(Location location){
		addMarker(location);
		centerCamera(location);
	}
	
	public void addMarker(Location location) {
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		MarkerOptions markerOptions = new MarkerOptions().position(latLng)
				.title(location.getFormattedAddres());
		getMap().addMarker(markerOptions);
	}
	
	public void centerCamera(Location location) {
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
		getMap().animateCamera(cameraUpdate);
	}

	public void cleanMarkers() {
		getMap().clear();
	}

}
