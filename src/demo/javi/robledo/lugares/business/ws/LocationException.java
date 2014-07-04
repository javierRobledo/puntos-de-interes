package demo.javi.robledo.lugares.business.ws;

public class LocationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5702343702061055210L;
	
	public LocationException(){
		super();
	}
	
	public LocationException(String message) {
		super(message);
	}
	
	public LocationException(Throwable cause) {
		super(cause);
	}
	
	public LocationException(String message, Throwable cause) {
		super(message, cause);
	}
}
