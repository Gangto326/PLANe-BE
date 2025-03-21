package com.plane.common.util;

import org.springframework.stereotype.Component;

import com.plane.notification.dto.NotificationAction;
import com.plane.notification.dto.NotificationTarget;

@Component
public class NotificationTitleGenerator {

	public String generateTitle(NotificationTarget target, NotificationAction action) {
        return switch (action) {
            case REPORT -> generateReportTitle(target);
            case REQUEST -> generateRequestTitle(target);
            case ACCEPT -> generateAcceptTitle(target);
            case UPDATE -> generateUpdateTitle(target);
            default -> throw new IllegalArgumentException("지원하지 않는 알림 액션입니다: " + action);
        };
    }

	
    private String generateReportTitle(NotificationTarget target) {
        return switch (target.getType()) {
            case ARTICLE -> String.format("%s님의 게시글 '%s'에 신고가 접수되었습니다", target.getNickname(), target.getContent());
            case COMMENT -> String.format("%s님의 댓글 '%s'에 신고가 접수되었습니다", target.getNickname(), target.getContent());
            case ACCOMPANY -> String.format("%s님의 동행 글 '%s'에 신고가 접수되었습니다", target.getNickname(), target.getContent());
            default -> throw new IllegalArgumentException("지원하지 않는 대상입니다: " + target.getType());
        };
    }

    
    private String generateRequestTitle(NotificationTarget target) {
        return switch (target.getType()) {
        	case ARTICLE, COMMENT -> throw new IllegalArgumentException("Unexpected value: " + target.getType());
            case ACCOMPANY -> String.format("%s님이 '%s' 동행에 신청하였습니다", target.getNickname(), target.getContent());
            case REVIEW -> String.format("'%s' 여행이 종료되었습니다. 리뷰를 작성해주세요!", target.getContent());
            case MANNER -> String.format("'%s' 동행원들에 대한 매너 평가를 작성해주세요!", target.getContent());
        };
    }

    
    private String generateAcceptTitle(NotificationTarget target) {
        return switch (target.getType()) {
        	case ARTICLE, COMMENT, REVIEW, MANNER -> throw new IllegalArgumentException("Unexpected value: " + target.getType());
            case ACCOMPANY -> String.format("%s님의 '%s' 동행 신청이 수락되었습니다", target.getNickname(), target.getContent());
        };
    }
    
    private String generateUpdateTitle(NotificationTarget target) {
        return switch (target.getType()) {
        	case ARTICLE, COMMENT, REVIEW, MANNER -> throw new IllegalArgumentException("Unexpected value: " + target.getType());
            case ACCOMPANY -> String.format("'%s' 동행의 계획이 수정되었습니다. 여행을 확인해주세요", target.getContent());
        };
    }
	
}
