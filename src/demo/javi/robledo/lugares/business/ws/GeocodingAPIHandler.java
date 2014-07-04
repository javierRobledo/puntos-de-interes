package demo.javi.robledo.lugares.business.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

public class GeocodingAPIHandler {
	
	private static final String SENSOR_QUERY_VALUE = "false";
	private static final String SENSOR_QUERY_NAME = "sensor";
	private static final String ADDRESS_QUERY_NAME = "address";
	private static final String MAPS_WS_INITIAL_URI = "http://maps.googleapis.com/maps/api/geocode/json";
	
	public JSONObject getLocationDetails(String partialAddress) {
		if(partialAddress == null || partialAddress.isEmpty()) {
			throw new IllegalArgumentException("The partial addresss can't be null");
		}
		
		HttpURLConnection connection = null;
		InputStream is = null;
		
		Uri uri = Uri.parse(MAPS_WS_INITIAL_URI).buildUpon().appendQueryParameter(ADDRESS_QUERY_NAME, partialAddress).appendQueryParameter(SENSOR_QUERY_NAME, SENSOR_QUERY_VALUE).build();
		
		JSONObject result = null;
		try {
			URL url = new URL(uri.toString());
			connection = (HttpURLConnection) url.openConnection();
			
			connection.connect();
			
			is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			 
            StringBuffer sb = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
 
            String response = sb.toString();
            is.close();
            br.close();
            
            result = new JSONObject(response);		
		} catch (IOException e) {
			throw new LocationException("Error inesperado al conectar con el API de geocodificación", e);
		} catch (JSONException e) {
			throw new LocationException("Error inesperado al decodificar la respuesta del API de geocodificación", e);
		} finally {
			if (connection != null){
				connection.disconnect();
			}
		}
		
		return result;
	}
}
