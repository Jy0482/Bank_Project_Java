package banking;

public class WithdrawCommandValidator extends CommandValidator {

	private static final double MAXIMUM_SAVINGS_WITHDRAW = 1000;
	private static final double MAXIMUM_CHECKING_WITHDRAW = 400;

	public WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateWithdrawCommand(String commandLine[]) {
		try {
			String id = commandLine[1];
			if (validEightDigitNumberID(id) && accountExists(id)) {
				String accountType = bank.retrieveAccounts(id).getAccountType();
				int cdAge = bank.retrieveAccounts(id).getAge();
				double cdBalance = bank.retrieveAccounts(id).getBalance();
				if (accountType.equals("savings")) {
					Savings savings = (Savings) bank.retrieveAccounts(id);
					return savings.getSavingsWithdrawStatus()
							&& validTransactionAmount(commandLine[2], MAXIMUM_SAVINGS_WITHDRAW)
							&& TransactionCommandHasRightNumberOfArguments(commandLine);
				} else if (accountType.equals("checking")) {
					return validTransactionAmount(commandLine[2], MAXIMUM_CHECKING_WITHDRAW)
							&& TransactionCommandHasRightNumberOfArguments(commandLine);
				} else if (accountType.equals("cd")) {
					return cdAge >= 12 && validCDWithdraw(commandLine[2], cdBalance)
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

	public boolean validCDWithdraw(String withdrawAmount, double cdBalance) {
		try {
			double amountAsDouble = Double.parseDouble(withdrawAmount);
			if (amountAsDouble >= cdBalance) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
