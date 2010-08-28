package com.gwtent.test.validate.constraints;

import static com.gwtent.client.test.validate.util.TestUtil.assertCorrectPropertyPaths;
import static com.gwtent.client.test.validate.util.TestUtil.assertNumberOfViolations;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hibernate.validator.engine.ConstraintValidatorContextImpl;
import org.hibernate.validator.engine.MessageAndPath;
import org.hibernate.validator.engine.PathImpl;
import org.junit.Test;

import com.gwtent.client.test.validate.constraints.Interval;
import com.gwtent.client.test.validate.constraints.Item;
import com.gwtent.client.test.validate.util.TestUtil;


/**
 * 
 * @author James Luo
 *
 * 27/08/2010 8:50:59 AM
 */
public class ConstraintValidatorContextTest extends com.gwtent.client.test.validate.constraints.ConstraintValidatorContextTest {
	public String getModuleName() {
		return null;
	}
	

	/**
	 * HV-198
	 */
	@Test
	public void testCorrectSubNodePath() {
		Validator validator = TestUtil.getValidator();

		Item item = new Item();
		item.interval = new Interval();
		item.interval.start = 10;
		item.interval.end = 5;

		Set<ConstraintViolation<Item>> constraintViolations = validator.validate( item );
		assertNumberOfViolations( constraintViolations, 1 );
		assertCorrectPropertyPaths( constraintViolations, "interval.start" );
	}

	/**
	 * HV-208
	 */
	@Test
	public void testCorrectPath() {
		Validator validator = TestUtil.getValidator();

		Item item = new Item();
		Interval interval = new Interval();
		item.interval = interval;
		item.interval.start = 10;
		item.interval.end = 5;

		Set<ConstraintViolation<Interval>> constraintViolations = validator.validate( interval );
		assertNumberOfViolations( constraintViolations, 1 );
		assertCorrectPropertyPaths( constraintViolations, "start" );
	}

	@Test
	public void testDifferentPaths() {
		String message = "message";
		ConstraintValidatorContextImpl context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message )
				.addNode( "foo" )
				.addNode( "bar" ).inIterable().atIndex( 3 )
				.addConstraintViolation();

		List<MessageAndPath> messageAndPathList = context.getMessageAndPathList();
		assertMessageAndPath( messageAndPathList.get( 0 ), message, "foo[3].bar" );


		context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message )
				.addNode( "foo" )
				.addNode( null ).inIterable().atKey( "test" )
				.addConstraintViolation();

		messageAndPathList = context.getMessageAndPathList();
		assertMessageAndPath( messageAndPathList.get( 0 ), message, "foo[test]" );

		context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message )
				.addNode( "foo" )
				.addNode( "bar" ).inIterable().atKey( "test" )
				.addNode( "fubar" )
				.addConstraintViolation();

		messageAndPathList = context.getMessageAndPathList();
		assertMessageAndPath( messageAndPathList.get( 0 ), message, "foo[test].bar.fubar" );

		context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message )
				.addNode( "foo" )
				.addNode( "bar" ).inIterable().atKey( "test" )
				.addNode( "fubar" ).inIterable().atIndex( 10 )
				.addConstraintViolation();

		messageAndPathList = context.getMessageAndPathList();
		assertMessageAndPath( messageAndPathList.get( 0 ), message, "foo[test].bar[10].fubar" );

		context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message )
				.addNode( "foo" )
				.addNode( "bar" ).inIterable().atKey( "test" )
				.addNode( "fubar" ).inIterable()
				.addConstraintViolation();

		messageAndPathList = context.getMessageAndPathList();
		assertMessageAndPath( messageAndPathList.get( 0 ), message, "foo[test].bar[].fubar" );
	}

	@Test
	public void testMultipleMessages() {
		String message1 = "message1";
		String message2 = "message2";
		ConstraintValidatorContextImpl context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message1 )
				.addNode( "foo" )
				.addNode( "bar" ).inIterable().atKey( "key" )
				.addConstraintViolation();
		context.buildConstraintViolationWithTemplate( message2 )
				.addConstraintViolation();

		List<MessageAndPath> messageAndPathList = context.getMessageAndPathList();
		assertTrue( messageAndPathList.size() == 2 );
		assertMessageAndPath( messageAndPathList.get( 0 ), message1, "foo[key].bar" );
		assertMessageAndPath( messageAndPathList.get( 1 ), message2, "" );
	}

	private ConstraintValidatorContextImpl createEmptyConstraintValidatorContextImpl() {
		ConstraintValidatorContextImpl context = new ConstraintValidatorContextImpl(
				PathImpl.createNewPath( null ), null
		);
		context.disableDefaultConstraintViolation();
		return context;
	}

	private void assertMessageAndPath(MessageAndPath messageAndPath, String expectedMessage, String expectedPath) {
		assertEquals( messageAndPath.getPath(), PathImpl.createPathFromString( expectedPath ), "Wrong path" );
		assertEquals( messageAndPath.getMessage(), expectedMessage, "Wrong message" );
	}
}
