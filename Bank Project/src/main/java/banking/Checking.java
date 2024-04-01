package banking;

public class Checking extends Account {
	public static final double CHECKING_DEFAULT_BALANCE = 0.0;

	public Checking(String accountType, String accountID, double apr) {
		super(accountType, accountID, apr, CHECKING_DEFAULT_BALANCE);
	}

}
