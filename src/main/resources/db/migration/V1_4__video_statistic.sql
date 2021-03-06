CREATE TABLE `video_statistic` (
  `id`       BIGINT(20) NOT NULL AUTO_INCREMENT,
  `disliked` BIGINT(20) NOT NULL,
  `liked`    BIGINT(20) NOT NULL,
  `views`    BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

ALTER TABLE video
  ADD statistic_id BIGINT(20) DEFAULT NULL;
ALTER TABLE video
  ADD CONSTRAINT fk_statistic_id FOREIGN KEY (statistic_id) REFERENCES video_statistic (id);

