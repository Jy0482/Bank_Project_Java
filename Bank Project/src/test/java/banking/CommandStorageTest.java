package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	CommandStorage commandStorage;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
	}

	@Test
	public void commandStorage_has_no_initial_commands_stored() {
		assertTrue(commandStorage.getInvalidCommands().isEmpty());
	}

	@Test
	public void store_an_invalid_command() {
		commandStorage.storeInvalidCommands("This is an invalid command");
		assertEquals("This is an invalid command", commandStorage.getInvalidCommands().get(0));
	}
}
