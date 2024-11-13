package com.plane.common.dto;

import java.util.List;

public class PageResponse<T> {
	
	private final List<T> content;
    private final PageInfo pageInfo;

    
    public PageResponse(List<T> content, PageInfo pageInfo) {
        this.content = content;
        this.pageInfo = pageInfo;
    }


	public List<T> getContent() {
		return content;
	}


	public PageInfo getPageInfo() {
		return pageInfo;
	}
    
}
