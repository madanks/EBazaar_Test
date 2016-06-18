package daotests;

import java.util.List;

import junit.framework.TestCase;
import alltests.AllTests;
import business.customersubsystem.CustomerSubsystemFacade;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderSubsystem;
import business.ordersubsystem.OrderSubsystemFacade;
import dbsetup.DbQueries;

public class DbClassOrderTest extends TestCase {
	
	static {
		AllTests.initializeProperties();
	}
	
	
	public void testReadAllOrders() {
		List<Order> expected = DbQueries.readOrders();
		
		//test real dbclass order
		CustomerSubsystem customer = new CustomerSubsystemFacade();
        //finish initialization
		CustomerProfile customerProfile = customer.getGenericCustomerProfile();
		customerProfile.setCustId(1);
        OrderSubsystem oss = new OrderSubsystemFacade(customerProfile);
		try {
			List<Order> found = oss.getOrderHistory();
			assertTrue(expected.size() == found.size());
		} catch(Exception e) {
			fail("Order Lists don't match");
		}
		
	}

}
