package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

	public static final double SAMPLE_SUPPLIED_APR = 2.1;
	public static final String SAMPLE_SUPPLIED_ID = "12345678";
	public static final String SAMPLE_ACCOUNT_TYPE = "checking";
	Account account;

	@BeforeEach
	public void setUp() {
		account = new Checking(SAMPLE_ACCOUNT_TYPE, SAMPLE_SUPPLIED_ID, SAMPLE_SUPPLIED_APR);
	}

	@Test
	public void account_has_correct_apr() {
		double apr = account.getAPR();
		assertEquals(SAMPLE_SUPPLIED_APR, apr);
	}

	@Test
	public void deposit_balance_to_checking_account() {
		account.depositBalance(50);
		double balance = account.getBalance();
		assertEquals(50, balance);
	}

	@Test
	public void withdraw_balance_from_checking_account() {
		account.depositBalance(60);
		account.withdrawBalance(40);
		double balance = account.getBalance();
		assertEquals(20, balance);
	}

	@Test
	public void withdraw_balance_cannot_go_below_zero() {
		account.depositBalance(60);
		account.withdrawBalance(120);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void deposit_balance_cannot_go_below_zero() {
		account.depositBalance(-10);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void deposit_balance_twice_works() {
		account.depositBalance(50);
		account.depositBalance(100);
		double balance = account.getBalance();
		assertEquals(150, balance);
	}

	@Test
	public void withdraw_balance_twice_works() {
		account.depositBalance(100);
		account.withdrawBalance(30);
		account.withdrawBalance(60);
		double balance = account.getBalance();
		assertEquals(10, balance);
	}
}
