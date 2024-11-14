package com.plane.article.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.plane.common.dto.PageRequest;
import com.plane.trip.domain.TripThema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;


public class ArticleSearchRequest {

	private PageRequest pageRequest;
	 
	 
	@Pattern(regexp = "^(동행|후기)$", message = "글 분류는 동행, 후기 중 하나여야 합니다")
	private String articleType;
    
	private Integer regionId;
    
	private List<TripThema> tripThema;
    
     
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate tripPeriod;
     
    
	@Min(value = 0, message = "여행 일수는 0 이상이어야 합니다")
	private Integer tripDays;
    
	private boolean isRecommand;

     
	public ArticleSearchRequest() {}


	public PageRequest getPageRequest() {
		return pageRequest;
	}


	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}


	public String getArticleType() {
		return articleType;
	}


	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}


	public Integer getRegionId() {
		return regionId;
	}


	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}


	public List<TripThema> getTripThema() {
		return tripThema;
	}


	public void setTripThema(List<TripThema> tripThema) {
		this.tripThema = tripThema;
	}


	public LocalDate getTripPeriod() {
		return tripPeriod;
	}


	public void setTripPeriod(LocalDate tripPeriod) {
		this.tripPeriod = tripPeriod;
	}


	public Integer getTripDays() {
		return tripDays;
	}


	public void setTripDays(Integer tripDays) {
		this.tripDays = tripDays;
	}


	public boolean isRecommand() {
		return isRecommand;
	}


	public void setRecommand(boolean isRecommand) {
		this.isRecommand = isRecommand;
	}

}
