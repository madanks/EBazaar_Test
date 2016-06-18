package subsystemtests;

import java.util.List;
import java.util.stream.Collectors;

import junit.framework.TestCase;
import alltests.AllTests;
import business.customersubsystem.CustomerSubsystemFacade;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.OrderSubsystem;
import business.ordersubsystem.OrderSubsystemFacade;
import dbsetup.DbQueries;

public class OrderSubsystemTest extends TestCase {

	static {
		AllTests.initializeProperties();
	}

	public void testGetOrderIds() {
		String[] vals = DbQueries.insertOrderRow();
		int expectedId = Integer.parseInt(vals[1]);
		List<Integer> orderIds = null;

		try {
			// Perform test
			CustomerSubsystem customer = new CustomerSubsystemFacade();
			// finish initialization
			CustomerProfile custProfile = customer.getGenericCustomerProfile();
			custProfile.setCustId(1);
			OrderSubsystem oss = new OrderSubsystemFacade(custProfile);
			orderIds = oss.getOrderHistory().stream().map(cat -> cat.getOrderId()).collect(Collectors.toList());
		} catch (Exception ex) {
			fail("Exception: " + ex.getMessage());
		} finally {
			assertTrue(orderIds != null);
			boolean idFound = false;
			if (orderIds != null) {
				for (int next : orderIds) {
					if (next == expectedId) {
						idFound = true;
						System.out.println(next);
						break;
					}
				}
			}
			assertTrue(idFound);
			// Clean up table
			DbQueries.deleteOrderRow(expectedId);

		}
	}

	
	
}
