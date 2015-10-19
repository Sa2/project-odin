SET SESSION FOREIGN_KEY_CHECKS=0;


/* Create Tables */

-- 記事
CREATE TABLE articles
(
	id int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
	title varchar(64) NOT NULL COMMENT '記事タイトル',
	body longtext COMMENT '本文',
	view_count bigint unsigned DEFAULT 0 NOT NULL COMMENT '閲覧数',
	is_hide boolean NOT NULL COMMENT '非表示化',
	posted_user_id int unsigned COMMENT '投稿したユーザーID',
	post_date datetime NOT NULL COMMENT '投稿日',
	update_date timestamp NOT NULL COMMENT '更新日時',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '記事';


-- 接続ステータス確認テーブル
CREATE TABLE db_connection_status
(
	id int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
	health varchar(8) NOT NULL COMMENT 'ヘルス',
	PRIMARY KEY (id),
	UNIQUE (id),
	UNIQUE (health)
) COMMENT = '接続ステータス確認テーブル';


-- 新規テーブル
CREATE TABLE files
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
CREATE TABLE users
(
	id int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
	user_id varchar(32) NOT NULL COMMENT 'ユーザーID',
	password varchar(64) NOT NULL COMMENT 'パスワード',
	password_salt varchar(256) NOT NULL COMMENT 'パスワードソルト',
	name varchar(32) NOT NULL COMMENT '名前',
	is_lock boolean NOT NULL COMMENT 'アカウントロック',
	register_date datetime NOT NULL COMMENT '登録日時',
	update_date timestamp NOT NULL COMMENT '更新日時',
	PRIMARY KEY (id),
	UNIQUE (id),
	UNIQUE (user_id)
) COMMENT = 'ユーザー情報';



/* Create Foreign Keys */

ALTER TABLE files
	ADD FOREIGN KEY (article_id)
	REFERENCES articles (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE articles
	ADD FOREIGN KEY (posted_user_id)
	REFERENCES users (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



