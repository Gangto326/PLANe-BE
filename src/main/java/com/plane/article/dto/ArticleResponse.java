package com.plane.article.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.plane.trip.domain.TripThema;

public class ArticleResponse {
	
	// authorId로 Users테이블에서 탐색
	private String nickName;
	
	// Board테이블에서 탐색
	private Long articleId;
	private Long tripId;
	private String articleType;
	private String title;
	private String articlePictureUrl;
	private LocalDateTime createdDate;
    
    // userId와 articleId로 Saved테이블에서 탐색
	private boolean isSaved;
	private boolean isRecommand;
    
    // tripId로 PLANe테이블에서 탐색
	private Integer accompanyNum;
	private String sigungu;
	private LocalDate departureDate;
	private LocalDate arrivedDate;
	
    // tripId로 PLANeTripThema테이블에서 탐색
	private List<Integer> tripThema;

	
	public ArticleResponse() {}

	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
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

	public String getArticlePictureUrl() {
		return articlePictureUrl;
	}

	public void setArticlePictureUrl(String articlePictureUrl) {
		this.articlePictureUrl = articlePictureUrl;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
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

	public Integer getAccompanyNum() {
		return accompanyNum;
	}

	public void setAccompanyNum(Integer accompanyNum) {
		this.accompanyNum = accompanyNum;
	}

	public String getSigungu() {
		return sigungu;
	}

	public void setSigungu(String sigungu) {
		this.sigungu = sigungu;
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

	public List<Integer> getTripThema() {
		return tripThema;
	}

	public void setTripThema(List<Integer> tripThema) {
		this.tripThema = tripThema;
	}
	
}
