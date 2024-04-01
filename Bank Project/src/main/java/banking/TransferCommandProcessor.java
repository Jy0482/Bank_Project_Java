package banking;

public class TransferCommandProcessor extends CommandProcessor {
	WithdrawCommandProcessor withdrawCommandProcessor;
	DepositCommandProcessor depositCommandProcessor;

	public TransferCommandProcessor(Bank bank) {
		super(bank);
		withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
		depositCommandProcessor = new DepositCommandProcessor(bank);
	}

	public void processTransferCommand(String[] commandLine) {
		String withdrawAccountID = commandLine[1];
		String depositAccountID = commandLine[2];
		Double transferAmount = Double.parseDouble(commandLine[3]);
		Account withdrawAccount = bank.retrieveAccounts(withdrawAccountID);
		if (withdrawAccount.getBalance() < transferAmount) {
			transferAmount = withdrawAccount.getBalance();
		}
		String transferAmountAsString = String.valueOf(transferAmount);
		String[] withdrawCommand = { "withdraw", withdrawAccountID, transferAmountAsString };
		String[] depositCommand = { "deposit", depositAccountID, transferAmountAsString };
		withdrawCommandProcessor.processWithdrawCommand(withdrawCommand);
		depositCommandProcessor.processDepositCommand(depositCommand);
	}
}
