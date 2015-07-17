# Using Validate #

## Links ##
> http://jcp.org/en/jsr/detail?id=303
> http://people.redhat.com/~ebernard/validation/

## Add module inherited ##

Add Validate module if you only using Validation functions in GWTENT
```
<inherits name="com.gwtent.validate.Validate" />
```

Or add GwtEnt to add all modules
```
<inherits name="com.gwtent.GwtEnt" />
```

To make validation message worked or support I18N to your messages, you need to add the codeline to your EntryPoint method
```
GWTValidateMessageStore.getInstance().addMessageObject(GWT.create(ValidateMessages.class), ValidateMessages.class);
```

or implement your own ValidationMessages.

## Create you domain object for validation ##
  1. Your class can inherited from any class
  1. Need reflection support for this class, you can simply add "@Reflectable" or  "@Reflect\_Domain" to your class
  1. Add any JSR303 validate annotation to your class

For example:
```
	@Reflectable
	public static class ClassToValidate{	
		
	  @Required
	  @Size(min=2, max=30)
	  private String firstName;
	  
	  @Required
	  private String lastName;
	  
	  //@Regular(regex="^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+", message="Please input right email address.")
	  //Direct using Regular of Email annotation
	  @Email
	  private String email;

		public void setEmail(String email) {
			this.email = email;
		}

		public String getEmail() {
			return email;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getFirstName() {
			return firstName;
		}
	}
```

# Validate it #

```
		void onClick(ClickEvent e) {
		//Create domain object and setup the values
		ClassToValidate obj = new ClassToValidate();
		obj.setFirstName("First Name");
		obj.setLastName(null); //
		obj.setEmail("not a good email address");
		
		//Validate it by GWT validator
		Set<ConstraintViolation<ClassToValidate>> validateResult = ValidatorFactory.getGWTValidator().validate(obj);
		
		//Display the result
		String errorMsg = "";
		for (ConstraintViolation<ClassToValidate> violation : validateResult){
			errorMsg += violation.getPropertyPath() + ":" + violation.getMessage() + " Value: " + violation.getInvalidValue() + "\n";
		}
		Window.alert(errorMsg);
	}
```

# The results #
```
email not right because it doesn't not follow the rules of a emal
lastName get error because it's "Required".

email:Email required. Value: not a good email address
lastName:This field is required. Value: null
```

# More Info #

See the demo source file in SVN: [http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent\_showcase/src/main/java//com/gwtent/showcase/client/validate/ValidateByCode.java](http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent_showcase/src/main/java//com/gwtent/showcase/client/validate/ValidateByCode.java)

GWTENT ShowCase: [http://gwtent-showcase.appspot.com/](http://gwtent-showcase.appspot.com/)

# Builtin Constraints #

  * AssertFalse
    1. AssertFalseValidator

  * AssertTrue
    1. AssertTrueValidator

  * DecimalMax
    1. DecimalMaxValidatorForString
    1. DecimalMaxValidatorForNumber

  * DecimalMin
    1. DecimalMinValidatorForNumber
    1. DecimalMinValidatorForString

  * Digits
    1. DigitsValidatorForString
    1. DigitsValidatorForNumber

  * Future
    1. FutureValidatorForCalendar
    1. FutureValidatorForDate

  * Max
    1. MaxValidatorForNumber
    1. MaxValidatorForString

  * Min
    1. MinValidatorForNumber
    1. MinValidatorForString

  * NotNull
    1. NotNullValidator

  * Null
    1. NullValidator

  * Past
    1. PastValidatorForCalendar
    1. PastValidatorForDate

  * Pattern
    1. PatternValidator

  * Size
    1. SizeValidatorForString
    1. SizeValidatorForCollection
    1. SizeValidatorForArray
    1. SizeValidatorForMap
    1. SizeValidatorForArraysOfBoolean
    1. SizeValidatorForArraysOfByte
    1. SizeValidatorForArraysOfChar
    1. SizeValidatorForArraysOfDouble
    1. SizeValidatorForArraysOfFloat
    1. SizeValidatorForArraysOfInt
    1. SizeValidatorForArraysOfLong


# For developer who want implement JSR303 #
Please send us a email, we can discuss how to build a way to share JSR303 emulation.
For simple, in Validation class, I just direct created GWT validation implement.