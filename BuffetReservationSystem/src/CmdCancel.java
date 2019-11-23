public class CmdCancel extends RecordedCommand {
	Reservation r;

	@Override
	public void execute(String[] cmdParts) {
		BookingOffice bo = BookingOffice.getInstance();
		try {
			if (cmdParts.length == 3) {
				SystemDate instance = SystemDate.getInstance();
				if (!instance.isPreviousDate(Integer.parseInt(cmdParts[1].substring(7, 11)),
						cmdParts[1].substring(3, 6), Integer.parseInt(cmdParts[1].substring(0, 2)))) {
					r = (Reservation) bo.getReservation(cmdParts[1], Integer.parseInt(cmdParts[2])).clone();
					bo.removeReservation(bo.getReservation(cmdParts[1], Integer.parseInt(cmdParts[2])));

					addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
											// RecordedCommand.java)
					clearRedoList();
					System.out.println("Done.");
				} else {
					throw new DateIncorrectException();
				}
			} else {
				throw new InsufficientCommandException();
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (BookingNotFoundException e) {

		} catch (InsufficientCommandException e) {
			System.out.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (DateIncorrectException e) {
		}
	}

	@Override
	public void undoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		bo.addReservation(r);
		// System.out.println("undo: "+removed);
		addRedoCommand(this); // <====== upon undo, we should keep a copy in the redo list (addRedoCommand is
								// implemented in RecordedCommand.java)
	}

	@Override
	public void redoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		bo.removeReservation(r);
		addUndoCommand(this); // <====== upon redo, we should keep a copy in the undo list
	}

}
