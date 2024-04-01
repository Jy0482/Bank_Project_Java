package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CertificateOfDepositTest {

	public static final String SAMPLE_ACCOUNT_TYPE = "cd";
	public static final String SAMPLE_SUPPLIED_ID = "88888888";
	public static final double SAMPLE_SUPPLIED_APR = 3.2;
	public static final double SAMPLE_SUPPLIED_STARTING_BALANCE = 150.00;
	CertificateOfDeposit cd;

	@BeforeEach
	public void setUp() {
		cd = new CertificateOfDeposit(SAMPLE_ACCOUNT_TYPE, SAMPLE_SUPPLIED_ID, SAMPLE_SUPPLIED_APR,
				SAMPLE_SUPPLIED_STARTING_BALANCE);
	}

	@Test
	public void cd_account_created_with_supplied_balance() {
		double balance = cd.getBalance();
		assertEquals(150.00, balance);
	}

}
