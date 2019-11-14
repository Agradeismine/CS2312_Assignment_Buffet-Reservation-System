public class CmdAssignTable extends RecordedCommand {
	Reservation r;
	String[] cmdParts;

	@Override
	public void execute(String[] cmdParts) {
		this.cmdParts = cmdParts;
		BookingOffice bo = BookingOffice.getInstance();
		r = bo.getReservation(cmdParts[1], Integer.parseInt(cmdParts[2])); // clone for undo
		try {
			if (r.getTableStatusArrayList().size() <= 0) {
				r = bo.assignTable(cmdParts);

				addUndoCommand(this);
				clearRedoList();
				System.out.println("Done.");
			} else {
				System.out.println("Table(s) already assigned for this booking!");
			}
		} catch (NullPointerException e) {
			System.out.println("Booking not found!");
		}
	}

	@Override
	public void undoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		bo.removeAssignTable(cmdParts);
		// System.out.println("undo: "+removed);
		addRedoCommand(this); // <====== upon undo, we should keep a copy in the redo list (addRedoCommand is
								// implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		r = bo.getReservation(cmdParts[1], Integer.parseInt(cmdParts[2])); // clone for undo
		r = bo.assignTable(cmdParts);
		addUndoCommand(this); // <====== upon redo, we should keep a copy in the undo list
	}

}
