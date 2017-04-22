CREATE TABLE source (
  dtype          VARCHAR(31) NOT NULL,
  id             BIGINT(20)  NOT NULL AUTO_INCREMENT PRIMARY KEY,
  last_harvested DATETIME    NOT NULL,
  name           VARCHAR(255)         DEFAULT NULL,
  channel_link   VARCHAR(255)         DEFAULT NULL
);


ALTER TABLE video
  ADD source_id bigint(20) DEFAULT NULL;
ALTER TABLE video
  ADD CONSTRAINT fk_source_id FOREIGN KEY (source_id) REFERENCES source(id);

