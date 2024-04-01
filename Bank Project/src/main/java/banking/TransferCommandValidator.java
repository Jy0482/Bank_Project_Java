package banking;

public class TransferCommandValidator extends CommandValidator {
	DepositCommandValidator depositCommandValidator;
	WithdrawCommandValidator withdrawCommandValidator;

	public TransferCommandValidator(Bank bank) {
		super(bank);
		depositCommandValidator = new DepositCommandValidator(bank);
		withdrawCommandValidator = new WithdrawCommandValidator(bank);
	}

	public boolean validateTransferCommand(String[] commandLine) {
		try {
			String withdrawAccountID = commandLine[1];
			String depositAccountID = commandLine[2];
			if ((validEightDigitNumberID(withdrawAccountID) && validEightDigitNumberID(depositAccountID))
					&& (accountExists(withdrawAccountID) && accountExists(depositAccountID))
					&& TransferCommandHasRightNumberOfArguments(commandLine)) {
				String withdrawAccountType = bank.retrieveAccounts(withdrawAccountID).getAccountType();
				String depositAccountType = bank.retrieveAccounts(depositAccountID).getAccountType();
				if ((withdrawAccountType.equals("cd") || depositAccountType.equals("cd"))
						|| (withdrawAccountID.equals(depositAccountID))) {
					return false;
				} else {
					String[] withdrawComponents = { "withdraw", withdrawAccountID, commandLine[3] };
					String[] depositComponents = { "deposit", depositAccountID, commandLine[3] };
					return withdrawCommandValidator.validateWithdrawCommand(withdrawComponents)
							&& depositCommandValidator.validateDepositCommand(depositComponents);
				}
			} else {
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public boolean TransferCommandHasRightNumberOfArguments(String[] commandLine) {
		return commandLine.length == 4;
	}
}
