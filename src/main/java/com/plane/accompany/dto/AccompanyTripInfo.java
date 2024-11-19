package com.plane.accompany.dto;

public class AccompanyTripInfo {

	private Long tripId;
    private int accompanyNum;
    private String applicantId;
    private Long articleId;
    
	public AccompanyTripInfo() {}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public int getAccompanyNum() {
		return accompanyNum;
	}


	public void setAccompanyNum(int accompanyNum) {
		this.accompanyNum = accompanyNum;
	}


	public String getApplicantId() {
		return applicantId;
	}


	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}


	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
    
}
