package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	MasterControl masterControl;
	Bank bank;
	List<String> input;

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@BeforeEach
	public void setUp() {
		input = new ArrayList<>();
		bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank),
				new CommandStorage(bank));
	}

	@Test
	public void typo_in_create_command_is_invalid() {
		input.add("creat checking 54321876 2.3");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 54321876 2.3", actual);
	}

	@Test
	public void typo_in_deposit_command_is_invalid() {
		input.add("depositt 54321876 100");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("depositt 54321876 100", actual);
	}

	@Test
	public void two_typo_commands_both_invalid() {
		input.add("creat checking 54321876 2.3");
		input.add("depositt 54321876 100");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("creat checking 54321876 2.3", actual.get(0));
		assertEquals("depositt 54321876 100", actual.get(1));
	}

	@Test
	void sample_make_sure_this_passes_unchanged() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

}
