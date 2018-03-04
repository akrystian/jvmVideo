CREATE TABLE `video` (

  `video_id`       VARCHAR(255) UNIQUE NOT NULL,
  `description`    VARCHAR(4096)       DEFAULT NULL,
  `duration`       TINYBLOB,
  `publish_date`   DATE       NOT NULL,
  `thumbnail_link` VARCHAR(255)        DEFAULT NULL,
  `title`          VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (`video_id`)
)
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

