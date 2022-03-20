/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : spring_db

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2022-03-20 08:30:00
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
                           `username` varchar(10) NOT NULL DEFAULT '',
                           `balance` double DEFAULT NULL,
                           PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO account VALUES ('lisi', '1000');
INSERT INTO account VALUES ('zhangsan', '1000');

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
                        `id` int(10) NOT NULL,
                        `book_name` varchar(10) DEFAULT NULL,
                        `price` double DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO book VALUES ('1', '西游记', '100');
INSERT INTO book VALUES ('2', '水浒传', '100');
INSERT INTO book VALUES ('3', '三国演义', '100');
INSERT INTO book VALUES ('4', '红楼梦', '100');

-- ----------------------------
-- Table structure for `book_stock`
-- ----------------------------
DROP TABLE IF EXISTS `book_stock`;
CREATE TABLE `book_stock` (
                              `id` int(255) NOT NULL DEFAULT '0',
                              `stock` int(11) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of book_stock
-- ----------------------------
INSERT INTO book_stock VALUES ('1', '1000');
INSERT INTO book_stock VALUES ('2', '1000');
INSERT INTO book_stock VALUES ('3', '1000');
INSERT INTO book_stock VALUES ('4', '1000');