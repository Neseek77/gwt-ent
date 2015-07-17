# Introduction #

> This demo show how to use domain object and doc annotation create a full function dialog.


# Create domain object #

```
/**
 * @Fields teamID teamName teamDesc
 * @Caption Team Information
 * 
 * @author James Luo
 * 2008-1-1 01:53:26 pm
 *
 */
public class Team implements Reflection, IsSerializable {
	/**
	 * @Caption "TeamID"
	 * @Desc getTeamIDDesc
	 * @Validate NotNull StrLength>6 StrLength<19 teamIDValidate
	 */
	private String teamID;
	/**
	 * @Caption "Team Name"
	 */
	private String teamName;
	/**
	 * @Caption "Team Description"
	 */
	private String teamDesc;
	
	
	public String getTeamDesc() {
		return teamDesc;
	}
	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}
	
	public void teamIDValidate(Object value){
		if (((String)value).equals("TeamID")){
			throw new ValidateException("TeamID cann't named TeamID");
		}else{
			//OK
		}
	}
	
	protected String getTeamIDDesc(){
		//this for our Internationalization function
		return AppMsgFactory.getInstance().TeamIDDesc();
	}
	
	public String getTeamID() {
		return teamID;
	}
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
```


# Generate UI #

As demo, we use gwtext create new dialog, the main code is:
```
...
Team team = new Team();
team.setTeamID("NewTeamID");
team.setTeamName("name");
team.setTeamDesc("this is team description");
Composite body = UIFactory.getInstance().gwtFactory(team, (ClassType)GWT.create(Team.class));
VerticalPanel wrapper = new VerticalPanel();
wrapper.add(body);
...
```

  * the result:
  * ![http://gwt-ent.googlecode.com/svn/images/uigenerate1.jpg](http://gwt-ent.googlecode.com/svn/images/uigenerate1.jpg)

  * we change to chinese locate, it's from getTeamIDDesc:
  * ![http://gwt-ent.googlecode.com/svn/images/uigenerate2.jpg](http://gwt-ent.googlecode.com/svn/images/uigenerate2.jpg)

# Validate #

  * **all validate occur when editor lose focuse or user press enter**

  * we change teamid to "TeamID", this may cann't pass teamIDValidate function
  * ![http://gwt-ent.googlecode.com/svn/images/uigenerate_validate1.jpg](http://gwt-ent.googlecode.com/svn/images/uigenerate_validate1.jpg)

  * we change teamid to "", this may show all validate message
  * ![http://gwt-ent.googlecode.com/svn/images/uigenerate_validate2.jpg](http://gwt-ent.googlecode.com/svn/images/uigenerate_validate2.jpg)

  * we change teamid to "ThisWillMoreThan19Words"
  * ![http://gwt-ent.googlecode.com/svn/images/uigenerate_validate3.jpg](http://gwt-ent.googlecode.com/svn/images/uigenerate_validate3.jpg)