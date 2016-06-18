package subsystemtests;

import java.util.logging.Logger;

import junit.framework.TestCase;
import alltests.AllTests;
import business.customersubsystem.CustomerSubsystemFacade;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.ShoppingCart;
import business.externalinterfaces.ShoppingCartSubsystem;
import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;
import dbsetup.DbQueries;

public class ShoppingCartSubsystemTest extends TestCase {
	static String name = "ShoppingCart Subsystem Test";
	static Logger log = Logger.getLogger(ShoppingCartSubsystem.class.getName());
	
	static{
		AllTests.initializeProperties();
	}
	
	public void testMakeSavedCartLive(){
		ShoppingCart expected = DbQueries.readSavedCart();
		
		ShoppingCartSubsystem scs = ShoppingCartSubsystemFacade.INSTANCE;
		
		CustomerSubsystem css = new CustomerSubsystemFacade();
		CustomerProfile custProfile = css.getGenericCustomerProfile();
		custProfile.setCustId(1);
		scs.setCustomerProfile(custProfile);
		try {
			scs.retrieveSavedCart();
		} catch (Exception e) {
			e.printStackTrace();
		}
		scs.makeSavedCartLive();
		ShoppingCart found = scs.getLiveCart();
		assertEquals(expected.getCartItems().size(), found.getCartItems().size());
	}

}
