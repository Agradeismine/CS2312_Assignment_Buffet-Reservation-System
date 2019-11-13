import java.util.*;

public class TableStatus {
	//10tables for 2ppl, 6tables for 4ppl, 3tables for 8ppl
	private final ArrayList<String> allTables = new ArrayList<String>(Arrays.asList("T01", "T02", "T03", "T04", "T05", "T06", "T07", "T08", "T09", "T10", "F01", "F02", "F03", "F04", "F05", "F06", "H01", "H02", "H03"));
	private ArrayList<String> availableTables;
	private ArrayList<String> AllocatedTables;
	public TableStatus() {
		availableTables = allTables;
		AllocatedTables = null;
	}
	public ArrayList<String> getAllocatedTables() {
		return AllocatedTables;
	}
	public void addAllocatedTables(String table) {
		AllocatedTables.add(table);
	}
	
	public void removeAllocatedTables(String table) {
		AllocatedTables.remove(table);
	}
	
	public void addAvailableTables(String table) {
		availableTables.add(table);
	}
	
	public void removeAvailableTables(String table) {
		availableTables.remove(table);
	}
	
	
	
	
}
