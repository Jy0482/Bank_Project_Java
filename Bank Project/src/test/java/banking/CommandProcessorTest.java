package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;
	String id;
	String accountType;
	Double apr;
	Account account;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void process_create_command_for_savings_account() {
		commandProcessor.process("create savings 87654321 3.1");
		accountType = "savings";
		id = "87654321";
		apr = 3.1;
		account = bank.retrieveAccounts("87654321");
		assertEquals(3.1, account.getAPR());
		assertEquals("87654321", account.getAccountID());
		assertEquals("savings", account.getAccountType());
	}

	@Test
	public void process_create_command_for_checking_account() {
		commandProcessor.process("create checking 43218765 4.2");
		accountType = "checking";
		id = "43218765";
		apr = 4.2;
		account = bank.retrieveAccounts("43218765");
		assertEquals(4.2, account.getAPR());
		assertEquals("43218765", account.getAccountID());
		assertEquals("checking", account.getAccountType());
	}

	@Test
	public void process_create_command_for_cd_account() {
		commandProcessor.process("create cd 33338888 5.6 5000");
		accountType = "cd";
		id = "33338888";
		apr = 5.6;
		account = bank.retrieveAccounts("33338888");
		assertEquals(5.6, account.getAPR());
		assertEquals("33338888", account.getAccountID());
		assertEquals("cd", account.getAccountType());
		assertEquals(5000, account.getBalance());
	}

	@Test
	public void process_deposit_command_for_savings_account() {
		commandProcessor.process("create savings 12341234 5.5");
		commandProcessor.process("deposit 12341234 500");
		account = bank.retrieveAccounts("12341234");
		assertEquals(500, account.getBalance());
		commandProcessor.process("deposit 12341234 700");
		assertEquals(1200, account.getBalance());
	}

	@Test
	public void process_deposit_command_for_checking_account() {
		commandProcessor.process("create checking 98769876 5.2");
		commandProcessor.process("deposit 98769876 350");
		account = bank.retrieveAccounts("98769876");
		assertEquals(350, account.getBalance());
		commandProcessor.process("deposit 98769876 650");
		assertEquals(1000, account.getBalance());
	}

	@Test
	public void process_withdraw_command_for_savings_account() {
		commandProcessor.process("create savings 54325432 4.5");
		commandProcessor.process("deposit 54325432 300");
		commandProcessor.process("withdraw 54325432 200");
		account = bank.retrieveAccounts("54325432");
		assertEquals(100, account.getBalance());
	}

	@Test
	public void process_withdraw_command_for_savings_account_after_month_reset() {
		commandProcessor.process("create savings 54325432 4.5");
		commandProcessor.process("deposit 54325432 300");
		commandProcessor.process("withdraw 54325432 200");
		commandProcessor.process("pass 1");
		commandProcessor.process("withdraw 54325432 200");
		account = bank.retrieveAccounts("54325432");
		assertEquals(0, account.getBalance());
	}

	@Test
	public void process_withdraw_command_for_checking_account() {
		commandProcessor.process("create checking 13572468 3.2");
		commandProcessor.process("deposit 13572468 250");
		commandProcessor.process("withdraw 13572468 140");
		account = bank.retrieveAccounts("13572468");
		assertEquals(110, account.getBalance());
	}

	@Test
	public void process_withdraw_command_for_cd_account() {
		commandProcessor.process("create cd 12341234 2.0 3000");
		commandProcessor.process("pass 12");
		commandProcessor.process("withdraw 12341234 4000");
		account = bank.retrieveAccounts("12341234");
		assertEquals(0, account.getBalance());
	}

	@Test
	public void process_pass_time_command_for_savings_account() {
		commandProcessor.process("create savings 54325432 4.5");
		commandProcessor.process("deposit 54325432 300");
		commandProcessor.process("pass 1");
		account = bank.retrieveAccounts("54325432");
		assertEquals(1, account.getAge());
	}

	@Test
	public void process_pass_time_command_for_checking_account() {
		commandProcessor.process("create checking 13572468 3.2");
		commandProcessor.process("deposit 13572468 250");
		commandProcessor.process("pass 3");
		account = bank.retrieveAccounts("13572468");
		assertEquals(3, account.getAge());
	}

	@Test
	public void process_pass_time_command_for_cd_account() {
		commandProcessor.process("create cd 21212121 4.1 2000");
		commandProcessor.process("pass 12");
		account = bank.retrieveAccounts("21212121");
		assertEquals(12, account.getAge());
	}

	@Test
	public void pass_time_command_deletes_accounts_with_zero_balance() {
		commandProcessor.process("create savings 12345678 2.1");
		commandProcessor.process("create checking 23456781 2.2");
		commandProcessor.process("pass 1");
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	public void pass_time_command_deducts_$25_when_below_$100_from_accounts() {
		commandProcessor.process("create savings 12345678 2.5");
		commandProcessor.process("create checking 23456781 2.5");
		commandProcessor.process("deposit 12345678 80");
		commandProcessor.process("deposit 23456781 80");
		commandProcessor.process("pass 1");
		assertEquals(55.11, bank.retrieveAccounts("12345678").getBalance());
		assertEquals(55.11, bank.retrieveAccounts("23456781").getBalance());
	}

	@Test
	public void pass_time_command_can_delete_accounts_with_below_$100_balance() {
		commandProcessor.process("create savings 12345678 2.1");
		commandProcessor.process("create checking 23456781 2.2");
		commandProcessor.process("deposit 12345678 81");
		commandProcessor.process("deposit 23456781 81");
		commandProcessor.process("pass 4");
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	public void transfer_command_from_one_account_to_another() {
		commandProcessor.process("create savings 12345678 2.1");
		commandProcessor.process("create checking 23456781 2.2");
		commandProcessor.process("deposit 12345678 50");
		commandProcessor.process("transfer 12345678 23456781 30");
		assertEquals(20, bank.retrieveAccounts("12345678").getBalance());
		assertEquals(30, bank.retrieveAccounts("23456781").getBalance());
	}

	@Test
	public void transfer_command_transfers_the_amount_actually_withdrawn() {
		commandProcessor.process("create savings 12345678 2.1");
		commandProcessor.process("create checking 23456781 2.2");
		commandProcessor.process("deposit 12345678 50");
		commandProcessor.process("transfer 12345678 23456781 100");
		assertEquals(0, bank.retrieveAccounts("12345678").getBalance());
		assertEquals(50, bank.retrieveAccounts("23456781").getBalance());
	}

	@Test
	public void APR_is_correctly_calculated_for_savings_or_checking_account() {
		commandProcessor.process("create savings 12345678 3.0");
		commandProcessor.process("create checking 23456781 3.0");
		commandProcessor.process("deposit 12345678 1000");
		commandProcessor.process("deposit 23456781 1000");
		commandProcessor.process("pass 1");
		assertEquals(1002.50, bank.retrieveAccounts("12345678").getBalance());
		assertEquals(1002.50, bank.retrieveAccounts("23456781").getBalance());
	}

	@Test
	public void APR_is_correctly_calculated_for_cd_account() {
		commandProcessor.process("create cd 43214321 2.1 2000");
		commandProcessor.process("pass 1");
		assertEquals(2014.03, bank.retrieveAccounts("43214321").getBalance());
	}
}
