CREATE DATABASE  IF NOT EXISTS `threadripper` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
ALTER SCHEMA `threadripper`  DEFAULT CHARACTER SET utf8mb4  DEFAULT COLLATE utf8mb4_general_ci ;
USE `threadripper`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: threadripper
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `avatar`
--

DROP TABLE IF EXISTS `avatar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `avatar` (
  `avatar_id` int(11) NOT NULL AUTO_INCREMENT,
  `avatarUrl` varchar(300) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`avatar_id`),
  KEY `fk_username_idx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avatar`
--

LOCK TABLES `avatar` WRITE;
/*!40000 ALTER TABLE `avatar` DISABLE KEYS */;
INSERT INTO `avatar` VALUES (7,'vre.hcmut.edu.vn/threadripper/api/avatar/7.png','a','2018-10-20 01:01:48'),(8,'vre.hcmut.edu.vn/threadripper/api/avatar/8.png','a','2018-10-20 01:01:54'),(9,'vre.hcmut.edu.vn/threadripper/api/avatar/9.png','a','2018-10-20 01:05:43'),(10,'vre.hcmut.edu.vn/threadripper/api/avatar/10.bmp','a','2018-10-20 01:33:28'),(16,'vre.hcmut.edu.vn/threadripper/api/avatar/16.jpg','a','2018-10-20 12:32:28'),(17,'https://www.w3schools.com/w3css/img_lights.jpg','test3','2018-10-20 12:33:29');
/*!40000 ALTER TABLE `avatar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversation`
--

DROP TABLE IF EXISTS `conversation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `conversation` (
  `conversationId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`conversationId`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversation`
--

LOCK TABLES `conversation` WRITE;
/*!40000 ALTER TABLE `conversation` DISABLE KEYS */;
INSERT INTO `conversation` VALUES (1,'b',NULL),(1,'f',NULL),(2,'a',NULL),(2,'c',NULL),(2,'d',NULL),(3,'a',NULL),(3,'d',NULL),(6,'a',NULL),(6,'b',NULL),(8,'a',NULL),(8,'b',NULL),(9,'a',NULL),(9,'c',NULL),(9,'d',NULL),(10,'a',NULL),(10,'d',NULL),(10,'g',NULL);
/*!40000 ALTER TABLE `conversation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversation_setting`
--

DROP TABLE IF EXISTS `conversation_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `conversation_setting` (
  `conversationId` int(11) NOT NULL AUTO_INCREMENT,
  `conversationName` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`conversationId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversation_setting`
--

LOCK TABLES `conversation_setting` WRITE;
/*!40000 ALTER TABLE `conversation_setting` DISABLE KEYS */;
INSERT INTO `conversation_setting` VALUES (1,NULL),(2,NULL),(3,NULL),(4,NULL),(5,NULL),(6,NULL),(7,NULL),(8,NULL),(9,NULL),(10,NULL);
/*!40000 ALTER TABLE `conversation_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `file` (
  `fileId` int(11) NOT NULL AUTO_INCREMENT,
  `filename` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `datetime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`fileId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (1,'1.','2018-10-20 01:26:13'),(2,'2.apk','2018-10-20 01:27:05'),(3,'3.py','2018-10-20 12:38:12');
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `image` (
  `imageId` int(11) NOT NULL AUTO_INCREMENT,
  `filename` varchar(50) DEFAULT NULL,
  `datetime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`imageId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,NULL,'2018-10-20 01:13:17'),(2,'2.jpg','2018-10-20 01:16:31');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `message` (
  `messageId` int(11) NOT NULL AUTO_INCREMENT,
  `conversationId` int(11) DEFAULT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `datetime` datetime DEFAULT CURRENT_TIMESTAMP,
  `read` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`messageId`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,2,'a','TEXT','ă â ơ ợ','2018-10-19 14:21:20',1),(2,2,'b','TEXT','độ đổ được','2018-10-19 14:22:18',0),(3,9,'a','TEXT','3','2018-10-19 14:40:34',0),(4,9,'a','TEXT','4','2018-10-19 14:40:34',0),(5,NULL,'a','',NULL,'2018-10-22 02:45:43',0),(6,2,'a','','ng Việt được chính thức ghi nhận trong Hiến pháp nước Cộng hòa Xã hội ... Tổ chức tiêu chuẩn hóa quốc tế đặt mã ngôn ngữ hai chữ cái cho tiếng Việt là ...','2018-10-22 02:51:23',0),(7,2,'a','','b','2018-10-22 02:56:37',0),(8,2,'a','','a2','2018-10-22 03:00:45',0),(9,1,'b','','độ','2018-10-22 03:00:50',0),(10,1,'f','','f1','2018-10-22 03:00:53',0),(11,2342,'b','','â','2018-10-22 03:04:33',0),(12,2,'b','',' b2','2018-10-22 03:09:56',0),(13,2,'a','','a2','2018-10-22 03:11:53',0),(14,2,'b','','b2','2018-10-22 03:13:40',0),(15,2,'a','','a2','2018-10-22 03:10:26',0),(16,432432,'a','','fas','2018-10-22 03:10:14',0),(17,1,'b','','fdsa','2018-10-22 03:10:44',0),(18,1,'f','','fds','2018-10-22 03:10:45',0),(19,2,'a','','fds','2018-10-22 03:10:47',0),(20,2,'a','','fds','2018-10-22 03:10:36',0),(21,1,'b','','b1','2018-10-22 03:10:51',0),(22,1,'f','','fds','2018-10-22 03:10:31',0),(23,1,'b','','fsd','2018-10-22 03:10:34',0),(24,2,'a','','fds','2018-10-22 03:10:36',0),(25,1,'f','','a','2018-10-22 03:10:50',0),(26,8,'a','','a8','2018-10-22 03:10:25',0),(27,8,'a','','a8','2018-10-22 03:10:58',0),(28,1,'b','','b1','2018-10-22 03:10:12',0),(29,1,'b','','fds','2018-10-22 04:10:26',0),(30,1,'f','','fds','2018-10-22 04:10:27',0),(31,8,'a','','fd','2018-10-22 04:10:07',0),(32,1,'f','','a','2018-10-22 10:10:47',0),(33,1,'f','','a','2018-10-22 10:10:12',0),(34,1,'f','','abc','2018-10-22 10:10:59',0),(35,1,'a','','adfds','2018-10-22 10:10:13',0),(36,2,'a','TEXT','được tẹt bịc oạt','2018-10-26 01:10:00',0),(37,2,'a','TEXT','mạnh','2018-10-27 21:56:14',0),(38,2,'a','TEXT','tẹt được','2018-10-27 21:56:17',0),(39,25,'x6','TEXT','fdsaf','2018-10-27 22:14:06',0),(40,25,'x6','TEXT','clgt','2018-10-27 22:14:25',0),(41,8,'b','TEXT','fdsafas','2018-10-27 22:26:01',0),(42,8,'b','TEXT','fdgg','2018-10-27 22:26:04',0),(43,2,'a','TEXT','ô ợ ự ở','2018-10-27 22:42:13',0);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `username` varchar(50) CHARACTER SET utf8 NOT NULL,
  `password` varchar(72) CHARACTER SET utf8 NOT NULL,
  `email` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `active` tinyint(1) DEFAULT '0',
  `lock` tinyint(1) DEFAULT '0',
  `datetime_update` datetime DEFAULT NULL,
  `datetime_create` datetime DEFAULT NULL,
  `displayName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `hash` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `online` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('a','$2a$10$gj4OtbyETCraxaVKBg5CXuyIxjOy93vo83nJCgnytwkv5BUHpjmi.','1611985@hcmut.edu.vn',1,0,'2018-10-14 18:05:29','2018-10-14 18:05:29','displayD','e7524bf7d12d8d414a15b8226e9b08f4',1),('b','$2a$10$gj4OtbyETCraxaVKBg5CXuyIxjOy93vo83nJCgnytwkv5BUHpjmi.','1611985@hcmut.edu.vn',1,0,'2018-10-19 15:20:56','2018-10-19 15:20:56','b','e7524bf7d12d8d414a15b8226e9b08f4',0),('c','$2a$10$gj4OtbyETCraxaVKBg5CXuyIxjOy93vo83nJCgnytwkv5BUHpjmi.','1611985@hcmut.edu.vn',1,0,'2018-10-19 15:20:56','2018-10-19 15:20:56','b','e7524bf7d12d8d414a15b8226e9b08f4',0),('e','$2a$10$gj4OtbyETCraxaVKBg5CXuyIxjOy93vo83nJCgnytwkv5BUHpjmi.','1611985@hcmut.edu.vn',1,0,'2018-10-19 15:20:56','2018-10-19 15:20:56','b','e7524bf7d12d8d414a15b8226e9b08f4',0),('f','$2a$10$gj4OtbyETCraxaVKBg5CXuyIxjOy93vo83nJCgnytwkv5BUHpjmi.','1611985@hcmut.edu.vn',1,0,'2018-10-19 15:20:56','2018-10-19 15:20:56','b','e7524bf7d12d8d414a15b8226e9b08f4',0),('g','$2a$10$gj4OtbyETCraxaVKBg5CXuyIxjOy93vo83nJCgnytwkv5BUHpjmi.','1611985@hcmut.edu.vn',1,0,'2018-10-19 15:20:56','2018-10-19 15:20:56','b','e7524bf7d12d8d414a15b8226e9b08f4',0),('h','$2a$10$gj4OtbyETCraxaVKBg5CXuyIxjOy93vo83nJCgnytwkv5BUHpjmi.','1611985@hcmut.edu.vn',1,0,'2018-10-19 15:20:56','2018-10-19 15:20:56','b','e7524bf7d12d8d414a15b8226e9b08f4',0),('huynhha12798','$2a$10$FRLgeu8M2XhFhFN40KvwDe./6fCNkiEFXs2BmJzNh9T4rD4IdEtd6','huynhha12798@gmail.com',1,0,'2018-10-17 08:34:00','2018-10-17 08:34:00','Huynh Ha','f162fe661200a04ffcca2e440a1e3519',0),('i','$2a$10$NBWohiFuQaQ8b6nPau94HO49vXS.hFo82bPmhlE2Ku.O6JAQOwECG','manhpcpro@gmail.com',1,0,'2018-10-19 17:50:05','2018-10-19 17:50:05','fsdfdsafa','96f785e13f8dec6cc97ddd2ee9f814ff',0),('t1','$2a$10$hk3RvO/fRtTAGjAs0tFsVOs/jF8nXnqErdDqwQpfmvGHpkDiggUOG','manhpcpro@gmail.com',0,0,'2018-10-23 03:22:22','2018-10-23 03:22:22','test','cee6b4dc5e86c3fb35753d4f055d6c0e',0),('t2','$2a$10$O2XBE0hlcRtSD96EyBazxunYzN/ocJLx4tPq8lvlipq64NYk1EjXe','manhpcpro@gmail.com',0,0,'2018-10-23 03:22:46','2018-10-23 03:22:46','test','29c3bbdd27fdc09df6fe644d8d17e5f5',0),('t3','$2a$10$IKUMdkLZTUyLahX0cnYy1ufyfWsj4sCyCba/HdKraMbbpu45v0hcS','manhpcpro@gmail.com',0,0,'2018-10-23 03:27:16','2018-10-23 03:27:16','test','fd55b9077f3fff225d619d88d86e5ade',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'threadripper'
--
/*!50003 DROP FUNCTION IF EXISTS `getAvatar` */;
ALTER DATABASE `threadripper` CHARACTER SET utf8 COLLATE utf8_general_ci ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getAvatar`(u varchar(50)) RETURNS varchar(300) CHARSET utf8
    DETERMINISTIC
BEGIN
	DECLARE var VARCHAR(300); 
	SELECT avatarUrl INTO var FROM threadripper.avatar WHERE avatar.username=u ORDER BY datetime DESC LIMIT 1;
	IF var IS NULL THEN SET var='vre.hcmut.edu.vn/threadripper/api/avatar/default.jpg';
	END IF;
	RETURN var;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
ALTER DATABASE `threadripper` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci ;
/*!50003 DROP FUNCTION IF EXISTS `getNotiCount` */;
ALTER DATABASE `threadripper` CHARACTER SET utf8 COLLATE utf8_general_ci ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getNotiCount`(id INTEGER) RETURNS int(11)
    DETERMINISTIC
BEGIN
	DECLARE var INTEGER; 
	SELECT count(1) INTO var FROM threadripper.message WHERE conversationId = id AND `read` = 0;
	RETURN var;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
ALTER DATABASE `threadripper` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-27 22:48:44
