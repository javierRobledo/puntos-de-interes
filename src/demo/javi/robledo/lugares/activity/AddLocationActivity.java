package demo.javi.robledo.lugares.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import demo.javi.robledo.lugares.R;
import demo.javi.robledo.lugares.bean.Location;
import demo.javi.robledo.lugares.business.LocationService;
import demo.javi.robledo.lugares.business.LocationServiceImpl;

public class AddLocationActivity extends Activity {

	private LocationService locationService;
	private LocationMapFragment mapFragment;
	private Location activeLocation;
	
	public void addLocation(View view) {
		locationService.addLocation(activeLocation);
		
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.location_description_container);
		linearLayout.setVisibility(View.INVISIBLE);
		
		Toast toast = Toast.makeText(this, R.string.addLocation_confirm_ok, Toast.LENGTH_LONG);
		toast.show();
	}

	public void onSearchLocationClick(View view) {
		cleanPreviousMarkers();

		if(isConnected()) {
			View progressBar = findViewById(R.id.progressBar);
			progressBar.setVisibility(View.VISIBLE);
			
			view.setVisibility(View.INVISIBLE);
			
			EditText editText = (EditText) findViewById(R.id.editText1);
			String location = editText.getText().toString();
			
			if(location != null && !location.isEmpty()) {
				searchLocation(location);
			} else {
				showErrorToast(R.string.addLocation_empty_location);				
			}
		} else {
			showErrorToast(R.string.addLocation_no_connectivity);
		}		
	}

	private void searchLocation(String location) {
		AsyncTask<String, Void, Location> getLocationDetails = new AsyncTask<String, Void, Location>() {
			protected Location doInBackground(String... params) {
				return locationService.getLocationDetails(params[0]);
			}

			@Override
			protected void onPostExecute(Location result) {
				onGetLocationDetailsResult(result);
			}
		};	
		getLocationDetails.execute(location);
	}

	private void showErrorToast(int resId) {
		Toast toast = Toast.makeText(this, resId, Toast.LENGTH_LONG);
		toast.show();
	}
	
	private void onGetLocationDetailsResult(Location location) {
		addMarker(location);		
		activeLocation = location;
		
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.location_description_container);
		linearLayout.setVisibility(View.VISIBLE);
		
		TextView textView = (TextView) findViewById(R.id.location_description);
		textView.setText(location.getFormattedAddres());
		
		View progressBar = findViewById(R.id.progressBar);
		progressBar.setVisibility(View.INVISIBLE);
		
		View searchButton = findViewById(R.id.searchBtn);
		searchButton.setVisibility(View.VISIBLE);
	}

	private void addMarker(Location location) {
		mapFragment.addMarkerAndCenter(location);
	}

	private void cleanPreviousMarkers() {
		mapFragment.cleanMarkers();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_location);

		locationService = new LocationServiceImpl(this);

		if (savedInstanceState == null) {
			mapFragment = new LocationMapFragment();
			getFragmentManager()
					.beginTransaction()
					.add(R.id.frameAddLocation, new PlaceholderFragment(),
							"placeHolder")
					.add(R.id.frameMap, mapFragment, "map").commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_location, menu);
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
	
	private boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);		
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_location,
					container, false);
			return rootView;
		}
	}

}
