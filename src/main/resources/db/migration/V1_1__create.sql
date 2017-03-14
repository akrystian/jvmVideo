CREATE TABLE video
(
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    description VARCHAR(255),
    duration TINYBLOB,
    publish_date DATE NOT NULL,
    thumbnail_link VARCHAR(255),
    title VARCHAR(255)
);
