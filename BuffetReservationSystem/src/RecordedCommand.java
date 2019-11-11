import java.util.ArrayList;

public abstract class RecordedCommand implements Command {
	public static ArrayList<RecordedCommand> undoList = new ArrayList<>();
	public static ArrayList<RecordedCommand> redoList = new ArrayList<>();
	
	@Override
	public abstract void execute(String[] cmdParts);
	public abstract void undoMe();
	public abstract void redoMe();
	
	public static void undoOneCommand() {
		if (undoList.size()>0) {
			undoList.remove(undoList.size() - 1).undoMe();
		} else {
			System.out.println("Nothing to undo.");
		}
	}
	
	public static void redoOneCommand() {
		if (redoList.size()>0) {
			redoList.remove(redoList.size() - 1).redoMe();
		} else {
			System.out.println("Nothing to redo.");
		}
	}
	
	protected void addUndoCommand(RecordedCommand cmd) {
		undoList.add(cmd);
	}

	protected void addRedoCommand(RecordedCommand cmd) {
		redoList.add(cmd);
	}
	
	protected void clearRedoList() {
		redoList.clear();
	}
	
}
