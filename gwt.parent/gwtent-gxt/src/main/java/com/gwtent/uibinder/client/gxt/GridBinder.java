package com.gwtent.uibinder.client.gxt;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.AbstractUIBinder;
import com.gwtent.client.uibinder.IBinderMetaData;
import com.gwtent.client.uibinder.ModelValue;
import com.gwtent.client.uibinder.UIBinder;
import com.gwtent.uibinder.client.gxt.model.ModelDataAdapter;

/**
 * Binder a Grid to a Iterable<POJO>
 * The Value which path point to must an Iterable<POJO> class
 * 
 * To use this binder
 * you have to declare your grid like this:
 * {@code} Grid<ModelDataAdapter<POJO>>
 * ModelDataAdapter implement ModelData automatically for you
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 * @param <D>
 */
public class GridBinder<D extends Iterable<Object>> extends AbstractUIBinder<Grid<ModelData>, D> {
  
  public static class BinderMetaData<D extends Iterable<Object>> implements IBinderMetaData<Grid<ModelData>, D>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{Grid.class};
    }

    public ObjectFactory<UIBinder<Grid<ModelData>, D>> getFactory() {
      return new ObjectFactory<UIBinder<Grid<ModelData>, D>>(){

        public UIBinder<Grid<ModelData>, D> getObject() {
          return new GridBinder<D>();
        }};
    }
  }

  protected void doInit(Grid<ModelData> widget, ModelValue<D> value) {
    //This grid is read only
  }

  protected void setValueToEditor(D value, Grid<ModelData> widget) {
    widget.getStore().removeAll();
    
    if (value != null){
    	List<ModelDataAdapter<Object>> values = new ArrayList<ModelDataAdapter<Object>>();
      for (Object object : value){
        values.add(new ModelDataAdapter<Object>(object));
      }
      widget.getStore().add(values);
    }
  }

}
