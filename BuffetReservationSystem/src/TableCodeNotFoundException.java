public class TableCodeNotFoundException extends Exception {
	public TableCodeNotFoundException(String s) 
    { 
        // Call constructor of parent Exception 
        super(); 
		System.out.println("Table code "+s+" not found!");
    } 
}
