public class TableReservedException extends Exception {
	public TableReservedException(String s) 
    { 
        // Call constructor of parent Exception 
        super(); 
		System.out.println("Table "+s+" is already reserved by another booking!");
    } 
}
