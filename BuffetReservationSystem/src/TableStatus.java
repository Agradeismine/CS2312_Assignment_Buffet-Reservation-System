import java.util.*;

public class TableStatus implements Comparator<String>{
	//10tables for 2ppl, 6tables for 4ppl, 3tables for 8ppl
	private static final ArrayList<String> allTables = new ArrayList<String>(Arrays.asList("T01", "T02", "T03", "T04", "T05", "T06", "T07", "T08", "T09", "T10", "F01", "F02", "F03", "F04", "F05", "F06", "H01", "H02", "H03"));
	private ArrayList<String> availableTables;
	private ArrayList<String> allocatedTables;
	public TableStatus() {
		availableTables = (ArrayList<String>) allTables.clone();
		allocatedTables = new ArrayList<String>();
	}
	
	public boolean hasThisTable(String target) {
		if(allTables.contains(target)) {
			return true;
		}
		return false;
	}
	
	public static ArrayList<String> getAllTables() {
		return allTables;
	}
	
	public ArrayList<String> getAllocatedTables() {
		return allocatedTables;
	}
	
	public ArrayList<String> getAvailableTables() {
		return availableTables;
	}
	
	public boolean addAllocatedTables(String table) {
		if(hasThisTable(table)) {
			allocatedTables.add(table);
			availableTables.remove(table);
			return true;
		}
		return false;
	}
	
	public boolean removeAllocatedTables(String table) {
		if(hasThisTable(table)) {
			allocatedTables.remove(table);
			availableTables.add(table);
			return true;
		}
		return false;
	}
	
	public void addAvailableTables(String table) {
		availableTables.add(table);
		Collections.sort(availableTables);
	}
	
	public void removeAvailableTables(String table) {
		availableTables.remove(table);
	}
	
	public static void printallTables() {
		String str="";
		for(String temp : allTables) {
			str+=temp+" ";
		}
		
		System.out.println(str);
	}
	
	public void printAvailableTables() {
		String str="";
		for(String temp : availableTables) {
			str+=temp+" ";
		}
		
		System.out.println(str);
	}

	@Override
	public int compare(String s1, String s2) {
		if(s1.substring(0, 1).equals("T")) {
			return s2.compareTo(s1);
		}
		return s1.compareTo(s2);
	}

	
}
