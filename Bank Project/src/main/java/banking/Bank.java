package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {

	private Map<String, Account> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public void createAccounts(String accountType, String id, double apr, double cdSuppliedBalance) {
		if (accountType.equals("savings")) {
			Savings account = new Savings(accountType, id, apr);
			addAccounts(account);
		} else if (accountType.equals("checking")) {
			Checking account = new Checking(accountType, id, apr);
			addAccounts(account);
		} else if (accountType.equals("cd")) {
			CertificateOfDeposit account = new CertificateOfDeposit(accountType, id, apr, cdSuppliedBalance);
			addAccounts(account);
		}
	}

	public void addAccounts(Account account) {
		String accountID = account.getAccountID();
		accounts.put(accountID, account);
	}

	public Account retrieveAccounts(String accountID) {
		return accounts.get(accountID);
	}

	public void depositMoneyToAccount(String accountID, double depositAmount) {
		accounts.get(accountID).depositBalance(depositAmount);
	}

	public void withdrawMoneyFromAccount(String accountID, double withdrawAmount) {
		accounts.get(accountID).withdrawBalance(withdrawAmount);
	}

	public boolean checkAccountIDExists(String accountID) {
		return accounts.containsKey(accountID);
	}

	public void passTime(int month) {
		ArrayList<String> zeroBalanceAccounts = new ArrayList<>();

		for (int startingMonth = 0; startingMonth < month; startingMonth++) {
			for (String accountID : accounts.keySet()) {
				Account eachAccount = accounts.get(accountID);
				if (eachAccount.getBalance() < 100) {
					withdrawMoneyFromAccount(accountID, 25);
				}
				if (eachAccount.getBalance() == 0.00) {
					zeroBalanceAccounts.add(accountID);
				}
				eachAccount.addMonthToAccountAge();
				eachAccount.calculateNewBalanceFromAPR();
				if (eachAccount.getAccountType().equals("savings")) {
					Savings savings = (Savings) eachAccount;
					savings.changeSavingsWithdrawStatus();
				}
			}

			for (String accountID : zeroBalanceAccounts) {
				accounts.remove(accountID);
			}

		}

	}
}
