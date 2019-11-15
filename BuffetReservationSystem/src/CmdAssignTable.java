import java.util.ArrayList;

public class CmdAssignTable extends RecordedCommand {
	Reservation r;
	String[] cmdParts;

	@Override
	public void execute(String[] cmdParts) {
		this.cmdParts = cmdParts;
		try {
			if (cmdParts.length >= 4) {
				SystemDate sd = SystemDate.getInstance();
				if (!sd.isPreviousDate(Integer.parseInt(cmdParts[1].substring(7, 11)), cmdParts[1].substring(3, 6),
						Integer.parseInt(cmdParts[1].substring(0, 2)))) {
					BookingOffice bo = BookingOffice.getInstance();
					r = bo.getReservation(cmdParts[1], Integer.parseInt(cmdParts[2])); // clone for undo
					int totalPersons = r.getTotalPersons();
					for (int i = 3; i < cmdParts.length; i++) {
						if(TableStatus.getAllTables().contains(cmdParts[i])) {
							if (cmdParts[i].substring(0, 1).equals("T")) {
								totalPersons -= 2;
							} else if (cmdParts[i].substring(0, 1).equals("F")) {
								totalPersons -= 4;
							} else if (cmdParts[i].substring(0, 1).equals("H")) {
								totalPersons -= 8;
							}
						}else {
							throw new TableCodeNotFoundException(cmdParts[i]);
						}
					}
					if (totalPersons <= 0) { // enough seats, then allow to assign table

						if (r.getTableStatusArrayList().size() <= 0) {
							
							r = bo.assignTable(cmdParts);
							if (r.getTableStatusArrayList().size()>0) {
							addUndoCommand(this);
							clearRedoList();
							System.out.println("Done.");
							}
						} else {
							System.out.println("Table(s) already assigned for this booking!");
						}
					} else { // not enough seats.
						System.out.println("Not enough seats for the booking!");
					}
				}
				else {
					throw new DateIncorrectException();
				}
			} else {
				throw new InsufficientCommandException();
			}
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (NullPointerException e) {
			System.out.println("Booking not found!");
			e.printStackTrace();
		} catch (InsufficientCommandException e) {
		} catch (DateIncorrectException e) {
		} catch (TableCodeNotFoundException e) {
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
