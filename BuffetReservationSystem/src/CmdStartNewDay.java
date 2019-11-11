public class CmdStartNewDay extends RecordedCommand {
	SystemDate instance;
	String oldDate, newDate;
	@Override
	public void execute(String[] cmdParts) {
		SystemDate instance = SystemDate.getInstance();
		oldDate = instance.toString();
		newDate = cmdParts[1];
		instance.set(newDate);
		
		addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
		clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
		System.out.println("Done.");
	}

	@Override
	public void undoMe() {
		SystemDate instance = SystemDate.getInstance();
		instance.set(oldDate);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)

	}

	@Override
	public void redoMe() {
		SystemDate instance = SystemDate.getInstance();
		instance.set(newDate);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}

}
