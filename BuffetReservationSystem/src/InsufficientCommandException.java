
public class InsufficientCommandException extends Exception {
	public InsufficientCommandException(){
		super("Insufficient command arguments!");
	}
	public InsufficientCommandException(String message) {	
		super(message);
	}
}
