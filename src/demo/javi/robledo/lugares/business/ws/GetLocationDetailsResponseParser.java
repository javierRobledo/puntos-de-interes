package demo.javi.robledo.lugares.business.ws;

import org.json.JSONException;
import org.json.JSONObject;

import demo.javi.robledo.lugares.bean.Location;

public class GetLocationDetailsResponseParser {

	public Location parseResponse(JSONObject jsonResponse) {
        try {
			String statusResponse = jsonResponse.getString("status");
			if(statusResponse.equals("OK")) {
				JSONObject result = jsonResponse.getJSONArray("results").getJSONObject(0);
				JSONObject coordinates = result.getJSONObject("geometry").getJSONObject("location");
				
				Location location = new Location();
				location.setFormattedAddres(result.getString("formatted_address"));
				location.setLatitude(Double.parseDouble(coordinates.getString("lat")));
				location.setLongitude(Double.parseDouble(coordinates.getString("lng")));
				
				return location;
			} else {
				throw new LocationException("El API de Google Maps ha devuelto un estado inesperado: "+statusResponse);
			}
		} catch (JSONException e) {
			throw new LocationException("Error inesperado al parsear la respuesta de Google Maps",e);
		}

	}
}
