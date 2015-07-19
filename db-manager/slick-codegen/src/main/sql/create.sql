SET SESSION FOREIGN_KEY_CHECKS=0;


/* Create Tables */

-- 記事
CREATE TABLE article
(
	id int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
	title varchar(64) NOT NULL COMMENT '記事タイトル',
	body longtext COMMENT '本文',
	is_hide boolean NOT NULL COMMENT '非表示化',
	posted_user_id int unsigned COMMENT '投稿したユーザーID',
	post_date datetime NOT NULL COMMENT '投稿日',
	update_date timestamp NOT NULL COMMENT '更新日時',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '記事';


-- 新規テーブル
CREATE TABLE file
(
	id int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ファイルID',
	name varchar(256) NOT NULL COMMENT 'ファイル名',
	file_blob longblob NOT NULL COMMENT 'ファイルデータ',
	article_id int unsigned NOT NULL COMMENT '記事ID',
	upload_date timestamp NOT NULL COMMENT 'アップロード日時',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '新規テーブル';


-- ユーザー情報
CREATE TABLE user
(
	id int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
	user_id varchar(32) NOT NULL COMMENT 'ユーザーID',
	password varchar(256) NOT NULL COMMENT 'パスワード',
	name varchar(32) NOT NULL COMMENT '名前',
	is_lock boolean NOT NULL COMMENT 'アカウントロック',
	register_date datetime NOT NULL COMMENT '登録日時',
	update_date timestamp NOT NULL COMMENT '更新日時',
	PRIMARY KEY (id),
	UNIQUE (id),
	UNIQUE (user_id)
) COMMENT = 'ユーザー情報';



/* Create Foreign Keys */

ALTER TABLE file
	ADD FOREIGN KEY (article_id)
	REFERENCES article (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE article
	ADD FOREIGN KEY (posted_user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


