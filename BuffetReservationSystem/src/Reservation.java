import java.util.ArrayList;

public class Reservation implements Cloneable, Comparable <Reservation>{
	private String guestName;
	private String phoneNumber;
	private int totPersons;
	private Day dateDine;
	private Day dateRequest;
	private int ticketCode;
	private ArrayList<String>tableStatus;

	public Reservation(String guestName, String phoneNumber, int totPersons, String sDateDine, int ticketCode)
	{	
		this.guestName = guestName;
		this.phoneNumber = phoneNumber;
		this.totPersons = totPersons;
		dateDine = new Day(sDateDine);
		dateRequest = SystemDate.getInstance().clone();
		this.ticketCode = ticketCode;
		tableStatus = new ArrayList<>();
	}
	
	public String getGuestName() {
		return guestName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public Day getDateDine() {
		return dateDine;
	}
	
	public int getTicketCode() {
		return ticketCode;
	}

	public void assignTable(String table) {
		tableStatus.add(table);
	}
	
	public boolean removeAssignedTable(String table) {
		return tableStatus.remove(table);
	}
	
	@Override
	public String toString() 
	{
		return String.format("%-13s%-11s%-14s%-24s%5d\t\t%s", guestName, phoneNumber, dateRequest, dateDine + " (Ticket "+ticketCode+")", totPersons, getTableStatus());
	}

	public static String getListingHeader() 
	{
		return String.format("%-13s%-11s%-14s%-25s%-11s\t%s", "Guest Name", "Phone", "Request Date", "Dining Date and Ticket", "#Persons", "Status");
	}
	
	public String getTableStatus() {
		if(tableStatus.size()>0) {
			String str="Table assigned: ";
			for(String temp: tableStatus ) {
				str+=temp+" ";
			}
			return	str;
		}else
			return "Pending";
	}
	
	public ArrayList<String> getTableStatusArrayList() {
		return tableStatus;
	}

	@Override
	public int compareTo(Reservation another) {
		if (this.guestName.compareTo(another.guestName)==0){
			if(this.phoneNumber.compareTo(another.getPhoneNumber())==0)
				if(this.dateDine.getYear()==(another.dateDine.getYear())) {
					if(this.dateDine.getMonth()==(another.dateDine.getMonth()))
						return String.valueOf(this.dateDine.getDay()).compareTo(String.valueOf(another.dateDine.getDay()));
					else {
						return String.valueOf(this.dateDine.getMonth()).compareTo(String.valueOf(another.dateDine.getMonth()));
					}
				}else 
					return String.valueOf(this.dateDine.getYear()).compareTo(String.valueOf(another.dateDine.getYear()));
			else
				return this.phoneNumber.compareTo(another.getPhoneNumber());
		}else {
			return this.guestName.compareTo(another.guestName);
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {

	    return super.clone();
	}

	public int getTotalPersons() {
		return totPersons;
	}
}
