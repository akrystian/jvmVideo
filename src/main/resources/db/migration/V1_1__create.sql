CREATE TABLE `video` (
  `id`             BIGINT(20) NOT NULL AUTO_INCREMENT,
  `video_id`       VARCHAR(255)        DEFAULT NULL,
  `description`    VARCHAR(4096)       DEFAULT NULL,
  `duration`       TINYBLOB,
  `publish_date`   DATE       NOT NULL,
  `thumbnail_link` VARCHAR(255)        DEFAULT NULL,
  `title`          VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

