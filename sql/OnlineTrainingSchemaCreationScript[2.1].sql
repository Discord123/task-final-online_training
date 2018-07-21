-- MySQL Script generated by MySQL Workbench
-- Mon Jan 22 19:13:58 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema OnlineTraining
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema OnlineTraining
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `OnlineTraining` DEFAULT CHARACTER SET utf8 ;
USE `OnlineTraining` ;

-- -----------------------------------------------------
-- Table `OnlineTraining`.`users`
-- -----------------------------------------------------
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


-- -----------------------------------------------------
-- Table `OnlineTraining`.`subjects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OnlineTraining`.`subjects` (
  `subject_id` INT NOT NULL AUTO_INCREMENT COMMENT 'subject_id - синтетический PK предмета.',
  `subject_language` ENUM('english', 'german', 'italian', 'spanish', 'french') NOT NULL COMMENT 'subject_language - вид преподаваемого языка.',
  `subject_level` ENUM('A1', 'A2', 'B1', 'B2', 'C1', 'C2') NOT NULL COMMENT 'subject_level - степень сложности преподаваемых знаний.',
  PRIMARY KEY (`subject_id`))
ENGINE = InnoDB
COMMENT = 'Предметы описываются названием иностанного языка и уровнем сложности подаваемого материала.';


-- -----------------------------------------------------
-- Table `OnlineTraining`.`courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OnlineTraining`.`courses` (
  `course_id` INT NOT NULL AUTO_INCREMENT COMMENT 'course_id - синтетический первичный ключ для курсов.',
  `course_title` VARCHAR(75) NOT NULL COMMENT 'course_title - название курса, и краткое описание.',
  `course_subject_id` INT NULL COMMENT 'course_subject_id - внешний ключ для предмета преподаваемого на курсе.',
  `course_status` ENUM('gathering', 'running', 'ended') NOT NULL DEFAULT 'gathering' COMMENT 'course_status - отображает состояние курса.\ngathering - набор студентов.\nrunning - группа студентов начала обучение.\nended - курс завершен.\nДобавлен индекс для запросов where.',
  `course_isAvailable` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'course_isAvailable - логическая переменная. Отображает доступность курса для вступления новых студентов.\n1 - можно присоединиться к группе, обучающейся на курсе.\n0 - курс не доступен для вступления новых учащихся.\nДобавлен индекс для запросов where.',
  `course_teacher_id` INT NULL COMMENT 'course_teacher_id - id преподавателя конкретного курса.',
  PRIMARY KEY (`course_id`),
  INDEX `fk_classes_subjects1_idx` (`course_subject_id` ASC),
  INDEX `fk_courses_users_idx` (`course_teacher_id` ASC),
  INDEX `course_status` (`course_status` ASC),
  INDEX `course_isAvailable` (`course_isAvailable` ASC),
  CONSTRAINT `fk_courses_subjects`
    FOREIGN KEY (`course_subject_id`)
    REFERENCES `OnlineTraining`.`subjects` (`subject_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_courses_users`
    FOREIGN KEY (`course_teacher_id`)
    REFERENCES `OnlineTraining`.`users` (`user_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Таблица хранит информацию о доступных курсах.\nНа каждом курсе преподается один предмет, одним преподавателем.';


-- -----------------------------------------------------
-- Table `OnlineTraining`.`tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OnlineTraining`.`tasks` (
  `task_id` INT NOT NULL AUTO_INCREMENT COMMENT 'task_id - синтетический PK для задания.',
  `task_name` VARCHAR(45) NOT NULL COMMENT 'task_name - название задания.',
  `task_description` VARCHAR(450) NOT NULL COMMENT 'task_description - описание задания.',
  `task_course_id` INT NULL COMMENT 'task_course_id - FK на id курса, в рамках которого студенту назначается задание.',
  PRIMARY KEY (`task_id`),
  INDEX `fk_tasks_courses1_idx` (`task_course_id` ASC),
  CONSTRAINT `fk_tasks_courses`
    FOREIGN KEY (`task_course_id`)
    REFERENCES `OnlineTraining`.`courses` (`course_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Таблица хранит задания для студентов.\nПреподаватель может выставлять задание, после чего оценивать его и давать рецензию.';


-- -----------------------------------------------------
-- Table `OnlineTraining`.`courses_has_students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OnlineTraining`.`courses_has_students` (
  `courses_course_id` INT NOT NULL COMMENT 'courses_course_id - ссылка на course_id таблицы courses.',
  `users_user_id` INT NOT NULL,
  PRIMARY KEY (`courses_course_id`, `users_user_id`),
  INDEX `fk_courses_has_users_users1_idx` (`users_user_id` ASC),
  INDEX `fk_courses_has_users_courses1_idx` (`courses_course_id` ASC),
  CONSTRAINT `fk_courses_has_users_courses1`
    FOREIGN KEY (`courses_course_id`)
    REFERENCES `OnlineTraining`.`courses` (`course_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_courses_has_users_users1`
    FOREIGN KEY (`users_user_id`)
    REFERENCES `OnlineTraining`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Таблица отражающая связь \"многие к многим\" между таблицами users и courses.\nСтуденты из users могут проходить обучение на нескольких курсах сразу.\nНа каждом курсе обучаются несколько студентов.';


-- -----------------------------------------------------
-- Table `OnlineTraining`.`students_has_tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `OnlineTraining`.`students_has_tasks` (
  `users_user_id` INT NOT NULL COMMENT 'Ссылка на id студента из таблицы users.',
  `tasks_task_id` INT NOT NULL COMMENT 'Ссылка на id задания из таблицы tasks.',
  `task_answer` VARCHAR(4500) NULL,
  `task_review` VARCHAR(1000) NULL COMMENT 'task_review - отзыв преподавателя о работе студента.',
  `task_mark` TINYINT(10) UNSIGNED NULL COMMENT 'task_mark - оценака выполненного задания. От 1 до 10 баллов.',
  INDEX `fk_users_has_tasks_tasks1_idx` (`tasks_task_id` ASC),
  INDEX `fk_users_has_tasks_users1_idx` (`users_user_id` ASC),
  CONSTRAINT `fk_users_has_tasks_users1`
    FOREIGN KEY (`users_user_id`)
    REFERENCES `OnlineTraining`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_tasks_tasks1`
    FOREIGN KEY (`tasks_task_id`)
    REFERENCES `OnlineTraining`.`tasks` (`task_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Таблица отражает связь многие к многим.\nОдин студет может получать несколько заданий.\nОдно задание может быть выдано нескольким студентам.';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;