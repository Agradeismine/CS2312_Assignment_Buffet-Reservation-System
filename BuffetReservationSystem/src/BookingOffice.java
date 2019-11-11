import java.util.ArrayList;
import java.util.Collections; //Provides sorting
import java.util.HashMap;

public class BookingOffice {

	private ArrayList<Reservation> allReservations;
	private HashMap<String, Integer> dayTicketCode;

	private static BookingOffice instance = new BookingOffice();

	private BookingOffice() {
		allReservations = new ArrayList();
		dayTicketCode = new HashMap<String, Integer>();
	}

	public static BookingOffice getInstance(){ return instance; }

	public HashMap<String, Integer> getDayTicketCode(){ return dayTicketCode; }

	public Reservation addReservation(String name, String num, int totalPersons, String sDate)  //bo.addReservation("LEE, Mr","90888000", 10, "18-Mar-2019"); 
	{
		if(!dayTicketCode.containsKey(sDate)) {
			dayTicketCode.put(sDate, 0);
		}
		
		dayTicketCode.replace(sDate, dayTicketCode.get(sDate)+1);	//(Integer)dayTicketCode.get(sDate)+1
		Reservation r = new Reservation(name, num, totalPersons, sDate, (int)dayTicketCode.get(sDate));
		allReservations.add(r);
		Collections.sort(allReservations);
		return r; //Why return?  Ans: You'll see that it is useful in CmdRequest.java in Q2. 
	}
	
	public boolean removeReservation(Reservation r)  //bo.addReservation("LEE, Mr","90888000", 10, "18-Mar-2019"); 
	{
		boolean removed = allReservations.remove(r);
		Collections.sort(allReservations);
		return removed;
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
