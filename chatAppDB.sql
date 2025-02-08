CREATE DATABASE `chatapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(12) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `announcement` (
  `announcement_id` int NOT NULL AUTO_INCREMENT,
  `desc` varchar(400) NOT NULL,
  `admin_id` int DEFAULT NULL,
  PRIMARY KEY (`announcement_id`),
  KEY `admin_id_idx` (`admin_id`),
  CONSTRAINT `admin_id` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `chat` (
  `chat_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `invitation` (
  `sender_id` int NOT NULL,
  `receiver_id` int NOT NULL,
  `status` enum('ACCEPT','REJECT','WAIT') NOT NULL DEFAULT 'WAIT',
  PRIMARY KEY (`sender_id`,`receiver_id`),
  KEY `receiver_id_idx` (`receiver_id`),
  CONSTRAINT `receiver_id` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `sender_id` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `message` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(1000) DEFAULT NULL,
  `file_url` varchar(200) DEFAULT NULL,
  `file_type` varchar(45) DEFAULT NULL,
  `chat_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `chat_id_idx` (`chat_id`),
  KEY `user_message_id_idx` (`user_id`),
  CONSTRAINT `chat_message_id` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`chat_id`) ON DELETE CASCADE,
  CONSTRAINT `user_message_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `notifcation` (
  `notifcation_id` int NOT NULL AUTO_INCREMENT,
  `describtion` varchar(400) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isMessage` tinyint NOT NULL DEFAULT '1',
  `chatId` int DEFAULT NULL,
  PRIMARY KEY (`notifcation_id`),
  UNIQUE KEY `notifcation_id_UNIQUE` (`notifcation_id`),
  KEY `chat_id_of_message_idx` (`chatId`),
  CONSTRAINT `chat_id_of_message` FOREIGN KEY (`chatId`) REFERENCES `chat` (`chat_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `particpant` (
  `chat_id` int NOT NULL,
  `particpant_id` int NOT NULL,
  `state` enum('BOLCKED','AVAILABLE') DEFAULT 'AVAILABLE',
  `category` enum('WORK','FAMILY','FRIEND') DEFAULT 'FRIEND',
  PRIMARY KEY (`chat_id`,`particpant_id`),
  KEY `participant_id_idx` (`particpant_id`),
  CONSTRAINT `chat_id` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`chat_id`) ON DELETE CASCADE,
  CONSTRAINT `participant_id` FOREIGN KEY (`particpant_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `session` (
  `session_id` int NOT NULL AUTO_INCREMENT,
  `user_session_id` int NOT NULL,
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `logout_time` timestamp NULL DEFAULT NULL,
  `isActive` tinyint NOT NULL,
  `status` enum('OFFLINE','ONLINE','NOTDEFINED') NOT NULL,
  PRIMARY KEY (`session_id`),
  KEY `user_session_id_idx` (`user_session_id`),
  CONSTRAINT `user_session_id` FOREIGN KEY (`user_session_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `picture` mediumblob,
  `gender` enum('FEMALE','MALE') NOT NULL DEFAULT 'MALE',
  `country` varchar(45) NOT NULL,
  `bio` varchar(250) DEFAULT NULL,
  `DOB` date NOT NULL,
  `password` varchar(12) NOT NULL,
  `count_of_login` int DEFAULT NULL,
  `mode` enum('AVALIABLE','BUSY','AWAY') DEFAULT 'AVALIABLE',
  `is_chatbot_enabled` tinyint NOT NULL,
  `name` varchar(50) NOT NULL,
  `linkedin_url` varchar(60) DEFAULT NULL,
  `facebook_url` varchar(60) DEFAULT NULL,
  `twitter_url` varchar(60) DEFAULT NULL,
  `is_online` tinyint DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_announcement` (
  `user_announcement_id` int NOT NULL,
  `announcement_id` int NOT NULL,
  PRIMARY KEY (`user_announcement_id`,`announcement_id`),
  KEY `announcment_id_idx` (`announcement_id`),
  CONSTRAINT `announcment_id` FOREIGN KEY (`announcement_id`) REFERENCES `announcement` (`announcement_id`) ON DELETE CASCADE,
  CONSTRAINT `user_announcement_id` FOREIGN KEY (`user_announcement_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_notifcation` (
  `user_id` int NOT NULL,
  `notifcation_id` int NOT NULL,
  `status` enum('READ','UNREAD') NOT NULL DEFAULT 'READ',
  PRIMARY KEY (`user_id`,`notifcation_id`),
  KEY `notidcation_id_idx` (`notifcation_id`),
  CONSTRAINT `notidcation_id` FOREIGN KEY (`notifcation_id`) REFERENCES `notifcation` (`notifcation_id`) ON DELETE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
