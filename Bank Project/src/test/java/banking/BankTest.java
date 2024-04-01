package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

	public static final String CHECKING_ACCOUNT_TYPE = "checking";
	public static final String SAVINGS_ACCOUNT_TYPE = "savings";
	public static final String FIRST_ACCOUNT_ID = "12345678";
	public static final String SECOND_ACCOUNT_ID = "66642096";
	public static final double SAMPLE_ACCOUNT_APR = 3.4;

	Bank bank;
	Checking checking;
	Savings savings;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		checking = new Checking(CHECKING_ACCOUNT_TYPE, FIRST_ACCOUNT_ID, SAMPLE_ACCOUNT_APR);
		savings = new Savings(SAVINGS_ACCOUNT_TYPE, SECOND_ACCOUNT_ID, SAMPLE_ACCOUNT_APR);
	}

	@Test
	public void bank_has_no_accounts_initially() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	public void add_one_account_to_bank() {
		bank.addAccounts(checking);
		assertEquals(1, bank.getAccounts().size());
	}

	@Test
	public void add_two_accounts_to_bank() {
		bank.addAccounts(checking);
		bank.addAccounts(savings);
		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	public void retrieve_correct_account() {
		bank.addAccounts(checking);
		String actual = bank.retrieveAccounts(FIRST_ACCOUNT_ID).getAccountID();
		assertEquals(FIRST_ACCOUNT_ID, actual);
	}

	@Test
	public void deposit_through_correct_account_ID() {
		bank.addAccounts(checking);
		bank.addAccounts(savings);
		bank.depositMoneyToAccount(FIRST_ACCOUNT_ID, 50);
		Account first = bank.getAccounts().get(FIRST_ACCOUNT_ID);
		Account second = bank.getAccounts().get(SECOND_ACCOUNT_ID);
		assertEquals(50, first.getBalance());
		assertEquals(0, second.getBalance());
	}

	@Test
	public void withdraw_through_correct_account_ID() {
		bank.addAccounts(checking);
		bank.addAccounts(savings);
		bank.depositMoneyToAccount(FIRST_ACCOUNT_ID, 50);
		bank.depositMoneyToAccount(SECOND_ACCOUNT_ID, 50);
		bank.withdrawMoneyFromAccount(FIRST_ACCOUNT_ID, 40);
		Account first = bank.getAccounts().get(FIRST_ACCOUNT_ID);
		Account second = bank.getAccounts().get(SECOND_ACCOUNT_ID);
		assertEquals(10, first.getBalance());
		assertEquals(50, second.getBalance());
	}

	@Test
	public void deposit_twice_through_an_account_ID() {
		bank.addAccounts(checking);
		bank.addAccounts(savings);
		bank.depositMoneyToAccount(FIRST_ACCOUNT_ID, 50);
		bank.depositMoneyToAccount(FIRST_ACCOUNT_ID, 50);
		Account first = bank.getAccounts().get(FIRST_ACCOUNT_ID);
		Account second = bank.getAccounts().get(SECOND_ACCOUNT_ID);
		assertEquals(100, first.getBalance());
		assertEquals(0, second.getBalance());
	}

	@Test
	public void withdraw_twice_through_an_account_ID() {
		bank.addAccounts(checking);
		bank.addAccounts(savings);
		bank.depositMoneyToAccount(FIRST_ACCOUNT_ID, 100);
		bank.depositMoneyToAccount(SECOND_ACCOUNT_ID, 100);
		bank.withdrawMoneyFromAccount(FIRST_ACCOUNT_ID, 40);
		bank.withdrawMoneyFromAccount(FIRST_ACCOUNT_ID, 20);
		Account first = bank.getAccounts().get(FIRST_ACCOUNT_ID);
		Account second = bank.getAccounts().get(SECOND_ACCOUNT_ID);
		assertEquals(40, first.getBalance());
		assertEquals(100, second.getBalance());
	}

}
