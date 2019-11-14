
public class SameBookingOnDateException extends Exception{
	public SameBookingOnDateException() 
    { 
        // Call constructor of parent Exception 
        super(); 
        System.out.println("Booking by the same person for the dining date already exists!");
    } 
}
