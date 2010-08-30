package com.gwtent.client.test.validate.tck;

import junit.framework.Test;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.gwtent.client.test.validate.constraints.ValidatorResolutionTest;
import com.gwtent.client.test.validate.tck.tests.bootstrap.ConfigurationTest;
import com.gwtent.client.test.validate.tck.tests.bootstrap.CustomMessageInterpolatorTest;
import com.gwtent.client.test.validate.tck.tests.bootstrap.ValidationProviderTest;
import com.gwtent.client.test.validate.tck.tests.bootstrap.customprovider.BootstrapCustomProviderDefinedInServiceFileTest;
import com.gwtent.client.test.validate.tck.tests.constraints.application.ValidationRequirementTest;
import com.gwtent.client.test.validate.tck.tests.constraints.builtinconstraints.BuiltinConstraintsTest;
import com.gwtent.client.test.validate.tck.tests.constraints.builtinconstraints.BuiltinValidatorOverrideTest;
import com.gwtent.client.test.validate.tck.tests.constraints.constraintcomposition.ConstraintCompositionTest;
import com.gwtent.client.test.validate.tck.tests.constraints.constraintcomposition.nestedconstraintcomposition.NestedConstraintCompositionTest;
import com.gwtent.client.test.validate.tck.tests.constraints.constraintdefinition.ConstraintDefinitionsTest;
import com.gwtent.client.test.validate.tck.tests.constraints.customconstraint.CustomConstraintValidatorTest;
import com.gwtent.client.test.validate.tck.tests.constraints.groups.DefaultGroupRedefinitionTest;
import com.gwtent.client.test.validate.tck.tests.constraints.groups.GroupTest;
import com.gwtent.client.test.validate.tck.tests.constraints.groups.groupsequence.SequenceResolutionTest;
import com.gwtent.client.test.validate.tck.tests.constraints.groups.groupsequenceisolation.GroupSequenceIsolationTest;
import com.gwtent.client.test.validate.tck.tests.constraints.groups.inheritance.GroupInheritanceTest;
import com.gwtent.client.test.validate.tck.tests.constraints.inheritance.ConstraintInheritanceTest;
import com.gwtent.client.test.validate.tck.tests.constraints.invalidconstraintdefinitions.InvalidConstraintDefinitionsTest;
import com.gwtent.client.test.validate.tck.tests.messageinterpolation.MessageInterpolationTest;
import com.gwtent.client.test.validate.tck.tests.metadata.BeanDescriptorTest;
import com.gwtent.client.test.validate.tck.tests.metadata.ConstraintDescriptorTest;
import com.gwtent.client.test.validate.tck.tests.metadata.ElementDescriptorTest;
import com.gwtent.client.test.validate.tck.tests.metadata.PropertyDescriptorTest;
import com.gwtent.client.test.validate.tck.tests.traversableresolver.TraversableResolverTest;
import com.gwtent.client.test.validate.tck.tests.validation.PropertyPathTest;
import com.gwtent.client.test.validate.tck.tests.validation.UnknownProviderBootstrapTest;
import com.gwtent.client.test.validate.tck.tests.validation.ValidatePropertyTest;
import com.gwtent.client.test.validate.tck.tests.validation.ValidateTest;
import com.gwtent.client.test.validate.tck.tests.validation.ValidateValueTest;
import com.gwtent.client.test.validate.tck.tests.validation.ValidateWithGroupsTest;
import com.gwtent.client.test.validate.tck.tests.validation.ValidationTest;
import com.gwtent.client.test.validate.tck.tests.validation.graphnavigation.GraphNavigationTest;

/**
 * 
 * @author James Luo
 *
 * 30/08/2010 9:27:18 AM
 */
public class TCKTestSuite extends GWTTestSuite{
	public static Test suite() {
		GWTTestSuite suite = new GWTTestSuite("Test for com.gwtent.client.validate");
    
    suite.addTest(bootstrap());
    suite.addTest(constraints());
    suite.addTest(messageInterpolation());
    suite.addTest(metadata());
    suite.addTest(traversableResolver());
    suite.addTest(validation());
    suite.addTest(validatorfactory());
    
    return suite;
  }
	
	private static Test bootstrap(){
		GWTTestSuite suite = new GWTTestSuite("Bootstrap");
		
		suite.addTestSuite(ConfigurationTest.class);
		suite.addTestSuite(CustomMessageInterpolatorTest.class);
		suite.addTestSuite(ValidationProviderTest.class);
		suite.addTestSuite(BootstrapCustomProviderDefinedInServiceFileTest.class);
		
		return suite;
	}
	
	private static Test constraints(){
		GWTTestSuite suite = new GWTTestSuite("Constraints");
		
		suite.addTestSuite(ValidationRequirementTest.class);
		suite.addTestSuite(BuiltinConstraintsTest.class);
		suite.addTestSuite(BuiltinValidatorOverrideTest.class);
		suite.addTestSuite(NestedConstraintCompositionTest.class);
		suite.addTestSuite(ConstraintCompositionTest.class);
		suite.addTestSuite(ConstraintDefinitionsTest.class);
		suite.addTestSuite(CustomConstraintValidatorTest.class);
		
		suite.addTestSuite(SequenceResolutionTest.class);
		suite.addTestSuite(GroupSequenceIsolationTest.class);
		suite.addTestSuite(GroupInheritanceTest.class);
		suite.addTestSuite(DefaultGroupRedefinitionTest.class);
		suite.addTestSuite(GroupTest.class);
		
		suite.addTestSuite(ConstraintInheritanceTest.class);
		suite.addTestSuite(InvalidConstraintDefinitionsTest.class);
		suite.addTestSuite(ValidatorResolutionTest.class);
		
		return suite;
	}
	
	private static Test messageInterpolation(){
		GWTTestSuite suite = new GWTTestSuite("Message Interpolation");
		
		suite.addTestSuite(MessageInterpolationTest.class);
		
		return suite;
	}
	
	private static Test metadata(){
		GWTTestSuite suite = new GWTTestSuite("Metadata");
		
		suite.addTestSuite(BeanDescriptorTest.class);
		suite.addTestSuite(ConstraintDescriptorTest.class);
		suite.addTestSuite(ElementDescriptorTest.class);
		suite.addTestSuite(PropertyDescriptorTest.class);

		return suite;
	}
	
	private static Test traversableResolver(){
		GWTTestSuite suite = new GWTTestSuite("Traversable Resolver");
		
		suite.addTestSuite(TraversableResolverTest.class);
		
		return suite;
	} 
	
	
	private static Test validation(){
		GWTTestSuite suite = new GWTTestSuite("Validation");
		
		suite.addTestSuite(GraphNavigationTest.class);
		suite.addTestSuite(com.gwtent.client.test.validate.tck.tests.validation.validatorcontext.ConstraintValidatorContextTest.class);
		suite.addTestSuite(PropertyPathTest.class);
		suite.addTestSuite(UnknownProviderBootstrapTest.class);
		suite.addTestSuite(ValidatePropertyTest.class);
		suite.addTestSuite(ValidateTest.class);
		suite.addTestSuite(ValidateValueTest.class);
		suite.addTestSuite(ValidateWithGroupsTest.class);
		suite.addTestSuite(ValidationTest.class);
		
		return suite;
	} 
	
	
	private static Test validatorfactory(){
		GWTTestSuite suite = new GWTTestSuite("Validator Factory");
		
		suite.addTestSuite(CustomConstraintValidatorTest.class);
		
		return suite;
	} 
}
