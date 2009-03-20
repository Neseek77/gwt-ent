package com.gwtent.client.test.validate;

import javax.validation.GroupSequence;
import javax.validation.Length;
import javax.validation.NotEmpty;
import javax.validation.NotNull;

import com.gwtent.client.validate.Validatable;

@Validatable
@GroupSequence(name="default", sequence={"minimal","full","extended"})
public class Company {
  
  @NotNull(groups={"minimal","full"})
  @NotEmpty(groups={"full"})
  @Length(min=8, groups={"extended"})
  private String name;
  
  private String address;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress() {
    return address;
  }
}
