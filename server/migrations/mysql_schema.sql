CREATE DATABASE IF NOT EXISTS `library`;

USE `library`;

CREATE TABLE IF NOT EXISTS `books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `borrow_time` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `return_time` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `user_id` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `create_time` int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `borrows` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `book_id` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `borrow_time` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `return_time` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `create_time` int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `create_time` int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY(`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `users` VALUES('1', 'user1', 'password1', UNIX_TIMESTAMP());
INSERT INTO `users` VALUES('2', 'user2', 'password2', UNIX_TIMESTAMP());
INSERT INTO `users` VALUES('3', 'user3', 'password3', UNIX_TIMESTAMP());

INSERT INTO `books` VALUES(NULL, '红楼梦', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '西游记', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '水浒传', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '三国演义', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '呐喊', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '死水微澜', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '呼兰河传', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '骆驼祥子', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '传奇', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '围城', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '台北人', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '家', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '棋王', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '官场现形记', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '半生缘', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '白鹿原', 0, 0, 0, UNIX_TIMESTAMP());
INSERT INTO `books` VALUES(NULL, '活着', 0, 0, 0, UNIX_TIMESTAMP());

