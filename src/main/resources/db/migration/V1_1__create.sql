CREATE TABLE `video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `video_id` varchar(255) DEFAULT NULL,
  `description` varchar(2024) DEFAULT NULL,
  `duration` tinyblob,
  `publish_date` date NOT NULL,
  `thumbnail_link` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

