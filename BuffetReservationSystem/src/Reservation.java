public class Reservation implements Comparable <Reservation>{
	private String guestName;
	private String phoneNumber;
	private int totPersons;
	private Day dateDine;
	private Day dateRequest;
	private int ticketCode;
	private int [] tableStatus;

	public Reservation(String guestName, String phoneNumber, int totPersons, String sDateDine, int ticketCode)
	{	
		this.guestName = guestName;
		this.phoneNumber = phoneNumber;
		this.totPersons = totPersons;
		dateDine = new Day(sDateDine);
		dateRequest = SystemDate.getInstance().clone();
		this.ticketCode = ticketCode;
	}
	
	public Day getDateDine() {
		return dateDine;
	}
	
	public int getTicketCode() {
		return ticketCode;
	}

	@Override
	public String toString() 
	{
		
		return String.format("%-13s%-11s%-14s%-24s%5d%14s", guestName, phoneNumber, dateRequest, dateDine + " (Ticket "+ticketCode+")", totPersons, getTableStatus());
	}

	public static String getListingHeader() 
	{
		return String.format("%-13s%-11s%-14s%-25s%-11s%s", "Guest Name", "Phone", "Request Date", "Dining Date and Ticket", "#Persons", "Status");
	}
	
	public String getTableStatus() {
		if(tableStatus!=null) {
			return	"....";
		}else
			return "Pending";
	}

	@Override
	public int compareTo(Reservation another) {
		if (this.guestName.compareTo(another.guestName)==0){
			return this.dateDine.toString().compareTo(another.dateDine.toString());
		}else {
			return this.guestName.compareTo(another.guestName);
		}
	}
}
