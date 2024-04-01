package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {

	public static final String SAMPLE_ACCOUNT_TYPE = "savings";
	public static final String SAMPLE_SUPPLIED_ID = "43218765";
	public static final double SAMPLE_SUPPLIED_APR = 2.8;
	Savings savings;

	@BeforeEach
	public void setUp() {
		savings = new Savings(SAMPLE_ACCOUNT_TYPE, SAMPLE_SUPPLIED_ID, SAMPLE_SUPPLIED_APR);
	}

	@Test
	public void checking_account_created_with_zero_balance() {
		double balance = savings.getBalance();
		assertEquals(0, balance);
	}
}
