package banking;

public class WithdrawCommandProcessor extends CommandProcessor {

	public WithdrawCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processWithdrawCommand(String[] commandLine) {
		String id = commandLine[1];
		Double withdrawAmount = Double.parseDouble(commandLine[2]);
		bank.withdrawMoneyFromAccount(id, withdrawAmount);
		if (bank.retrieveAccounts(id).getAccountType().equals("savings")) {
			Savings savings = (Savings) bank.retrieveAccounts(id);
			savings.changeSavingsWithdrawStatus();
		}
	}
}
