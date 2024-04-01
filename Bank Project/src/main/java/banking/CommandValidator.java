package banking;

public class CommandValidator {

	public Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public String[] commandToLowercaseParse(String commandLine) {
		String commandLineLowercase = commandLine.toLowerCase();
		return commandLineLowercase.stripTrailing().split(" ");
	}

	public boolean validate(String[] commandLine) {
		if (commandLine[0].equals("create")) {
			CreateCommandValidator validator = new CreateCommandValidator(bank);
			return validator.validateCreateCommand(commandLine);
		} else if (commandLine[0].equals("deposit")) {
			DepositCommandValidator validator = new DepositCommandValidator(bank);
			return validator.validateDepositCommand(commandLine);
		} else if (commandLine[0].equals("withdraw")) {
			WithdrawCommandValidator validator = new WithdrawCommandValidator(bank);
			return validator.validateWithdrawCommand(commandLine);
		} else if (commandLine[0].equals("pass")) {
			TimeCommandValidator validator = new TimeCommandValidator(bank);
			return validator.validateTimeCommand(commandLine);
		} else if (commandLine[0].equals("transfer")) {
			TransferCommandValidator validator = new TransferCommandValidator(bank);
			return validator.validateTransferCommand(commandLine);
		} else {
			return false;
		}
	}

	public boolean validEightDigitNumberID(String id) {
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return false;
		}
		return id.length() == 8;
	}

	public boolean validAPR(String apr) {
		try {
			double aprAsDouble = Double.parseDouble(apr);
			if (aprAsDouble >= 0 && aprAsDouble <= 10) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean validCDStartingBalance(String balance) {
		try {
			double balanceAsDouble = Double.parseDouble(balance);
			if (balanceAsDouble >= 1000 && balanceAsDouble <= 10000) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean notDuplicateID(String id) {
		return !(bank.checkAccountIDExists(id));
	}

	public boolean accountExists(String id) {
		return bank.checkAccountIDExists(id);
	}

	public boolean validTransactionAmount(String balance, double amount) {
		try {
			double balanceAsDouble = Double.parseDouble(balance);
			if (balanceAsDouble >= 0 && balanceAsDouble <= amount) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean TransactionCommandHasRightNumberOfArguments(String[] commandLine) {
		return commandLine.length == 3;
	}

	public boolean validMonth(String month) {
		try {
			int monthAsInt = Integer.parseInt(month);
			if (monthAsInt >= 1 && monthAsInt <= 60) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
