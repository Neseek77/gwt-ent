package com.gwtent.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class PagingPanel extends Grid{
	
	public static interface PagingClickListener{
		public void onClick(final int startIndex, final int pageSize);
	}
	
	static class PagingLink extends Hyperlink{
		private int startIndex;
		private int pageSize;
		private int pageIndex;
		
		private PagingClickListener clickListener;
		
		public PagingLink(int startIndex, int pageSize, int pageIndex, PagingClickListener clickListener){
			super("", "");
			
			this.setStartIndex(startIndex);
			this.setPageSize(pageSize);
			this.setPageIndex(pageIndex);
			this.clickListener = clickListener;
			
			
			
			this.addClickListener(new ClickListener(){

				public void onClick(Widget sender) {
					doOnClick();
				}});
		}
		
		public void setStartIndex(int startIndex) {
			this.startIndex = startIndex;
		}
		public int getStartIndex() {
			return startIndex;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageIndex(int pageIndex) {
			this.pageIndex = pageIndex;
			
			this.setText(String.valueOf(pageIndex + 1));
		}
		
		public int getPageIndex() {
			return pageIndex;
		}
		
		private void doOnClick(){
			if (clickListener != null){
				clickListener.onClick(startIndex, pageSize);
			}
		}

		public void setClickListener(PagingClickListener clickListener) {
			this.clickListener = clickListener;
		}

		public PagingClickListener getClickListener() {
			return clickListener;
		}
	}
	
	private int pageSize = 20;
	private int totalRecords;
	private int displayPages = 10;
	
	private int currentPageIndex;
	
	private List<PagingClickListener> listeners = new ArrayList<PagingClickListener>();
	
	public PagingPanel(){
		this.setStylePrimaryName("gwtent-pagination");
		
		this.resizeRows(1);
	}
	
	/**
	 * How many records per page.
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		
		sizeChanged();
	}

	/**
	 * How many records per page.
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * How many records database have
	 * @param totalRecords
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
		
		sizeChanged();
	}

	/**
	 * How many records database have
	 * @return
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * How many page links you want display to user
	 * @param displayPages
	 */
	public void setDisplayPages(int displayPages) {
		if (displayPages < 5)
			throw new RuntimeException("Display pages must more the 5 pages.");
		
		this.displayPages = displayPages;
	}

	/**
	 * How many page links you want display to user
	 * @return
	 */
	public int getDisplayPages() {
		return displayPages;
	}
	
	/**
	 * How many pages it have
	 * TotalRecords / pageSize
	 * @return
	 */
	public int getPages(){
		if (this.totalRecords % this.pageSize != 0)
			return this.totalRecords / this.pageSize + 1;
		else
			return this.totalRecords / this.pageSize;
	}
	
	private void clearElements(){
		this.resizeRows(0);
	}
	
	private enum CurrentIndexPosition {
		FirstSeg, SecSeg, LastSeg;
		
		//FirstSeg     XXXXXX...XXX
		//SecSeg       XXX...XXX...XX
		//LastSeg      XXX...XXXXXX
	} 
	
	private void updatePageLinks(){
//		int pageLinkCount = getPages();
//		if (pageLinkCount > this.displayPages){
//			pageLinkCount = this.displayPages;
//			
//			int subSegement = this.displayPages / 3; //每一段有多长
//			CurrentIndexPosition pos = CurrentIndexPosition.FirstSeg;
//			if (currentPageIndex <= subSegement * 2)  //10 page, 1-6 just show first and last segement
//				pos = CurrentIndexPosition.FirstSeg;
//			if (currentPageIndex > displayPages - subSegement * 2)
//				pos = CurrentIndexPosition.LastSeg;
//			else
//				pos = CurrentIndexPosition.SecSeg;
//				
//			
//			if (pos == CurrentIndexPosition.FirstSeg){
//				for (int i = 0; i < subSegement * 2; i++){
//					createPageLink(i);
//				}
//			}
//			
//		}else{
			this.resize(1, getPages());
			
			for (int i = 0; i < getPages(); i++){
				createPageLink(i);
			}
//		}
	}

	/**
	 * 
	 * @param index which index we are creating
	 */
	private void createPageLink(int index) {
		this.setWidget(0, index, new PagingLink(pageSize * index, pageSize, index, pagingClickListenerImpl));
	}
	
	private void sizeChanged(){
		clearElements();
		
		if (pageSize <= 0)
			return;
		
		if (totalRecords <= 0)
			return;
		
		if (this.totalRecords > 0){
			updatePageLinks();
		}
	}
	
	private PagingClickListenerImpl pagingClickListenerImpl = new PagingClickListenerImpl();
	class PagingClickListenerImpl implements PagingClickListener{

		public void onClick(final int startIndex, final int pageSize) {
			doPagingClickListener(startIndex, pageSize);
		}
		
	}
	
	
	private void doPagingClickListener(int startIndex, int pageSize){
		for (PagingClickListener listener : this.listeners){
			listener.onClick(startIndex, pageSize);
		}
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}
	
	public void addPagingListener(PagingClickListener listener){
		listeners.add(listener);
	}
	
	public void removePagingListener(PagingClickListener listener){
		listeners.remove(listener);
	}
	
}
