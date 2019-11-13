import java.util.ArrayList;
import java.util.Collections; //Provides sorting
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

	public static BookingOffice getInstance(){ return instance; }

	public HashMap<String, Integer> getDayTicketCode(){ return dayTicketCode; }
	public HashMap<String, TableStatus> getDateTableStatus(){ return dateTableStatus; }
	
	public Reservation assignTable (String [] cmdParts) {
		Reservation r=getReservation(cmdParts[1], Integer.parseInt(cmdParts[2]));
		for	(int i=3; i< cmdParts.length; i++) {
			r.assignTable(cmdParts[i]);
		}
		return r;
	}

	public Reservation addReservation(String name, String num, int totalPersons, String sDate)  //bo.addReservation("LEE, Mr","90888000", 10, "18-Mar-2019"); 
	{
		if(!dayTicketCode.containsKey(sDate)) {
			dayTicketCode.put(sDate, 0);
		}
		
		dayTicketCode.replace(sDate, dayTicketCode.get(sDate)+1);
		Reservation r = new Reservation(name, num, totalPersons, sDate, (int)dayTicketCode.get(sDate));
		allReservations.add(r);
		Collections.sort(allReservations);
		return r; //Why return?  Ans: You'll see that it is useful in CmdRequest.java in Q2. 
	}
	
	public void addReservation(Reservation r)  //bo.addReservation(r); 
	{		
		allReservations.add(r);
		Collections.sort(allReservations);
	}
	
	public boolean removeReservation(Reservation r)  //bo.addReservation("LEE, Mr","90888000", 10, "18-Mar-2019"); 
	{
		boolean removed = allReservations.remove(r);
		Collections.sort(allReservations);
		return removed;
	}
	
	public Reservation getReservation(String dinDate, int ticketCode)  //bo.getReservation("22-Mar-2019", 3); 
	{
		Reservation r = null;
		for(Reservation e : allReservations) {
			if(e.getDateDine().toString().equals(dinDate) && e.getTicketCode()==ticketCode) {
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
	
//	public int getTotalReserveInDday(String diningDate) {
//		int count=0;
//		for	(Reservation r:allReservations) {
//			//System.out.println("diningDate: "+diningDate+", r.getDateDine(): "+r.getDateDine());	//test
//			if(r.getDateDine().toString().equals(diningDate)) {
//				count++;
//			}
//		}
//		return count;
//	}
}
