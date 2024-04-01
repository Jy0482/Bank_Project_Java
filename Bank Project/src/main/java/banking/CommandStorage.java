package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandStorage {
	private final List<String> invalidCommands;
	private final Map<String, List<String>> validCommands;
	private final Bank bank;

	public CommandStorage(Bank bank) {
		this.bank = bank;
		invalidCommands = new ArrayList<>();
		validCommands = new HashMap<>();
	}

	public void storeValidCommands(String command) {
		String commandLineLowercase = command.toLowerCase();
		String[] commandLine = commandLineLowercase.stripTrailing().split(" ");
		if (commandLine[0].equals("create") || commandLine[0].equals("withdraw") || commandLine[0].equals("deposit")
				|| commandLine[0].equals("transfer")) {
			addToMap(validCommands, commandLine[1], command);
		}
		if (commandLine[0].equals("transfer")) {
			addToMap(validCommands, commandLine[2], command);
		}
	}

	private void addToMap(Map<String, List<String>> map, String accountID, String commandLine) {
		if (map.get(accountID) != null) {
			map.get(accountID).add(commandLine);
		} else if (map.get(accountID) == null) {
			map.put(accountID, new ArrayList<>());
			map.get(accountID).add(commandLine);
		}
	}

	public void storeInvalidCommands(String command) {
		invalidCommands.add(command);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}

	public List<String> getOutput() {
		List<String> output = new ArrayList<>();
		for (String accountID : bank.getAccounts().keySet()) {
			output.add(formatAccount(accountID));
			if (validCommands.get(accountID) != null) {
				output.addAll(validCommands.get(accountID));
			}
		}
		output.addAll(invalidCommands);
		return output;
	}

	public String formatAccount(String accountID) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		Account account = bank.getAccounts().get(accountID);
		String accountType = account.getAccountType();
		String accountUppercase = accountType.substring(0, 1).toUpperCase() + accountType.substring(1);
		String truncatedBalance = decimalFormat.format(account.getBalance());
		String truncatedAPR = decimalFormat.format(account.getAPR());
		return accountUppercase + " " + account.getAccountID() + " " + truncatedBalance + " " + truncatedAPR;
	}
}
