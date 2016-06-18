package subsystemtests;

import java.util.List;
import java.util.stream.Collectors;

import junit.framework.TestCase;
import alltests.AllTests;
import business.customersubsystem.CustomerSubsystemFacade;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.DbClassAddressForTest;
import dbsetup.DbQueries;

public class CustomerSubsystemTest extends TestCase {

	static {
		AllTests.initializeProperties();
	}

	public void testCustomerAddress() {
		String[] insertResult = DbQueries.insertAddressRow();
		String expected = insertResult[1];
		System.out.println("Expected Data:" + expected);

		CustomerSubsystem customerSubsystem = new CustomerSubsystemFacade();
		CustomerProfile custProfile = customerSubsystem.getGenericCustomerProfile();
		custProfile.setCustId(1);

		try {
			DbClassAddressForTest dbclass = customerSubsystem.getGenericDbClassAddress();
			List<Integer> found = dbclass.readAllAddresses(custProfile).stream().map(add -> add.getId())
					.collect(Collectors.toList());
			boolean valfound = false;
			for (Integer addData : found) {
				if (addData.toString().equals(expected))
					valfound = true;
			}
			assertTrue(valfound);

		} catch (Exception e) {
			fail("Inserted value not found");
		} finally {
			DbQueries.deleteAddressRow(expected);
		}

	}

}
