### GWT way to support I18N ###

#### Configure Validation Framework for I18N support ####
```
ValidatorFactory factory = Validation.byProvider(GWTEntValidatorProvider.class).configure()
	.addI18NMessages(GWT.create(ValidationMessages.class))
	.buildValidatorFactory();

Validator validator = factory.getValidator();
```


### Message Format ###

> In JSR303, it expected a message format like this:
```
javax.validation.constraints.Size.message=size must be between {min} and {max}
```

> But in GWT, there are two difference, A)The key of message must be a method in a Java interface, so it can't contain "."; B)The parameter in message only support number, for example {0} but not {min}, so we have to change it to following:
```
javax_validation_constraints_Size_message=size must be between {0} and {1}
```