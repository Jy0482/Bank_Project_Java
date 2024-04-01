package banking;

public class DepositCommandProcessor extends CommandProcessor {

	public DepositCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processDepositCommand(String[] commandLine) {
		String id = commandLine[1];
		Double depositAmount = Double.parseDouble(commandLine[2]);
		bank.depositMoneyToAccount(id, depositAmount);
	}
}
