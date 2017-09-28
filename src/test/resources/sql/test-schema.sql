CREATE TABLE spitter (
  id         BIGINT                              NOT NULL IDENTITY,
  name       VARCHAR(120)                        NOT NULL,
  password   VARCHAR(16)                         NOT NULL,
  avatar     VARCHAR(1024),
  created_at TIMESTAMP DEFAULT current_timestamp NOT NULL,
  enabled    BOOLEAN DEFAULT TRUE                NOT NULL,
  UNIQUE (name)
);

CREATE TABLE spittle (
  id             BIGINT                              NOT NULL IDENTITY,
  username       VARCHAR(120)                        NOT NULL,
  text           VARCHAR(1024),
  created_at     TIMESTAMP DEFAULT current_timestamp NOT NULL,
  attach_type    INTEGER,
  attach_content VARCHAR(1024),
  repost         VARCHAR(1024),
  comment        VARCHAR(1024),
  "like"         VARCHAR(1024),
  enabled        BOOLEAN DEFAULT TRUE                NOT NULL
);

CREATE TABLE comment (
  id             BIGINT                              NOT NULL IDENTITY,
  username       VARCHAR(120)                        NOT NULL,
  spittle_id     BIGINT                              NOT NULL,
  commented_id   BIGINT,
  text           VARCHAR(1024)                       NOT NULL,
  attach_type    INTEGER,
  attach_content VARCHAR(1024),
  create_time    TIMESTAMP DEFAULT current_timestamp NOT NULL,
  enabled        BOOLEAN DEFAULT TRUE                NOT NULL
);

ALTER TABLE comment
  ADD CONSTRAINT comment_spittle_id_fk
FOREIGN KEY (spittle_id) REFERENCES spittle (id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE comment
  ADD CONSTRAINT comment_spittle_name_fk
FOREIGN KEY (username) REFERENCES spitter (name)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE spittle
  ADD CONSTRAINT spittle_spitter_name_fk
FOREIGN KEY (username) REFERENCES spitter (name)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE spittle
    DROP comment;
ALTER TABLE spittle
    DROP attach_type;
ALTER TABLE comment
    DROP attach_type;
ALTER TABLE spittle
    DROP attach_content;
ALTER TABLE comment
    DROP attach_content;

ALTER TABLE spittle
  ADD COLUMN attachment VARCHAR(1024) DEFAULT NULL;
ALTER TABLE comment
  ADD attachment VARCHAR(1024) DEFAULT NULL;