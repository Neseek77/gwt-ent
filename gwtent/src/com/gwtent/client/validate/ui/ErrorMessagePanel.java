package com.gwtent.client.validate.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class ErrorMessagePanel extends SimplePanel implements EventPreview{
	public ErrorMessagePanel(){
		super();
		
		//setZIndex(this, 5000);  //Fix for thirdparty componenets, i.e, gxt, gwtext
		
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
	
	
	
	public void hide(){
		DOM.removeEventPreview(this);
		this.setVisible(false);
	}
	
	protected void onDetach() {
    super.onDetach();
  }
	
	public void showPanel(Element attachedElement){
		if (! isAttached){
			Element parent = DOM.getParent(attachedElement);
			DOM.appendChild(parent, this.getElement());
			isAttached = true;
		}
		
		DOM.addEventPreview(this);
		
		setZIndex(this, 5000);
		
		box.setVisible(true);
		
		int offsetWidth = box.getOffsetWidth();
		int offsetHeight = box.getOffsetHeight();
		
		//panel.setWidth(String.valueOf(offsetWidth) + "px");
		//panel.setHeight(String.valueOf(offsetHeight) + "px");
		
		this.setWidgetPositionImpl(this, getLeft(attachedElement, offsetWidth), getTop(attachedElement, offsetHeight));
		
		this.setVisible(true);
		//this.show();
//		this.setPopupPositionAndShow(new PositionCallback(){
//
//			public void setPosition(int offsetWidth, int offsetHeight) {
//				setPopupPosition(getLeft(attachedElement, offsetWidth), getTop(attachedElement, offsetHeight));
//			}});
	}
	
	public void addErrorMsg(String msg){
		box.addErrorMsg(msg);
	}
	
	public void clearErrorMsgs(){
		box.clearErrorMsgs();
	}
	
	private int getTop(Element uiObject, int offsetHeight){
		int result = uiObject.getAbsoluteTop();
	  //result = result - box.getOffsetHeight();
		//int result = uiObject.getOffsetTop();
		result = result - offsetHeight;
	  return result;
	}
	
	private int getLeft(Element uiObject, int offsetWidth){
		//return uiObject.getAbsoluteLeft() + uiObject.getOffsetWidth() - 40;
		return uiObject.getOffsetLeft() + uiObject.getOffsetWidth() - 40;
	}
	
	private static void changeToStaticPositioning(Element elem) {
    DOM.setStyleAttribute(elem, "left", "");
    DOM.setStyleAttribute(elem, "top", "");
    DOM.setStyleAttribute(elem, "position", "");
  }
	
	protected void setZIndex(Widget w, int zindex){
		Element h = w.getElement();
		DOM.setStyleAttribute(h, "z-index", String.valueOf(zindex));
	}
	
	protected void setWidgetPositionImpl(Widget w, int left, int top) {
    Element h = w.getElement();
    if ((left == -1) && (top == -1)) {
      changeToStaticPositioning(h);
    } else {
    	if (left < 0)
    		left = 0;
    	if (top < 0)
    		top = 0;
    	
      DOM.setStyleAttribute(h, "position", "absolute");
      DOM.setStyleAttribute(h, "left", left + "px");
      DOM.setStyleAttribute(h, "top", top + "px");
    }
  }

	private final ErrorMessageBox box;

	public boolean onEventPreview(Event event) {
		Element target = DOM.eventGetTarget(event);

//    boolean eventTargetsPopup = (target != null)
//        && DOM.isOrHasChild(getElement(), target);

    int type = DOM.eventGetType(event);
    switch (type) {
      

      case Event.ONCLICK:
      case Event.ONDBLCLICK: {
        if (target != null) {
        	if (target == box.getLinkClose().getElement())
        		hide();
        }
        break;
      }
    }
    
    
    return true;
	}
	
	private boolean isAttached = false;
}
