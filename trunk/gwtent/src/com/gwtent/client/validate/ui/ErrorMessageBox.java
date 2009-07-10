package com.gwtent.client.validate.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.gwtent.client.utils.UserAgent;

public class ErrorMessageBox extends FlexTable {
	public ErrorMessageBox(){
		this.setCellPadding(0);
		this.setCellSpacing(0);
		
		addStyleName(0, 0, "tl");
		addStyleName(0, 1, "t");
		addStyleName(0, 2, "tr");
		
		addStyleName(1, 0, "l");
		addStyleName(1, 1, "c");
		addStyleName(1, 2, "r");
		
		addStyleName(2, 0, "bl");
		addStyleName(2, 1, "b");
		addStyleName(2, 2, "br");
		
		tblErrors.setStylePrimaryName("err");
		this.setWidget(1, 1, tblErrors);
		
		this.setStylePrimaryName("validate_tb");
	}
	
	private void addStyleName(int row, int column, String styleName){
		if (UserAgent.isIE6())
			styleName = styleName + "-ie6";
		
		this.getCellFormatter().addStyleName(row, column, styleName);
	}
	
	public void setShowCloseButton(boolean showCloseButton) {
		this.showCloseButton = showCloseButton;
		
		if (showCloseButton){
			if (linkClose == null){
				linkClose = new Hyperlink("", "");
				if (UserAgent.isIE6())
					linkClose.setStylePrimaryName("close-ie6");
				else
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
	
	public void clearErrorMsgs(){
		for (int i = tblErrors.getRowCount() - 1; i>0; i--){
			tblErrors.removeRow(i);
		}
		
		tblErrors.setHTML(0, 0, "");
	}
	
	public boolean hasErrorMessages(){
		return (tblErrors.getRowCount() > 1) || (tblErrors.getRowCount() == 1 && tblErrors.getHTML(0, 0) != null && tblErrors.getHTML(0, 0).length() > 0);
	}

	Hyperlink getLinkClose(){
		return linkClose;
	}
	
	private Hyperlink linkClose = null;
	private FlexTable tblErrors = new FlexTable();
	private boolean showCloseButton;
	
}
