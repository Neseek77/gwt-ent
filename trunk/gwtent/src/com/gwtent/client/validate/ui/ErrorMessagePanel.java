package com.gwtent.client.validate.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class ErrorMessagePanel extends PopupPanel{
	public ErrorMessagePanel(){
		this.box = new ErrorMessageBox();
		box.setShowCloseButton(true);
		this.add(box);
		
		box.getLinkClose().addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				ErrorMessagePanel.this.hide();
			}});
		
		this.setStylePrimaryName("validate_pnl");
	}
	
	public ErrorMessageBox getErrorBox() {
		return box;
	}

	public void showPanel(final UIObject uiObject){
		//this.setPopupPosition(getLeft(uiObject), getTop(uiObject));
		
		//this.show();
		this.setPopupPositionAndShow(new PositionCallback(){

			public void setPosition(int offsetWidth, int offsetHeight) {
				setPopupPosition(getLeft(uiObject, offsetWidth), getTop(uiObject, offsetHeight));
			}});
	}
	
	public void addErrorMsg(String msg){
		box.addErrorMsg(msg);
	}
	
	private int getTop(UIObject uiObject, int offsetHeight){
		int result = uiObject.getAbsoluteTop();
	  //result = result - box.getOffsetHeight();
		result = result - offsetHeight;
	  return result;
	}
	
	private int getLeft(UIObject uiObject, int offsetWidth){
		return uiObject.getAbsoluteLeft() + uiObject.getOffsetWidth() - 40;
	}
	
	private static void changeToStaticPositioning(Element elem) {
    DOM.setStyleAttribute(elem, "left", "");
    DOM.setStyleAttribute(elem, "top", "");
    DOM.setStyleAttribute(elem, "position", "");
  }
	
	protected void setWidgetPositionImpl(Widget w, int left, int top) {
    Element h = w.getElement();
    if ((left == -1) && (top == -1)) {
      changeToStaticPositioning(h);
    } else {
      DOM.setStyleAttribute(h, "position", "absolute");
      DOM.setStyleAttribute(h, "left", left + "px");
      DOM.setStyleAttribute(h, "top", top + "px");
    }
  }
	
	private final ErrorMessageBox box;
}
