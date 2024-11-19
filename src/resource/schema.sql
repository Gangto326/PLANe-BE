DROP DATABASE IF EXISTS `plane_db`;
CREATE DATABASE `plane_db`;
USE `plane_db`;

CREATE TABLE `Users` (
	`userId` VARCHAR(100) NOT NULL COMMENT 'PK',
	`password` VARCHAR(128) NOT NULL COMMENT 'Hash 처리',
	`nickName` VARCHAR(100) NOT NULL,
	`phone` VARCHAR(128) NOT NULL COMMENT 'Hash 처리, 중복 가입 방지',
	`role` VARCHAR(10) NOT NULL DEFAULT '회원' COMMENT '회원, 동행, 관리자',
	`email` VARCHAR(100) NULL COMMENT '비밀번호 찾기 시 사용',
	`manner` DOUBLE NOT NULL DEFAULT 0,
	`profileUrl` VARCHAR(255) NULL DEFAULT '기본 이미지',
	`introduce` VARCHAR(100) NULL,
	`authentication` BOOL NOT NULL DEFAULT FALSE,
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`state` VARCHAR(10) NOT NULL DEFAULT '정상' COMMENT '정상, 정지, 탈퇴',
	`isPublic` BOOL NOT NULL DEFAULT TRUE,
    `loginAttempts` INT NOT NULL DEFAULT 0,
	PRIMARY KEY (`userId`)
);

CREATE TABLE `Notification` (
	`noId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL COMMENT '알림받을 회원의 아이디',
	`isRead` BOOL NOT NULL DEFAULT FALSE,
	`notificationType` VARCHAR(100) NOT NULL DEFAULT '기본' COMMENT '기본, 동행, 신고, 매너 등',
	`contentId` BIGINT(20) NOT NULL COMMENT '관련 컨텐츠의 ID',
	`title` VARCHAR(255) NOT NULL,
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`deletedDate` TIMESTAMP NULL,
	PRIMARY KEY (`noId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
);

CREATE TABLE `Region` (
	`regionId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`city` VARCHAR(50) NOT NULL COMMENT 'Unique',
	`sigungu` VARCHAR(50) NOT NULL COMMENT 'Unique',
	PRIMARY KEY (`regionId`)
);

CREATE TABLE `PLANe` (
	`tripId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`regionId` BIGINT(20) NOT NULL,
	`tripName` VARCHAR(100) NOT NULL,
	`departureDate` DATE NOT NULL,
	`arrivedDate` DATE NOT NULL COMMENT '계획에 맞게 자동 설정',
	`state` VARCHAR(10) NOT NULL DEFAULT '임시저장' COMMENT '임시저장, 확정',
	`accompanyNum` INT NOT NULL DEFAULT 1 COMMENT '최대 6',
	`tripDays` INT NOT NULL COMMENT '도착일 - 출발일',
	`isLiked` BOOL NOT NULL DEFAULT FALSE,
	`isPublic` BOOL NOT NULL DEFAULT FALSE,
	`isReviewed` BOOL NOT NULL DEFAULT FALSE,
	PRIMARY KEY (`tripId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`regionId`) REFERENCES `Region`(`regionId`)
);

CREATE TABLE `TripPlan` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`tripId` BIGINT(20) NOT NULL,
	`tripDay` INT NOT NULL COMMENT '1~3일',
	`tripOrder` INT NOT NULL COMMENT '해당 위치의 여행 순서',
	`name` VARCHAR(100) NOT NULL,
	`point` POINT NOT NULL COMMENT '해당 위치의 좌표값',
	`address` VARCHAR(100) NULL COMMENT '도로명 우선',
	`numAddress` VARCHAR(100) NULL COMMENT '도로명 주소 없을 시 필수',
	PRIMARY KEY (`id`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`)
);

CREATE TABLE `TripThema` (
	`themaId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`themaName` VARCHAR(10) NOT NULL,
	PRIMARY KEY (`themaId`)
);

CREATE TABLE `PLANeTripThema` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`tripId` BIGINT(20) NOT NULL,
	`themaId` BIGINT(20) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`),
	FOREIGN KEY (`themaId`) REFERENCES `TripThema`(`themaId`)
);

CREATE TABLE `Board` (
	`articleId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`authorId` VARCHAR(100) NOT NULL,
	`tripId` BIGINT(20) NOT NULL,
	`articleType` VARCHAR(50) NOT NULL COMMENT '동행/후기',
	`title` VARCHAR(100) NOT NULL,
	`content` TEXT NULL,
	`articlePictureUrl` VARCHAR(255) NULL,
	`likeCount` INT NOT NULL DEFAULT 0,
	`viewCount` INT NOT NULL DEFAULT 1,
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`updatedDate` TIMESTAMP NULL,
	`deletedDate` TIMESTAMP NULL,
	PRIMARY KEY (`articleId`),
	FOREIGN KEY (`authorId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`)
);

CREATE TABLE `Manners` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`evaluatorId` VARCHAR(100) NOT NULL,
	`score` INT NOT NULL DEFAULT 0 COMMENT '0 점은 미평가',
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	PRIMARY KEY (`id`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`evaluatorId`) REFERENCES `Users`(`userId`)
);

CREATE TABLE `AfterTrip` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`tripId` BIGINT(20) NOT NULL,
	`tripDay` INT NOT NULL COMMENT '1~3일',
	`content` VARCHAR(500) NULL COMMENT 'AI 생성, 수정 가능',
	PRIMARY KEY (`id`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`)
);

CREATE TABLE `Report` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`articleId` BIGINT(20) NOT NULL,
	`userId` VARCHAR(100) NOT NULL,
	`reportId` BIGINT(20) NOT NULL,
	`details` VARCHAR(255) NULL,
	`reportDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	PRIMARY KEY (`id`),
	FOREIGN KEY (`articleId`) REFERENCES `Board`(`articleId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
);

CREATE TABLE `UsersTripThema` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`themaId` BIGINT(20) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`themaId`) REFERENCES `TripThema`(`themaId`)
);

CREATE TABLE `TripStyle` (
	`styleId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`styleName` VARCHAR(20) NOT NULL,
	PRIMARY KEY (`styleId`)
);

CREATE TABLE `UsersTripStyle` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`styleId` BIGINT(20) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`styleId`) REFERENCES `TripStyle`(`styleId`)
);

CREATE TABLE `MannerTags` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`evaluatorId` VARCHAR(100) NOT NULL,
	`mannerTagName` VARCHAR(50) NOT NULL COMMENT '꼼꼼해요, 친절해요 등',
	`createdDate` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`evaluatorId`) REFERENCES `Users`(`userId`)
);

CREATE TABLE `Interactions` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`articleId` BIGINT(20) NOT NULL,
	`type` ENUM('RECOMMAND', 'SAVE') NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`articleId`) REFERENCES `Board`(`articleId`)
);

CREATE TABLE `Accompany` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`tripId` BIGINT(20) NOT NULL,
	`userId` VARCHAR(100) NOT NULL,
	`role` VARCHAR(10) NOT NULL DEFAULT '일반' COMMENT '팀장, 동행원(수정가능), 일반',
	PRIMARY KEY (`id`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
);

CREATE TABLE `TripMap` (
	`mapId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`tripId` BIGINT(20) NOT NULL,
	`mapLocate` POINT NOT NULL COMMENT '여행을 대표할 좌표',
	`mapPictureUrl` VARCHAR(255) NULL COMMENT '지도에 표시 될 사진',
	`mapContent` VARCHAR(500) NULL,
	`createdDate` TIMESTAMP NOT NULL,
	PRIMARY KEY (`mapId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`)
);

CREATE TABLE `Record` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`id2` BIGINT(20) NOT NULL,
	`RecordPictureUrl` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`id2`) REFERENCES `PLANe`(`tripId`)
);

CREATE TABLE `Comment` (
	`commentId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`articleId` BIGINT(20) NOT NULL,
	`authorId` VARCHAR(100) NOT NULL,
	`parents` BIGINT(20) NULL COMMENT 'NULL이면 최상위 글',
	`commentContents` VARCHAR(255) NOT NULL,
	`status` VARCHAR(10) NOT NULL DEFAULT '공개',
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`updatedDate` TIMESTAMP NULL,
	PRIMARY KEY (`commentId`),
	FOREIGN KEY (`articleId`) REFERENCES `Board`(`articleId`),
	FOREIGN KEY (`authorId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`parents`) REFERENCES `Comment`(`commentId`)
);

CREATE TABLE `AccompanyApply` (
	`applyId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`articleId` BIGINT(20) NOT NULL,
	`userId` VARCHAR(100) NOT NULL,
	`isOk` BOOL NOT NULL DEFAULT FALSE,
	`status` VARCHAR(10) NOT NULL DEFAULT '미확인' COMMENT '확인, 미확인, 수정',
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`deletedDate` TIMESTAMP NULL,
	PRIMARY KEY (`applyId`),
	FOREIGN KEY (`articleId`) REFERENCES `Board`(`articleId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
);

CREATE TABLE `ApplyDetails` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`applyId` BIGINT(20) NOT NULL,
	`askId` BIGINT(20) NOT NULL,
	`answer` VARCHAR(500) NOT NULL,
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
    `updatedDate` TIMESTAMP NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`applyId`) REFERENCES `AccompanyApply`(`applyId`),
	FOREIGN KEY (`askId`) REFERENCES `Ask`(`askId`)	
);

CREATE TABLE `Ask` (
	`askId` BIGINT(20) NOT NULL COMMENT 'PK',
	`content` VARCHAR(500) NOT NULL,
	PRIMARY KEY (`askId`)
);

CREATE TABLE `AuthenticationData` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`Field` VARCHAR(255) NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
);

CREATE TABLE `CommentReport` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`commentId` BIGINT(20) NOT NULL,
	`userId` VARCHAR(100) NOT NULL,
	`reportId` BIGINT(20) NOT NULL,
	`details` VARCHAR(255) NULL,
	`reportDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	PRIMARY KEY (`id`),
	FOREIGN KEY (`commentId`) REFERENCES `Comment`(`commentId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
);

CREATE TABLE `VerificationCodes` (
	`codeId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`email` VARCHAR(100) NULL COMMENT '코드를 받을 email',
    `verificationCode` VARCHAR(100) NOT NULL COMMENT '인증코드',
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`codeId`)
);

CREATE TABLE `Tokens` (
    `tokenId` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `userId` VARCHAR(50) NOT NULL,
    `tokenType` VARCHAR(20) NOT NULL,
    `tokenValue` TEXT NOT NULL,
    `isActive` BOOLEAN DEFAULT true,
    `issuedAt` BIGINT NOT NULL,
    `expiresAt` BIGINT NOT NULL,
    `createdAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updatedAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- FK 제약조건 추가
    FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
        ON DELETE CASCADE    -- 사용자 삭제시 토큰도 삭제
        ON UPDATE CASCADE,   -- 사용자 ID 변경시 토큰도 업데이트

    INDEX idx_user_token (`userId`, `tokenType`),
    INDEX idx_expires (`expiresAt`)
);

-- Users
INSERT INTO `Users` (`userId`, `password`, `nickName`, `phone`, `role`, `email`, `manner`, `profileUrl`, `introduce`, `authentication`, `createdDate`, `state`, `isPublic`, `loginAttempts`)
VALUES
('user001', 'hashed_password1', 'Traveler01', '01012345678', '회원', 'user001@example.com', 4.5, 'profile001.jpg', 'Hello! I love traveling.', TRUE, NOW(), '정상', TRUE, 0),
('user002', 'hashed_password2', 'Explorer02', '01023456789', '동행', 'user002@example.com', 3.0, 'profile002.jpg', 'I enjoy exploring new places.', FALSE, NOW(), '정상', TRUE, 0),
('user003', 'hashed_password3', 'Admin03', '01034567890', '관리자', 'user003@example.com', 5.0, 'profile003.jpg', 'Site administrator.', TRUE, NOW(), '정상', TRUE, 0),
('user004', 'hashed_password4', 'Adventurer04', '01045678901', '회원', 'user004@example.com', 2.5, 'profile004.jpg', 'Looking for adventure buddies!', FALSE, NOW(), '정상', TRUE, 0),
('user005', 'hashed_password5', 'Nomad05', '01056789012', '동행', 'user005@example.com', 3.8, 'profile005.jpg', 'Digital nomad, always on the move.', TRUE, NOW(), '정상', TRUE, 0);

-- Region
INSERT INTO `Region` (`regionId`, `city`, `sigungu`)
VALUES
(1, 'Seoul', 'Jongno-gu'),
(2, 'Jeju', 'Hallim-eup'),
(3, 'Gangwon', 'Pyeongchang-gun');

-- Notification
INSERT INTO `Notification` (`noId`, `userId`, `isRead`, `notificationType`, `details`, `createdDate`)
VALUES
(1, 'user001', FALSE, '기본', 'You have a new message.', NOW()),
(2, 'user002', TRUE, '동행', 'Your trip request has been approved.', NOW()),
(3, 'user003', FALSE, '신고', 'A user has been reported.', NOW()),
(4, 'user004', TRUE, '매너', 'You received a 5-star manner rating.', NOW());

-- PLANe
INSERT INTO `PLANe` (`tripId`, `userId`, `regionId`, `tripName`, `departureDate`, `arrivedDate`, `state`, `accompanyNum`, `tripDays`, `isLiked`, `isPublic`, `isReviewed`)
VALUES
(1, 'user001', 1, 'Seoul to Busan Adventure', '2024-11-10', '2024-11-15', '확정', 3, 5, TRUE, TRUE, TRUE),
(2, 'user002', 2, 'Jeju Island Escape', '2024-12-01', '2024-12-05', '임시저장', 1, 4, FALSE, FALSE, FALSE),
(3, 'user003', 3, 'Gangwon Hiking Trip', '2024-12-10', '2024-12-14', '확정', 2, 4, TRUE, TRUE, TRUE);

-- TripPlan
INSERT INTO `TripPlan` (`id`, `tripId`, `tripDay`, `tripOrder`, `name`, `point`, `address`, `numAddress`)
VALUES
(1, 1, 1, 1, 'Seoul Tower', POINT(37.5512, 126.9882), 'Namsan, Seoul', '123-45'),
(2, 1, 2, 2, 'Gyeongbok Palace', POINT(37.5796, 126.9770), '161 Sajik-ro, Jongno-gu, Seoul', '161-1'),
(3, 2, 1, 1, 'Hyeopjae Beach', POINT(33.3944, 126.2399), 'Hyeopjae-ri, Hallim-eup, Jeju', '112-3'),
(4, 2, 2, 2, 'Hallasan Mountain', POINT(33.3617, 126.5334), 'Hallasan, Jeju', '11-22'),
(5, 3, 1, 1, 'Odaesan National Park', POINT(37.7898, 128.5919), 'Odaesan, Gangwon', '89-1');

-- TripThema
INSERT INTO `TripThema` (`themaId`, `themaName`)
VALUES
(1, 'Nature'),
(2, 'Culture'),
(3, 'Adventure');

-- PLANeTripThema
INSERT INTO `PLANeTripThema` (`id`, `tripId`, `themaId`)
VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 1),
(4, 3, 3);

-- Board
INSERT INTO `Board` (`articleId`, `authorId`, `tripId`, `articleType`, `title`, `content`, `articlePictureUrl`, `likeCount`, `viewCount`, `createdDate`)
VALUES
(1, 'user001', 1, '동행', 'Looking for travel buddies!', 'Join us for an exciting trip to Busan.', 'busan_trip.jpg', 10, 100, NOW()),
(2, 'user002', 2, '후기', 'Amazing Jeju Island trip!', 'Had a wonderful time exploring Jeju.', 'jeju_trip.jpg', 20, 150, NOW());

-- Manners
INSERT INTO `Manners` (`id`, `userId`, `evaluatorId`, `score`, `createdDate`)
VALUES
(1, 'user001', 'user002', 5, NOW()),
(2, 'user002', 'user001', 4, NOW()),
(3, 'user003', 'user001', 5, NOW());

-- AfterTrip
INSERT INTO `AfterTrip` (`id`, `tripId`, `tripDay`, `content`)
VALUES
(1, 1, 1, 'Amazing first day visiting Seoul Tower!'),
(2, 1, 2, 'Gyeongbok Palace was breathtaking.');

-- Report
INSERT INTO `Report` (`id`, `articleId`, `userId`, `reportId`, `details`, `reportDate`)
VALUES
(1, 1, 'user002', 1, 'This article seems to be spam.', NOW());

-- UsersTripThema
INSERT INTO `UsersTripThema` (`id`, `userId`, `themaId`)
VALUES
(1, 'user001', 1),
(2, 'user002', 2),
(3, 'user003', 3);

-- TripStyle
INSERT INTO `TripStyle` (`styleId`, `styleName`)
VALUES
(1, 'Backpacking'),
(2, 'Luxury'),
(3, 'Road Trip');

-- UsersTripStyle
INSERT INTO `UsersTripStyle` (`id`, `userId`, `styleId`)
VALUES
(1, 'user001', 1),
(2, 'user002', 2),
(3, 'user003', 3);

-- MannerTags
INSERT INTO `MannerTags` (`id`, `userId`, `evaluatorId`, `mannerTagName`, `createdDate`)
VALUES
(1, 'user001', 'user002', '친절해요', NOW()),
(2, 'user001', 'user003', '꼼꼼해요', NOW());

-- Saved
INSERT INTO `Interactions` (`id`, `userId`, `articleId`, `type`)
VALUES
(1, 'user001', 1, 'SAVE'),
(2, 'user002', 2, 'RECOMMAND');

-- Accompany
INSERT INTO `Accompany` (`id`, `tripId`, `userId`, `role`)
VALUES
(1, 1, 'user001', '팀장'),
(2, 1, 'user002', '일반'),
(3, 1, 'user003', '동행원');

-- TripMap
INSERT INTO `TripMap` (`mapId`, `userId`, `tripId`, `mapLocate`, `mapPictureUrl`, `mapContent`, `createdDate`)
VALUES
(1, 'user001', 1, POINT(37.5512, 126.9882), 'seoul_map.jpg', 'Trip map for Seoul to Busan Adventure', NOW());

-- Record
INSERT INTO `Record` (`id`, `id2`, `RecordPictureUrl`)
VALUES
(1, 1, 'record_seoul_tower.jpg'),
(2, 1, 'record_gyeongbok_palace.jpg');

-- Apply
INSERT INTO `Apply` (`id`, `articleId`, `userId`, `isOk`, `createdDate`)
VALUES
(1, 1, 'user002', TRUE, NOW()),
(2, 1, 'user003', FALSE, NOW());

-- Comment
INSERT INTO `Comment` (`commentId`, `articleId`, `authorId`, `parents`, `commentContents`, `createdDate`)
VALUES
(1, 1, 'user002', NULL, 'This trip looks amazing!', NOW()),
(2, 1, 'user003', 1, 'I totally agree, can’t wait!', NOW());

-- ApplyDetails
INSERT INTO `ApplyDetails` (`ask`, `applyContentId`, `details`)
VALUES
('Why do you want to join?', 1, 'I want to explore Busan with like-minded people.');

-- AuthenticationData
INSERT INTO `AuthenticationData` (`id`, `userId`, `Field`)
VALUES
(1, 'user001', 'passport');

-- CopyOfReport
INSERT INTO `CopyOfReport` (`id`, `commentId`, `reporterId`, `reportType`, `details`, `reportDate`)
VALUES
(1, 1, 'user003', 'Harassment', 'The comment was inappropriate.', NOW());