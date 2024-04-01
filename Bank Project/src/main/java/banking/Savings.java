package banking;

public class Savings extends Account {
	public static final double SAVINGS_DEFAULT_BALANCE = 0.0;
	private boolean savingsWithdraw = true;

	public Savings(String accountType, String accountID, double apr) {
		super(accountType, accountID, apr, SAVINGS_DEFAULT_BALANCE);
	}

	public boolean getSavingsWithdrawStatus() {
		return savingsWithdraw;
	}

	public void changeSavingsWithdrawStatus() {
		savingsWithdraw = !savingsWithdraw;
	}
}
