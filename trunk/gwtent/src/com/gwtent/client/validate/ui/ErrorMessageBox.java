package com.gwtent.client.validate.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;

public class ErrorMessageBox extends FlexTable {
	public ErrorMessageBox(){
		this.setCellPadding(0);
		this.setCellSpacing(0);
		
		this.getCellFormatter().addStyleName(0, 0, "tl");
		this.getCellFormatter().addStyleName(0, 1, "t");
		this.getCellFormatter().addStyleName(0, 2, "tr");
		
		this.getCellFormatter().addStyleName(1, 0, "l");
		this.getCellFormatter().addStyleName(1, 1, "c");
		this.getCellFormatter().addStyleName(1, 2, "r");
		
		this.getCellFormatter().addStyleName(2, 0, "bl");
		this.getCellFormatter().addStyleName(2, 1, "b");
		this.getCellFormatter().addStyleName(2, 2, "br");
		
		tblErrors.setStylePrimaryName("err");
		this.setWidget(1, 1, tblErrors);
		
		this.setStylePrimaryName("validate_tb");
	}
	
	public void setShowCloseButton(boolean showCloseButton) {
		this.showCloseButton = showCloseButton;
		
		if (showCloseButton){
			if (linkClose == null){
				linkClose = new Hyperlink("", "");
				linkClose.setStylePrimaryName("close");
				
				tblErrors.setWidget(0, 1, linkClose);
			}
			
			linkClose.setVisible(true);
		}else{
			if (linkClose != null)
				linkClose.setVisible(false);
		}
	}

	public boolean isShowCloseButton() {
		return showCloseButton;
	}
	
	public void addErrorMsg(String msg){
		String firstLine = tblErrors.getText(0, 0);
		int count = tblErrors.getRowCount();
		
		if (firstLine == null || firstLine.length() <= 0)
			count = 0;
		
		tblErrors.setHTML(count, 0, "<p>" + msg + "</p>");
	}

	Hyperlink getLinkClose(){
		return linkClose;
	}
	
	private Hyperlink linkClose = null;
	private FlexTable tblErrors = new FlexTable();
	private boolean showCloseButton;
	
}
