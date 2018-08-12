CREATE DATABASE  IF NOT EXISTS `onlinetraining` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `onlinetraining`;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'course_id - синтетический первичный ключ для курсов.',
  `course_title` varchar(75) NOT NULL COMMENT 'course_title - название курса, и краткое описание.',
  `course_subject_id` int(11) DEFAULT NULL COMMENT 'course_subject_id - внешний ключ для предмета преподаваемого на курсе.',
  `course_status` enum('gathering','running','ended') NOT NULL DEFAULT 'gathering' COMMENT 'course_status - отображает состояние курса.\ngathering - набор студентов.\nrunning - группа студентов начала обучение.\nended - курс завершен.\nДобавлен индекс для запросов where.',
  `course_isAvailable` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'course_isAvailable - логическая переменная. Отображает доступность курса для вступления новых студентов.\n1 - можно присоединиться к группе, обучающейся на курсе.\n0 - курс не доступен для вступления новых учащихся.\nДобавлен индекс для запросов where.',
  `course_teacher_id` int(11) DEFAULT NULL COMMENT 'course_teacher_id - id преподавателя конкретного курса.',
  PRIMARY KEY (`course_id`),
  KEY `fk_classes_subjects1_idx` (`course_subject_id`),
  KEY `fk_courses_users_idx` (`course_teacher_id`),
  KEY `course_status` (`course_status`),
  KEY `course_isAvailable` (`course_isAvailable`),
  CONSTRAINT `fk_courses_subjects` FOREIGN KEY (`course_subject_id`) REFERENCES `subjects` (`subject_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_courses_users` FOREIGN KEY (`course_teacher_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='Таблица хранит информацию о доступных курсах.\nНа каждом курсе преподается один предмет, одним преподавателем.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'English for beginers',1,'running',1,2),(2,'English for advanced',5,'running',1,2),(3,'German for beginers',8,'gathering',1,3),(4,'French for expirienced',16,'running',1,4),(5,'English for expirienced',4,'gathering',1,2);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students_courses`
--

DROP TABLE IF EXISTS `students_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students_courses` (
  `courses_course_id` int(11) NOT NULL COMMENT 'courses_course_id - ссылка на course_id таблицы courses.',
  `users_user_id` int(11) NOT NULL,
  PRIMARY KEY (`courses_course_id`,`users_user_id`),
  KEY `fk_students_courses_users1_idx` (`users_user_id`),
  KEY `fk_students_courses_courses1_idx` (`courses_course_id`),
  CONSTRAINT `fk_students_courses_courses1` FOREIGN KEY (`courses_course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_students_courses_users1` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Таблица отражающая связь "многие к многим" между таблицами users и courses.\nСтуденты из users могут проходить обучение на нескольких курсах сразу.\nНа каждом курсе обучаются несколько студентов.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_has_students`
--

LOCK TABLES `students_courses` WRITE;
/*!40000 ALTER TABLE `students_courses` DISABLE KEYS */;
INSERT INTO `students_courses` VALUES (1,5),(2,5),(1,6),(1,7),(1,8),(1,9),(1,10),(2,11),(2,12),(2,13),(2,14),(2,15),(3,16),(3,17),(4,18),(4,19),(4,20),(4,21),(5,22),(5,23),(5,24);
/*!40000 ALTER TABLE `students_courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students_tasks`
--

DROP TABLE IF EXISTS `students_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students_tasks` (
  `users_user_id` int(11) NOT NULL COMMENT 'Ссылка на id студента из таблицы users.',
  `tasks_task_id` int(11) NOT NULL COMMENT 'Ссылка на id задания из таблицы tasks.',
  `task_answer` varchar(4500) DEFAULT NULL,
  `task_review` varchar(1000) DEFAULT NULL COMMENT 'task_review - отзыв преподавателя о работе студента.',
  `task_mark` tinyint(10) unsigned DEFAULT NULL COMMENT 'task_mark - оценака выполненного задания. От 1 до 10 баллов.',
  KEY `fk_students_tasks_tasks1_idx` (`tasks_task_id`),
  KEY `fk_students_tasks_users1_idx` (`users_user_id`),
  CONSTRAINT `fk_students_tasks_tasks1` FOREIGN KEY (`tasks_task_id`) REFERENCES `tasks` (`task_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_students_tasks_users1` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Таблица отражает связь многие к многим.\nОдин студет может получать несколько заданий.\nОдно задание может быть выдано нескольким студентам.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students_has_tasks`
--

LOCK TABLES `students_tasks` WRITE;
/*!40000 ALTER TABLE `students_tasks` DISABLE KEYS */;
INSERT INTO `students_tasks` VALUES (5,1,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','not bad',7),(6,1,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','try harder',5),(7,1,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','excellent',9),(8,1,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','not bad',6),(9,1,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','pathetic',4),(10,1,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','not bad',7),(11,2,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','try harder',5),(12,2,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','not bad',7),(13,2,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','good enough',8),(14,2,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','excellent',10),(15,2,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','not bad',7),(18,3,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','good enough',8),(19,3,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','excellent',9),(20,3,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','not bad',7),(21,3,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lacinia pulvinar aliquam. Nam nunc nibh, interdum non iaculis at, scelerisque non mi. Integer facilisis eros risus, mattis efficitur nibh malesuada eget. In ultricies turpis eu tellus hendrerit euismod. Fusce lacinia quis enim ut cursus. Proin et arcu ac sapien feugiat dictum non id ipsum. Nullam finibus luctus turpis. Aliquam tincidunt.','not bad',6),(5,4,NULL,NULL,NULL),(6,4,NULL,NULL,NULL),(7,4,NULL,NULL,NULL),(8,4,NULL,NULL,NULL),(9,4,NULL,NULL,NULL),(5,5,NULL,NULL,NULL),(6,5,NULL,NULL,NULL),(7,5,NULL,NULL,NULL),(8,5,NULL,NULL,NULL),(9,5,NULL,NULL,NULL),(10,5,NULL,NULL,NULL);
/*!40000 ALTER TABLE `students_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subjects` (
  `subject_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'subject_id - синтетический PK предмета.',
  `subject_language` enum('english','german','italian','spanish','french') NOT NULL COMMENT 'subject_language - вид преподаваемого языка.',
  `subject_level` enum('A1','A2','B1','B2','C1','C2') NOT NULL COMMENT 'subject_level - степень сложности преподаваемых знаний.',
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='Предметы описываются названием иностанного языка и уровнем сложности подаваемого материала.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (1,'english','A1'),(2,'english','A2'),(3,'english','B1'),(4,'english','B2'),(5,'english','C1'),(6,'english','C2'),(7,'german','A1'),(8,'german','A2'),(9,'german','B1'),(10,'german','B2'),(11,'german','C1'),(12,'german','C2'),(13,'french','A1'),(14,'french','A2'),(15,'french','B1'),(16,'french','B2'),(17,'french','C1'),(18,'french','C2'),(19,'italian','A1'),(20,'italian','A2'),(21,'italian','B1'),(22,'italian','B2'),(23,'italian','C1'),(24,'italian','C2'),(25,'spanish','A1'),(26,'spanish','A2'),(27,'spanish','B1'),(28,'spanish','B2'),(29,'spanish','C1'),(30,'spanish','C2');
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tasks` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'task_id - синтетический PK для задания.',
  `task_name` varchar(45) NOT NULL COMMENT 'task_name - название задания.',
  `task_description` varchar(450) NOT NULL COMMENT 'task_description - описание задания.',
  `task_course_id` int(11) DEFAULT NULL COMMENT 'task_course_id - FK на id курса, в рамках которого студенту назначается задание.',
  PRIMARY KEY (`task_id`),
  KEY `fk_tasks_courses1_idx` (`task_course_id`),
  CONSTRAINT `fk_tasks_courses` FOREIGN KEY (`task_course_id`) REFERENCES `courses` (`course_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='Таблица хранит задания для студентов.\nПреподаватель может выставлять задание, после чего оценивать его и давать рецензию.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (1,'First beginers English task','Write down 10 English words you just learned',1),(2,'First advanced English task','Write a text on English. Minimal text length is 1000 words.',2),(3,'First French task','Write down 10 french words you just learned',4),(4,'Second advanced English task','Write a poem on English. Minimal text length is 100 words.',2),(5,'A Poem','Write an impressing poem!',5);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;

DROP TRIGGER IF EXISTS onlinetraining.tasks_AFTER_INSERT;
DELIMITER $$
USE `onlinetraining`$$
CREATE DEFINER = CURRENT_USER TRIGGER onlinetraining.tasks_AFTER_INSERT AFTER INSERT ON tasks FOR EACH ROW
  BEGIN
    SET @task_id = (SELECT MAX(task_id) FROM tasks);
    SET @course_id = (SELECT task_course_id FROM tasks WHERE task_id = @task_id);
    INSERT INTO students_tasks (users_user_id, tasks_task_id)
      SELECT users_user_id, @task_id FROM students_courses WHERE courses_course_id = @course_id;
  END
$$
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `OnlineTraining`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT COMMENT 'user_id - синтетический первичный ключ для пользователей.',
  `user_email` VARCHAR(320) NOT NULL COMMENT 'user_email - поле используется как login, поэтому оно не может быть null и обязано иметь unique значение.\nРазмер поля выбрано согласно стандарту. 64 симовола для user_name, 255 для domain_name и еще 1 сибвол для знака @.\nUnique index понадобится для запросов с where.',
  `user_password` CHAR(40) NOT NULL COMMENT 'user_password - поле для хранения значения пароля, после его хеширования.',
  `first_name` VARCHAR(30) NULL COMMENT 'first_name - имя пользователя.',
  `last_name` VARCHAR(30) NULL COMMENT 'last_name - фамилия пользователя.',
  `user_role` ENUM('admin', 'teacher', 'student') NOT NULL DEFAULT 'student' COMMENT 'user_role - тип и права пользователя.\nadmin - полный доступ к базе.\nteacher - управление заданиями(tasks) для студентов.\nstudent - возможность добавления ответа для полученных заданий.',
  `user_isDeleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'user_isDeleted - удален ли пользователь из базы. 1 = удален, 0 = доступен.',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_email_UNIQUE` (`user_email` ASC),
  INDEX `user_role_index` (`user_role` ASC))
  ENGINE = InnoDB
  COMMENT = 'Таблица users содержит информацию о всех пользователях базы данных.';

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `onlinetraining`.`users` (`user_email`, `user_password`, `first_name`, `last_name`, `user_role`, `user_isDeleted`)
VALUES
  ('adnorthon@example.com', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Advard', 'Northon', 'admin', '0'),
  ('ivanov@example.com', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Ivan', 'Ivanov', 'teacher', '0'),
  ('petrov@example.com', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Petr', 'Petrov', 'teacher', '0'),
  ('sidorov@example.com', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Alex', 'Sidorov', 'teacher', '0'),
  ('pavlenchuk@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Pavel', 'Pavlencuk', 'student', '0'),
  ('putin@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Vladimir', 'Putin', 'student', '0'),
  ('sokol@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Vladislav', 'Sakolik', 'student', '0'),
  ('tihaya@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Alesya', 'Tihaya', 'student', '0'),
  ('pavlova@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Vlada', 'Pavlova', 'student', '0'),
  ('tumen@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Aleksandra', 'Tumen', 'student', '0'),
  ('savchenko@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Petr', 'Savchenko', 'student', '0'),
  ('glebov@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Artem', 'Glebov', 'student', '0'),
  ('zabugornaya@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Sabrina', 'Zabugornaya', 'student', '0'),
  ('rus@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Vera', 'Russkaya', 'student', '0'),
  ('self@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Dasha', 'Kakayuaest', 'student', '0'),
  ('same@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Masha', 'Toje', 'student', '0'),
  ('bondarchuk@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Genadiy', 'Bondarchuk', 'student', '0'),
  ('kravchenko@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Valentin', 'Kravchenko', 'student', '0'),
  ('menishov@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Oleg', 'Menishov', 'student', '0'),
  ('mstislavec@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Fedor', 'Mstislavec', 'student', '0'),
  ('sobolev@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Marshal', 'Sobolev', 'student', '0'),
  ('kirilenko@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Oleg', 'Kirilenko', 'student', '0'),
  ('kolovrat@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Ivan', 'Drujko', 'student', '0'),
  ('kami@example.com', '45f106ef4d5161e7aa38cf6c666607f25748b6ca', 'Dmitriy', 'Morozov', 'student', '0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'onlinetraining'
--

--
-- Dumping routines for database 'onlinetraining'
--
/*!50003 DROP PROCEDURE IF EXISTS `deleteTeacher` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteTeacher`(in id INT)
  BEGIN
    UPDATE users
    SET user_isDeleted = 1
    WHERE user_id = id;

    UPDATE courses
    SET course_teacher_id=NULL
    WHERE course_teacher_id=id;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
