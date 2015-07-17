# How to create new editor #
  * If you use gwt-ext, you can create new class extends from AbstractExtFieldEditor
  * Other wish you can create new class DateEditor extends from AbstractEditor
  * Or just implement Editor interface, but extends from Abstract classes will save your time.

# Create a date editor using gwtext's DateField #
### 1  Create new class DateEditor ###
> Create new class DateExtEditor extends from AbstractExtFieldEditor or just implements Editor, but extends from AbstractExtFieldEditor will save your time.
> GWT now can't dynamic create class, as a supplement, we provide a creator interface to create new Editor.
> The blank class will looks like this:
```
public class DateExtEditor extends AbstractExtFieldEditor {
	
	public static class DateEditorCreator implements EditorCreator {

		public Editor createEditor(Value value) {
			return new DateExtEditor(value);
		}
	}

	public DateExtEditor(Value value) {
		super(value);
	}

	public Object getValueFromWidget() {
		return null;
	}	
}
```

### 2  Add function code ###
  * In contructor, create and setup real editor
  * Add events to editor and process validate
  * Complete abstract functions

  * The code will looks like this:
```
public class DateExtEditor extends AbstractExtFieldEditor {
	
	public static class DateEditorCreator implements EditorCreator {

		public Editor createEditor(Value value) {
			return new DateExtEditor(value);
		}
	}
	
	private DateField dateField;
	
	public DateExtEditor(Value value) {
		super(value);
		
		dateField = new DateField(new DateFieldConfig(){
			{
				//your config
				 setName("username");
	             setWidth("100%");
			}
		});
		
		if (value.getValue() != null){
			dateField.setValue((Date)value.getValue());
		}
		
		dateField.setDisabled(value.getReadOnly());
		
		getForm().add(dateField);
		dateField.addFieldListener(new FieldListenerImpl());
		getForm().end();
		getForm().render();
	}

	public Object getValueFromWidget() {
		return dateField.getValue();
	}	
}
```

### 3  Register this editor ###
> In class **UIFactory**, this is a fasade class for ui generator system, there is a function named **registerValueTypesAndEditor**, you can create new class inherited from UIFactory and override registerValueTypesAndEditor function, at here, we just put the fllowing sentence:
```
ValueEditorRegister.getInstance().register("java.util.Date", new ValueDefaultImpl.ValueDefaultImplCreator(), new DateExtEditor.DateEditorCreator());
```

### 4  Everything have done, Just test ###
> In our domain class Team, we add two fields, as you see, we just provide "get" function for field registerDate, this will make the editor readonly.
```
	/**
	 * @Caption "Register Date"
	 */
	private Date registerDate;
	/**
	 * @Caption "Effect Date"
	 */
	private Date effectDate = null; 

	public Date getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	public Date getRegisterDate() {
		if (registerDate == null)
			registerDate = new Date();
		
		return registerDate;
	}
//	public void setRegisterDate(Date registerDate){
//		this.registerDate = registerDate;
//	}
```


### The Screenshots ###
  * ![http://gwt-ent.googlecode.com/svn/images/DateExtEditor-Result.jpg](http://gwt-ent.googlecode.com/svn/images/DateExtEditor-Result.jpg)]

# Create a complex Editor #
TODO