package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {

	public static final String SAMPLE_ACCOUNT_TYPE = "checking";
	public static final String SAMPLE_SUPPLIED_ID = "87654321";
	public static final double SAMPLE_SUPPLIED_APR = 3.2;
	Checking checking;

	@BeforeEach
	public void setUp() {
		checking = new Checking(SAMPLE_ACCOUNT_TYPE, SAMPLE_SUPPLIED_ID, SAMPLE_SUPPLIED_APR);
	}

	@Test
	public void checking_account_created_with_zero_balance() {
		double balance = checking.getBalance();
		assertEquals(0, balance);
	}

}
