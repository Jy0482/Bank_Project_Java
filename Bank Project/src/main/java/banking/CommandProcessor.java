package banking;

public class CommandProcessor {
	public Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String commandInput) {
		String commandLowercase = commandInput.toLowerCase();
		String[] commandLine = commandLowercase.stripTrailing().split(" ");
		if (commandLine[0].equals("create")) {
			CreateCommandProcessor createCommand = new CreateCommandProcessor(bank);
			createCommand.processCreateCommand(commandLine);
		} else if (commandLine[0].equals("deposit")) {
			DepositCommandProcessor depositCommand = new DepositCommandProcessor(bank);
			depositCommand.processDepositCommand(commandLine);
		} else if (commandLine[0].equals("withdraw")) {
			WithdrawCommandProcessor withdrawCommand = new WithdrawCommandProcessor(bank);
			withdrawCommand.processWithdrawCommand(commandLine);
		} else if (commandLine[0].equals("pass")) {
			TimeCommandProcessor timeCommand = new TimeCommandProcessor(bank);
			timeCommand.processTimeCommand(commandLine);
		} else if (commandLine[0].equals("transfer")) {
			TransferCommandProcessor transferCommand = new TransferCommandProcessor(bank);
			transferCommand.processTransferCommand(commandLine);
		}
	}
}
