package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;
	String[] command;
	Checking checking;
	Checking checkingSecondAccount;
	Savings savings;
	Savings savingsSecondAccount;
	CommandProcessor commandProcessor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		checking = new Checking("checking", "99999999", 5.5);
		savings = new Savings("savings", "11111111", 3.4);
		savingsSecondAccount = new Savings("savings", "12121212", 2.4);
		checkingSecondAccount = new Checking("checking", "98989898", 3.6);
		bank.addAccounts(checking);
		bank.addAccounts(savings);
		bank.addAccounts(savingsSecondAccount);
		bank.addAccounts(checkingSecondAccount);
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void validate_correct_checking_or_savings_create_command() {
		command = commandValidator.commandToLowercaseParse("create savings 87654321 0.1");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_case_insensitivity_for_correct_create_command() {
		command = commandValidator.commandToLowercaseParse("cReAte chEcking 88866622 1.2");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_correct_cd_create_command() {
		command = commandValidator.commandToLowercaseParse("create cd 21543521 2.2 2500");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_too_large_cd_initial_deposit_in_create_command() {
		command = commandValidator.commandToLowercaseParse("create cd 21543521 2.2 10001");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_small_cd_initial_deposit_in_create_command() {
		command = commandValidator.commandToLowercaseParse("create cd 21543521 2.2 999");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_less_arguments_for_savings_or_checking_create_command() {
		command = commandValidator.commandToLowercaseParse("create checking");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_many_arguments_for_savings_or_checking_create_command() {
		command = commandValidator.commandToLowercaseParse("create savings 87654321 0.1 1000");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_less_arguments_for_cd_command() {
		command = commandValidator.commandToLowercaseParse("create cd 12345678 1.4");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_many_arguments_for_cd_command() {
		command = commandValidator.commandToLowercaseParse("create cd 12345678 1.7 3000 30");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_incorrect_apr_made_of_letters() {
		command = commandValidator.commandToLowercaseParse("create savings 32145678 haha");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_incorrect_apr_value() {
		command = commandValidator.commandToLowercaseParse("create checking 12345678 12.1");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_negative_apr_value() {
		command = commandValidator.commandToLowercaseParse("create checking 12345678 -1.1");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_incorrect_number_of_digits_for_id() {
		command = commandValidator.commandToLowercaseParse("create checking 1234567 2.1");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_incorrect_id_made_from_letters() {
		command = commandValidator.commandToLowercaseParse("create savings Jonathan 2.1");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_incorrect_create_command_due_to_typos() {
		command = commandValidator.commandToLowercaseParse("crate savings 87654321 1.5");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_no_duplicate_bank_id() {
		command = commandValidator.commandToLowercaseParse("create checking 99999999 5.5");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_correct_deposit_command_for_checking() {
		command = commandValidator.commandToLowercaseParse("deposit 99999999 999");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_correct_deposit_command_for_savings() {
		command = commandValidator.commandToLowercaseParse("deposit 11111111 999");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_maximum_deposit_for_checking() {
		command = commandValidator.commandToLowercaseParse("deposit 99999999 1000");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_maximum_deposit_for_savings() {
		command = commandValidator.commandToLowercaseParse("deposit 11111111 2500");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_zero_deposit_amount_for_checking() {
		command = commandValidator.commandToLowercaseParse("deposit 99999999 0");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_zero_deposit_amount_for_savings() {
		command = commandValidator.commandToLowercaseParse("deposit 11111111 0");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_deposit_amount_too_high_for_checking() {
		command = commandValidator.commandToLowercaseParse("deposit 99999999 1001");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_deposit_amount_too_high_for_savings() {
		command = commandValidator.commandToLowercaseParse("deposit 11111111 2501");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_negative_deposit_amount_for_checking() {
		command = commandValidator.commandToLowercaseParse("deposit 99999999 -1");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_negative_deposit_amount_for_savings() {
		command = commandValidator.commandToLowercaseParse("deposit 11111111 -1");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_many_arguments_in_deposit_command() {
		command = commandValidator.commandToLowercaseParse("deposit 99999999 1500 1000");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_less_arguments_in_deposit_command() {
		command = commandValidator.commandToLowercaseParse("deposit 99999999");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_deposit_command_for_an_account_that_does_not_exist() {
		command = commandValidator.commandToLowercaseParse("deposit 46521345");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_invalid_id_digits_for_deposit_command() {
		command = commandValidator.commandToLowercaseParse("deposit 9999");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_id_as_string_for_deposit_command() {
		command = commandValidator.commandToLowercaseParse("deposit Amir");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_typo_in_deposit_command() {
		command = commandValidator.commandToLowercaseParse("depoit 99999999");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_correct_withdraw_command_for_checking() {
		command = commandValidator.commandToLowercaseParse("withdraw 99999999 300");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_correct_withdraw_command_for_savings() {
		command = commandValidator.commandToLowercaseParse("withdraw 11111111 700");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_maximum_withdraw_for_checking() {
		command = commandValidator.commandToLowercaseParse("withdraw 99999999 400");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_maximum_withdraw_for_savings() {
		command = commandValidator.commandToLowercaseParse("withdraw 11111111 1000");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_zero_withdraw_amount_for_checking() {
		command = commandValidator.commandToLowercaseParse("withdraw 99999999 0");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_zero_withdraw_amount_for_savings() {
		command = commandValidator.commandToLowercaseParse("withdraw 11111111 0");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_withdraw_amount_too_high_for_checking() {
		command = commandValidator.commandToLowercaseParse("withdraw 99999999 401");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_withdraw_amount_too_high_for_savings() {
		command = commandValidator.commandToLowercaseParse("withdraw 11111111 1001");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_negative_withdraw_amount_for_checking() {
		command = commandValidator.commandToLowercaseParse("withdraw 99999999 -1");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_negative_withdraw_amount_for_savings() {
		command = commandValidator.commandToLowercaseParse("withdraw 11111111 -1");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_many_arguments_in_withdraw_command() {
		command = commandValidator.commandToLowercaseParse("withdraw 99999999 100 100");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_less_arguments_in_withdraw_command() {
		command = commandValidator.commandToLowercaseParse("withdraw 99999999");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_withdraw_command_for_an_account_that_does_not_exist() {
		command = commandValidator.commandToLowercaseParse("withdraw 76582123");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_invalid_id_digits_for_withdraw_command() {
		command = commandValidator.commandToLowercaseParse("withdraw 1234");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_id_as_string_for_withdraw_command() {
		command = commandValidator.commandToLowercaseParse("withdraw Dr3x3l");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_typo_in_withdraw_command() {
		command = commandValidator.commandToLowercaseParse("withdrawl 99999999");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_one_withdraw_per_month_for_savings() {
		commandProcessor.process("create savings 22224444 1.2");
		commandProcessor.process("deposit 22224444 200");
		commandProcessor.process("withdraw 22224444 100");
		command = commandValidator.commandToLowercaseParse("withdraw 22224444 50");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_monthly_withdraw_limit_for_savings_reset() {
		commandProcessor.process("create savings 22224444 1.2");
		commandProcessor.process("deposit 22224444 200");
		commandProcessor.process("withdraw 22224444 100");
		commandProcessor.process("pass 1");
		command = commandValidator.commandToLowercaseParse("withdraw 22224444 50");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_cd_12_months_withdraw_rule() {
		commandProcessor.process("create cd 12341234 2.2 2000");
		commandProcessor.process("pass 12");
		command = commandValidator.commandToLowercaseParse("withdraw 12341234 3000");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_cd_later_than_12_months_withdraw() {
		commandProcessor.process("create cd 12341234 2.2 2000");
		commandProcessor.process("pass 14");
		command = commandValidator.commandToLowercaseParse("withdraw 12341234 3000");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_cd_before_12_months_withdraw() {
		commandProcessor.process("create cd 12341234 2.2 2000");
		commandProcessor.process("pass 11");
		command = commandValidator.commandToLowercaseParse("withdraw 12341234 3000");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_cd_withdraw_must_be_greater_than_stored_balance() {
		commandProcessor.process("create cd 12341234 2.2 2000");
		commandProcessor.process("pass 12");
		command = commandValidator.commandToLowercaseParse("withdraw 12341234 1800");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_cd_withdraw_can_be_greater_than_stored_balance() {
		commandProcessor.process("create cd 12341234 2.2 2000");
		commandProcessor.process("pass 12");
		command = commandValidator.commandToLowercaseParse("withdraw 12341234 2200");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_correct_pass_time_command() {
		command = commandValidator.commandToLowercaseParse("pass 10");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_too_many_arguments_in_pass_time_command() {
		command = commandValidator.commandToLowercaseParse("pass 10 21");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_less_arguments_in_pass_time_command() {
		command = commandValidator.commandToLowercaseParse("pass");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_incorrect_parameter_as_month_in_pass_time_command() {
		command = commandValidator.commandToLowercaseParse("pass 12.4");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_string_as_month_in_pass_time_command() {
		command = commandValidator.commandToLowercaseParse("pass month");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_transfer_command_from_savings_to_savings() {
		command = commandValidator.commandToLowercaseParse("transfer 11111111 12121212 400");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_transfer_command_from_checking_to_checking() {
		command = commandValidator.commandToLowercaseParse("transfer 99999999 98989898 300");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_transfer_command_from_savings_to_checking() {
		command = commandValidator.commandToLowercaseParse("transfer 11111111 99999999 300");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_transfer_command_from_checking_to_savings() {
		command = commandValidator.commandToLowercaseParse("transfer 99999999 11111111 300");
		assertTrue(commandValidator.validate(command));
	}

	@Test
	public void validate_transfer_command_involving_cd_accounts() {
		commandProcessor.process("create cd 12341234 2.2 2000");
		command = commandValidator.commandToLowercaseParse("transfer 12341234 11111111 300");
		assertFalse(commandValidator.validate(command));
		command = commandValidator.commandToLowercaseParse("transfer 11111111 12341234 300");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_transfer_command_on_same_account() {
		command = commandValidator.commandToLowercaseParse("transfer 11111111 11111111 300");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_transfer_command_on_accounts_that_do_not_exist() {
		command = commandValidator.commandToLowercaseParse("transfer 11111111 64657512 300");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_many_arguments_in_transfer_command() {
		command = commandValidator.commandToLowercaseParse("transfer 11111111 99999999 300 200");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_too_less_arguments_in_transfer_command() {
		command = commandValidator.commandToLowercaseParse("transfer 11111111 99999999");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_incorrect_number_of_account_id_digits_in_transfer_command() {
		command = commandValidator.commandToLowercaseParse("transfer 11111 99999 300 ");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_strings_as_account_id_in_transfer_command() {
		command = commandValidator.commandToLowercaseParse("transfer John Smith");
		assertFalse(commandValidator.validate(command));
	}

	@Test
	public void validate_typo_in_transfer_command() {
		command = commandValidator.commandToLowercaseParse("trasfer 11111111 99999999");
		assertFalse(commandValidator.validate(command));
	}
}
