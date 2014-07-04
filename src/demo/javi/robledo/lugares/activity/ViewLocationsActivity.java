package demo.javi.robledo.lugares.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import demo.javi.robledo.lugares.R;
import demo.javi.robledo.lugares.bean.Location;
import demo.javi.robledo.lugares.business.LocationService;
import demo.javi.robledo.lugares.business.LocationServiceImpl;

public class ViewLocationsActivity extends Activity {
	
	private LocationService locationService;	
	private LocationMapFragment mapFragment;
	
	private Marker activeMarker;
	private Location activeLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_locations);
		locationService = new LocationServiceImpl(this);
		
		if (savedInstanceState == null) {
			mapFragment = new LocationMapFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.frameMap, mapFragment).commit();						
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mapFragment.getMap().setOnMarkerClickListener(new OnMarkerClickListener());
		mapFragment.getMap().setOnMapClickListener(new OnMapClickListener());
		createMarkers();
	}

	private void createMarkers() {
		List<Location> locations = locationService.getLocations();
		
		for(int i = 0; i<locations.size(); i++){
			mapFragment.addMarker(locations.get(i));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_locations, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onDeleteLocationClick(View view) {
		locationService.removeLocation(activeLocation);
		activeMarker.remove();
		clearActiveLocation();
	}

	private void setActiveLocation(Location location) {
		activeLocation = location;
		TextView locationDescriptionView = (TextView) findViewById(R.id.location_description_txt);
		locationDescriptionView.setText(location.getFormattedAddres());
		
		LinearLayout locationDescriptionLayout = (LinearLayout) findViewById(R.id.location_description_container);
		locationDescriptionLayout.setVisibility(View.VISIBLE);
	}
	
	private void clearActiveLocation() {
		activeLocation = null;
		LinearLayout locationDescriptionLayout = (LinearLayout) findViewById(R.id.location_description_container);
		locationDescriptionLayout.setVisibility(View.INVISIBLE);
	}
	
	public class OnMarkerClickListener implements GoogleMap.OnMarkerClickListener {
		@Override
		public boolean onMarkerClick(Marker marker) {
			Location location = new Location();
			location.setFormattedAddres(marker.getTitle());
			location.setLatitude(marker.getPosition().latitude);
			location.setLongitude(marker.getPosition().longitude);
			setActiveLocation(location);
			activeMarker = marker;
			return true;
		}		
	}	
	
	public class OnMapClickListener implements GoogleMap.OnMapClickListener {
		@Override
		public void onMapClick(LatLng point) {
			activeMarker = null;
			clearActiveLocation();
			
		}
		
	}
	
}
