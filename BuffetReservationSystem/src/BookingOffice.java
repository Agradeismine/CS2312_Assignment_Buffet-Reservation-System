import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections; //Provides sorting
import java.util.Comparator;
import java.util.HashMap;

public class BookingOffice {

	private ArrayList<Reservation> allReservations;
	private HashMap<String, Integer> dayTicketCode;
	private HashMap<String, TableStatus> dateTableStatus;

	private static BookingOffice instance = new BookingOffice();

	private BookingOffice() {
		allReservations = new ArrayList();
		dayTicketCode = new HashMap<String, Integer>();
		dateTableStatus = new HashMap<String, TableStatus>();
	}

	public static BookingOffice getInstance() {
		return instance;
	}

	public HashMap<String, Integer> getDayTicketCode() {
		return dayTicketCode;
	}

	public HashMap<String, TableStatus> getDateTableStatus() {
		return dateTableStatus;
	}

	public Reservation assignTable(String[] cmdParts) { // assignTable|22-Mar-2019|2|T01|T02
		Reservation r = getReservation(cmdParts[1], Integer.parseInt(cmdParts[2]));
		try {
		if(r==null) {
			throw new BookingNotFoundException();
		}
		
		if (!dateTableStatus.containsKey(cmdParts[1])) {
			dateTableStatus.put(cmdParts[1], new TableStatus());
		}
		
		//check all tables is it available
		//boolean allTableAvailable = true;
		ArrayList <String> availableTable = dateTableStatus.get(cmdParts[1]).getAvailableTables();
		for (int i =3; i<cmdParts.length; i++ ) {
			if(!availableTable.contains(cmdParts[i])) {	//set allTableAvailable to false if one of tables is allocated
				//allTableAvailable=false;
				throw new TableReservedException(cmdParts[i]);
			}
		}

		//if(allTableAvailable==true){
		TableStatus tableStatus = dateTableStatus.get(cmdParts[1]);
			for (int i = 3; i < cmdParts.length; i++) {
				if (tableStatus.addAllocatedTables(cmdParts[i])) { // save the status in Reservation if assign table successfully
					r.assignTable(cmdParts[i]);
				}
			}
			dateTableStatus.replace(cmdParts[1], tableStatus);
		//}else {	//some of tables reserved
		//	throw new TableReservedException();
		//}
		
		}catch(BookingNotFoundException e) {
		} catch (TableReservedException e) {
		}
		return r;
	}

	public void removeAssignTable(String[] cmdParts) { // cmdParts: assignTable|22-Mar-2019|2|T01|T02
		Reservation r = getReservation(cmdParts[1], Integer.parseInt(cmdParts[2]));
		TableStatus tableStatus = dateTableStatus.get(cmdParts[1]);
		for (int i = 3; i < cmdParts.length; i++) {
			if (tableStatus.removeAllocatedTables(cmdParts[i])) { // save the status in Reservation if assign table
																	// successfully
				r.removeAssignedTable(cmdParts[i]);
			}
		}
		Collections.sort(tableStatus.getAvailableTables(), new TableSorter());
		dateTableStatus.replace(cmdParts[1], tableStatus);
	}

	public Reservation addReservation(String name, String num, int totalPersons, String sDate) // bo.addReservation("LEE,
																								// Mr","90888000", 10,
																								// "18-Mar-2019");
	{
		Reservation r = null;
		if (!hasSamePersonForDinDate(name, num, sDate)) {
			if (!dayTicketCode.containsKey(sDate)) {
				dayTicketCode.put(sDate, 0);
			}

			dayTicketCode.replace(sDate, dayTicketCode.get(sDate) + 1);
			r = new Reservation(name, num, totalPersons, sDate, (int) dayTicketCode.get(sDate));
			allReservations.add(r);
			Collections.sort(allReservations);
		}
		return r; // Why return? Ans: You'll see that it is useful in CmdRequest.java in Q2.
	}

	private boolean hasSamePersonForDinDate(String name, String num, String sDate) {
		for (Reservation r : allReservations) {
			if (r.getGuestName().equals(name) && r.getPhoneNumber().equals(num)
					&& r.getDateDine().toString().equals(sDate)) {
				return true;
			}
		}
		return false;
	}

	public void addReservation(Reservation r) // bo.addReservation(r);
	{
		allReservations.add(r);
		Collections.sort(allReservations);
	}

	public boolean removeReservation(Reservation r) // bo.addReservation("LEE, Mr","90888000", 10, "18-Mar-2019");
	{
		//dayTicketCode.replace(r.getDateDine().toString(), dayTicketCode.get(r.getDateDine().toString()) - 1);
		boolean removed = allReservations.remove(r);
		Collections.sort(allReservations);
		return removed;
	}

	public Reservation getReservation(String dinDate, int ticketCode) // bo.getReservation("22-Mar-2019", 3);
	{
		Reservation r = null;
		for (Reservation e : allReservations) {
			if (e.getDateDine().toString().equals(dinDate) && e.getTicketCode() == ticketCode) {
				r = e;
			}
		}
		Collections.sort(allReservations);
		return r;
	}

	public void listReservations() {
		System.out.println(Reservation.getListingHeader());
		for (Reservation r : allReservations)
			System.out.println(r); // r or r.toString()
	}

	public void listTableAllocations(String dinDate) {
		// list Allocated tables
		boolean hasAllocatedTable = false;
		ArrayList <String> tempAllocatedTblList = new ArrayList<>();

		
		System.out.println("Allocated tables: ");
		for (Reservation r : allReservations) {
			if (r.getDateDine().toString().equals(dinDate)) {
				ArrayList<String> arrList = r.getTableStatusArrayList();
				if (arrList.size() > 0) {
					hasAllocatedTable = true;
					for (int i = 0; i < arrList.size(); i++) {
						//System.out.println(arrList.get(i) + " (Ticket " + r.getTicketCode() + ")");
						tempAllocatedTblList.add(arrList.get(i) + " (Ticket " + r.getTicketCode() + ")");
					}
				}
			}
		}
		Collections.sort(tempAllocatedTblList, new TableSorter());
		for(String s : tempAllocatedTblList) {
			System.out.println(s);
		}
		
		if (!hasAllocatedTable) { // no allocated table
			System.out.println("[None]");
		}

		// list available tables
		System.out.println();
		System.out.println("Available tables: ");
		TableStatus ts = dateTableStatus.get(dinDate);
		if (ts != null) {
			ts.printAvailableTables();
		} else {
			TableStatus.printallTables();
		}

		// list total number of pending requests
		int countPending = 0, totalPendingPersons = 0;
		System.out.println();
		System.out.print("Total number of pending requests = ");
		//if (ts != null) {
			for (Reservation r : allReservations) { // count all pending requests on dinDate
				if (r.getDateDine().toString().equals(dinDate)) {
					ArrayList<String> arrList = r.getTableStatusArrayList();
					if (arrList.size() == 0) {
						countPending++;
						totalPendingPersons += r.getTotalPersons();
					}
				}
			}
		//}
		System.out.println(countPending + " (Total number of persons = " + totalPendingPersons + ")");
	}

	public void suggestTable(String dinDate, int ticketCode) {
		Reservation r = getReservation(dinDate, ticketCode);
		TableStatus tablestatus = getDateTableStatus().get(dinDate);
		ArrayList<String> availableTables;
		if (tablestatus != null) {
			availableTables = (ArrayList<String>) tablestatus.getAvailableTables().clone();
		} else {
			availableTables = (ArrayList<String>) TableStatus.getAllTables().clone();
		}
		int totalPersons = r.getTotalPersons();
		String suggestTables = "";
		System.out.println("availableTables size: " + availableTables.size()); // test
		System.out.print("Suggestion for " + totalPersons + " persons: ");
		while (totalPersons >= 10) {
			for (int i = 0; i < availableTables.size(); i++) {
				String s = availableTables.get(i);
				if (availableTables.get(i).charAt(0) == 'H') { // check has eight people table
					suggestTables += availableTables.remove(i) + " ";
					totalPersons -= 8;
					// System.out.println("remaining person: "+totalPersons);
					break;
				}
			}
		}

		while (totalPersons >= 4) {
			for (int i = 0; i < availableTables.size(); i++) {
				if (availableTables.get(i).charAt(0) == 'F') { // check has eight people table
					suggestTables += availableTables.remove(i) + " ";
					totalPersons -= 4;
					// System.out.println("remaining person: "+totalPersons);
					break;
				}
			}
		}

		while (totalPersons > 0) {
			for (int i = 0; i < availableTables.size(); i++) {
				if (availableTables.get(i).charAt(0) == 'T') { // check has eight people table
					suggestTables += availableTables.remove(i) + " ";
					totalPersons -= 2;
					// System.out.println("remaining person: "+totalPersons);
					break;
				}
			}
		}

		System.out.println(suggestTables);
	}

}
