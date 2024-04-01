package banking;

public class DepositCommandValidator extends CommandValidator {

	private static final double MAXIMUM_SAVINGS_DEPOSIT = 2500;
	private static final double MAXIMUM_CHECKING_DEPOSIT = 1000;

	public DepositCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateDepositCommand(String[] commandLine) {
		try {
			String id = commandLine[1];
			if (validEightDigitNumberID(id) && accountExists(id)) {
				String account = bank.retrieveAccounts(id).getAccountType();
				if (account.equals("savings")) {
					return validTransactionAmount(commandLine[2], MAXIMUM_SAVINGS_DEPOSIT)
							&& TransactionCommandHasRightNumberOfArguments(commandLine);
				} else if (account.equals("checking")) {
					return validTransactionAmount(commandLine[2], MAXIMUM_CHECKING_DEPOSIT)
							&& TransactionCommandHasRightNumberOfArguments(commandLine);
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}
