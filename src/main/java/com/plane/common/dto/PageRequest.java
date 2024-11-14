package com.plane.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class PageRequest {
	
    @Min(value = 1, message = "페이지는 1 이상이어야 합니다")
    private int page = 1;

    
    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다")
    private int size = 12;

    
    @Pattern(regexp = "^(createdDate|likeCount|viewCount)$", 
            message = "정렬 기준은 createdDate, likeCount, viewCount 중 하나여야 합니다")
    private String sortBy = "createdDate";

    
    @Pattern(regexp = "^(ASC|DESC)$", 
            message = "정렬 방향은 ASC 또는 DESC여야 합니다")
    private String sortDirection = "DESC";


	public PageRequest() {}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public String getSortBy() {
		return sortBy;
	}


	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}


	public String getSortDirection() {
		return sortDirection;
	}


	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	
	public int getOffset() {
        return (this.page - 1) * this.size;
    }
	
}