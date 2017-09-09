CREATE TABLE `source` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_harvested` datetime NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `channel_link` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

ALTER TABLE video
  ADD source_id bigint(20) DEFAULT NULL;
ALTER TABLE video
  ADD CONSTRAINT fk_source_id FOREIGN KEY (source_id) REFERENCES source(id);

