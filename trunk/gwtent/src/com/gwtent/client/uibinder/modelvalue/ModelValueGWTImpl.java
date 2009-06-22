package com.gwtent.client.uibinder.modelvalue;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.client.reflection.pathResolver.PathResolver;
import com.gwtent.client.uibinder.ModelRootAccessor;
import com.gwtent.client.uibinder.ModelValue;

public class ModelValueGWTImpl extends ModelValueImpl implements
		ModelValue<Object> {

	// final Field field;

	Method getter;
	Method setter;

	final String fullPath;

	// ClassType of the root
	private final ClassType instanceClassType;

	private ClassType lastLevelClassType;
	private String lastPath;

	public ModelValueGWTImpl(Class<?> rootClass, String fullPath,
			boolean readOnly, ModelRootAccessor rootAccessor) {
		super(rootClass, readOnly, rootAccessor);

		this.fullPath = fullPath;
		this.readOnly = readOnly;

		instanceClassType = TypeOracle.Instance.getClassType(rootClass);

		lastLevelClassType = PathResolver.getLastClassTypeByPath(rootClass,
				fullPath);

		lastPath = PathResolver.getLastElementByPath(fullPath);

		getter = ReflectionUtils.getGetter(lastLevelClassType, lastPath);

		if (getter == null)
			throw new RuntimeException("Can't get getter of " + lastPath + " on "
					+ lastLevelClassType.getName());
		
		// this.field = lastLevelClassType.getField(lastPath);
		// if (field == null){
		// this.getter = lastLevelClassType.getMethod(lastPath, null);
		// }
	}

	public boolean getReadOnly() {
		return super.getReadOnly() || (setter == null);
	}

	public Object getValue() {
		Object instance = super.getValue();
		if (instance != null) {
			Object lastLevel = PathResolver.getInstanceLastLevelByPath(instance,
					fullPath);
			if (lastLevel != null) {
				return getter.invoke(lastLevel);
			}
		}

		return null;
	}

	public void setValue(Object value) {

		Object instance = super.getValue();

		setter = ReflectionUtils.getSetter(lastLevelClassType, lastPath);
		if (setter != null) {
			Object lastLevel = PathResolver.getInstanceLastLevelByPath(instance,
					fullPath);
			if (lastLevel == null) // TODO null? throw error or just return?
				return;

			if (doBeforeChangedByBinding(lastLevel, lastPath, value)) {
				setter.invoke(lastLevel, value);
				doAfterChangedByBinding(lastLevel, lastPath, value);
			}
		}

		// if (field != null){
		// Object lastLevel = PathResolver.getInstanceLastLevelByPath(instance,
		// fullPath);
		// if (lastLevel != null){
		// field.setFieldValue(lastLevel, value);
		// }else {
		// //throw new
		// RuntimeException("Instance is null, please check root model class(" +
		// root.getName() + "), path(" + fullPath + ")");
		// }
		// }
	}

	public Class<?> getValueClass() {
		ReflectionUtils.checkReflection(getter.getReturnTypeName());
		
		return ((ClassType) getter.getReturnType()).getDeclaringClass();

		// if (field != null)
		// return ((ClassType)field.getType()).getDeclaringClass();
		// else if (method != null)
		// return ((ClassType)method.getReturnType()).getDeclaringClass();
		//		
		// return null;
	}

}
