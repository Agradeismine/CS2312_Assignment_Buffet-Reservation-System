
public class CmdRequest extends RecordedCommand{
	String name, number, diningDate;
	int totalPersons;
	Reservation r;
	
	@Override
	public void execute(String[] cmdParts) {
		name = cmdParts[1];
		number = cmdParts[2];
		totalPersons = Integer.parseInt(cmdParts[3]);
		diningDate = cmdParts[4];
		BookingOffice bo = BookingOffice.getInstance();
		r = bo.addReservation(name,number, totalPersons, diningDate); 
		
		addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
		clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
		System.out.println("Done. Ticket code for "+ diningDate+": "+r.getTicketCode());
	}

	@Override
	public void undoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		boolean removed = bo.removeReservation(r);
		//System.out.println("undo: "+removed);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		r = bo.addReservation(name, number, totalPersons, diningDate); 
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}

}
