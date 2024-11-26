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
	`manner` DOUBLE NOT NULL DEFAULT 30,
	`profileUrl` VARCHAR(255) NULL,
	`introduce` VARCHAR(100) NULL,
	`authentication` BOOL NOT NULL DEFAULT FALSE,
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`state` VARCHAR(10) NOT NULL DEFAULT '정상' COMMENT '정상, 정지, 탈퇴',
	`isPublic` BOOL NOT NULL DEFAULT FALSE,
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
	`regionId` INT NOT NULL COMMENT 'PK',
	`title` VARCHAR(10) NOT NULL COMMENT 'Unique',
	`region` VARCHAR(10) NOT NULL COMMENT 'Unique',
    `coordinate` POINT NOT NULL COMMENT '해당 위치의 좌표값',
	PRIMARY KEY (`regionId`)
);

CREATE TABLE `PLANe` (
	`tripId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`regionId` INT NOT NULL,
	`tripName` VARCHAR(100) NOT NULL,
	`departureDate` DATE NOT NULL,
	`arrivedDate` DATE NOT NULL COMMENT '계획에 맞게 자동 설정',
	`state` VARCHAR(10) NOT NULL DEFAULT '임시저장' COMMENT '임시저장, 저장',
	`accompanyNum` INT NOT NULL DEFAULT 1 COMMENT '최대 6',
	`tripDays` INT NOT NULL COMMENT '도착일 - 출발일',
	`isLiked` BOOL NOT NULL DEFAULT FALSE,
	`isReviewed` BOOL NOT NULL DEFAULT FALSE,
  `createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`deletedDate` TIMESTAMP NULL,
	PRIMARY KEY (`tripId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`regionId`) REFERENCES `Region`(`regionId`)
);

CREATE TABLE `TripPlan` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`tripId` BIGINT(20) NOT NULL,
	`tripDay` INT NOT NULL COMMENT '1~3일',
	`tripOrder` INT NOT NULL COMMENT '해당 위치의 여행 순서',
	`title` VARCHAR(100) NOT NULL,
	`memo` VARCHAR(255) NULL,
	`point` POINT NOT NULL COMMENT '해당 위치의 좌표값',
	`address` VARCHAR(100) NULL COMMENT '도로명 우선',
	`url` VARCHAR(1000) NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`)
);

CREATE TABLE `TripThema` (
	`themaId` BIGINT(20) NOT NULL COMMENT 'PK',
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
	`articlePictureUrl` VARCHAR(1000) NULL,
	`likeCount` INT NOT NULL DEFAULT 0,
	`viewCount` INT NOT NULL DEFAULT 1,
	`isPublic` BOOL NOT NULL DEFAULT TRUE,
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`updatedDate` TIMESTAMP NULL,
	`deletedDate` TIMESTAMP NULL,
	PRIMARY KEY (`articleId`),
	FOREIGN KEY (`authorId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`)
);

CREATE TABLE `MannerTags` (
	`mannerTagId` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`mannerTagName` VARCHAR(50) NOT NULL COMMENT '꼼꼼해요, 친절해요 등',
	PRIMARY KEY (`mannerTagId`)
);

CREATE TABLE `Manners` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`evaluatorId` VARCHAR(100) NOT NULL,
	`mannerTagId` INT NOT NULL,
	`score` INT NOT NULL DEFAULT 0 COMMENT '0 점은 미평가',
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	PRIMARY KEY (`id`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`evaluatorId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`mannerTagId`) REFERENCES `MannerTags`(`mannerTagId`)
);

CREATE TABLE `AfterTrip` (
	`afterTripId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`tripId` BIGINT(20) NOT NULL,
	`tripDay` INT NOT NULL COMMENT '1~3일',
	`content` VARCHAR(500) NULL COMMENT 'AI 생성, 수정 가능',
  `createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
  `deletedDate` TIMESTAMP NULL,
	PRIMARY KEY (`afterTripId`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`)
);

CREATE TABLE `AfterPic` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`afterTripId` BIGINT(20) NOT NULL,
	`afterPictureUrl` VARCHAR(1000) NOT NULL,
	`originalFilename` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`afterTripId`) REFERENCES `AfterTrip`(`afterTripId`)
	ON DELETE CASCADE
);

CREATE TABLE `ReportContent` (
	`reportId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`content` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`reportId`)
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
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`reportId`) REFERENCES `ReportContent`(`reportId`)
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
	`styleId` BIGINT(20) NOT NULL COMMENT 'PK',
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
	`deletedDate` TIMESTAMP NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
);

CREATE TABLE `AccompanyApply` (
	`applyId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`articleId` BIGINT(20) NOT NULL,
	`userId` VARCHAR(100) NOT NULL,
	`status` VARCHAR(10) NOT NULL DEFAULT '미확인' COMMENT '확인, 미확인, 수락, 거절',
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`deletedDate` TIMESTAMP NULL,
	PRIMARY KEY (`applyId`),
	FOREIGN KEY (`articleId`) REFERENCES `Board`(`articleId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
);

CREATE TABLE `TripMap` (
	`mapId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`tripId` BIGINT(20) NOT NULL,
	`regionId` INT NOT NULL,
	`mapLocate` POINT NOT NULL COMMENT '여행을 대표할 좌표',
	`mapPictureUrl` VARCHAR(1000) NULL COMMENT '지도에 표시 될 사진',
	`mapContent` TEXT NULL,
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
  `deletedDate` TIMESTAMP NULL,
	PRIMARY KEY (`mapId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`),
	FOREIGN KEY (`regionId`) REFERENCES `Region`(`regionId`)
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

CREATE TABLE `Ask` (
	`askId` BIGINT(20) NOT NULL COMMENT 'PK',
	`content` VARCHAR(500) NOT NULL,
	PRIMARY KEY (`askId`)
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

CREATE TABLE `AuthenticationData` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`userId` VARCHAR(100) NOT NULL,
	`authenticationfileUrl` VARCHAR(1000) NOT NULL,
	`originalFilename` VARCHAR(255) NULL,
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`deletedDate` TIMESTAMP NULL,
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
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
	FOREIGN KEY (`reportId`) REFERENCES `ReportContent`(`reportId`)
);

CREATE TABLE `VerificationCodes` (
	`codeId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
	`email` VARCHAR(100) NULL COMMENT '코드를 받을 email',
  `verificationCode` VARCHAR(100) NOT NULL COMMENT '인증코드',
	`createdDate` TIMESTAMP NOT NULL DEFAULT NOW(),
	`deletedDate` TIMESTAMP NULL,
	PRIMARY KEY (`codeId`)
);

CREATE TABLE `Tokens` (
    `tokenId` BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    `userId` VARCHAR(100) NOT NULL,
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

CREATE TABLE `ScheduledNotification` (
    `id` BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    `tripId` BIGINT(20) NOT NULL,
    `userId` VARCHAR(100) NOT NULL,
    `type` VARCHAR(10) NOT NULL DEFAULT '후기',
    `title` VARCHAR(255) NOT NULL,
    `scheduledTime` TIMESTAMP NOT NULL DEFAULT NOW(),
    `isSent` BOOL NOT NULL DEFAULT FALSE,
    `isActive` BOOL NOT NULL DEFAULT TRUE,
	FOREIGN KEY (`tripId`) REFERENCES `PLANe`(`tripId`),
	FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
);

INSERT INTO `Users` (`userId`, `password`, `nickName`, `phone`, `email`)
VALUES ('kangsansam123', '96f504c387d4abda9c2b5707519a3c81de75af462fc16f1ab38eff15acc1abc708efa7fcb6deebcc999de28b0b24c67273da29835148babbd99b2e94b0ff641e', '김강토', 'ece684acedc2807034a11f259b7f3c83b354f0f791be0fc819fa66fc6e048adbb94594990cf423ea6499a84b50b210914c9d92fa913362f9ec6aea2d653dd4ce', 'kangsansam123@naver.com');

INSERT INTO `TripStyle` (`styleId`, `styleName`) VALUES
(0, '대문자 J'),
(1, '뚜벅이'),
(2, '맛잘알'),
(3, '대문자 P'),
(4, '사진쟁이'),
(5, '힐링러'),
(6, '쇼핑요정'),
(7, '관광지 헌터'),
(8, '고독러'),
(9, '나는 자연인이다'),
(10, '핫플 콜렉터'),
(11, '박학다식 가이드'),
(12, '도파민 중독'),
(13, '아트홀릭'),
(14, '느림보'),
(15, '패스트무버'),
(16, '에너지러쉬'),
(17, '역사덕후');

INSERT INTO `TripThema` (`themaId`, `themaName`) VALUES
(0, '자연'),
(1, '역사'), 
(2, '휴양'), 
(3, '체험'), 
(4, '건축/조형물'), 
(5, '문화시설'), 
(6, '축제'), 
(7, '공연/행사'), 
(8, '육상 레포츠'), 
(9, '수상 레포츠'), 
(10, '항공 레포츠'), 
(11, '숙소'), 
(12, '쇼핑'), 
(13, '음식점');

INSERT INTO `MannerTags` (`mannerTagName`) VALUES
('시간약속철저'),
('대화잘해요'),
('배려심많아요'),
('분위기메이커'),
('대처능력이좋아요'),
('정산철저해요'),
('계획적이에요'),
('개인시간존중해요'),
('깔끔해요'),
('긍정적이에요');

INSERT INTO `ReportContent` (`content`)
VALUES ('부적절한 콘텐츠'), ('스팸 및 광고'), ('욕설 및 비방'), ('허위 정보'), ('개인정보 유출');

INSERT INTO `Ask` (`askId`, `content`)
VALUES (1,'간단한 자기소개'), (2, '여행 중 같이 하고싶은 활동'), (3, '유의사항'), (4, '연결가능한 연락처');

INSERT INTO Region (regionId, title, region, coordinate)
VALUES
(0, '강원', '강릉시', ST_GeomFromText(CONCAT('POINT(', 128.8784972, ' ', 37.74913611, ')'))),
(1, '강원', '고성군', ST_GeomFromText(CONCAT('POINT(', 128.4701639, ' ', 38.37796111, ')'))),
(2, '강원', '동해시', ST_GeomFromText(CONCAT('POINT(', 129.1166333, ' ', 37.52193056, ')'))),
(3, '강원', '삼척시', ST_GeomFromText(CONCAT('POINT(', 129.1674889, ' ', 37.44708611, ')'))),
(4, '강원', '속초시', ST_GeomFromText(CONCAT('POINT(', 128.5941667, ' ', 38.204275, ')'))),
(5, '강원', '양구군', ST_GeomFromText(CONCAT('POINT(', 127.9922444, ' ', 38.10729167, ')'))),
(6, '강원', '양양군', ST_GeomFromText(CONCAT('POINT(', 128.6213556, ' ', 38.07283333, ')'))),
(7, '강원', '영월군', ST_GeomFromText(CONCAT('POINT(', 128.4640194, ' ', 37.18086111, ')'))),
(8, '강원', '원주시', ST_GeomFromText(CONCAT('POINT(', 127.9220556, ' ', 37.33908333, ')'))),
(9, '강원', '인제군', ST_GeomFromText(CONCAT('POINT(', 128.1726972, ' ', 38.06697222, ')'))),
(10, '강원', '정선군', ST_GeomFromText(CONCAT('POINT(', 128.6630861, ' ', 37.37780833, ')'))),
(11, '강원', '철원군', ST_GeomFromText(CONCAT('POINT(', 127.3157333, ' ', 38.14405556, ')'))),
(12, '강원', '춘천시', ST_GeomFromText(CONCAT('POINT(', 127.7323111, ' ', 37.87854167, ')'))),
(13, '강원', '태백시', ST_GeomFromText(CONCAT('POINT(', 128.9879972, ' ', 37.16122778, ')'))),
(14, '강원', '평창군', ST_GeomFromText(CONCAT('POINT(', 128.3923528, ' ', 37.36791667, ')'))),
(15, '강원', '홍천군', ST_GeomFromText(CONCAT('POINT(', 127.8908417, ' ', 37.69442222, ')'))),
(16, '강원', '화천군', ST_GeomFromText(CONCAT('POINT(', 127.7103556, ' ', 38.10340833, ')'))),
(17, '강원', '횡성군', ST_GeomFromText(CONCAT('POINT(', 127.9872222, ' ', 37.48895833, ')'))),
(18, '경기', '가평군', ST_GeomFromText(CONCAT('POINT(', 127.5117778, ' ', 37.82883056, ')'))),
(19, '경기', '고양시', ST_GeomFromText(CONCAT('POINT(', 126.7770556, ' ', 37.65590833, ')'))),
(20, '경기', '과천시', ST_GeomFromText(CONCAT('POINT(', 126.9898, ' ', 37.42637222, ')'))),
(21, '경기', '광명시', ST_GeomFromText(CONCAT('POINT(', 126.8667083, ' ', 37.47575, ')'))),
(22, '경기', '광주시', ST_GeomFromText(CONCAT('POINT(', 127.2577861, ' ', 37.41450556, ')'))),
(23, '경기', '구리시', ST_GeomFromText(CONCAT('POINT(', 127.1318639, ' ', 37.591625, ')'))),
(24, '경기', '군포시', ST_GeomFromText(CONCAT('POINT(', 126.9375, ' ', 37.35865833, ')'))),
(25, '경기', '김포시', ST_GeomFromText(CONCAT('POINT(', 126.7177778, ' ', 37.61245833, ')'))),
(26, '경기', '남양주시', ST_GeomFromText(CONCAT('POINT(', 127.2186333, ' ', 37.63317778, ')'))),
(27, '경기', '동두천시', ST_GeomFromText(CONCAT('POINT(', 127.0626528, ' ', 37.90091667, ')'))),
(28, '경기', '부천시', ST_GeomFromText(CONCAT('POINT(', 126.766, ' ', 37.5035917, ')'))),
(29, '경기', '성남시', ST_GeomFromText(CONCAT('POINT(', 127.1477194, ' ', 37.44749167, ')'))),
(30, '경기', '수원시', ST_GeomFromText(CONCAT('POINT(', 127.0122222, ' ', 37.30101111, ')'))),
(31, '경기', '시흥시', ST_GeomFromText(CONCAT('POINT(', 126.8050778, ' ', 37.37731944, ')'))),
(32, '경기', '안산시', ST_GeomFromText(CONCAT('POINT(', 126.8468194, ' ', 37.29851944, ')'))),
(33, '경기', '안성시', ST_GeomFromText(CONCAT('POINT(', 127.2818444, ' ', 37.005175, ')'))),
(34, '경기', '안양시', ST_GeomFromText(CONCAT('POINT(', 126.9533556, ' ', 37.3897, ')'))),
(35, '경기', '양주시', ST_GeomFromText(CONCAT('POINT(', 127.0478194, ' ', 37.78245, ')'))),
(36, '경기', '양평군', ST_GeomFromText(CONCAT('POINT(', 127.4898861, ' ', 37.48893611, ')'))),
(37, '경기', '여주시', ST_GeomFromText(CONCAT('POINT(', 127.6396222, ' ', 37.29535833, ')'))),
(38, '경기', '연천군', ST_GeomFromText(CONCAT('POINT(', 127.0770667, ' ', 38.09336389, ')'))),
(39, '경기', '오산시', ST_GeomFromText(CONCAT('POINT(', 127.0796417, ' ', 37.14691389, ')'))),
(40, '경기', '용인시', ST_GeomFromText(CONCAT('POINT(', 127.2038444, ' ', 37.23147778, ')'))),
(41, '경기', '의왕시', ST_GeomFromText(CONCAT('POINT(', 126.9703889, ' ', 37.34195, ')'))),
(42, '경기', '의정부시', ST_GeomFromText(CONCAT('POINT(', 127.0358417, ' ', 37.73528889, ')'))),
(43, '경기', '이천시', ST_GeomFromText(CONCAT('POINT(', 127.4432194, ' ', 37.27543611, ')'))),
(44, '경기', '파주시', ST_GeomFromText(CONCAT('POINT(', 126.7819528, ' ', 37.75708333, ')'))),
(45, '경기', '평택시', ST_GeomFromText(CONCAT('POINT(', 127.1146556, ' ', 36.98943889, ')'))),
(46, '경기', '포천시', ST_GeomFromText(CONCAT('POINT(', 127.2024194, ' ', 37.89215556, ')'))),
(47, '경기', '하남시', ST_GeomFromText(CONCAT('POINT(', 127.217, ' ', 37.53649722, ')'))),
(48, '경기', '화성시', ST_GeomFromText(CONCAT('POINT(', 126.8335306, ' ', 37.19681667, ')'))),
(49, '경상', '거제시', ST_GeomFromText(CONCAT('POINT(', 128.6233556, ' ', 34.87735833, ')'))),
(50, '경상', '거창군', ST_GeomFromText(CONCAT('POINT(', 127.9116556, ' ', 35.683625, ')'))),
(51, '경상', '경산시', ST_GeomFromText(CONCAT('POINT(', 128.7434639, ' ', 35.82208889, ')'))),
(52, '경상', '경주시', ST_GeomFromText(CONCAT('POINT(', 129.2270222, ' ', 35.85316944, ')'))),
(53, '경상', '고령군', ST_GeomFromText(CONCAT('POINT(', 128.2650222, ' ', 35.72298611, ')'))),
(54, '경상', '고성군', ST_GeomFromText(CONCAT('POINT(', 128.3245417, ' ', 34.9699, ')'))),
(55, '경상', '구미시', ST_GeomFromText(CONCAT('POINT(', 128.3467778, ' ', 36.11655, ')'))),
(56, '경상', '군위군', ST_GeomFromText(CONCAT('POINT(', 128.5750778, ' ', 36.23999722, ')'))),
(57, '경상', '김천시', ST_GeomFromText(CONCAT('POINT(', 128.1158, ' ', 36.13689722, ')'))),
(58, '경상', '김해시', ST_GeomFromText(CONCAT('POINT(', 128.8916667, ' ', 35.22550556, ')'))),
(59, '경상', '남해군', ST_GeomFromText(CONCAT('POINT(', 127.8944667, ' ', 34.83455833, ')'))),
(60, '경상', '문경시', ST_GeomFromText(CONCAT('POINT(', 128.1890194, ' ', 36.58363056, ')'))),
(61, '경상', '밀양시', ST_GeomFromText(CONCAT('POINT(', 128.7489444, ' ', 35.50077778, ')'))),
(62, '경상', '봉화군', ST_GeomFromText(CONCAT('POINT(', 128.734875, ' ', 36.89026111, ')'))),
(63, '경상', '사천시', ST_GeomFromText(CONCAT('POINT(', 128.0667778, ' ', 35.00028333, ')'))),
(64, '경상', '산청군', ST_GeomFromText(CONCAT('POINT(', 127.8756194, ' ', 35.41249167, ')'))),
(65, '경상', '상주시', ST_GeomFromText(CONCAT('POINT(', 128.1612639, ' ', 36.40796944, ')'))),
(66, '경상', '성주군', ST_GeomFromText(CONCAT('POINT(', 128.2851528, ' ', 35.91621111, ')'))),
(67, '경상', '안동시', ST_GeomFromText(CONCAT('POINT(', 128.7316222, ' ', 36.56546389, ')'))),
(68, '경상', '양산시', ST_GeomFromText(CONCAT('POINT(', 129.0394111, ' ', 35.33192778, ')'))),
(69, '경상', '영덕군', ST_GeomFromText(CONCAT('POINT(', 129.3683556, ' ', 36.41210278, ')'))),
(70, '경상', '영양군', ST_GeomFromText(CONCAT('POINT(', 129.1146222, ' ', 36.664275, ')'))),
(71, '경상', '영주시', ST_GeomFromText(CONCAT('POINT(', 128.6263444, ' ', 36.80293611, ')'))),
(72, '경상', '영천시', ST_GeomFromText(CONCAT('POINT(', 128.940775, ' ', 35.97005278, ')'))),
(73, '경상', '예천군', ST_GeomFromText(CONCAT('POINT(', 128.4550222, ' ', 36.65495, ')'))),
(74, '경상', '울릉군', ST_GeomFromText(CONCAT('POINT(', 130.9037889, ' ', 37.480575, ')'))),
(75, '경상', '울진군', ST_GeomFromText(CONCAT('POINT(', 129.4027861, ' ', 36.99018611, ')'))),
(76, '경상', '의령군', ST_GeomFromText(CONCAT('POINT(', 128.2638222, ' ', 35.31911944, ')'))),
(77, '경상', '의성군', ST_GeomFromText(CONCAT('POINT(', 128.6993639, ' ', 36.34975833, ')'))),
(78, '경상', '진주시', ST_GeomFromText(CONCAT('POINT(', 128.11, ' ', 35.17703333, ')'))),
(79, '경상', '창녕군', ST_GeomFromText(CONCAT('POINT(', 128.4945333, ' ', 35.54153611, ')'))),
(80, '경상', '창원시', ST_GeomFromText(CONCAT('POINT(', 128.6401544, ' ', 35.2540033, ')'))),
(81, '경상', '청도군', ST_GeomFromText(CONCAT('POINT(', 128.7362000, ' ', 35.64431111, ')'))),
(82, '경상', '청송군', ST_GeomFromText(CONCAT('POINT(', 129.0594000, ' ', 36.43329167, ')'))),
(83, '경상', '칠곡군', ST_GeomFromText(CONCAT('POINT(', 128.4037972, ' ', 35.99254722, ')'))),
(84, '경상', '통영시', ST_GeomFromText(CONCAT('POINT(', 128.4352778, ' ', 34.85125833, ')'))),
(85, '경상', '포항시', ST_GeomFromText(CONCAT('POINT(', 129.3616667, ' ', 36.00568611, ')'))),
(86, '경상', '하동군', ST_GeomFromText(CONCAT('POINT(', 127.7534306, ' ', 35.06420278, ')'))),
(87, '경상', '함안군', ST_GeomFromText(CONCAT('POINT(', 128.4087083, ' ', 35.26940556, ')'))),
(88, '경상', '함양군', ST_GeomFromText(CONCAT('POINT(', 127.7274194, ' ', 35.51746944, ')'))),
(89, '경상', '합천군', ST_GeomFromText(CONCAT('POINT(', 128.1679306, ' ', 35.56361667, ')'))),
(90, '경상', '부산시', ST_GeomFromText(CONCAT('POINT(', 129.075, ' ', 35.1798, ')'))),
(91, '경상', '대구시', ST_GeomFromText(CONCAT('POINT(', 128.6017, ' ', 35.8715, ')'))),
(92, '경상', '울산시', ST_GeomFromText(CONCAT('POINT(', 129.3123, ' ', 35.546, ')'))),
(93, '서울시', '서울시', ST_GeomFromText(CONCAT('POINT(', 127.0495556, ' ', 37.514575, ')'))),
(94, '인천시', '인천시', ST_GeomFromText(CONCAT('POINT(', 126.737744, ' ', 37.53770728, ')'))),
(95, '인천시', '강화군', ST_GeomFromText(CONCAT('POINT(', 126.4878417, ' ', 37.74692907, ')'))),
(96, '전라', '강진군', ST_GeomFromText(CONCAT('POINT(', 126.7691972, ' ', 34.63891111, ')'))),
(97, '전라', '고창군', ST_GeomFromText(CONCAT('POINT(', 126.7041083, ' ', 35.43273889, ')'))),
(98, '전라', '고흥군', ST_GeomFromText(CONCAT('POINT(', 127.2870556, ' ', 34.60806944, ')'))),
(99, '전라', '곡성군', ST_GeomFromText(CONCAT('POINT(', 127.2941083, ' ', 35.27895556, ')'))),
(100, '전라', '광양시', ST_GeomFromText(CONCAT('POINT(', 127.6981778, ' ', 34.93753611, ')'))),
(101, '전라', '구례군', ST_GeomFromText(CONCAT('POINT(', 127.4649333, ' ', 35.19945833, ')'))),
(102, '전라', '군산시', ST_GeomFromText(CONCAT('POINT(', 126.7388444, ' ', 35.96464167, ')'))),
(103, '전라', '김제시', ST_GeomFromText(CONCAT('POINT(', 126.8827528, ' ', 35.800575, ')'))),
(104, '전라', '나주시', ST_GeomFromText(CONCAT('POINT(', 126.7128667, ' ', 35.01283889, ')'))),
(105, '전라', '남원시', ST_GeomFromText(CONCAT('POINT(', 127.3925, ' ', 35.41325556, ')'))),
(106, '전라', '담양군', ST_GeomFromText(CONCAT('POINT(', 126.9901639, ' ', 35.318125, ')'))),
(107, '전라', '목포시', ST_GeomFromText(CONCAT('POINT(', 126.3944194, ' ', 34.80878889, ')'))),
(108, '전라', '무안군', ST_GeomFromText(CONCAT('POINT(', 126.4837, ' ', 34.98736944, ')'))),
(109, '전라', '무주군', ST_GeomFromText(CONCAT('POINT(', 127.6628667, ' ', 36.00382778, ')'))),
(110, '전라', '보성군', ST_GeomFromText(CONCAT('POINT(', 127.0820889, ' ', 34.76833333, ')'))),
(111, '전라', '부안군', ST_GeomFromText(CONCAT('POINT(', 126.7356778, ' ', 35.72853333, ')'))),
(112, '전라', '순창군', ST_GeomFromText(CONCAT('POINT(', 127.1396306, ' ', 35.37138889, ')'))),
(113, '전라', '순천시', ST_GeomFromText(CONCAT('POINT(', 127.4893306, ' ', 34.94760556, ')'))),
(114, '전라', '신안군', ST_GeomFromText(CONCAT('POINT(', 126.3817306, ' ', 34.78981111, ')'))),
(115, '전라', '여수시', ST_GeomFromText(CONCAT('POINT(', 127.6643861, ' ', 34.75731111, ')'))),
(116, '전라', '영광군', ST_GeomFromText(CONCAT('POINT(', 126.5140861, ' ', 35.27416667, ')'))),
(117, '전라', '영암군', ST_GeomFromText(CONCAT('POINT(', 126.6986194, ' ', 34.79698889, ')'))),
(118, '전라', '완도군', ST_GeomFromText(CONCAT('POINT(', 126.7570972, ' ', 34.30785278, ')'))),
(119, '전라', '완주군', ST_GeomFromText(CONCAT('POINT(', 127.1495972, ' ', 35.84296944, ')'))),
(120, '전라', '익산시', ST_GeomFromText(CONCAT('POINT(', 126.9598528, ' ', 35.945275, ')'))),
(121, '전라', '임실군', ST_GeomFromText(CONCAT('POINT(', 127.2847528, ' ', 35.60806389, ')'))),
(122, '전라', '장성군', ST_GeomFromText(CONCAT('POINT(', 126.786975, ' ', 35.29881111, ')'))),
(123, '전라', '장수군', ST_GeomFromText(CONCAT('POINT(', 127.5233, ' ', 35.64429722, ')'))),
(124, '전라', '장흥군', ST_GeomFromText(CONCAT('POINT(', 126.9091083, ' ', 34.678525, ')'))),
(125, '전라', '전주시', ST_GeomFromText(CONCAT('POINT(', 127.1219194, ' ', 35.80918889, ')'))),
(126, '전라', '정읍시', ST_GeomFromText(CONCAT('POINT(', 126.8581111, ' ', 35.56687222, ')'))),
(127, '전라', '진도군', ST_GeomFromText(CONCAT('POINT(', 126.2655444, ' ', 34.48375, ')'))),
(128, '전라', '진안군', ST_GeomFromText(CONCAT('POINT(', 127.4269667, ' ', 35.78871944, ')'))),
(129, '전라', '함평군', ST_GeomFromText(CONCAT('POINT(', 126.5186194, ' ', 35.06274444, ')'))),
(130, '전라', '해남군', ST_GeomFromText(CONCAT('POINT(', 126.6012889, ' ', 34.57043611, ')'))),
(131, '전라', '화순군', ST_GeomFromText(CONCAT('POINT(', 126.9885667, ' ', 35.06148056, ')'))),
(132, '전라', '광주시', ST_GeomFromText(CONCAT('POINT(', 127.2551569, ' ', 37.42949814, ')'))),
(133, '제주', '서귀포시', ST_GeomFromText(CONCAT('POINT(', 126.5125556, ' ', 33.25235, ')'))),
(134, '제주', '제주시', ST_GeomFromText(CONCAT('POINT(', 126.5332083, ' ', 33.49631111, ')'))),
(135, '충청', '계룡시', ST_GeomFromText(CONCAT('POINT(', 127.2509306, ' ', 36.27183611, ')'))),
(136, '충청', '공주시', ST_GeomFromText(CONCAT('POINT(', 127.1211194, ' ', 36.44361389, ')'))),
(137, '충청', '괴산군', ST_GeomFromText(CONCAT('POINT(', 127.7888306, ' ', 36.81243056, ')'))),
(138, '충청', '금산군', ST_GeomFromText(CONCAT('POINT(', 127.4903083, ' ', 36.10586944, ')'))),
(139, '충청', '논산시', ST_GeomFromText(CONCAT('POINT(', 127.1009111, ' ', 36.18420278, ')'))),
(140, '충청', '단양군', ST_GeomFromText(CONCAT('POINT(', 128.3678417, ' ', 36.98178056, ')'))),
(141, '충청', '당진시', ST_GeomFromText(CONCAT('POINT(', 126.6302528, ' ', 36.89075, ')'))),
(142, '충청', '세종시', ST_GeomFromText(CONCAT('POINT(', 127.2623972, ' ', 36.51731111, ')'))),
(143, '충청', '보령시', ST_GeomFromText(CONCAT('POINT(', 126.6148861, ' ', 36.330575, ')'))),
(144, '충청', '보은군', ST_GeomFromText(CONCAT('POINT(', 127.7316083, ' ', 36.48653333, ')'))),
(145, '충청', '부여군', ST_GeomFromText(CONCAT('POINT(', 126.9118639, ' ', 36.27282222, ')'))),
(146, '충청', '서산시', ST_GeomFromText(CONCAT('POINT(', 126.4521639, ' ', 36.78209722, ')'))),
(147, '충청', '서천군', ST_GeomFromText(CONCAT('POINT(', 126.6938889, ' ', 36.07740556, ')'))),
(148, '충청', '아산시', ST_GeomFromText(CONCAT('POINT(', 127.0046417, ' ', 36.78710556, ')'))),
(149, '충청', '영동군', ST_GeomFromText(CONCAT('POINT(', 127.7856111, ' ', 36.17205833, ')'))),
(150, '충청', '예산군', ST_GeomFromText(CONCAT('POINT(', 126.850875, ' ', 36.67980556, ')'))),
(151, '충청', '옥천군', ST_GeomFromText(CONCAT('POINT(', 127.5736333, ' ', 36.30355, ')'))),
(152, '충청', '음성군', ST_GeomFromText(CONCAT('POINT(', 127.6926222, ' ', 36.93740556, ')'))),
(153, '충청', '제천시', ST_GeomFromText(CONCAT('POINT(', 128.1931528, ' ', 37.12976944, ')'))),
(154, '충청', '증평군', ST_GeomFromText(CONCAT('POINT(', 127.5832889, ' ', 36.78218056, ')'))),
(155, '충청', '진천군', ST_GeomFromText(CONCAT('POINT(', 127.4376444, ' ', 36.85253889, ')'))),
(156, '충청', '천안시', ST_GeomFromText(CONCAT('POINT(', 127.1524667, ' ', 36.804125, ')'))),
(157, '충청', '청양군', ST_GeomFromText(CONCAT('POINT(', 126.8042556, ' ', 36.45626944, ')'))),
(158, '충청', '청주시', ST_GeomFromText(CONCAT('POINT(', 127.5117306, ' ', 36.58399722, ')'))),
(159, '충청', '충주시', ST_GeomFromText(CONCAT('POINT(', 127.9281444, ' ', 36.98818056, ')'))),
(160, '충청', '태안군', ST_GeomFromText(CONCAT('POINT(', 126.299975, ' ', 36.74266667, ')'))),
(161, '충청', '홍성군', ST_GeomFromText(CONCAT('POINT(', 126.6629083, ' ', 36.59836111, ')'))),
(162, '충청', '대전시', ST_GeomFromText(CONCAT('POINT(', 127.3848616, ' ', 36.3506295, ')')));