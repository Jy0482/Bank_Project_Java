package banking;

public class CreateCommandValidator extends CommandValidator {

	public CreateCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateCreateCommand(String[] commandLine) {
		try {
			if (commandLine[1].equals("checking") || commandLine[1].equals("savings")) {
				return createSavingsOrCheckingCommandHasRightNumberOfArguments(commandLine)
						&& notDuplicateID(commandLine[2]) && validEightDigitNumberID(commandLine[2])
						&& validAPR(commandLine[3]);
			} else if (commandLine[1].equals("cd")) {
				return createCDCommandHasRightNumberOfArguments(commandLine) && notDuplicateID(commandLine[2])
						&& validEightDigitNumberID(commandLine[2]) && validAPR(commandLine[3])
						&& validCDStartingBalance(commandLine[4]);
			} else {
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public boolean createSavingsOrCheckingCommandHasRightNumberOfArguments(String[] commandLine) {
		return commandLine.length == 4;
	}

	public boolean createCDCommandHasRightNumberOfArguments(String[] commandLine) {
		return commandLine.length == 5;
	}
}
