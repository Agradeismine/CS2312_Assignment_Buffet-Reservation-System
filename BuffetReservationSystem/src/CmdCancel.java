public class CmdCancel extends RecordedCommand{
	Reservation r;
	
	@Override
	public void execute(String[] cmdParts) {
		BookingOffice bo = BookingOffice.getInstance();
		r = bo.getReservation(cmdParts[1], Integer.parseInt(cmdParts[2]));
		bo.removeReservation(r);
		
		addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
		clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
		System.out.println("Done.");
	}

	@Override
	public void undoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		bo.addReservation(r);
		//System.out.println("undo: "+removed);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		bo.removeReservation(r);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}

}
