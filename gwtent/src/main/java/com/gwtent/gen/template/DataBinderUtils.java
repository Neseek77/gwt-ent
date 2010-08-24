package com.gwtent.gen.template;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.gen.GenUtils;
import com.gwtent.reflection.client.pathResolver.PathResolver;
import com.gwtent.uibinder.client.DataBinder;
import com.gwtent.uibinder.client.UIBind;

/**
 * 
 * @author James Luo
 *
 */
public class DataBinderUtils {

	private final SourceWriter source;
	private final JClassType classType;
	
	
	/**
	 * 
	 * @param source
	 * @param ownerClassType the owner class, for HTMLTemplate, this is the HTMLTemplate self, 
	 * for GWTUIBinder, this is the container of UIBinder
	 * @param classType the user define interface type which is extends from DataBinder
	 */
	public DataBinderUtils(SourceWriter source, JClassType classType){
		this.source = source;
		this.classType = classType;
	}
	
	
	public static String getDataBinderClassName(JClassType classType){
		return GenUtils.getQualifiedFullNmae(classType) + "DataBinderImpl";
	}
	
	
	/**
	 * interface GWTUiBinderDataBinder extends DataBinder<GWTUiBinder>{
	 * <p>
	 * <p>	}
	 * 
	 * <p> find a Parameterized class/interface in super classs/interfaces,
	 * this class should a sub class of DataBinder class and will point out what's the owner class
	 * 
	 * <p> if can NOT found, give a error, user should correct this before final compile
	 * @param classType
	 * @return
	 */
	public static JClassType getOwnerClassType(TypeOracle oracle, JClassType classType){
		JClassType dataBinderClassType;
		try {
			dataBinderClassType = oracle.getType(DataBinder.class.getCanonicalName());
		} catch (NotFoundException e) {
			throw new RuntimeException("Can not found DataBinder class, forgot include module xml file?" + e.getMessage());
		}
		for (JClassType supClass : classType.getFlattenedSupertypeHierarchy()){
			if (supClass.isParameterized() != null && supClass.isAssignableTo(dataBinderClassType)){
				if (supClass.isParameterized().getTypeArgs().length == 1){
					return supClass.isParameterized().getTypeArgs()[0];
				}else{
					throw new RuntimeException("DataBinder should have only one Parameterized type, please see document of DataBinder interface. Current processing type: " + classType.getQualifiedSourceName() + ", Current parameterized type count:" + classType.isParameterized().getTypeArgs().length);
				}
			}
		}
		
		throw new RuntimeException("DataBinder should have at least one Parameterized type, please see document of DataBinder interface. Current processing type: " + classType.getQualifiedSourceName());
	}
	
	/**
	 * Build the class, this will build the whole class define
	 * 
	 * @param source
	 * @param ownerClassType
	 */
	public void buildDataBinderClass(TypeOracle oracle){
		JClassType ownerClassType = getOwnerClassType(oracle, classType);
		source.println("public class " + getDataBinderClassName(classType) + 
				" extends com.gwtent.uibinder.client.UIBinderManager<" + ownerClassType.getQualifiedSourceName() + ">" +
				" implements " + classType.getQualifiedSourceName() + "{");
		source.indent();
		
		buildSource(oracle);
		
		source.outdent();
		source.println("}");
	}
	
	
	public void buildSource(TypeOracle oracle){
		JClassType ownerClassType = getOwnerClassType(oracle, classType);
	  source.println("public void bindAll(final " + ownerClassType.getQualifiedSourceName() + " owner) {");
    source.indent();
    
    JClassType curType = ownerClassType;
    while (curType != null) {
      for (JMethod method: curType.getMethods()){
        UIBind bind = GenUtils.getMethodAnnotation(method, UIBind.class);
        processUIBinder(source, ownerClassType, bind, "owner." + method.getName()+"()");
      }
      
      for (JField field : curType.getFields()){
        UIBind bind = field.getAnnotation(UIBind.class);
        processUIBinder(source, ownerClassType, bind, "owner." + field.getName());
      }
      
      curType = curType.getSuperclass();
    }
    
    source.outdent();
    source.println("}");
	}



  private static void processUIBinder(SourceWriter source, JClassType classType,
      UIBind bind, String widgetSource) {
    if (bind != null){
      String path = bind.path();
      Boolean readonly = bind.readonly();
      Class<?> modelClass = bind.modelClass();
      
      //if (PathResolver.getResetElementByPath(path).length() <= 0)
      //  throw new RuntimeException("Path (" + path + ") of class (" + classType.getQualifiedSourceName() + ") not right, we are not support variable binding for now.");

      //getUIBinderManager().addBinder(btn, "path", false, "Model.class");
//      source.println("getUIBinderManager().addBinder(" + widgetSource + ", \"" + PathResolver.getResetElementByPath(path) + "\", " + 
//          readonly.toString() + ", " +
//      		"" + findClassTypeByPath(classType, path) + ".class);");
      String setCode = "";
      String rootValueName = PathResolver.getFirstElementByPath(path);
      
      checkVisable(classType, rootValueName);
      
      String pureValueName = rootValueName;
      rootValueName = "owner." + rootValueName;
      
      if (! rootValueName.endsWith("()")){
        setCode = rootValueName + " = (" + findClassTypeByPath(classType, path)+")value;";
      }
        
      String autoValidate = "false";
      if (bind.autoValidate())
      	autoValidate = "true";
      
      //new Class<?>[]{Default.class}
      StringBuilder sb = new StringBuilder();
      sb.append("new Class<?>[]{");
      boolean first = true;
      for (Class<?> clazz : bind.groups()){
      	if (!first)
      		sb.append(", ");
      	
      	sb.append(clazz.getCanonicalName()).append(".class");
      	
      	first = false;
      }
      sb.append("}");
      
      
      source.println("addBinder(" + widgetSource +", \"" + PathResolver.getResetElementByPath(path) + "\", "
          + readonly.toString() + ", " + findClassTypeByPath(classType, path) + ".class,\n" + 
        "        new com.gwtent.uibinder.client.ModelRootAccessor(){\n"+
        "          public Object getValue() {\n" +
        "            return " + rootValueName + ";\n"+
        "          }\n" +
        "          public String getRootPath() {\n" +
				"		         return \""+ pureValueName +"\";\n" +
				"          }\n" + 
        "          public void setValue(Object value) {\n" +
        "            " + setCode + "\n" +           
        "          }}, "+ autoValidate +", " + sb.toString() + ");");
    }
  }


	private static void checkVisable(JClassType classType, String rootValueName) {
		JField field = GenUtils.findField(classType, rootValueName);
		if (field != null && (field.isPrivate() || field.isProtected())){
			throw new RuntimeException("Field \"" + field.getName() + "\" in " + classType.getQualifiedSourceName() + " can not be private and protected.");
		}
		
		if (rootValueName.endsWith("()"))
			rootValueName = rootValueName.replace("()", "");
		JMethod method = GenUtils.findMethod(classType, rootValueName, new JType[]{});
		if (method != null && (method.isPrivate() || method.isProtected())){
			throw new RuntimeException("Method \"" + method.getName() + "\" in " + classType.getQualifiedSourceName() + " can not be private and protected.");
		}
	}
  
  

	private static String findClassTypeByPath(JClassType classType, String path){
	  String firstPath = PathResolver.getFirstElementByPath(path);
	  
	  if (firstPath.endsWith("()")){
	  	firstPath = firstPath.substring(0, firstPath.length() - 2);
	  }
	  
	  if (GenUtils.findField(classType, firstPath) != null)
	    return GenUtils.findField(classType, firstPath).getType().getQualifiedSourceName();
	  else if (GenUtils.findMethod(classType, firstPath, new JType[]{}) != null){
	    return GenUtils.findMethod(classType, firstPath, new JType[]{}).getReturnType().getQualifiedSourceName();
	  } else{
	    throw new RuntimeException("Can not find first part(" + firstPath + ") of path(" + path + ") in class(" + classType.getQualifiedSourceName() + ")");
	  }
	}
	
	
}
