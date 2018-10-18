CREATE DATABASE  IF NOT EXISTS `threadripper` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `threadripper`;
-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
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
  `filename` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`avatar_id`),
  UNIQUE KEY `filename_UNIQUE` (`filename`),
  KEY `fk_username_idx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avatar`
--

LOCK TABLES `avatar` WRITE;
/*!40000 ALTER TABLE `avatar` DISABLE KEYS */;
/*!40000 ALTER TABLE `avatar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversation`
--

DROP TABLE IF EXISTS `conversation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `conversation` (
  `conversation_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`conversation_id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversation`
--

LOCK TABLES `conversation` WRITE;
/*!40000 ALTER TABLE `conversation` DISABLE KEYS */;
INSERT INTO `conversation` VALUES (1,'b'),(1,'f'),(2,'a'),(2,'c'),(2,'d'),(2,'f'),(3,'a'),(3,'d'),(6,'a'),(6,'b'),(8,'a'),(8,'b'),(9,'a'),(9,'c'),(9,'d');
/*!40000 ALTER TABLE `conversation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversation_setting`
--

DROP TABLE IF EXISTS `conversation_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `conversation_setting` (
  `conversation_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`conversation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversation_setting`
--

LOCK TABLES `conversation_setting` WRITE;
/*!40000 ALTER TABLE `conversation_setting` DISABLE KEYS */;
INSERT INTO `conversation_setting` VALUES (1,NULL),(2,NULL),(3,NULL),(4,NULL),(5,NULL),(6,NULL),(7,NULL),(8,NULL),(9,NULL);
/*!40000 ALTER TABLE `conversation_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `conversation_id` int(11) DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(72) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '0',
  `lock` tinyint(1) DEFAULT '0',
  `datetime_update` datetime DEFAULT NULL,
  `datetime_create` datetime DEFAULT NULL,
  `displayName` varchar(100) DEFAULT NULL,
  `hash` varchar(50) DEFAULT NULL,
  `online` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('a','$2a$10$gj4OtbyETCraxaVKBg5CXuyIxjOy93vo83nJCgnytwkv5BUHpjmi.','1611985@hcmut.edu.vn',1,0,'2018-10-14 18:05:29','2018-10-14 18:05:29','displayD','e7524bf7d12d8d414a15b8226e9b08f4',0),('afdfs','$2a$10$xdmaMrasAbjEfd6JkZGVGOG6sLkxR9fQeZ7wOO5x0KztKshLPxAD2','fsdfsdfdsf',1,0,'2018-10-17 08:28:05','2018-10-17 08:28:05','fsdfdsafa','ed312a0cf2a9cf992848dd73b0eb46f3',0),('dá','$2a$10$z42NDaay5NkuSeybjf/sZOeVICaA8T5N7YN74nPfAuJveT0h3ik7O','gsdgsdsd',1,0,'2018-10-17 08:31:35','2018-10-17 08:31:35','fsdgsdf','50457bf3ed27922c830683c56d12a1fc',0),('huynhha12798','$2a$10$FRLgeu8M2XhFhFN40KvwDe./6fCNkiEFXs2BmJzNh9T4rD4IdEtd6','huynhha12798@gmail.com',1,0,'2018-10-17 08:34:00','2018-10-17 08:34:00','Huynh Ha','f162fe661200a04ffcca2e440a1e3519',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-19  1:54:14
