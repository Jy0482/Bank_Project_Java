package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public abstract class Account {

	private final double apr;
	private final String id;
	private final String accountType;
	private final DecimalFormat decimalFormat = new DecimalFormat("0.00");
	private double balance;
	private int age = 0;

	public Account(String accountType, String accountID, double apr, double startingBalance) {
		this.accountType = accountType;
		this.id = accountID;
		this.apr = apr;
		this.balance = startingBalance;
	}

	public String getAccountID() {
		return id;
	}

	public double getAPR() {
		return apr;
	}

	public double getBalance() {
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		balance = Double.parseDouble(decimalFormat.format(balance));
		return balance;
	}

	public String getAccountType() {
		return accountType;
	}

	public int getAge() {
		return age;
	}

	public void depositBalance(double depositAmount) {
		if (depositAmount >= 0) {
			balance += depositAmount;
		}
	}

	public void withdrawBalance(double withdrawAmount) {
		if (withdrawAmount >= 0) {
			balance -= withdrawAmount;
		}
		if (balance <= 0) {
			balance = 0.00;
		}
	}

	public void addMonthToAccountAge() {
		age += 1;
	}

	public void calculateNewBalanceFromAPR() {
		if (accountType.equals("cd")) {
			for (int cdAPRCounter = 0; cdAPRCounter < 4; cdAPRCounter++) {
				balance += (apr / 1200) * balance;
			}
		} else {
			balance += (apr / 1200) * balance;
		}
	}
}
