package com.gwtent.client.test.validate;

import javax.validation.GroupSequence;

import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

@GroupSequence({Minimal.class, Full.class, Extended.class})
public class Person implements IValidatable {
  
  @NotNull(groups={"minimal","full"})
  @NotEmpty(groups={"full"})
  @Length(minimum=8, groups={"extended"})
  private String lastName;

  @NotNull
  @NotEmpty
  @Length(minimum=3)
  private String firstName;

  @NotEmpty
  public String getLastName() { return this.lastName; }
  public String getFirstName() { return this.firstName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

//  @Pattern(pattern="(.*), (.*)")
//  public String getFullName() {
//     return this.lastName + ", " + this.firstName;
//  }
}
