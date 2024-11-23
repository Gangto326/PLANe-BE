package com.plane.article.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.plane.trip.domain.TripThema;

public class ArticleDetailResponse {
	
	// articleId로 Board테이블에서 탐색
	private Long articleId;
	private String authorId;
    private Long tripId;
    private String articleType;
    private String title;
    private String content;
    private String articlePictureUrl;
    private Integer likeCount;
    private Integer viewCount;
    private LocalDateTime createdDate;
    private LocalDateTime deletedDate;
    
    // userId와 articleId로 Saved테이블에서 탐색
    private boolean isSaved;
    private boolean isRecommand;
    private boolean isAuthor;
    
    // tripId로 PLANe테이블에서 탐색
    private Integer accompanyNum;
    private Integer regionId;
    private LocalDate departureDate;
    private LocalDate arrivedDate;
	
    // tripId로 PLANeTripThema테이블에서 탐색
    private List<TripThema> tripThema;

    
	public ArticleDetailResponse() {}


	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}


	public String getAuthorId() {
		return authorId;
	}


	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public String getArticleType() {
		return articleType;
	}


	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getArticlePictureUrl() {
		return articlePictureUrl;
	}


	public void setArticlePictureUrl(String articlePictureUrl) {
		this.articlePictureUrl = articlePictureUrl;
	}


	public Integer getLikeCount() {
		return likeCount;
	}


	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}


	public Integer getViewCount() {
		return viewCount;
	}


	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}


	public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}


	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}


	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}


	public boolean isSaved() {
		return isSaved;
	}


	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}


	public boolean isRecommand() {
		return isRecommand;
	}


	public void setRecommand(boolean isRecommand) {
		this.isRecommand = isRecommand;
	}
	

	public boolean isAuthor() {
		return isAuthor;
	}


	public void setAuthor(boolean isAuthor) {
		this.isAuthor = isAuthor;
	}


	public Integer getAccompanyNum() {
		return accompanyNum;
	}


	public void setAccompanyNum(Integer accompanyNum) {
		this.accompanyNum = accompanyNum;
	}


	public Integer getRegionId() {
		return regionId;
	}


	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}


	public LocalDate getDepartureDate() {
		return departureDate;
	}


	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}


	public LocalDate getArrivedDate() {
		return arrivedDate;
	}


	public void setArrivedDate(LocalDate arrivedDate) {
		this.arrivedDate = arrivedDate;
	}


	public List<TripThema> getTripThema() {
		return tripThema;
	}


	public void setTripThema(List<TripThema> tripThema) {
		this.tripThema = tripThema;
	}	
    
}
