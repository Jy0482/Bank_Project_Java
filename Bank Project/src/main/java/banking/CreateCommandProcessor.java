package banking;

public class CreateCommandProcessor extends CommandProcessor {

	public CreateCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processCreateCommand(String[] commandLine) {
		String accountType = commandLine[1];
		String id = commandLine[2];
		Double apr = Double.parseDouble(commandLine[3]);
		Double suppliedBalance = 0.0;
		if (commandLine.length == 5) {
			suppliedBalance = Double.parseDouble(commandLine[4]);
		}
		bank.createAccounts(accountType, id, apr, suppliedBalance);
	}
}
