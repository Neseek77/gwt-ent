package com.gwtent.client.reflection;


public class Field implements HasMetaData, AccessDef{

	  private final ClassType enclosingType;


	  private int modifierBits;

	  private final String name;

	  //TODO not support yet(in ReflectionCreator), use typename insteed
	  private Type type;
	  private String typeName;
	  
	  private final HasMetaData metaData = new MetaData();

	  public Field(ClassType enclosingType, String name) {
	    this.enclosingType = enclosingType;
	    this.name = name;

	    assert (enclosingType != null);
	    enclosingType.addField(this);
	  }

	  public void addModifierBits(int modifierBits) {
	    this.modifierBits |= modifierBits;
	  }

	  public ClassType getEnclosingType() {
	    return enclosingType;
	  }

	  public String getName() {
	    assert (name != null);
	    return name;
	  }

	  public Type getType() {
	    assert (type != null);
	    return type;
	  }

	  public boolean isDefaultAccess() {
	    return 0 == (modifierBits & (TypeOracle.MOD_PUBLIC | TypeOracle.MOD_PRIVATE | TypeOracle.MOD_PROTECTED));
	  }

	  public boolean isFinal() {
	    return 0 != (modifierBits & TypeOracle.MOD_FINAL);
	  }

	  public boolean isPrivate() {
	    return 0 != (modifierBits & TypeOracle.MOD_PRIVATE);
	  }

	  public boolean isProtected() {
	    return 0 != (modifierBits & TypeOracle.MOD_PROTECTED);
	  }

	  public boolean isPublic() {
	    return 0 != (modifierBits & TypeOracle.MOD_PUBLIC);
	  }

	  public boolean isStatic() {
	    return 0 != (modifierBits & TypeOracle.MOD_STATIC);
	  }

	  public boolean isTransient() {
	    return 0 != (modifierBits & TypeOracle.MOD_TRANSIENT);
	  }

	  public boolean isVolatile() {
	    return 0 != (modifierBits & TypeOracle.MOD_VOLATILE);
	  }

	  public void setType(Type type) {
	    this.type = type;
	  }

	public void addMetaData(String tagName, String[] values) {
		metaData.addMetaData(tagName, values);
		
	}

	public String[][] getMetaData(String tagName) {
		return metaData.getMetaData(tagName);
	}

	public String[] getMetaDataTags() {
		return metaData.getMetaDataTags();
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
