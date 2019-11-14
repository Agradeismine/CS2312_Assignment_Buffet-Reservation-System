
public class DateIncorrectException extends Exception {
	public DateIncorrectException() 
    { 
        // Call constructor of parent Exception 
        super(); 
		System.out.println("Date has already passed!");
    } 
}
