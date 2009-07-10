package test;

import java.lang.reflect.Field;

import org.testng.annotations.Test;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.Assert;

/**
 * @author Emmanuel Bernard
 */
@Test
public class AccessTypeTest {

	@Test
	public void testExcludeTransientFieldAndStatic() throws Exception{
		absenceOfField( "model.Product_", "nonPersistent" );
		absenceOfField( "model.Product_", "nonPersistent2" );
	}

	@Test
	public void testDefaultAccessTypeOnEntity() throws Exception{
		absenceOfField( "model.User_", "nonPersistent" );
	}

	@Test
	public void testDefaultAccessTypeForSubclassOfEntity() throws Exception{
		absenceOfField( "model.Customer_", "nonPersistent" );
	}

	@Test
	public void testDefaultAccessTypeForEmbeddable() throws Exception{
		absenceOfField( "model.Detail_", "nonPersistent" );
	}

	@Test
	public void testInheritedAccessTypeForEmbeddable() throws Exception{
		absenceOfField( "model.Country_", "nonPersistent" );
		absenceOfField( "model.Pet_", "nonPersistent", "Colleciton of membeddable not taken care of" );
	}

	@Test
	public void testDefaultAccessTypeForMappedSuperclass() throws Exception{
		absenceOfField( "model.Detail_", "volume" );
	}

	@Test
	public void testExplicitAccessTypeAndDefaultFromRootEntity() throws Exception{
		absenceOfField( "model.LivingBeing_", "nonPersistent", "eplicit access type on mapped superclass" );
		absenceOfField( "model.Hominidae_", "nonPersistent", "eplicit access type on entity" );
		absenceOfField( "model.Human_", "nonPersistent", "proper inheritance from root entity access type" );
	}

	@Test
	public void testMemberAccessType() throws Exception{
		presenceOfField( "model.Customer_", "goodPayer", "access type overriding" );
	}

	private void absenceOfField(String className, String fieldName) throws ClassNotFoundException {
		absenceOfField( className, fieldName, "field should not be persistent" );
	}
	private void absenceOfField(String className, String fieldName, String errorString) throws ClassNotFoundException {
		Assert.assertFalse( isFieldHere(className, fieldName), errorString );
	}

	private void presenceOfField(String className, String fieldName, String errorString) throws ClassNotFoundException {
		Assert.assertTrue( isFieldHere(className, fieldName), errorString );
	}

	private boolean isFieldHere(String className, String fieldName) throws ClassNotFoundException {
		Class<?> user_ = Class.forName( className );
		try {
			final Field field = user_.getField( fieldName );
			return true;
		}
		catch (NoSuchFieldException e) {
			return false;
		}
	}
}
