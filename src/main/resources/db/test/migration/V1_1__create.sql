CREATE TABLE `video` (
  `video_id` varchar(255) NOT NULL,
  `description` varchar(2024) DEFAULT NULL,
  `duration` tinyblob,
  `publish_date` date NOT NULL,
  `thumbnail_link` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`video_id`)
);

