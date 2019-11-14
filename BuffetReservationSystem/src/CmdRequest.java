
public class CmdRequest extends RecordedCommand {
	String name, number, diningDate;
	int totalPersons;
	Reservation r;

	@Override
	public void execute(String[] cmdParts) {
		try {
			name = cmdParts[1];
			number = cmdParts[2];
			totalPersons = Integer.parseInt(cmdParts[3]);
			diningDate = cmdParts[4];
			BookingOffice bo = BookingOffice.getInstance();
			SystemDate sd = SystemDate.getInstance();
			if (Day.valid(Integer.parseInt(diningDate.substring(7, 11)), diningDate.substring(3, 6),
					Integer.parseInt(diningDate.substring(0, 2)))
					&& !sd.isPreviousDate(Integer.parseInt(diningDate.substring(7, 11)), diningDate.substring(3, 6),
							Integer.parseInt(diningDate.substring(0, 2)))) { // validate the diningDate

				r = bo.addReservation(name, number, totalPersons, diningDate);
				if (r != null) {
					addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
					clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.
					System.out.println("Done. Ticket code for " + diningDate + ": " + r.getTicketCode());
				} else { // null that's mean not create the reservation as there is a booking by the same
							// person for the dining date already exists
					throw new SameBookingOnDateException();
				}
			} else {
				throw new DateIncorrectException();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Insufficient command arguments!");
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (SameBookingOnDateException e) {
		} catch (DateIncorrectException e) {
		}
	}

	@Override
	public void undoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		boolean removed = bo.removeReservation(r);
		addRedoCommand(this);
	}

	@Override
	public void redoMe() {
		BookingOffice bo = BookingOffice.getInstance();
		r = bo.addReservation(name, number, totalPersons, diningDate);
		addUndoCommand(this);
	}

}
