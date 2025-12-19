-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: hospital
-- ------------------------------------------------------
-- Server version	8.4.0

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

LOCK TABLES `cards` WRITE;
/*!40000 ALTER TABLE `cards` DISABLE KEYS */;
INSERT INTO `cards` VALUES (1,2,'2','ОРВИ','2024-04-09 21:00:00',NULL,NULL,'Поступила с жалобами на сопли и чихание. Низкое АД 90/60'),(3,1,'123123c3423psefe','ОРВИ','2024-04-09 21:00:00',NULL,NULL,'Высокая температура'),(11,2,'4','ОРВИ проп','2024-04-09 21:00:00',NULL,NULL,'Высокая температура');
/*!40000 ALTER TABLE `cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'первая категория'),(2,'вторая категория'),(3,'высшая категория');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `employee_has_role`
--

LOCK TABLES `employee_has_role` WRITE;
/*!40000 ALTER TABLE `employee_has_role` DISABLE KEYS */;
INSERT INTO `employee_has_role` VALUES (1,1,1),(2,2,2),(3,3,2),(4,4,2);
/*!40000 ALTER TABLE `employee_has_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,1,1,'$2a$12$zLAhTP75GlzbdAr0eaV2A.ky3Fm4yltx7KZUOCIYt9qogBqARFRW2','admin@company.com','admin','admin','admin'),(2,2,3,'$2a$12$X9KQ5AeclU3IxXAXfkMWAOWLTg/DhevoCdvOjqXDTRAD8ViRmhq4m','solomka@tut.by','Соломенцева','Ольга','Ивановна'),(3,1,1,'$2a$12$eUVKzEBw1cAItRmsPALcS.0YzCboZwoLJDVruI5eDF1ZUADWKTlqm','semen@mail.ru','Панфило','Инокентий','Васильевич'),(4,1,1,'$2a$12$F6wDbgcpX5PAy4A8r9dqbeI9BBDSruNX8aLIqgbTaV8q9dR8qU0Sy','maximus@mail.ru','Цыганек','Максим','Константиновна');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES ('1','Жабинский','Пётр','Николаевич','1985-03-25','г. Барановичи, ул. Солнечная, д.8, кв. 12','+375(29)745-0902',1,'gab@asb.by'),('123123c3423psefe','Бездомный','Петр','Николаевич ','1985-02-25','Планета земля','+375(29)000-1000',0,'gab@asb.by'),('123123c3423sefe','Жабинский','Сергей','Николаевич ','1984-07-23','г. Столинград, ул. Солнечная, д.8, кв. 12','+375(29)645-0902',1,'gab@asb.by'),('123123c3423sfe','Жабинский','Сергей','Николаевич ','1985-03-25','г. Столин, ул. Солнечная, д.8, кв. 12','+375(29)645-0702',1,'gab@asb.by'),('2','Колесник','Марина','Викторовна','1985-04-04','г. Минск, ул. Наполеона Орды, д.51б кв. 69',NULL,1,NULL),('3','Живицкий','Юрий','Вацлович','1972-02-28','г. Столбцы, ул. Парковая, д.4',NULL,1,NULL),('4','Живицкий','Юрий','Вацлавович','1972-02-28','г. Барановичи, ул. текстильная, д.8, кв. 12','+375(29)825-0902',1,'giv@asb.by');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `procedure_types`
--

LOCK TABLES `procedure_types` WRITE;
/*!40000 ALTER TABLE `procedure_types` DISABLE KEYS */;
INSERT INTO `procedure_types` VALUES (1,'операция'),(2,'лекарства'),(3,'физиотерапия'),(4,'диагностика'),(5,'назначение');
/*!40000 ALTER TABLE `procedure_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `procedures`
--

LOCK TABLES `procedures` WRITE;
/*!40000 ALTER TABLE `procedures` DISABLE KEYS */;
INSERT INTO `procedures` VALUES (1,3,4,1,'ФГДС','острый гастрит','2024-04-07 21:00:00');
/*!40000 ALTER TABLE `procedures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_DOCTOR'),(3,'ROLE_NURSE');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `specializations`
--

LOCK TABLES `specializations` WRITE;
/*!40000 ALTER TABLE `specializations` DISABLE KEYS */;
INSERT INTO `specializations` VALUES (1,'хирург'),(2,'кардиолог'),(3,'терапевт'),(4,'окулист');
/*!40000 ALTER TABLE `specializations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-06 17:36:39
