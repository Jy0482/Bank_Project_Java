package banking;

public class TimeCommandValidator extends CommandValidator {

	public TimeCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateTimeCommand(String[] commandLine) {
		try {
			String month = commandLine[1];
			return timeCommandHasRightNumberOfArguments(commandLine) && validMonth(month);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public boolean timeCommandHasRightNumberOfArguments(String[] commandLine) {
		return commandLine.length == 2;
	}
}
