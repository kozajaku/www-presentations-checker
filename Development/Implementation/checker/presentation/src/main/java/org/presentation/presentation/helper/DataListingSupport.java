/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import org.presentation.presentation.ProtectedBean;

// inspired by http://www.javacodegeeks.com/2013/11/using-jsf-2-2-features-to-develop-ajax-scrollable-lazy-loading-data-table.html

public abstract class DataListingSupport<T extends Serializable> extends ProtectedBean implements Serializable { 
    private int recordCount = 0;
    private int totalPages = 0;
    private DataModel<T> data;

    private int page = 1;
    private Integer rowsPerPage = null;
    private boolean ascending = true;
    private String sortField;

    public void navigatePage(final int page) {
        setPage(page);
        refresh();
    }

    public void sort(final String sortField) {
        setSortField(sortField);
        setAscending(getSortField().equals(sortField) ? !isAscending() : true);
        refresh();
    }

    public void updateRowsPerPage(final AjaxBehaviorEvent event) {
        setPage(1); // page must reset to the first one
        refresh();
    }

    public void refresh() {
        // hook to populate count and data
        populateCountAndData();
        // compute total pages
        setTotalPages(10); //countTotalPages(getRecordCount(), getRowsPerPage()));
    }

    /**
     * The concreate implementation of this class must perform data retrieval
     * based on the current information available (accessible via methods such
     * as {@link #getSortField()}, {@link #isAscending()}, etc.
     * <p>
     * The implementation is responsible in populating the values for {@link #setRecordCount(int)}
     * and {@link #setData(javax.faces.model.DataModel)}
     */
    protected abstract void populateCountAndData();

    /************************************************************** HELPER(S) */

    private int countTotalPages(int totalRecord, int rowsPerPage) {
        
	int pageCounter = 0;
        for (int pageCountTracker = 0; pageCountTracker < totalRecord; ++pageCounter) {
            pageCountTracker += rowsPerPage;
        }
        return pageCounter;
	
	//return (int)Math.ceil((double)totalRecord / rowsPerPage);
    }

    /************************************************* ACCESSORS AND MUTATORS */

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public DataModel<T> getData() {
        return data;
    }

    public void setData(DataModel<T> data) {
        this.data = data;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    
    public Integer[] getPagesAvailable(){
	List<Integer> pages = new ArrayList<>();	
	int pageMax, pageMin;
	int pageRange = 5;
	
	pageMax = this.page + pageRange;
	pageMin = this.page - pageRange;
	
	if(pageMax > this.totalPages) pageMax = this.totalPages;	
	if(pageMin < 1) pageMin = 1;
	
	if((pageMax - this.page) < pageRange) pageMin -= pageMax - this.page;
	if((this.page - pageMin) < pageRange) pageMax += this.page - pageMin;

	if(pageMax > this.totalPages) pageMax = this.totalPages;	
	if(pageMin < 1) pageMin = 1;
	
	
	if(pageMin > 1) pages.add(1); // add the first page always
	for(int i = pageMin; i <= pageMax; i++) {
	    pages.add(i);
	}
	
	return pages.toArray(new Integer[0]);
	
    }
    
}