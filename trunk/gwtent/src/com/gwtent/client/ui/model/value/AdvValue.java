package com.gwtent.client.ui.model.value;

import com.gwtent.client.ui.model.Value;
import com.gwtent.client.ui.validate.Validate;

/**
 * the advance interface of Value
 * 
 * @author James Luo
 * 2008-1-2 上午09:00:50
 *
 */
public interface AdvValue extends Value{

	public abstract Object getOriginalValue();

	public abstract void setOriginalValue(Object originalValue);

	public abstract Getter getValueGetter();

	public abstract void setValueGetter(Getter valueGetter);

	public abstract Setter getValueSetter();

	public abstract void setValueSetter(Setter valueSetter);

	public abstract void setValidate(Validate validate);

}