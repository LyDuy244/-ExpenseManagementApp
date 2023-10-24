-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: expensedb
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `group_expense`
--

DROP TABLE IF EXISTS `group_expense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_expense` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `owner_id` int NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `is_active` bit(1) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_GROUP_OWNER_idx` (`owner_id`),
  CONSTRAINT `FK_GROUP_OWNER` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_expense`
--

LOCK TABLES `group_expense` WRITE;
/*!40000 ALTER TABLE `group_expense` DISABLE KEYS */;
INSERT INTO `group_expense` VALUES (1,'NhÃ³m 1',2,'test','test','2023-10-07 00:00:00','2023-10-15 00:00:00',_binary '','2023-10-07 15:07:46'),(2,'Group1',2,'Du lich','Du lich Da Lat 3 ngay 2 dem','2023-10-24 00:00:00','2023-11-05 00:00:00',_binary '','2023-10-24 12:25:52');
/*!40000 ALTER TABLE `group_expense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_member`
--

DROP TABLE IF EXISTS `group_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_id` int NOT NULL,
  `user_id` int NOT NULL,
  `is_active` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_GROUP_MEMBER_GROUP_idx` (`group_id`),
  KEY `FK_GROUP_MEMBER_USER_idx` (`user_id`),
  CONSTRAINT `FK_GROUP_MEMBER_GROUP` FOREIGN KEY (`group_id`) REFERENCES `group_expense` (`id`),
  CONSTRAINT `FK_GROUP_MEMBER_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_member`
--

LOCK TABLES `group_member` WRITE;
/*!40000 ALTER TABLE `group_member` DISABLE KEYS */;
INSERT INTO `group_member` VALUES (1,1,2,_binary ''),(2,2,2,_binary ''),(3,2,6,_binary ''),(4,2,5,_binary ''),(5,2,4,_binary '');
/*!40000 ALTER TABLE `group_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_transaction`
--

DROP TABLE IF EXISTS `group_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_id` int NOT NULL,
  `group_member_id` int NOT NULL,
  `type_id` int NOT NULL,
  `purpose` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `amount` decimal(12,2) NOT NULL,
  `created_date` datetime NOT NULL,
  `is_active` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_GROUP_TRANSACTION_GROUP_idx` (`group_id`),
  KEY `FK_GROUP_TRANSACTION_MEMBER_idx` (`group_member_id`),
  KEY `FK_GROUP_TRANSACTION_TYPE_idx` (`type_id`),
  CONSTRAINT `FK_GROUP_TRANSACTION_GROUP` FOREIGN KEY (`group_id`) REFERENCES `group_expense` (`id`),
  CONSTRAINT `FK_GROUP_TRANSACTION_MEMBER` FOREIGN KEY (`group_member_id`) REFERENCES `group_member` (`id`),
  CONSTRAINT `FK_GROUP_TRANSACTION_TYPE` FOREIGN KEY (`type_id`) REFERENCES `type_transaction` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_transaction`
--

LOCK TABLES `group_transaction` WRITE;
/*!40000 ALTER TABLE `group_transaction` DISABLE KEYS */;
INSERT INTO `group_transaction` VALUES (1,1,1,1,'test','test',100000.00,'2023-10-07 15:08:18',_binary '\0');
/*!40000 ALTER TABLE `group_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `month` int NOT NULL,
  `quarter` int NOT NULL,
  `year` int NOT NULL,
  `type` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_NOTIFICATION_USER_idx` (`user_id`),
  CONSTRAINT `FK_NOTIFICATION_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `type_id` int NOT NULL,
  `purpose` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `amount` decimal(12,2) NOT NULL,
  `created_date` datetime NOT NULL,
  `is_active` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_TRANSACTION_USER_idx` (`user_id`),
  KEY `FK_TRANSACTION_TYPE_idx` (`type_id`),
  CONSTRAINT `FK_TRANSACTION_TYPE` FOREIGN KEY (`type_id`) REFERENCES `type_transaction` (`id`),
  CONSTRAINT `FK_TRANSACTION_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,1,2,'Chi tiÃªu cÃ¡ nhÃ¢n','test',100000.00,'2023-09-25 00:00:00',_binary ''),(2,2,2,'testt','test',100000.00,'2023-10-13 00:00:00',_binary ''),(3,2,2,'test','test',100000.00,'2023-10-23 00:00:00',_binary ''),(4,2,2,'test2','test',200000.00,'2023-09-14 00:00:00',_binary '');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_transaction`
--

DROP TABLE IF EXISTS `type_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_transaction`
--

LOCK TABLES `type_transaction` WRITE;
/*!40000 ALTER TABLE `type_transaction` DISABLE KEYS */;
INSERT INTO `type_transaction` VALUES (1,'Thu'),(2,'Chi');
/*!40000 ALTER TABLE `type_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `avartar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` bit(1) NOT NULL,
  `birthday` datetime NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `is_active` bit(1) NOT NULL,
  `verification_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$iW.UD/vbD.6MnuKuks5gG.3mCFZqjkLxJ0udw1KgJUqP.EqOrXQh6','ROLE_ADMIN','https://res.cloudinary.com/dhcy5k05t/image/upload/v1695632164/q8gibzbxsuhihiugcpct.jpg',_binary '\0','2002-04-24 00:00:00','2051052019duy@ou.edu.vn','Lý','Duy',_binary '','ubY7ntzlWgYwzOwtNvIFmqfizEadGmPCSQC4R5Acj7a8U8di50wzrczuSB8ZokuN'),(2,'user1','$2a$10$IW6GFCB1hrGe4kUXY0zCheW0Th0JqGFnIPvMvGxEyAdtPV2pVnLH.','ROLE_USER','https://res.cloudinary.com/dhcy5k05t/image/upload/v1696491547/whhsqtwiepdy3y2z4uid.jpg',_binary '\0','2004-02-28 00:00:00','lynguyenngocduy123@gmail.com','Lý','Duy',_binary '','LuVpT3U93456F1OxpvTRahW18AexH1pwF93RkCYxJU4AOjvTxRgoZMlGEpOw17rk'),(3,'user2','$2a$10$GCASY.GZgocsRSgHhefmd.cTQrIrAww37dLDVwrOrhPpN4XuTpwdG','ROLE_USER','https://res.cloudinary.com/dhcy5k05t/image/upload/v1698061988/tzju9ahhkwaobf4g6jpp.jpg',_binary '\0','2003-02-23 00:00:00','lynguyenngocduy1234@gmail.com','Lý','Khánh',_binary '','5yUn0EbdlbwUNIj8kkpfiBokKPuqDU8fAicJuZ6wYMlRdSDNTdn920PxRghDqtHg'),(4,'user3','$2a$10$DkGaHiGBmFNwOHcX5gorjujzG3wHTwTOhqcZkriEzBuBirESCiOt2','ROLE_USER','https://res.cloudinary.com/dhcy5k05t/image/upload/v1698062089/ae5dmgr5ofaxjxrngzxi.png',_binary '\0','2001-10-11 00:00:00','lynguyenngocduy223@gmail.com','Cáp','Đạt',_binary '','UufzfoW7NL0X31u7Ei7LTsDFE0R1rNdRQ4hZG4itMiLVYVJDEY02Rxyz5FFBEix2'),(5,'user4','$2a$10$zZBohJjs3/sp9Pl/9AIuYeH7NfVlxFriY.ZWhDcXFU5nmhsxVrfEm','ROLE_USER','https://res.cloudinary.com/dhcy5k05t/image/upload/v1698062177/iizkwqzsehuaxnrszjoe.jpg',_binary '','2002-02-06 00:00:00','lynguyenngocduy323@gmail.com','Nguyễn','Nhi',_binary '','7Ve1LNFKvU7AFykQGxHLlNVJr3qtNg5ZFY5SsuRRMOavwvWUZnYIceeubVAzR9sf'),(6,'user5','$2a$10$sRNEelggByV6c91vLROZ0eD80sDcR45dE7XUDUcz0//kntUjpMvOW','ROLE_USER','https://res.cloudinary.com/dhcy5k05t/image/upload/v1698062250/kpavnzmcmhvdxqqpmedn.jpg',_binary '\0','2000-02-17 00:00:00','lynguyenngocduy5153@gmail.com','Quang','Tới',_binary '','Tsvjq0BJ4aoexdAzufHxs8ulWw2sO8y84ajoXhVegnvkjh1zeuAvQYbpMoFawVym');
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

-- Dump completed on 2023-10-24 12:46:19
