
public class TableAssignedException extends Exception {
	public TableAssignedException(){
		super("Table(s) already assigned for this booking!");
	}
	public TableAssignedException(String message) {	
		super(message);
	}
}
