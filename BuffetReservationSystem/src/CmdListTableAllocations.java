public class CmdListTableAllocations implements Command{
	
	@Override
	public void execute(String[] cmdParts) {	//cmdParts: listTableAllocations|24-Mar-2019
		BookingOffice bo = BookingOffice.getInstance();
		bo.listTableAllocations(cmdParts[1]);
	}

}