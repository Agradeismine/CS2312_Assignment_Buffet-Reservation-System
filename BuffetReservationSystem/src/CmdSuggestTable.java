public class CmdSuggestTable implements Command{

	@Override
	public void execute(String[] cmdParts) {
		int totalPersons;
		String suggestTables="";
		BookingOffice bo = BookingOffice.getInstance();
		bo.suggestTable(cmdParts[1], Integer.parseInt(cmdParts[2]));
	}

}
