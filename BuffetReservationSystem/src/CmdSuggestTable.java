public class CmdSuggestTable implements Command{

	@Override
	public void execute(String[] cmdParts) {
		BookingOffice bo = BookingOffice.getInstance();
		try {
			if(cmdParts.length==3) {
				bo.suggestTable(cmdParts[1], Integer.parseInt(cmdParts[2]));
			}else {
				throw new InsufficientCommandException();
			}
		} catch (BookingNotFoundException e) {
		} catch (InsufficientCommandException e) {
			System.out.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Wrong number format!");
		} catch (TableAssignedException e) {
			System.out.println(e.getMessage());
		}
	}

}
