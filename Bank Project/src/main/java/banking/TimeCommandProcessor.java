package banking;

public class TimeCommandProcessor extends CommandProcessor {
	public TimeCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processTimeCommand(String[] commandLine) {
		int months = Integer.parseInt(commandLine[1]);
		bank.passTime(months);
	}
}
