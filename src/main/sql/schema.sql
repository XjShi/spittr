-- 数据库初始化脚本
CREATE DATABASE spittr;
USE spittr;

CREATE TABLE spitter (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'spitter id',
  `name` VARCHAR(120) NOT NULL UNIQUE COMMENT 'username',
  `password` VARCHAR(16) NOT NULL COMMENT 'password',
  `avatar` TEXT COMMENT 'avatar',
  `created_at` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT 'create time',
  `enabled` BOOL NOT NULL DEFAULT TRUE COMMENT 'if spitter is enabled',
  PRIMARY KEY (id),
  KEY idx_name(name)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8 COMMENT = 'spitter table';

CREATE TABLE spittle(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'spittle id',
  `username` VARCHAR(120) NOT NULL COMMENT 'username',
  `text` TEXT COMMENT 'text text',
  `created_at` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT 'create time',
  `attach_type` INTEGER COMMENT 'attach type: 0:none, 1:image 2:audio 3:video',
  `attach_content` TEXT COMMENT 'attach url',
  `repost` TEXT COMMENT '转发的用户的用户名',
  `comment` TEXT COMMENT 'comment',
  `like` TEXT COMMENT '点赞的用户的用户名',
  `enabled` BOOL NOT NULL DEFAULT TRUE COMMENT 'if spittle is enabled',
  PRIMARY KEY (id),
  KEY idx_username(username)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8 COMMENT = 'spittle table';

CREATE TABLE comment(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'comment id',
  `username` BIGINT NOT NULL COMMENT 'username',
  `spittle_id` BIGINT NOT NULL COMMENT 'spittle id',
  `commented_id` BIGINT COMMENT 'commented_id',
  `text` TEXT NOT NULL COMMENT 'text text',
  `attach_type` INTEGER COMMENT 'attach type: 0:none, 1:image 2:audio 3:video',
  `attach_content` TEXT COMMENT 'attach url',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT 'create time',
  `enabled` BOOL NOT NULL DEFAULT TRUE COMMENT 'enabled',
  PRIMARY KEY (id),
  KEY idx_user_id(user_id)
) ENGINE = InnoDB DEFAULT CHARSET = UTF8 COMMENT = 'comment table';

ALTER TABLE spittr.comment
ADD CONSTRAINT comment_spittle_id_fk
FOREIGN KEY (spittle_id) REFERENCES spittle (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE spittr.comment
ADD CONSTRAINT comment_spittle_name_fk
FOREIGN KEY (username) REFERENCES spitter (name) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE spittr.spittle
  ADD CONSTRAINT spittle_spitter_name_fk
FOREIGN KEY (username) REFERENCES spitter (name) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE spittr.spittle DROP comment;

ALTER TABLE spittr.spittle DROP attach_type, DROP attach_content;
ALTER TABLE spittr.comment DROP attach_type, DROP attach_content;

CREATE TABLE spittle_attachment(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'spittle attachment id',
  `attach_type` INTEGER COMMENT 'attach type',
  `attach_content` VARCHAR(200) NOT NULL COMMENT 'attach content',
  `spittle_id` BIGINT NOT NULL COMMENT 'spittle id',
  PRIMARY KEY (id),
  FOREIGN KEY (spittle_id) REFERENCES spittle (id) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = UTF8 COMMENT = 'spittle attachment table';

CREATE TABLE comment_attachment(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'comment attachment id',
  `attach_type` INTEGER COMMENT 'attach type',
  `attach_content` VARCHAR(200) NOT NULL COMMENT 'attach content',
  `comment_id` BIGINT NOT NULL COMMENT 'comment id',
  PRIMARY KEY (id),
  FOREIGN KEY (comment_id) REFERENCES comment (id) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = UTF8 COMMENT = 'comment attachment table';