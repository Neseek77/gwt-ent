package com.gwtent.showcase.client.uibinder;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;
import com.gwtent.client.reflection.annotations.Reflect_Domain;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.client.template.UIBind;
import com.gwtent.client.widget.PagingPanel;
import com.gwtent.client.widget.PagingPanel.PagingClickListener;
import com.gwtent.showcase.client.BaseShowCasePanel;


@HTMLTemplate("UIBinderSmallExample.html")
public class UIBinderSmallExample extends BaseShowCasePanel {


	@Reflect_Domain
	public class PagingPanelParams {
		final private PagingPanel pnlPaging;
		public PagingPanelParams(PagingPanel pnlPaging){
			this.pnlPaging = pnlPaging;
		}
		public void setDisplayPages(String displayPages) {
			pnlPaging.setDisplayPages(Integer.valueOf(displayPages));
		}
		public String getDisplayPages() {
			return String.valueOf(pnlPaging.getDisplayPages());
		}
		public void setTotalPages(String totalPages) {
			pnlPaging.setTotalRecords(Integer.valueOf(totalPages));
		}
		public String getTotalPages() {
			return String.valueOf(pnlPaging.getTotalRecords());
		}
		public void setPageSize(String pageSize) {
			pnlPaging.setPageSize(Integer.valueOf(pageSize));
		}
		public String getPageSize() {
			return String.valueOf(pnlPaging.getPageSize());
		}
		public void setCurrentPage(String currentPage) {
			pnlPaging.setCurrentPageIndex(Integer.valueOf(currentPage));
		}
		public String getCurrentPage() {
			return String.valueOf(pnlPaging.getCurrentPageIndex());
		}
		
		public void setStartIndex(String startIndex) {
			this.startIndex = startIndex;
		}
		public String getStartIndex() {
			return startIndex;
		}

		private String startIndex;
		
	}
	
	public UIBinderSmallExample(String html) {
		super(html);
		
		params.setTotalPages("500");
		params.setPageSize("10");
		params.setDisplayPages("10");
		
		pnlPaging.addPagingListener(new PagingClickListener(){

			public void onClick(int startIndex, int pageSize) {
				params.setStartIndex(String.valueOf(startIndex));
				modelChanged();
			}});
	}

	
	public String[] getResourceFileNames() {
		return new String[]{"UIBinderSmallExample.java", "UIBinderSmallExample.html"};
	}

	public String getCaseName() {
		return "Basic UIBinder Page";
	}
	
	
	@HTMLWidget
	@UIBind(path = "params.displayPages")
	protected TextBox edtDispalyPages = new TextBox();
	
	@HTMLWidget
	@UIBind(path = "params.totalPages")
	protected TextBox edtTotalPages = new TextBox();
	
	@HTMLWidget
	@UIBind(path = "params.pageSize")
	protected TextBox edtPageSize = new TextBox();
	
	@HTMLWidget
	@UIBind(path = "params.currentPage")
	protected TextBox edtCurrentPage = new TextBox();
	
	@HTMLWidget
	@UIBind(path = "params.startIndex")
	protected TextBox edtStartIndex = new TextBox();
	
	
	@HTMLWidget
	protected PagingPanel pnlPaging = new PagingPanel();
	
	
	protected PagingPanelParams params = new PagingPanelParams(pnlPaging);
}
