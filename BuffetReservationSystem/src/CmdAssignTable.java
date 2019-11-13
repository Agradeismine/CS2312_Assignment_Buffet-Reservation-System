public class CmdAssignTable extends RecordedCommand{
	Reservation newR, previousR;
	
	@Override
	public void execute(String[] cmdParts) {
		BookingOffice bo = BookingOffice.getInstance();
		try {
			previousR = (Reservation) bo.getReservation(cmdParts[1], Integer.parseInt(cmdParts[2])).clone();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		newR = bo.assignTable(cmdParts); 
		addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
		clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
		System.out.println("Done.");
	}

	@Override
	public void undoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		bo.removeReservation(newR);
		bo.addReservation(previousR);
		//System.out.println("undo: "+removed);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		bo.removeReservation(previousR);
		bo.addReservation(newR);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}

}
