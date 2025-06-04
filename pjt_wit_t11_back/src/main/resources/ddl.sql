-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: wit
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attraction_reviews`
--

DROP TABLE IF EXISTS `attraction_reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attraction_reviews` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '리뷰 식별자',
  `attraction_no` int NOT NULL COMMENT '명소 번호 (FK)',
  `rating` float DEFAULT NULL COMMENT '카카오맵 평점',
  `review_count` int DEFAULT NULL COMMENT '카카오맵 리뷰 수',
  `last_updated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_attraction_no` (`attraction_no`),
  CONSTRAINT `fk_reviews_to_attractions` FOREIGN KEY (`attraction_no`) REFERENCES `attractions` (`no`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46757 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attractions`
--

DROP TABLE IF EXISTS `attractions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attractions` (
  `no` int NOT NULL AUTO_INCREMENT COMMENT '명소코드',
  `content_id` int DEFAULT NULL COMMENT '콘텐츠번호',
  `title` varchar(500) DEFAULT NULL COMMENT '명소이름',
  `content_type_id` int DEFAULT NULL COMMENT '콘텐츠타입',
  `area_code` int DEFAULT NULL COMMENT '시도코드',
  `si_gun_gu_code` int DEFAULT NULL COMMENT '구군코드',
  `first_image1` varchar(1000) DEFAULT NULL,
  `first_image2` varchar(100) DEFAULT NULL COMMENT '이미지경로2',
  `map_level` int DEFAULT NULL COMMENT '줌레벨',
  `latitude` decimal(20,17) DEFAULT NULL COMMENT '위도',
  `longitude` decimal(20,17) DEFAULT NULL COMMENT '경도',
  `tel` varchar(20) DEFAULT NULL COMMENT '전화번호',
  `addr1` varchar(100) DEFAULT NULL COMMENT '주소1',
  `addr2` varchar(100) DEFAULT NULL COMMENT '주소2',
  `homepage` varchar(1000) DEFAULT NULL COMMENT '홈페이지',
  `overview` varchar(10000) DEFAULT NULL COMMENT '설명',
  PRIMARY KEY (`no`),
  UNIQUE KEY `uq_title_addr1` (`title`,`addr1`),
  KEY `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id`),
  KEY `attractions_sido_to_sidos_code_fk_idx` (`area_code`),
  KEY `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code`),
  CONSTRAINT `attractions_area_to_sidos_code_fk` FOREIGN KEY (`area_code`) REFERENCES `sidos` (`sido_code`),
  CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk` FOREIGN KEY (`si_gun_gu_code`) REFERENCES `guguns` (`gugun_code`),
  CONSTRAINT `attractions_typeid_to_types_typeid_fk` FOREIGN KEY (`content_type_id`) REFERENCES `contenttypes` (`content_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=135136 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='명소정보테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contenttypes`
--

DROP TABLE IF EXISTS `contenttypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contenttypes` (
  `content_type_id` int NOT NULL COMMENT '콘텐츠타입번호',
  `content_type_name` varchar(45) DEFAULT NULL COMMENT '콘텐츠타입이름',
  PRIMARY KEY (`content_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='콘텐츠타입정보테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `day`
--

DROP TABLE IF EXISTS `day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `day` (
  `day_id` int NOT NULL AUTO_INCREMENT,
  `schedule_id` int NOT NULL,
  `day_number` int DEFAULT NULL,
  PRIMARY KEY (`day_id`,`schedule_id`),
  KEY `FK_Schedule_TO_Day_1` (`schedule_id`),
  CONSTRAINT `FK_Schedule_TO_Day_1` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`schedule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dayplace`
--

DROP TABLE IF EXISTS `dayplace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dayplace` (
  `place_id` int NOT NULL AUTO_INCREMENT,
  `day_id` int NOT NULL,
  `no` int NOT NULL,
  `visit_order` int DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`place_id`,`day_id`,`no`),
  KEY `FK_Day_TO_DayPlace_1` (`day_id`),
  KEY `FK_Attractions_TO_DayPlace_1` (`no`),
  CONSTRAINT `FK_Attractions_TO_DayPlace_1` FOREIGN KEY (`no`) REFERENCES `attractions` (`no`),
  CONSTRAINT `FK_Day_TO_DayPlace_1` FOREIGN KEY (`day_id`) REFERENCES `day` (`day_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `guguns`
--

DROP TABLE IF EXISTS `guguns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guguns` (
  `no` int NOT NULL AUTO_INCREMENT COMMENT '구군번호',
  `sido_code` int NOT NULL COMMENT '시도코드',
  `gugun_code` int NOT NULL COMMENT '구군코드',
  `gugun_name` varchar(20) DEFAULT NULL COMMENT '구군이름',
  PRIMARY KEY (`no`),
  KEY `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code`),
  KEY `gugun_code_idx` (`gugun_code`),
  CONSTRAINT `guguns_sido_to_sidos_cdoe_fk` FOREIGN KEY (`sido_code`) REFERENCES `sidos` (`sido_code`)
) ENGINE=InnoDB AUTO_INCREMENT=469 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='구군정보테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `schedule_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `day_count` int DEFAULT NULL,
  PRIMARY KEY (`schedule_id`,`user_id`),
  KEY `FK_User_TO_Schedule_1` (`user_id`),
  CONSTRAINT `FK_User_TO_Schedule_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sidos`
--

DROP TABLE IF EXISTS `sidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sidos` (
  `no` int NOT NULL AUTO_INCREMENT COMMENT '시도번호',
  `sido_code` int NOT NULL COMMENT '시도코드',
  `sido_name` varchar(20) DEFAULT NULL COMMENT '시도이름',
  PRIMARY KEY (`no`),
  UNIQUE KEY `sido_code_UNIQUE` (`sido_code`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='시도정보테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `roles` varchar(50) NOT NULL DEFAULT 'ROLE_USER',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-25 11:01:01
