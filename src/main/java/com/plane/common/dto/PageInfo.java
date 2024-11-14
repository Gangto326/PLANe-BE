package com.plane.common.dto;

public class PageInfo {
	
	private final int currentPage;
    private final int pageSize;
    private final int totalPages;
    private final long totalElements;
    
    private final boolean hasNext;
    private final boolean hasPrevious;
    
    
    public PageInfo(int currentPage, int pageSize, int totalPages, long totalElements) {
    	this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        
        this.hasNext = currentPage < totalPages;
        this.hasPrevious = currentPage > 1;
    }
    
    
	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}
    
}
