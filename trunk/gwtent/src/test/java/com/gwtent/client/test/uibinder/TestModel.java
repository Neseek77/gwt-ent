package com.gwtent.client.test.uibinder;

import java.util.Date;

import com.gwtent.reflection.client.Reflection;

public class TestModel implements Reflection {
  
    
  private String firstName;
  
  private String lastName;
  
  private Date dateOfBirth;
  
  private Sex sex;

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setSex(Sex sex) {
    this.sex = sex;
  }

  public Sex getSex() {
    return sex;
  }
}
