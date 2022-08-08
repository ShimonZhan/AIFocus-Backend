/*
 Navicat Premium Data Transfer

 Source Server         : swj
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : swj.zhanxm.cn:32768
 Source Schema         : aifocus

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 16/06/2020 18:01:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer_sheet
-- ----------------------------
DROP TABLE IF EXISTS `answer_sheet`;
CREATE TABLE `answer_sheet`
(
    `id`            varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '表id',
    `exam_id`       varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '考试id',
    `student_id`    varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '学生id',
    `student_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '学生姓名',
    `exam_paper_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '试卷中题库id',
    `question_type` tinyint(0)                                               NULL DEFAULT NULL COMMENT '题目类型',
    `answer`        varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '答案',
    `score`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '分数',
    `is_marked`     bit(1)                                                   NULL DEFAULT NULL COMMENT '是否已批改',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `student_id` (`student_id`) USING BTREE,
    INDEX `exam_id` (`exam_id`, `student_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '答题卡表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answer_sheet
-- ----------------------------
INSERT INTO `answer_sheet`
VALUES ('03cd535cdfcaf5bcdd7692aa3c80d76c', '9da1224196e7c3944cbf2eda8db49957', 'd2eaeba7c1a2a967fe749be6b998458b',
        'testStudent', 'c0ccbe710242061b4affaba872a3156e', 3, '0', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('078b23634b8b204da5caa0cb9c67ad38', '873715982b213990ff91790f411c2930', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', 'f4ae35ffdc687ffbb8a5cc4f4fd249b2', 0, '1', '2344', b'1');
INSERT INTO `answer_sheet`
VALUES ('1265dd91a869bc647c32d2c55179e19f', '142ede91a9892a0f1f4640b2adb69bd1', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', '3bcc8b8a151cc59db89d088900431da5', 2, 'aaaaaaaaaaaaaaaaaaaaa', '4', b'1');
INSERT INTO `answer_sheet`
VALUES ('136c37458c15279f2ee1de4e03cd5a04', '142ede91a9892a0f1f4640b2adb69bd1', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', '973cbd900db7dfb3cd49b3aebc4cb156', 3, '1', '3', b'1');
INSERT INTO `answer_sheet`
VALUES ('3cd443806f3fa1ad06701743d17fe1f6', '9da1224196e7c3944cbf2eda8db49957', 'd2eaeba7c1a2a967fe749be6b998458b',
        'testStudent', '2c9178ca95705a4a2cca538fbc0c162b', 4, 'dddd', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('44c9ac5455c4a80f0a3f1cfc8f00bfa3', '83c4c5834f659a4ccb2ee198ac455599', 'd2eaeba7c1a2a967fe749be6b998458b',
        'testStudent', '9f8cd92c1a0965cb15327ebe1ed0ffa8', 0, '2', '0', b'1');
INSERT INTO `answer_sheet`
VALUES ('4a91ebc9b77c91f59c82055a764bf3da', 'dd01d1c4c9215fbb8962720cef0b9060', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', '697a2cad068c288ac7474709ac16c250', 0, '3', '5', b'1');
INSERT INTO `answer_sheet`
VALUES ('4be41940ec1d656db788b5a1eb3a665e', '83c4c5834f659a4ccb2ee198ac455599', 'd2eaeba7c1a2a967fe749be6b998458b',
        'testStudent', '77f1ec7b223d86edc85f6633d52021ac', 2, 'sdvfasdf', '0', b'1');
INSERT INTO `answer_sheet`
VALUES ('4e508872db742659a994aab274cd9261', '142ede91a9892a0f1f4640b2adb69bd1', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', '3bcc8b8a151cc59db89d088900431da5', 2, 'aaaaaaaaaaaaaaaaaaaaa', '4', b'1');
INSERT INTO `answer_sheet`
VALUES ('5114b0de09715b51902636f5eed5fb50', '83c4c5834f659a4ccb2ee198ac455599', 'd2eaeba7c1a2a967fe749be6b998458b',
        'testStudent', '8bc4368fe634f6710d0fa7e0bf22637d', 1, '2', '0', b'1');
INSERT INTO `answer_sheet`
VALUES ('5859c462ed7e714e512674e94a844741', '873715982b213990ff91790f411c2930', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', 'a6b6076408ec5fd27aebb416bd864735', 0, '2', '123', b'1');
INSERT INTO `answer_sheet`
VALUES ('5a5666cdd1a40003ae0cddeb899dbf5c', '142ede91a9892a0f1f4640b2adb69bd1', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', 'e91d980d5189b1c9888b94eff6626a4a', 1, '2', '0', b'1');
INSERT INTO `answer_sheet`
VALUES ('6a00e4572b0dc5afb370bfdb49e8bd3e', '142ede91a9892a0f1f4640b2adb69bd1', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', 'e91d980d5189b1c9888b94eff6626a4a', 1, '2', '0', b'1');
INSERT INTO `answer_sheet`
VALUES ('703ecdc3daee2181ef81426325cc693a', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', '2c90a934b53f95fbc37588718d0f156f', 3, '1', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('8069f5cf7da590f19c3aed2e3275f591', '873715982b213990ff91790f411c2930', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', 'f4ae35ffdc687ffbb8a5cc4f4fd249b2', 0, '1', '2344', b'1');
INSERT INTO `answer_sheet`
VALUES ('878e1ef62bcefaa765459dc1bb07a56f', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', 'a8c6e4b71f9db93e402e6fd9a3d9cb53', 1, '5', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('8e73b75960eecb507a610d263ce04179', 'dd01d1c4c9215fbb8962720cef0b9060', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', '26cd6efacc2241946cacc3d7c882792c', 0, '1', '10', b'1');
INSERT INTO `answer_sheet`
VALUES ('9ac45f1fa046fd40e32f09c6677a3a24', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', 'cdba46703936db496fe522d25011fceb', 0, '3', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('9d686acbd2752aabda953bd58e340895', '6886443ee43e780b5f97369159eeccbc', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', '256f5d43f55c34f5db016967739f571f', 1, '1', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('9e950ce71aea3a2adf940a3bb1539c5f', 'dd01d1c4c9215fbb8962720cef0b9060', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', '26cd6efacc2241946cacc3d7c882792c', 0, '1', '10', b'1');
INSERT INTO `answer_sheet`
VALUES ('9f704eaa0fb3578fa48b94dfabfe77f3', '9da1224196e7c3944cbf2eda8db49957', 'd2eaeba7c1a2a967fe749be6b998458b',
        'testStudent', '9f4d84b729d66631617dd0ae4201d914', 2, 'dddd', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('a0cd21209162997dba1a63b6c23ee508', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', 'a8c6e4b71f9db93e402e6fd9a3d9cb53', 1, '4', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('a45610f4c3da7fed58ebb940459449c0', 'dd01d1c4c9215fbb8962720cef0b9060', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', '9c502a9db6a981d2b53cc4d1fae63163', 0, '2', '8', b'1');
INSERT INTO `answer_sheet`
VALUES ('a81a22367d0af060386e357958d05596', '83c4c5834f659a4ccb2ee198ac455599', 'd2eaeba7c1a2a967fe749be6b998458b',
        'testStudent', '6035b5242ca25432c46512f9e489c346', 3, '0', '0', b'1');
INSERT INTO `answer_sheet`
VALUES ('aa8999682374879130a252a058fde2ed', 'dd01d1c4c9215fbb8962720cef0b9060', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', '9c502a9db6a981d2b53cc4d1fae63163', 0, '0', '0', b'1');
INSERT INTO `answer_sheet`
VALUES ('bf8b054e911f5e31cf5aadb2d4dc3c21', 'dd01d1c4c9215fbb8962720cef0b9060', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', '697a2cad068c288ac7474709ac16c250', 0, '2', '0', b'1');
INSERT INTO `answer_sheet`
VALUES ('cb986548681d54527b891c534fae26d9', '873715982b213990ff91790f411c2930', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', 'a6b6076408ec5fd27aebb416bd864735', 0, '0', '0', b'1');
INSERT INTO `answer_sheet`
VALUES ('cbca053fa757099d39945888ba455a5d', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', 'cdba46703936db496fe522d25011fceb', 0, '3', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('cbec5f265833f057f699c2b0f77384e3', '142ede91a9892a0f1f4640b2adb69bd1', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', 'ee5a1ac8e8702e95a38327480cfb09db', 0, '1', '3', b'1');
INSERT INTO `answer_sheet`
VALUES ('d181c2321da59fa5a97dfdf1663cf98d', '142ede91a9892a0f1f4640b2adb69bd1', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', '973cbd900db7dfb3cd49b3aebc4cb156', 3, '1', '3', b'1');
INSERT INTO `answer_sheet`
VALUES ('df5d30771adf9c5f211f050c0dddc5b9', '83c4c5834f659a4ccb2ee198ac455599', 'd2eaeba7c1a2a967fe749be6b998458b',
        'testStudent', 'f9d040152e9870e503c63cd1642b2ef8', 4, 'asdfsdfas', '10', b'1');
INSERT INTO `answer_sheet`
VALUES ('df8287b7dabbdf339320997ce0a86ea4', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', '2c90a934b53f95fbc37588718d0f156f', 3, '1', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('e25038f5edc2127ee7a7c5c951a773ef', '6886443ee43e780b5f97369159eeccbc', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', '14d70ff5f4854df49859ab6db0d00a91', 3, '1', '2', b'0');
INSERT INTO `answer_sheet`
VALUES ('e89c89eeb894453ee93f0c95e7e41a02', '6886443ee43e780b5f97369159eeccbc', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', '256f5d43f55c34f5db016967739f571f', 1, '0', '7', b'0');
INSERT INTO `answer_sheet`
VALUES ('eeed34a0453b526586a902459aa350bb', '6886443ee43e780b5f97369159eeccbc', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', '14d70ff5f4854df49859ab6db0d00a91', 3, '1', '2', b'0');
INSERT INTO `answer_sheet`
VALUES ('ef15b6c433bfe8718fccbb2186be48be', '142ede91a9892a0f1f4640b2adb69bd1', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', 'ecd13e337aa9bfad7d71a0bdc93e94c8', 4, '这是手动批改的内容1', '8', b'1');
INSERT INTO `answer_sheet`
VALUES ('f3227e06c6284bbfed90667155b36041', '9da1224196e7c3944cbf2eda8db49957', 'd2eaeba7c1a2a967fe749be6b998458b',
        'testStudent', '6a193c62b1193954d5f1d734db5f40e5', 1, '3', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('f33a2b13cf9422c0010a33efbbd79a4a', '9da1224196e7c3944cbf2eda8db49957', 'd2eaeba7c1a2a967fe749be6b998458b',
        'testStudent', 'cbac05bf7518555b3f8754013adf3811', 0, '3', '0', b'0');
INSERT INTO `answer_sheet`
VALUES ('fa380d5baf2a7c31c946887aaa86b152', '142ede91a9892a0f1f4640b2adb69bd1', '3239b29e922c20ae2f65ccd10a83a7d3',
        '小明', 'ecd13e337aa9bfad7d71a0bdc93e94c8', 4, '这是手动批改的内容', '9', b'1');
INSERT INTO `answer_sheet`
VALUES ('ff5a27e3fab7d3aa06249ce89053a072', '142ede91a9892a0f1f4640b2adb69bd1', '25d26c4eb4c29d57c9948bf5af20dc3f',
        '小天', 'ee5a1ac8e8702e95a38327480cfb09db', 0, '1', '3', b'1');

-- ----------------------------
-- Table structure for cheat
-- ----------------------------
DROP TABLE IF EXISTS `cheat`;
CREATE TABLE `cheat`
(
    `id`            varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '表id',
    `student_id`    varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '学生id',
    `exam_id`       varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '考试id',
    `monitor_photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '监控照片URL',
    `time_stamp`    datetime(0)                                             NOT NULL COMMENT '时间戳',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `student_exam` (`student_id`, `exam_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '可疑行为记录表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cheat
-- ----------------------------
INSERT INTO `cheat`
VALUES ('1a72eecc1be9feab0d2c6ec34047ac5b', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-16 15:53:48.png',
        '2020-06-16 15:53:48');
INSERT INTO `cheat`
VALUES ('1bd728c219429228d222c12a7732c1bb', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-12 23:52:18.png',
        '2020-06-12 23:52:18');
INSERT INTO `cheat`
VALUES ('1f44b1fff7e6007fe72244fe9d03f0d5', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-16 15:53:35.png',
        '2020-06-16 15:53:35');
INSERT INTO `cheat`
VALUES ('2690fb9da79e07f2cd7c86afe139e285', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-12 23:52:12.png',
        '2020-06-12 23:52:12');
INSERT INTO `cheat`
VALUES ('2fb162af1bb83bde5fec49fcecd622c8', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-16 15:53:33.png',
        '2020-06-16 15:53:33');
INSERT INTO `cheat`
VALUES ('3f9cdf2416977a8da56fabb86da59bb9', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-16 15:53:53.png',
        '2020-06-16 15:53:53');
INSERT INTO `cheat`
VALUES ('4ff3c1e2f119fc7c2cad78e41de41e43', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-16 15:53:40.png',
        '2020-06-16 15:53:40');
INSERT INTO `cheat`
VALUES ('566cd9ea82d380a2a6aca01cdd54daf2', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-16 15:53:51.png',
        '2020-06-16 15:53:51');
INSERT INTO `cheat`
VALUES ('59907b7332ce0916a8030c265db39735', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-16 15:53:43.png',
        '2020-06-16 15:53:43');
INSERT INTO `cheat`
VALUES ('8d85b5373f5c85d60ab52c573550ac37', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-16 15:53:42.png',
        '2020-06-16 15:53:42');
INSERT INTO `cheat`
VALUES ('8da6b6634292ef5852bf7e176f2bd2ec', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-16 15:53:37.png',
        '2020-06-16 15:53:37');
INSERT INTO `cheat`
VALUES ('c5e8fa2d90ed1e6c85e8ed56a422ae4e', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-16 15:53:46.png',
        '2020-06-16 15:53:46');
INSERT INTO `cheat`
VALUES ('d6abf51d9479a4fe4f6eb19bfd0353f3', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957',
        '/CheatEvidence/Face/9da1224196e7c3944cbf2eda8db49957/d2eaeba7c1a2a967fe749be6b998458b/2020-06-12 23:52:20.png',
        '2020-06-12 23:52:20');

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`
(
    `id`                      varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '班级id',
    `class_name`              varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班级名',
    `course_name`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名',
    `avater`                  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像URL',
    `invite_code`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邀请码',
    `invite_code_expire_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '邀请码过期时间',
    `is_visible`              bit(1)                                                  NOT NULL COMMENT '是否搜索可见',
    `create_time`             datetime(0)                                             NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `invite_code` (`invite_code`) USING BTREE,
    INDEX `class_name` (`class_name`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '班级表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class`
VALUES ('090cc0350610261cc8ac7289812e2e5d', '厚大法考10', '厚大10', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        '123456', '2020-05-26 17:46:48', b'1', '2020-04-26 20:19:12');
INSERT INTO `class`
VALUES ('0b495598e2cb0285f74233e94beeaa92', '罗翔老师的0612测试课堂', '0612测试',
        'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png', 'X9JL6G', '2020-06-19 15:48:29', b'1',
        '2020-06-12 15:48:29');
INSERT INTO `class`
VALUES ('248accef36f8da9776fdcd5876e55f16', '班级1', '课程1', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        'J8SZ7N', '2020-05-11 17:44:20', b'1', '2020-05-04 17:44:20');
INSERT INTO `class`
VALUES ('326e976db47cc5e91a928263057c07d7', '罗翔老师的test1课堂', 'test1',
        'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png', 'AACGB', '2020-05-09 17:38:51', b'1',
        '2020-05-02 17:38:51');
INSERT INTO `class`
VALUES ('3784cd5ef963537e3be222fb0c347332', '123', '', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png', 'ZCI9DJ',
        '2020-06-15 21:58:49', b'1', '2020-06-08 21:58:49');
INSERT INTO `class`
VALUES ('5c8bbf46108b9e85b050fcd4167991a6', 'testclass', 'testcourse',
        'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png', 'UMNBG', '2020-05-09 17:26:18', b'1',
        '2020-05-02 17:26:18');
INSERT INTO `class`
VALUES ('64fba23bc0b8ec8e692d1fbb6711ca03', '厚大法考4', '厚大4', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        '123DYY', '2020-05-09 23:46:29', b'1', '2020-04-26 20:18:31');
INSERT INTO `class`
VALUES ('795373e3c1a4ecc24176db8c754f295e', '厚大法考5', '厚大5', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        '12GG82', '2020-05-03 20:18:48', b'1', '2020-04-26 20:18:48');
INSERT INTO `class`
VALUES ('884fe0d497e5e85359ac63d56c271430', 'testclass1', 'testcourse1',
        'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png', '121322', '2020-05-09 23:31:34', b'1',
        '2020-05-02 23:31:34');
INSERT INTO `class`
VALUES ('91a98191a1d6495d6336c7ec14914d01', '厚大法考3', '厚大3', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        'OPA233', '2020-05-03 20:18:23', b'1', '2020-04-26 20:18:23');
INSERT INTO `class`
VALUES ('a0a5b32a133c28c727c36404cc318d40', '罗翔老师的123课堂', '123', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        'QWSI2K', '2020-06-15 23:09:07', b'1', '2020-06-08 23:09:07');
INSERT INTO `class`
VALUES ('ab7ad1f4d4c4f8dfc3ee7962c5b6cd56', '厚大法考9', '厚大9', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        'ACIAMK', '2020-05-03 20:19:07', b'1', '2020-04-26 20:19:07');
INSERT INTO `class`
VALUES ('b12258323daba9e70430bf899a6db703', '班级2', '课程2', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        'A23B42', '2020-05-11 17:51:50', b'1', '2020-05-04 17:48:35');
INSERT INTO `class`
VALUES ('b488edfbdd603df1e610b81f04c190e9', '仙女老师的阿万艾斯亿神奇魔法课堂', '阿万艾斯亿神奇魔法',
        'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png', '0KJKI0', '2020-05-10 20:52:53', b'1',
        '2020-05-03 20:52:53');
INSERT INTO `class`
VALUES ('b96f4987d26854440eeec6e9e0c6e0d9', '罗翔老师的0522课堂', '0522',
        'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png', 'SH8CIU', '2020-05-29 19:43:08', b'1',
        '2020-05-22 19:43:08');
INSERT INTO `class`
VALUES ('c022172dfde19732ffa02bd9780824d6', '厚大法考8', '厚大8', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        'AOI097', '2020-05-03 20:19:02', b'1', '2020-04-26 20:19:02');
INSERT INTO `class`
VALUES ('c4fa7df8567cf6c883d8feee48899806', 'test0616', 'testcourse0616',
        'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png', '666666', '2020-06-23 01:13:43', b'1',
        '2020-06-16 01:13:43');
INSERT INTO `class`
VALUES ('cb913ed69da22a9d5a03574010e6bcda', '罗翔老师的测试课堂', '测试', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        'QWER52', '2020-05-09 17:35:34', b'1', '2020-05-02 17:35:34');
INSERT INTO `class`
VALUES ('d48a7121de9f4544fbfdf3bf123975cd', '厚大法考6', '厚大6', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        'AIOKL9', '2020-05-03 20:18:52', b'1', '2020-04-26 20:18:52');
INSERT INTO `class`
VALUES ('e6ef770b08aa9cacad47f8d08284641c', '厚大法考2', '厚大2', 'https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png',
        '765MNV', '2020-05-03 20:18:04', b'1', '2020-04-26 20:18:04');

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`
(
    `id`                        varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '考试id',
    `name`                      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考试名',
    `teacher_id`                varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '老师id',
    `class_id`                  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '班级id',
    `course_name`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班级名',
    `full_score`                int(0)                                                  NULL DEFAULT NULL COMMENT '考试总分',
    `create_time`               datetime(0)                                             NOT NULL COMMENT '创建时间',
    `start_time`                datetime(0)                                             NOT NULL COMMENT '开始时间',
    `finish_time`               datetime(0)                                             NOT NULL COMMENT '结束时间',
    `is_auto_mark_message_send` bit(1)                                                  NULL DEFAULT NULL COMMENT '是否已发送自动批改的消息',
    `status`                    tinyint(0)                                              NULL DEFAULT NULL COMMENT '考试状态',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `class_id` (`class_id`) USING BTREE,
    INDEX `is_auto_marked` (`is_auto_mark_message_send`, `finish_time`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '考试表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam`
VALUES ('142ede91a9892a0f1f4640b2adb69bd1', '测试考试1', '2cc865e31e22a3fe185e1645bf04fd69',
        '64fba23bc0b8ec8e692d1fbb6711ca03', '厚大4', 28, '2020-05-05 18:39:01', '2020-05-03 20:18:48',
        '2020-05-04 20:18:48', b'1', 4);
INSERT INTO `exam`
VALUES ('1524c96d40c49aadb4e393bc20de0638', '123', '2cc865e31e22a3fe185e1645bf04fd69',
        '3784cd5ef963537e3be222fb0c347332', '', 0, '2020-06-08 22:52:20', '2020-06-08 12:00:00', '2020-06-08 10:53:00',
        b'1', 3);
INSERT INTO `exam`
VALUES ('4c432c45e83ba1f590830d24ce69ba2e', '测试考试123', '2cc865e31e22a3fe185e1645bf04fd69',
        '090cc0350610261cc8ac7289812e2e5d', '厚大10', 50, '2020-05-19 22:09:19', '2020-05-19 12:00:00',
        '2020-05-29 01:25:00', b'1', 3);
INSERT INTO `exam`
VALUES ('51fab6e0cc1ed40584d55e86297d66b5', '测试1', '2cc865e31e22a3fe185e1645bf04fd69',
        '090cc0350610261cc8ac7289812e2e5d', '厚大10', 12, '2020-05-06 13:58:14', '2020-05-06 01:05:00',
        '2020-05-07 01:05:00', b'0', 0);
INSERT INTO `exam`
VALUES ('6886443ee43e780b5f97369159eeccbc', '刑法考1', '2cc865e31e22a3fe185e1645bf04fd69',
        '64fba23bc0b8ec8e692d1fbb6711ca03', '厚大4', 9, '2020-05-04 14:10:53', '2020-05-05 14:09:00',
        '2020-05-05 16:09:00', b'1', 5);
INSERT INTO `exam`
VALUES ('6c094da58baad8315d20af4c093b4836', '7777', '2cc865e31e22a3fe185e1645bf04fd69',
        '090cc0350610261cc8ac7289812e2e5d', '厚大10', 0, '2020-05-19 22:28:34', '2020-05-20 02:10:00',
        '2020-05-21 12:00:00', b'1', 3);
INSERT INTO `exam`
VALUES ('83c4c5834f659a4ccb2ee198ac455599', '测试考试0612_2', '2cc865e31e22a3fe185e1645bf04fd69',
        '0b495598e2cb0285f74233e94beeaa92', '罗翔老师的0612测试课堂', 50, '2020-06-12 17:01:38', '2020-06-12 17:10:00',
        '2020-06-12 17:20:00', b'1', 5);
INSERT INTO `exam`
VALUES ('84e254610196caaf2937a03bcc41d747', 'test3', '2cc865e31e22a3fe185e1645bf04fd69',
        '64fba23bc0b8ec8e692d1fbb6711ca03', '厚大4', 712, '2020-05-31 14:49:42', '2020-05-31 12:00:00',
        '2020-05-31 04:00:00', b'1', 3);
INSERT INTO `exam`
VALUES ('8612aa1079bd3750791c423aac4f49b2', '12345', '2cc865e31e22a3fe185e1645bf04fd69',
        'b96f4987d26854440eeec6e9e0c6e0d9', '0522', 0, '2020-06-02 00:35:33', '2020-06-02 12:00:00',
        '2020-06-02 01:05:00', b'1', 3);
INSERT INTO `exam`
VALUES ('873715982b213990ff91790f411c2930', 'test4', '2cc865e31e22a3fe185e1645bf04fd69',
        '64fba23bc0b8ec8e692d1fbb6711ca03', '厚大4', 2467, '2020-05-31 14:57:45', '2020-05-30 12:00:00',
        '2020-05-31 15:33:00', b'1', 5);
INSERT INTO `exam`
VALUES ('8beef682e165dd4344d5f1cdf204acb3', '123', '2cc865e31e22a3fe185e1645bf04fd69',
        '64fba23bc0b8ec8e692d1fbb6711ca03', '厚大4', 1, '2020-06-08 23:50:33', '2020-03-02 12:00:00',
        '2020-06-10 09:45:00', b'0', 0);
INSERT INTO `exam`
VALUES ('8cc65c25619d66315a6017f5f1d838d4', '123456', '2cc865e31e22a3fe185e1645bf04fd69',
        '090cc0350610261cc8ac7289812e2e5d', '厚大10', 123, '2020-05-19 22:20:57', '2020-05-20 12:00:00',
        '2020-05-21 12:00:00', b'0', 0);
INSERT INTO `exam`
VALUES ('8f9aeb8b7897109dab3647dc81a3ac42', '测试考试0612', '2cc865e31e22a3fe185e1645bf04fd69',
        '0b495598e2cb0285f74233e94beeaa92', '罗翔老师的0612测试课堂', 0, '2020-06-12 16:11:30', '2020-06-12 16:20:00',
        '2020-06-12 16:30:00', b'1', 3);
INSERT INTO `exam`
VALUES ('9b6fc0e035218905851480b7aefc8997', '考试测试1', '2cc865e31e22a3fe185e1645bf04fd69',
        '090cc0350610261cc8ac7289812e2e5d', '厚大10', 0, '2020-05-19 22:03:33', '2020-05-19 12:00:00',
        '2020-05-20 12:00:00', b'1', 3);
INSERT INTO `exam`
VALUES ('9da1224196e7c3944cbf2eda8db49957', '测试考试0521', '2cc865e31e22a3fe185e1645bf04fd69',
        '090cc0350610261cc8ac7289812e2e5d', '厚大10', 5, '2020-05-21 23:46:58', '2020-05-21 12:00:00',
        '2020-06-21 12:00:00', b'0', 2);
INSERT INTO `exam`
VALUES ('a13c800d7265f7af8f606bf90d2b10c0', '1234', '2cc865e31e22a3fe185e1645bf04fd69',
        'b96f4987d26854440eeec6e9e0c6e0d9', '0522', 0, '2020-06-02 00:33:39', '2020-06-02 12:00:00',
        '2020-06-03 12:00:00', b'1', 3);
INSERT INTO `exam`
VALUES ('a2b2510507072874b478d99bf65b21e8', '测试考试10', '2cc865e31e22a3fe185e1645bf04fd69',
        '090cc0350610261cc8ac7289812e2e5d', '厚大10', 0, '2020-05-19 22:05:37', '2020-05-19 12:00:00',
        '2020-05-20 12:00:00', b'0', 0);
INSERT INTO `exam`
VALUES ('a9b0bcae2f08f7fca993517df097bb8e', '1234', '2cc865e31e22a3fe185e1645bf04fd69',
        '3784cd5ef963537e3be222fb0c347332', '', 0, '2020-06-08 23:48:59', '2020-06-08 12:59:00', '2020-06-08 12:00:00',
        b'0', 0);
INSERT INTO `exam`
VALUES ('bfd1594e420dfe40ae6e377c6a637446', '123', '2cc865e31e22a3fe185e1645bf04fd69',
        'b96f4987d26854440eeec6e9e0c6e0d9', '0522', 0, '2020-06-02 00:32:36', '2020-06-03 12:00:00',
        '2020-06-04 12:00:00', b'1', 3);
INSERT INTO `exam`
VALUES ('d0c7ba300112c771cd1a0d8089264761', '123', '2cc865e31e22a3fe185e1645bf04fd69',
        '3784cd5ef963537e3be222fb0c347332', '', 1233455353, '2020-06-08 22:38:24', '2020-05-01 12:20:00',
        '2020-02-01 12:00:00', b'1', 3);
INSERT INTO `exam`
VALUES ('dd01d1c4c9215fbb8962720cef0b9060', 'test5', '2cc865e31e22a3fe185e1645bf04fd69',
        '64fba23bc0b8ec8e692d1fbb6711ca03', '厚大4', 23, '2020-05-31 18:45:03', '2020-05-31 12:00:00',
        '2020-05-31 15:00:00', b'1', 4);
INSERT INTO `exam`
VALUES ('fc6a26b7c68ce9eb3fd8736aa24ffa37', '测试2', '2cc865e31e22a3fe185e1645bf04fd69',
        '64fba23bc0b8ec8e692d1fbb6711ca03', '厚大4', 126, '2020-05-31 13:58:23', '2020-05-30 01:00:00',
        '2020-05-31 14:27:00', b'1', 3);

-- ----------------------------
-- Table structure for exam_mark_record
-- ----------------------------
DROP TABLE IF EXISTS `exam_mark_record`;
CREATE TABLE `exam_mark_record`
(
    `id`               varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表id',
    `student_id`       varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生id',
    `exam_id`          varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考试id',
    `blank_score`      int(0)                                                 NULL DEFAULT NULL COMMENT '填空题总分',
    `judge_score`      int(0)                                                 NULL DEFAULT NULL COMMENT '判断题总分',
    `multiple_score`   int(0)                                                 NULL DEFAULT NULL COMMENT '多选题总分',
    `single_score`     int(0)                                                 NULL DEFAULT NULL COMMENT '单选题总分',
    `subjective_score` int(0)                                                 NULL DEFAULT NULL COMMENT '主观题总分',
    `total_score`      int(0)                                                 NULL DEFAULT NULL COMMENT '总分',
    `is_marked`        bit(1)                                                 NULL DEFAULT NULL COMMENT '是否已批改',
    `is_submit`        bit(1)                                                 NULL DEFAULT NULL COMMENT '是否提交',
    `state`            int(0)                                                 NULL DEFAULT NULL COMMENT '作弊状态',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `exam_id` (`exam_id`, `student_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '学生考试批改记录表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_mark_record
-- ----------------------------
INSERT INTO `exam_mark_record`
VALUES ('09336019af00417504b935b4ecac3d5d', '25d26c4eb4c29d57c9948bf5af20dc3f', '142ede91a9892a0f1f4640b2adb69bd1', 4,
        3, 0, 3, 8, 18, b'1', b'1', 0);
INSERT INTO `exam_mark_record`
VALUES ('0b4810e590d03b34599d5828355ae2f2', 'd2eaeba7c1a2a967fe749be6b998458b', '8f9aeb8b7897109dab3647dc81a3ac42', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('0e2383773bd1c0f613c76c3bae9d9728', '3239b29e922c20ae2f65ccd10a83a7d3', '873715982b213990ff91790f411c2930', 0,
        0, 0, 2344, 0, 2344, b'1', b'1', 0);
INSERT INTO `exam_mark_record`
VALUES ('1dd971599218cec9fa62c311b9fe431e', '3239b29e922c20ae2f65ccd10a83a7d3', '8beef682e165dd4344d5f1cdf204acb3', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('1e7c28ac2258b6f2e08b83189e802a63', 'd2eaeba7c1a2a967fe749be6b998458b', '1524c96d40c49aadb4e393bc20de0638', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('27627bf82cd51378bbfff36b1043ab8d', 'd2eaeba7c1a2a967fe749be6b998458b', 'd0c7ba300112c771cd1a0d8089264761', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('32db8752264d0d25df6de63a9b08b917', '25d26c4eb4c29d57c9948bf5af20dc3f', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', 0,
        0, 0, 0, 0, 0, b'1', b'1', 0);
INSERT INTO `exam_mark_record`
VALUES ('39ecf18cf35407ae0e248ead011b74b0', '25d26c4eb4c29d57c9948bf5af20dc3f', '873715982b213990ff91790f411c2930', 0,
        0, 0, 2467, 0, 2467, b'1', b'1', 0);
INSERT INTO `exam_mark_record`
VALUES ('417a682270f395ddca0e060d1260ec88', '3239b29e922c20ae2f65ccd10a83a7d3', 'dd01d1c4c9215fbb8962720cef0b9060', 0,
        0, 0, 18, 0, 18, b'1', b'1', 0);
INSERT INTO `exam_mark_record`
VALUES ('4358590dea9fe19475f0c9fc975d8445', 'd2eaeba7c1a2a967fe749be6b998458b', '8cc65c25619d66315a6017f5f1d838d4', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('530a2af828ea554c45418ca92a4b128c', 'd2eaeba7c1a2a967fe749be6b998458b', '9da1224196e7c3944cbf2eda8db49957', 0,
        0, 0, 0, 0, 0, b'0', b'1', 2);
INSERT INTO `exam_mark_record`
VALUES ('6e8698631c47a8f308c34c2500e10e7b', '3239b29e922c20ae2f65ccd10a83a7d3', '142ede91a9892a0f1f4640b2adb69bd1', 4,
        3, 0, 3, 9, 19, b'1', b'1', 0);
INSERT INTO `exam_mark_record`
VALUES ('7f25eab44c8ff9a6d8e73b2313212ebc', '25d26c4eb4c29d57c9948bf5af20dc3f', '8beef682e165dd4344d5f1cdf204acb3', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('84146428298d6367cb227837b3c41924', '3239b29e922c20ae2f65ccd10a83a7d3', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', 0,
        0, 0, 0, 0, 0, b'1', b'1', 0);
INSERT INTO `exam_mark_record`
VALUES ('998c90641ef75d9c7c08713616d9b937', '25d26c4eb4c29d57c9948bf5af20dc3f', '84e254610196caaf2937a03bcc41d747', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('b9fbd3c45160cf19d82c996443bee013', '25d26c4eb4c29d57c9948bf5af20dc3f', '6886443ee43e780b5f97369159eeccbc', 0,
        2, 7, 0, 0, 9, b'1', b'1', 0);
INSERT INTO `exam_mark_record`
VALUES ('be7fc4e11af349d1964a3525aa624732', 'd2eaeba7c1a2a967fe749be6b998458b', '6c094da58baad8315d20af4c093b4836', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('c428bb144b84a633a3dbb2bba2e36a60', 'd2eaeba7c1a2a967fe749be6b998458b', '83c4c5834f659a4ccb2ee198ac455599', 0,
        0, 0, 0, 10, 10, b'1', b'1', 0);
INSERT INTO `exam_mark_record`
VALUES ('c9e3e9b01edeca975dac85671281e5fe', 'd2eaeba7c1a2a967fe749be6b998458b', 'a2b2510507072874b478d99bf65b21e8', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('d507f220f599a25cef79f88abf93c9fa', '3239b29e922c20ae2f65ccd10a83a7d3', '6886443ee43e780b5f97369159eeccbc', 0,
        2, 0, 0, 0, 2, b'1', b'1', 0);
INSERT INTO `exam_mark_record`
VALUES ('d6e70abdd3ca57465ea931da5d923dbb', '3239b29e922c20ae2f65ccd10a83a7d3', '84e254610196caaf2937a03bcc41d747', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('de8f60623311c08e4a3f1826ba6eecea', 'd2eaeba7c1a2a967fe749be6b998458b', '4c432c45e83ba1f590830d24ce69ba2e', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('eb237136988bd7e53c28bea8e06a1926', 'd2eaeba7c1a2a967fe749be6b998458b', '9b6fc0e035218905851480b7aefc8997', 0,
        0, 0, 0, 0, 0, b'0', b'0', 0);
INSERT INTO `exam_mark_record`
VALUES ('f19849eb71bb3581e12ba12e222ef0df', '25d26c4eb4c29d57c9948bf5af20dc3f', 'dd01d1c4c9215fbb8962720cef0b9060', 0,
        0, 0, 15, 0, 15, b'1', b'1', 0);

-- ----------------------------
-- Table structure for exam_mark_summary
-- ----------------------------
DROP TABLE IF EXISTS `exam_mark_summary`;
CREATE TABLE `exam_mark_summary`
(
    `id`                   varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表id',
    `exam_id`              varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考试id',
    `total_student_number` int(0)                                                 NULL DEFAULT NULL COMMENT '学生总人数',
    `mark_student_number`  int(0)                                                 NULL DEFAULT NULL COMMENT '已批改的学生数',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `exam_id` (`exam_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '考试批改表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_mark_summary
-- ----------------------------
INSERT INTO `exam_mark_summary`
VALUES ('07d11745639752fd240382f6d419d203', 'dd01d1c4c9215fbb8962720cef0b9060', 2, 2);
INSERT INTO `exam_mark_summary`
VALUES ('2be42d7dfa5cadad9aa9b915883533f3', 'a13c800d7265f7af8f606bf90d2b10c0', 0, 0);
INSERT INTO `exam_mark_summary`
VALUES ('302ebf344053f112da0008527f098040', '8612aa1079bd3750791c423aac4f49b2', 0, 0);
INSERT INTO `exam_mark_summary`
VALUES ('46bcf8ca5a390d578071094fdf4f626d', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', 2, 2);
INSERT INTO `exam_mark_summary`
VALUES ('4a1e564c94383ccd42c8f5f13c1c8133', '6c094da58baad8315d20af4c093b4836', 1, 0);
INSERT INTO `exam_mark_summary`
VALUES ('561afc0b6e7a19921f15bad8ec424696', '8f9aeb8b7897109dab3647dc81a3ac42', 1, 0);
INSERT INTO `exam_mark_summary`
VALUES ('58a54e5a50cb6cfe6131387c4adec66a', '8beef682e165dd4344d5f1cdf204acb3', 2, 0);
INSERT INTO `exam_mark_summary`
VALUES ('64aac728eaddf8e6ce8c1f9c704d1dd0', 'a2b2510507072874b478d99bf65b21e8', 1, 0);
INSERT INTO `exam_mark_summary`
VALUES ('657a13860e62dc55f92133ce66a3599a', '6886443ee43e780b5f97369159eeccbc', 2, 2);
INSERT INTO `exam_mark_summary`
VALUES ('73cf69c5f8e0e9d62a52db34f44f891c', 'a9b0bcae2f08f7fca993517df097bb8e', 0, 0);
INSERT INTO `exam_mark_summary`
VALUES ('79432c0bdf908e29c76be61c4928e29b', '873715982b213990ff91790f411c2930', 2, 1);
INSERT INTO `exam_mark_summary`
VALUES ('7c8345086752c3094998cee33a6826bf', '4c432c45e83ba1f590830d24ce69ba2e', 1, 0);
INSERT INTO `exam_mark_summary`
VALUES ('7d50a46ec1fc9e5a30c50b5895b2052e', '83c4c5834f659a4ccb2ee198ac455599', 1, 1);
INSERT INTO `exam_mark_summary`
VALUES ('843dab99ac0985b0e9a0c62e661d49ef', '9b6fc0e035218905851480b7aefc8997', 1, 0);
INSERT INTO `exam_mark_summary`
VALUES ('878c0334eecb49e19eb27041ba8606a7', '1524c96d40c49aadb4e393bc20de0638', 1, 0);
INSERT INTO `exam_mark_summary`
VALUES ('8d21a917fa938e18b97a0564f238077a', 'd0c7ba300112c771cd1a0d8089264761', 1, 0);
INSERT INTO `exam_mark_summary`
VALUES ('90d31c17d33c8a827f791b6d22ee2d6a', 'bfd1594e420dfe40ae6e377c6a637446', 0, 0);
INSERT INTO `exam_mark_summary`
VALUES ('9e0a2c8f99456fe11a72304ee5df0b78', '8cc65c25619d66315a6017f5f1d838d4', 1, 0);
INSERT INTO `exam_mark_summary`
VALUES ('a0a91e161c1bc68bef4ad4dc688d6344', '142ede91a9892a0f1f4640b2adb69bd1', 2, 2);
INSERT INTO `exam_mark_summary`
VALUES ('a9cadab3dbd37001f4522fa026f0aa44', '51fab6e0cc1ed40584d55e86297d66b5', 0, 0);
INSERT INTO `exam_mark_summary`
VALUES ('abf3c50cabe2cfffa7eac8328780fc42', '84e254610196caaf2937a03bcc41d747', 2, 0);
INSERT INTO `exam_mark_summary`
VALUES ('c7650e14ef6cdb15bb9b21c4d236012c', '9da1224196e7c3944cbf2eda8db49957', 1, 0);

-- ----------------------------
-- Table structure for exam_paper_blank
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper_blank`;
CREATE TABLE `exam_paper_blank`
(
    `id`          varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '表id',
    `exam_id`     varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '考试id',
    `question_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '题库id',
    `content`     varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
    `answer`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '答案',
    `score`       int(0)                                                  NOT NULL COMMENT '分数',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `teacher` (`exam_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '试卷填空题表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_paper_blank
-- ----------------------------
INSERT INTO `exam_paper_blank`
VALUES ('3bcc8b8a151cc59db89d088900431da5', '142ede91a9892a0f1f4640b2adb69bd1', '91d00facd31b2f969a9dccc3c7c7f4d2',
        '这是填空题3', 'aaaaaaaaaaaaaaaaaaaaa', 4);
INSERT INTO `exam_paper_blank`
VALUES ('77f1ec7b223d86edc85f6633d52021ac', '83c4c5834f659a4ccb2ee198ac455599', '78207527f7570f2a2b4944435786aca0',
        'asdasd', 'asdas', 10);
INSERT INTO `exam_paper_blank`
VALUES ('9f4d84b729d66631617dd0ae4201d914', '9da1224196e7c3944cbf2eda8db49957', '91d00facd31b2f969a9dccc3c7c7f4d2',
        '这是填空题3', 'aaaaaaaaaaaaaaaaaaaaa', 1);
INSERT INTO `exam_paper_blank`
VALUES ('bda2cdf928ed1527cd28091c2b35d844', '4c432c45e83ba1f590830d24ce69ba2e', '7bf571161f3a0be3337b205bb24724ce',
        'wzt是()人', '包头', 10);

-- ----------------------------
-- Table structure for exam_paper_judge
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper_judge`;
CREATE TABLE `exam_paper_judge`
(
    `id`          varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '表id',
    `exam_id`     varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '考试id',
    `question_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '题库id',
    `content`     varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
    `answer`      tinyint(0)                                              NOT NULL COMMENT '答案',
    `score`       int(0)                                                  NOT NULL COMMENT '分数',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `teacher_id` (`exam_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '试卷判断题表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_paper_judge
-- ----------------------------
INSERT INTO `exam_paper_judge`
VALUES ('14d70ff5f4854df49859ab6db0d00a91', '6886443ee43e780b5f97369159eeccbc', 'cf8b63a487046c6d9e8bbb5c7b82e7c4',
        '这是一道神器的判断题哈哈哈哈哈', 1, 2);
INSERT INTO `exam_paper_judge`
VALUES ('2c90a934b53f95fbc37588718d0f156f', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', 'cf8b63a487046c6d9e8bbb5c7b82e7c4',
        '这是一道神器的判断题哈哈哈哈哈', 1, 5);
INSERT INTO `exam_paper_judge`
VALUES ('4a8a011efcadb21fe145aefdd5e54f05', '4c432c45e83ba1f590830d24ce69ba2e', '6ed90d5b57d73ac5185868da89bd280a',
        'wzt是不是帅哥!', 1, 10);
INSERT INTO `exam_paper_judge`
VALUES ('6035b5242ca25432c46512f9e489c346', '83c4c5834f659a4ccb2ee198ac455599', '6f85295cdfae57d214a2079fb03d7dbd',
        'wzt是不是帅哥', 1, 10);
INSERT INTO `exam_paper_judge`
VALUES ('973cbd900db7dfb3cd49b3aebc4cb156', '142ede91a9892a0f1f4640b2adb69bd1', 'cf8b63a487046c6d9e8bbb5c7b82e7c4',
        '这是一道神器的判断题哈哈哈哈哈', 1, 3);
INSERT INTO `exam_paper_judge`
VALUES ('c0ccbe710242061b4affaba872a3156e', '9da1224196e7c3944cbf2eda8db49957', '6ed90d5b57d73ac5185868da89bd280a',
        'wzt是不是帅哥!', 1, 1);

-- ----------------------------
-- Table structure for exam_paper_multiple
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper_multiple`;
CREATE TABLE `exam_paper_multiple`
(
    `id`          varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '表id',
    `exam_id`     varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '考试id',
    `question_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '题库id',
    `content`     varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
    `a`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `d`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `c`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `b`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `answer`      tinyint(0)                                              NOT NULL COMMENT '答案',
    `score`       int(0)                                                  NOT NULL COMMENT '分数',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `teacherid` (`exam_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '试卷多选题表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_paper_multiple
-- ----------------------------
INSERT INTO `exam_paper_multiple`
VALUES ('256f5d43f55c34f5db016967739f571f', '6886443ee43e780b5f97369159eeccbc', '33f43738949721d21fd88fbb6eea5506',
        '多选测试', '111', '444', '333', '222', 0, 7);
INSERT INTO `exam_paper_multiple`
VALUES ('6a193c62b1193954d5f1d734db5f40e5', '9da1224196e7c3944cbf2eda8db49957', '290494a4a5429af2366c99b7221792e1',
        'dd多选题啦啦啦', 'AAA', 'dddd', 'cc', 'BBBBBBBBB', 0, 1);
INSERT INTO `exam_paper_multiple`
VALUES ('8bc4368fe634f6710d0fa7e0bf22637d', '83c4c5834f659a4ccb2ee198ac455599', '290494a4a5429af2366c99b7221792e1',
        'dd多选题啦啦啦', 'AAA', 'dddd', 'cc', 'BBBBBBBBB', 14, 10);
INSERT INTO `exam_paper_multiple`
VALUES ('a8c6e4b71f9db93e402e6fd9a3d9cb53', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', '33f43738949721d21fd88fbb6eea5506',
        '多选测试', '111', '444', '333', '222', 0, 111);
INSERT INTO `exam_paper_multiple`
VALUES ('bcd676050fd8b58a2fbfa4e441a48b62', '4c432c45e83ba1f590830d24ce69ba2e', '290494a4a5429af2366c99b7221792e1',
        'dd多选题啦啦啦', 'AAA', 'dddd', 'cc', 'BBBBBBBBB', 0, 10);
INSERT INTO `exam_paper_multiple`
VALUES ('e91d980d5189b1c9888b94eff6626a4a', '142ede91a9892a0f1f4640b2adb69bd1', '33f43738949721d21fd88fbb6eea5506',
        '多选测试', '111', '444', '333', '222', 0, 8);

-- ----------------------------
-- Table structure for exam_paper_single
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper_single`;
CREATE TABLE `exam_paper_single`
(
    `id`          varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '表id',
    `exam_id`     varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '考试id',
    `question_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '题库id',
    `content`     varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
    `a`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `d`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `c`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `b`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `answer`      tinyint(0)                                              NOT NULL COMMENT '答案',
    `score`       int(0)                                                  NOT NULL COMMENT '分数',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `teacherid` (`exam_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '试卷单选题表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_paper_single
-- ----------------------------
INSERT INTO `exam_paper_single`
VALUES ('1942079e7b583b52fc03e7483ad95e76', '84e254610196caaf2937a03bcc41d747', 'a738ab653a80dddcf5480fdaa0660a2e',
        '11111', '11111', '11111', '11111', '11111', 1, 233);
INSERT INTO `exam_paper_single`
VALUES ('26cd6efacc2241946cacc3d7c882792c', 'dd01d1c4c9215fbb8962720cef0b9060', 'a738ab653a80dddcf5480fdaa0660a2e',
        '11111', '11111', '11111', '11111', '11111', 1, 10);
INSERT INTO `exam_paper_single`
VALUES ('697a2cad068c288ac7474709ac16c250', 'dd01d1c4c9215fbb8962720cef0b9060', '2c7b6cd9c06a5f9c10c23f104758ba04',
        '单选题啊啊啊啊啊dfd', '拉a', '就选d', '不选c', '选b', 3, 5);
INSERT INTO `exam_paper_single`
VALUES ('83830868ec180cd2faee6761b2caa555', '8cc65c25619d66315a6017f5f1d838d4', '395c07be68b80e440a5613f86de05316',
        'aaaaaaaaaaaabbccdd', 'a', 'd', 'c', 'b', 2, 123);
INSERT INTO `exam_paper_single`
VALUES ('9c502a9db6a981d2b53cc4d1fae63163', 'dd01d1c4c9215fbb8962720cef0b9060', '395c07be68b80e440a5613f86de05316',
        'aaaaaaaaaaaabbccdd', 'a', 'd', 'c', 'b', 2, 8);
INSERT INTO `exam_paper_single`
VALUES ('9d9e16f08667b6722ac924252740a233', '84e254610196caaf2937a03bcc41d747', '2c7b6cd9c06a5f9c10c23f104758ba04',
        '单选题啊啊啊啊啊dfd', '拉a', '就选d', '不选c', '选b', 3, 245);
INSERT INTO `exam_paper_single`
VALUES ('9f8cd92c1a0965cb15327ebe1ed0ffa8', '83c4c5834f659a4ccb2ee198ac455599', '2c7b6cd9c06a5f9c10c23f104758ba04',
        'qwe单选题啊啊啊啊啊dfd', '拉a', '就选d',
        'sdaksajdlkasjld;kjaslkdjaslkjmcxk;jmvlk;zmlkc;vmzlkxc;vlkmzxlkvmlkzmlkdsamflkmalksjflkajslkfjalksjflkjaslkfjl;asjdfkl;aj',
        '选b', 0, 10);
INSERT INTO `exam_paper_single`
VALUES ('a3f3c181abb4433388d3a1841f87ba1b', '51fab6e0cc1ed40584d55e86297d66b5', '2c7b6cd9c06a5f9c10c23f104758ba04',
        '单选题啊啊啊啊啊dfd', '拉a', '就选d', '不选c', '选b', 3, 12);
INSERT INTO `exam_paper_single`
VALUES ('a528ed1068892c315b30344b5cc3b11f', '4c432c45e83ba1f590830d24ce69ba2e', '2c7b6cd9c06a5f9c10c23f104758ba04',
        '单选题啊啊啊啊啊dfd', '拉a', '就选d', '不选c', '选b', 3, 10);
INSERT INTO `exam_paper_single`
VALUES ('a6b6076408ec5fd27aebb416bd864735', '873715982b213990ff91790f411c2930', '395c07be68b80e440a5613f86de05316',
        'aaaaaaaaaaaabbccdd', 'a', 'd', 'c', 'b', 2, 123);
INSERT INTO `exam_paper_single`
VALUES ('b139b7cc594ae5b00590c760d9a5f3c0', '8beef682e165dd4344d5f1cdf204acb3', '395c07be68b80e440a5613f86de05316',
        'aaaaaaaaaaaabbccdd', 'a', 'd', 'c', 'b', 2, 1);
INSERT INTO `exam_paper_single`
VALUES ('cbac05bf7518555b3f8754013adf3811', '9da1224196e7c3944cbf2eda8db49957', '2c7b6cd9c06a5f9c10c23f104758ba04',
        '单选题啊啊啊啊啊dfd', '拉a', '就选d', '不选c', '选b', 3, 1);
INSERT INTO `exam_paper_single`
VALUES ('cdba46703936db496fe522d25011fceb', 'fc6a26b7c68ce9eb3fd8736aa24ffa37', '2c7b6cd9c06a5f9c10c23f104758ba04',
        '单选题啊啊啊啊啊dfd', '拉a', '就选d', '不选c', '选b', 3, 10);
INSERT INTO `exam_paper_single`
VALUES ('e162041d336e7c1426fdf1adf1a69555', '84e254610196caaf2937a03bcc41d747', '395c07be68b80e440a5613f86de05316',
        'aaaaaaaaaaaabbccdd', 'a', 'd', 'c', 'b', 2, 234);
INSERT INTO `exam_paper_single`
VALUES ('ee5a1ac8e8702e95a38327480cfb09db', '142ede91a9892a0f1f4640b2adb69bd1', 'a738ab653a80dddcf5480fdaa0660a2e',
        '11111', '11111', '11111', '11111', '11111', 1, 3);
INSERT INTO `exam_paper_single`
VALUES ('f4ae35ffdc687ffbb8a5cc4f4fd249b2', '873715982b213990ff91790f411c2930', 'a738ab653a80dddcf5480fdaa0660a2e',
        '11111', '11111', '11111', '11111', '11111', 1, 2344);

-- ----------------------------
-- Table structure for exam_paper_subjective
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper_subjective`;
CREATE TABLE `exam_paper_subjective`
(
    `id`          varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '表id',
    `exam_id`     varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '老师id',
    `question_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '题库id',
    `content`     varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '内容',
    `answer`      varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '答案',
    `score`       int(0)                                                   NOT NULL COMMENT '分数',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `teacher_id` (`exam_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '试卷主观题表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_paper_subjective
-- ----------------------------
INSERT INTO `exam_paper_subjective`
VALUES ('2c9178ca95705a4a2cca538fbc0c162b', '9da1224196e7c3944cbf2eda8db49957', '4894ec3a382fee353f1b6f1531ae3d23',
        '这是个主观题ddd', '嘻嘻嘻', 1);
INSERT INTO `exam_paper_subjective`
VALUES ('6acf55b2f8475be27af34779f90f14ba', 'd0c7ba300112c771cd1a0d8089264761', '92e4ea3290c60a761a5f7db1d36774cb',
        '请写一篇议论文，讨论wzt是不是帅哥，要求：4000字以上', 'wdnmd', 1233455353);
INSERT INTO `exam_paper_subjective`
VALUES ('ecd13e337aa9bfad7d71a0bdc93e94c8', '142ede91a9892a0f1f4640b2adb69bd1', '5314e56634d66a5edf81ec618eea77fb',
        '这是个主观题', '嘻嘻嘻', 10);
INSERT INTO `exam_paper_subjective`
VALUES ('f30bca0a3e5309245ba2bf0f95b07e9a', '4c432c45e83ba1f590830d24ce69ba2e', '4894ec3a382fee353f1b6f1531ae3d23',
        '这是个主观题ddd', '嘻嘻嘻', 10);
INSERT INTO `exam_paper_subjective`
VALUES ('f9d040152e9870e503c63cd1642b2ef8', '83c4c5834f659a4ccb2ee198ac455599', '4fd727a6ffc064988280e650446b7d69',
        'ddddddddddddddddd', 'asdsadsadsadsadsad', 10);

-- ----------------------------
-- Table structure for message_log
-- ----------------------------
DROP TABLE IF EXISTS `message_log`;
CREATE TABLE `message_log`
(
    `id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '消息id',
    `message`     varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息',
    `try_count`   int(0)                                                   NULL DEFAULT 0 COMMENT '尝试次数',
    `status`      tinyint(0)                                               NULL DEFAULT NULL COMMENT '状态',
    `next_retry`  datetime(0)                                              NOT NULL COMMENT '下次尝试时间',
    `create_time` datetime(0)                                              NOT NULL COMMENT '创建时间',
    `update_time` datetime(0)                                              NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `status` (`status`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '消息重试表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_log
-- ----------------------------
INSERT INTO `message_log`
VALUES ('mail-123@qq.com$2020-06-16T14:12:47.942',
        '{\"messageId\":\"mail-123@qq.com$2020-06-16T14:12:47.942\",\"to\":\"123@qq.com\",\"code\":\"432610\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":16},\"time\":{\"hour\":14,\"minute\":12,\"second\":47,\"nano\":942000000}}}',
        3, 2, '2020-06-16 12:13:48', '2020-06-16 12:12:48', '2020-06-16 12:13:57');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-05-13T13:54:29.747',
        '{\"messageId\":\"mail-942890268@qq.com$2020-05-13T13:54:29.747\",\"to\":\"942890268@qq.com\",\"code\":\"545664\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":5,\"day\":13},\"time\":{\"hour\":13,\"minute\":54,\"second\":29,\"nano\":747000000}}}',
        0, 1, '2020-05-13 11:55:30', '2020-05-13 11:54:30', '2020-05-13 11:54:30');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-05-13T15:48:52.456',
        '{\"messageId\":\"mail-942890268@qq.com$2020-05-13T15:48:52.456\",\"to\":\"942890268@qq.com\",\"code\":\"635374\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":5,\"day\":13},\"time\":{\"hour\":15,\"minute\":48,\"second\":52,\"nano\":456000000}}}',
        0, 1, '2020-05-13 13:49:52', '2020-05-13 13:48:52', '2020-05-13 13:48:53');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-05-13T15:54:20.762',
        '{\"messageId\":\"mail-942890268@qq.com$2020-05-13T15:54:20.762\",\"to\":\"942890268@qq.com\",\"code\":\"416278\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":5,\"day\":13},\"time\":{\"hour\":15,\"minute\":54,\"second\":20,\"nano\":762000000}}}',
        0, 1, '2020-05-13 13:55:21', '2020-05-13 13:54:21', '2020-05-13 13:54:21');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-05-13T15:57:40.092',
        '{\"messageId\":\"mail-942890268@qq.com$2020-05-13T15:57:40.092\",\"to\":\"942890268@qq.com\",\"code\":\"665937\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":5,\"day\":13},\"time\":{\"hour\":15,\"minute\":57,\"second\":40,\"nano\":92000000}}}',
        0, 1, '2020-05-13 13:58:40', '2020-05-13 13:57:40', '2020-05-13 13:57:40');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-06-08T23:53:09.700',
        '{\"messageId\":\"mail-942890268@qq.com$2020-06-08T23:53:09.700\",\"to\":\"942890268@qq.com\",\"code\":\"103936\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":8},\"time\":{\"hour\":23,\"minute\":53,\"second\":9,\"nano\":700000000}}}',
        3, 2, '2020-06-08 21:54:10', '2020-06-08 21:53:10', '2020-06-08 21:54:23');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-06-10T10:21:39.756',
        '{\"messageId\":\"mail-942890268@qq.com$2020-06-10T10:21:39.756\",\"to\":\"942890268@qq.com\",\"code\":\"020067\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":10},\"time\":{\"hour\":10,\"minute\":21,\"second\":39,\"nano\":756000000}}}',
        3, 2, '2020-06-10 08:22:40', '2020-06-10 08:21:40', '2020-06-10 08:23:13');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-06-10T13:10:58.265',
        '{\"messageId\":\"mail-942890268@qq.com$2020-06-10T13:10:58.265\",\"to\":\"942890268@qq.com\",\"code\":\"907831\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":10},\"time\":{\"hour\":13,\"minute\":10,\"second\":58,\"nano\":265000000}}}',
        3, 2, '2020-06-10 11:11:58', '2020-06-10 11:10:58', '2020-06-10 11:12:05');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-06-10T13:34:13.675',
        '{\"messageId\":\"mail-942890268@qq.com$2020-06-10T13:34:13.675\",\"to\":\"942890268@qq.com\",\"code\":\"522092\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":10},\"time\":{\"hour\":13,\"minute\":34,\"second\":13,\"nano\":675000000}}}',
        3, 1, '2020-06-10 11:35:14', '2020-06-10 11:34:14', '2020-06-10 11:54:17');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-06-10T14:00:58.027',
        '{\"messageId\":\"mail-942890268@qq.com$2020-06-10T14:00:58.027\",\"to\":\"942890268@qq.com\",\"code\":\"249133\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":10},\"time\":{\"hour\":14,\"minute\":0,\"second\":58,\"nano\":27000000}}}',
        3, 2, '2020-06-10 12:01:58', '2020-06-10 12:00:58', '2020-06-10 12:02:17');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-06-10T15:39:33.945',
        '{\"messageId\":\"mail-942890268@qq.com$2020-06-10T15:39:33.945\",\"to\":\"942890268@qq.com\",\"code\":\"250638\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":10},\"time\":{\"hour\":15,\"minute\":39,\"second\":33,\"nano\":945000000}}}',
        3, 1, '2020-06-10 13:40:34', '2020-06-10 13:39:34', '2020-06-10 13:40:43');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-06-10T15:50:03.365',
        '{\"messageId\":\"mail-942890268@qq.com$2020-06-10T15:50:03.365\",\"to\":\"942890268@qq.com\",\"code\":\"581099\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":10},\"time\":{\"hour\":15,\"minute\":50,\"second\":3,\"nano\":365000000}}}',
        0, 1, '2020-06-10 13:51:03', '2020-06-10 13:50:03', '2020-06-10 13:50:03');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-06-10T16:06:57.279',
        '{\"messageId\":\"mail-942890268@qq.com$2020-06-10T16:06:57.279\",\"to\":\"942890268@qq.com\",\"code\":\"897315\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":10},\"time\":{\"hour\":16,\"minute\":6,\"second\":57,\"nano\":279000000}}}',
        3, 2, '2020-06-10 14:07:57', '2020-06-10 14:06:57', '2020-06-10 14:08:15');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-06-10T16:42:32.182',
        '{\"messageId\":\"mail-942890268@qq.com$2020-06-10T16:42:32.182\",\"to\":\"942890268@qq.com\",\"code\":\"607215\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":10},\"time\":{\"hour\":16,\"minute\":42,\"second\":32,\"nano\":182000000}}}',
        0, 1, '2020-06-10 14:43:32', '2020-06-10 14:42:32', '2020-06-10 14:42:33');
INSERT INTO `message_log`
VALUES ('mail-942890268@qq.com$2020-06-10T16:52:52.218',
        '{\"messageId\":\"mail-942890268@qq.com$2020-06-10T16:52:52.218\",\"to\":\"942890268@qq.com\",\"code\":\"657319\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":10},\"time\":{\"hour\":16,\"minute\":52,\"second\":52,\"nano\":218000000}}}',
        0, 1, '2020-06-10 14:53:52', '2020-06-10 14:52:52', '2020-06-10 14:52:53');
INSERT INTO `message_log`
VALUES ('mail-962584889@qq.com$2020-06-08T23:29:17.866',
        '{\"messageId\":\"mail-962584889@qq.com$2020-06-08T23:29:17.866\",\"to\":\"962584889@qq.com\",\"code\":\"379885\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":8},\"time\":{\"hour\":23,\"minute\":29,\"second\":17,\"nano\":866000000}}}',
        3, 2, '2020-06-08 21:30:18', '2020-06-08 21:29:18', '2020-06-08 21:30:36');
INSERT INTO `message_log`
VALUES ('mail-962584889@qq.com$2020-06-08T23:32:59.840',
        '{\"messageId\":\"mail-962584889@qq.com$2020-06-08T23:32:59.840\",\"to\":\"962584889@qq.com\",\"code\":\"549655\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":8},\"time\":{\"hour\":23,\"minute\":32,\"second\":59,\"nano\":840000000}}}',
        3, 2, '2020-06-08 21:34:00', '2020-06-08 21:33:00', '2020-06-08 21:34:16');
INSERT INTO `message_log`
VALUES ('mail-962584889@qq.com$2020-06-08T23:33:24.857',
        '{\"messageId\":\"mail-962584889@qq.com$2020-06-08T23:33:24.857\",\"to\":\"962584889@qq.com\",\"code\":\"578367\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":8},\"time\":{\"hour\":23,\"minute\":33,\"second\":24,\"nano\":857000000}}}',
        3, 2, '2020-06-08 21:34:25', '2020-06-08 21:33:25', '2020-06-08 21:34:42');
INSERT INTO `message_log`
VALUES ('mail-nmbtzjl@vip.qq.com$2020-06-08T23:38:37.218',
        '{\"messageId\":\"mail-nmbtzjl@vip.qq.com$2020-06-08T23:38:37.218\",\"to\":\"nmbtzjl@vip.qq.com\",\"code\":\"975073\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":8},\"time\":{\"hour\":23,\"minute\":38,\"second\":37,\"nano\":218000000}}}',
        3, 2, '2020-06-08 21:39:37', '2020-06-08 21:38:37', '2020-06-08 21:39:56');
INSERT INTO `message_log`
VALUES ('mail-student@qq.com$2020-05-15T02:25:57.039',
        '{\"messageId\":\"mail-student@qq.com$2020-05-15T02:25:57.039\",\"to\":\"student@qq.com\",\"code\":\"824132\",\"expireTime\":{\"date\":{\"year\":2020,\"month\":5,\"day\":15},\"time\":{\"hour\":2,\"minute\":25,\"second\":57,\"nano\":39000000}}}',
        0, 1, '2020-05-15 00:26:57', '2020-05-15 00:25:57', '2020-05-15 00:25:57');
INSERT INTO `message_log`
VALUES ('mark-142ede91a9892a0f1f4640b2adb69bd1$25d26c4eb4c29d57c9948bf5af20dc3f',
        '{\"messageId\":\"mark-142ede91a9892a0f1f4640b2adb69bd1$25d26c4eb4c29d57c9948bf5af20dc3f\",\"examId\":\"142ede91a9892a0f1f4640b2adb69bd1\",\"studentId\":\"25d26c4eb4c29d57c9948bf5af20dc3f\"}',
        0, 1, '2020-05-05 19:41:23', '2020-05-05 19:40:23', '2020-05-05 19:40:23');
INSERT INTO `message_log`
VALUES ('mark-142ede91a9892a0f1f4640b2adb69bd1$3239b29e922c20ae2f65ccd10a83a7d3',
        '{\"messageId\":\"mark-142ede91a9892a0f1f4640b2adb69bd1$3239b29e922c20ae2f65ccd10a83a7d3\",\"examId\":\"142ede91a9892a0f1f4640b2adb69bd1\",\"studentId\":\"3239b29e922c20ae2f65ccd10a83a7d3\"}',
        0, 1, '2020-05-05 19:41:23', '2020-05-05 19:40:23', '2020-05-05 19:40:23');
INSERT INTO `message_log`
VALUES ('mark-1524c96d40c49aadb4e393bc20de0638$d2eaeba7c1a2a967fe749be6b998458b',
        '{\"messageId\":\"mark-1524c96d40c49aadb4e393bc20de0638$d2eaeba7c1a2a967fe749be6b998458b\",\"examId\":\"1524c96d40c49aadb4e393bc20de0638\",\"studentId\":\"d2eaeba7c1a2a967fe749be6b998458b\"}',
        3, 2, '2020-06-08 22:56:14', '2020-06-08 22:55:14', '2020-06-08 22:56:33');
INSERT INTO `message_log`
VALUES ('mark-6c094da58baad8315d20af4c093b4836$d2eaeba7c1a2a967fe749be6b998458b',
        '{\"messageId\":\"mark-6c094da58baad8315d20af4c093b4836$d2eaeba7c1a2a967fe749be6b998458b\",\"examId\":\"6c094da58baad8315d20af4c093b4836\",\"studentId\":\"d2eaeba7c1a2a967fe749be6b998458b\"}',
        3, 2, '2020-06-08 23:11:15', '2020-06-08 23:10:15', '2020-06-08 23:11:34');
INSERT INTO `message_log`
VALUES ('mark-83c4c5834f659a4ccb2ee198ac455599$d2eaeba7c1a2a967fe749be6b998458b',
        '{\"messageId\":\"mark-83c4c5834f659a4ccb2ee198ac455599$d2eaeba7c1a2a967fe749be6b998458b\",\"examId\":\"83c4c5834f659a4ccb2ee198ac455599\",\"studentId\":\"d2eaeba7c1a2a967fe749be6b998458b\"}',
        0, 1, '2020-06-12 17:24:02', '2020-06-12 17:23:02', '2020-06-12 17:23:02');
INSERT INTO `message_log`
VALUES ('mark-84e254610196caaf2937a03bcc41d747$25d26c4eb4c29d57c9948bf5af20dc3f',
        '{\"messageId\":\"mark-84e254610196caaf2937a03bcc41d747$25d26c4eb4c29d57c9948bf5af20dc3f\",\"examId\":\"84e254610196caaf2937a03bcc41d747\",\"studentId\":\"25d26c4eb4c29d57c9948bf5af20dc3f\"}',
        3, 2, '2020-05-31 14:53:29', '2020-05-31 14:52:29', '2020-05-31 14:53:49');
INSERT INTO `message_log`
VALUES ('mark-84e254610196caaf2937a03bcc41d747$3239b29e922c20ae2f65ccd10a83a7d3',
        '{\"messageId\":\"mark-84e254610196caaf2937a03bcc41d747$3239b29e922c20ae2f65ccd10a83a7d3\",\"examId\":\"84e254610196caaf2937a03bcc41d747\",\"studentId\":\"3239b29e922c20ae2f65ccd10a83a7d3\"}',
        3, 2, '2020-05-31 14:53:29', '2020-05-31 14:52:29', '2020-05-31 14:53:49');
INSERT INTO `message_log`
VALUES ('mark-873715982b213990ff91790f411c2930$25d26c4eb4c29d57c9948bf5af20dc3f',
        '{\"messageId\":\"mark-873715982b213990ff91790f411c2930$25d26c4eb4c29d57c9948bf5af20dc3f\",\"examId\":\"873715982b213990ff91790f411c2930\",\"studentId\":\"25d26c4eb4c29d57c9948bf5af20dc3f\"}',
        3, 2, '2020-05-31 15:37:50', '2020-05-31 15:36:50', '2020-05-31 15:37:57');
INSERT INTO `message_log`
VALUES ('mark-873715982b213990ff91790f411c2930$3239b29e922c20ae2f65ccd10a83a7d3',
        '{\"messageId\":\"mark-873715982b213990ff91790f411c2930$3239b29e922c20ae2f65ccd10a83a7d3\",\"examId\":\"873715982b213990ff91790f411c2930\",\"studentId\":\"3239b29e922c20ae2f65ccd10a83a7d3\"}',
        4, 2, '2020-05-31 15:37:50', '2020-05-31 15:36:50', '2020-05-31 15:38:09');
INSERT INTO `message_log`
VALUES ('mark-8f9aeb8b7897109dab3647dc81a3ac42$d2eaeba7c1a2a967fe749be6b998458b',
        '{\"messageId\":\"mark-8f9aeb8b7897109dab3647dc81a3ac42$d2eaeba7c1a2a967fe749be6b998458b\",\"examId\":\"8f9aeb8b7897109dab3647dc81a3ac42\",\"studentId\":\"d2eaeba7c1a2a967fe749be6b998458b\"}',
        0, 1, '2020-06-12 16:34:01', '2020-06-12 16:33:01', '2020-06-12 16:33:01');
INSERT INTO `message_log`
VALUES ('mark-9b6fc0e035218905851480b7aefc8997$d2eaeba7c1a2a967fe749be6b998458b',
        '{\"messageId\":\"mark-9b6fc0e035218905851480b7aefc8997$d2eaeba7c1a2a967fe749be6b998458b\",\"examId\":\"9b6fc0e035218905851480b7aefc8997\",\"studentId\":\"d2eaeba7c1a2a967fe749be6b998458b\"}',
        3, 2, '2020-06-08 23:08:34', '2020-06-08 23:07:34', '2020-06-08 23:08:54');
INSERT INTO `message_log`
VALUES ('mark-d0c7ba300112c771cd1a0d8089264761$d2eaeba7c1a2a967fe749be6b998458b',
        '{\"messageId\":\"mark-d0c7ba300112c771cd1a0d8089264761$d2eaeba7c1a2a967fe749be6b998458b\",\"examId\":\"d0c7ba300112c771cd1a0d8089264761\",\"studentId\":\"d2eaeba7c1a2a967fe749be6b998458b\"}',
        3, 2, '2020-06-08 22:50:47', '2020-06-08 22:49:47', '2020-06-08 22:51:03');
INSERT INTO `message_log`
VALUES ('mark-dd01d1c4c9215fbb8962720cef0b9060$25d26c4eb4c29d57c9948bf5af20dc3f',
        '{\"messageId\":\"mark-dd01d1c4c9215fbb8962720cef0b9060$25d26c4eb4c29d57c9948bf5af20dc3f\",\"examId\":\"dd01d1c4c9215fbb8962720cef0b9060\",\"studentId\":\"25d26c4eb4c29d57c9948bf5af20dc3f\"}',
        3, 2, '2020-05-31 18:54:38', '2020-05-31 18:53:38', '2020-05-31 18:54:58');
INSERT INTO `message_log`
VALUES ('mark-dd01d1c4c9215fbb8962720cef0b9060$3239b29e922c20ae2f65ccd10a83a7d3',
        '{\"messageId\":\"mark-dd01d1c4c9215fbb8962720cef0b9060$3239b29e922c20ae2f65ccd10a83a7d3\",\"examId\":\"dd01d1c4c9215fbb8962720cef0b9060\",\"studentId\":\"3239b29e922c20ae2f65ccd10a83a7d3\"}',
        3, 2, '2020-05-31 18:54:38', '2020-05-31 18:53:38', '2020-05-31 18:54:58');
INSERT INTO `message_log`
VALUES ('mark-fc6a26b7c68ce9eb3fd8736aa24ffa37$25d26c4eb4c29d57c9948bf5af20dc3f',
        '{\"messageId\":\"mark-fc6a26b7c68ce9eb3fd8736aa24ffa37$25d26c4eb4c29d57c9948bf5af20dc3f\",\"examId\":\"fc6a26b7c68ce9eb3fd8736aa24ffa37\",\"studentId\":\"25d26c4eb4c29d57c9948bf5af20dc3f\"}',
        3, 2, '2020-05-31 14:48:09', '2020-05-31 14:47:09', '2020-05-31 14:48:29');
INSERT INTO `message_log`
VALUES ('mark-fc6a26b7c68ce9eb3fd8736aa24ffa37$3239b29e922c20ae2f65ccd10a83a7d3',
        '{\"messageId\":\"mark-fc6a26b7c68ce9eb3fd8736aa24ffa37$3239b29e922c20ae2f65ccd10a83a7d3\",\"examId\":\"fc6a26b7c68ce9eb3fd8736aa24ffa37\",\"studentId\":\"3239b29e922c20ae2f65ccd10a83a7d3\"}',
        3, 2, '2020-05-31 14:48:09', '2020-05-31 14:47:09', '2020-05-31 14:48:29');

-- ----------------------------
-- Table structure for question_blank
-- ----------------------------
DROP TABLE IF EXISTS `question_blank`;
CREATE TABLE `question_blank`
(
    `id`         varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '表id',
    `teacher_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '老师id',
    `content`    varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
    `answer`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '答案',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `teacher` (`teacher_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '题库填空题表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_blank
-- ----------------------------
INSERT INTO `question_blank`
VALUES ('373a36a54c70f1a9cca85110938483ea', '2cc865e31e22a3fe185e1645bf04fd69', '', '');
INSERT INTO `question_blank`
VALUES ('78207527f7570f2a2b4944435786aca0', '2cc865e31e22a3fe185e1645bf04fd69', 'asdasd', 'asdas');
INSERT INTO `question_blank`
VALUES ('91d00facd31b2f969a9dccc3c7c7f4d2', '2cc865e31e22a3fe185e1645bf04fd69', '这是填空题3', 'aaaaaaaaaaaaaaaaaaaaa');
INSERT INTO `question_blank`
VALUES ('efae761b822174bf255498ef7cc9b5e6', '2cc865e31e22a3fe185e1645bf04fd69', '这是填空题4', '哈哈哈');

-- ----------------------------
-- Table structure for question_judge
-- ----------------------------
DROP TABLE IF EXISTS `question_judge`;
CREATE TABLE `question_judge`
(
    `id`         varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '表id',
    `teacher_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '考试id',
    `content`    varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
    `answer`     tinyint(0)                                              NOT NULL COMMENT '答案',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `teacher_id` (`teacher_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '题库判断题表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_judge
-- ----------------------------
INSERT INTO `question_judge`
VALUES ('344c64d64b45d6c5a1524c1d2c7db214', '2cc865e31e22a3fe185e1645bf04fd69', '', 0);
INSERT INTO `question_judge`
VALUES ('6f85295cdfae57d214a2079fb03d7dbd', '2cc865e31e22a3fe185e1645bf04fd69', 'wzt是不是帅哥', 1);
INSERT INTO `question_judge`
VALUES ('cf8b63a487046c6d9e8bbb5c7b82e7c4', '2cc865e31e22a3fe185e1645bf04fd69', '这是一道神器的判断题哈哈哈哈哈', 1);

-- ----------------------------
-- Table structure for question_multiple
-- ----------------------------
DROP TABLE IF EXISTS `question_multiple`;
CREATE TABLE `question_multiple`
(
    `id`         varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '表id',
    `teacher_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '老师id',
    `content`    varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
    `a`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `d`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `c`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `b`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `answer`     tinyint(0)                                              NOT NULL COMMENT '答案',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `teacherid` (`teacher_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '题库多选题表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_multiple
-- ----------------------------
INSERT INTO `question_multiple`
VALUES ('290494a4a5429af2366c99b7221792e1', '2cc865e31e22a3fe185e1645bf04fd69', 'dd多选题啦啦啦', 'AAA', 'dddd', 'cc',
        'BBBBBBBBB', 14);
INSERT INTO `question_multiple`
VALUES ('33f43738949721d21fd88fbb6eea5506', '2cc865e31e22a3fe185e1645bf04fd69', '多选测试', '111', '444', '333', '222', 0);
INSERT INTO `question_multiple`
VALUES ('3923483fa253142c9aa9ecb2bbc0f163', '2cc865e31e22a3fe185e1645bf04fd69', '多选题啦啦啦', 'AAA', 'dddd', 'cc',
        'BBBBBBBBB', 12);
INSERT INTO `question_multiple`
VALUES ('baa2c547735eb6a84b2c29dd3116adb8', '2cc865e31e22a3fe185e1645bf04fd69', '多选题啦啦啦', 'AAA', 'dddd', 'cc',
        'BBBBBBBBB', 0);

-- ----------------------------
-- Table structure for question_single
-- ----------------------------
DROP TABLE IF EXISTS `question_single`;
CREATE TABLE `question_single`
(
    `id`         varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '表id',
    `teacher_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '老师id',
    `content`    varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
    `a`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `d`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `c`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `b`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `answer`     tinyint(0)                                              NOT NULL COMMENT '答案',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `teacherid` (`teacher_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '题库单选题表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_single
-- ----------------------------
INSERT INTO `question_single`
VALUES ('2c7b6cd9c06a5f9c10c23f104758ba04', '2cc865e31e22a3fe185e1645bf04fd69', 'qwe单选题啊啊啊啊啊dfd', '拉a', '就选d',
        'sdaksajdlkasjld;kjaslkdjaslkjmcxk;jmvlk;zmlkc;vmzlkxc;vlkmzxlkvmlkzmlkdsamflkmalksjflkajslkfjalksjflkjaslkfjl;asjdfkl;aj',
        '选b', 0);
INSERT INTO `question_single`
VALUES ('395c07be68b80e440a5613f86de05316', '2cc865e31e22a3fe185e1645bf04fd69', 'aaaaaaaaaaaabbccdd', 'a', 'd', 'c',
        'b', 2);
INSERT INTO `question_single`
VALUES ('a738ab653a80dddcf5480fdaa0660a2e', '2cc865e31e22a3fe185e1645bf04fd69', '11111', '11111', '11111', '11111',
        '11111', 1);

-- ----------------------------
-- Table structure for question_subjective
-- ----------------------------
DROP TABLE IF EXISTS `question_subjective`;
CREATE TABLE `question_subjective`
(
    `id`         varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '表id',
    `teacher_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL COMMENT '老师id',
    `content`    varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '内容',
    `answer`     varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '答案',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `teacher_id` (`teacher_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '题库主观题表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_subjective
-- ----------------------------
INSERT INTO `question_subjective`
VALUES ('4894ec3a382fee353f1b6f1531ae3d23', '2cc865e31e22a3fe185e1645bf04fd69', '这是个主观题ddd', '嘻嘻嘻1');
INSERT INTO `question_subjective`
VALUES ('4fd727a6ffc064988280e650446b7d69', '2cc865e31e22a3fe185e1645bf04fd69', 'ddddddddddddddddd',
        'asdsadsadsadsadsad');
INSERT INTO `question_subjective`
VALUES ('5314e56634d66a5edf81ec618eea77fb', '2cc865e31e22a3fe185e1645bf04fd69', '这是个主观题', '嘻嘻嘻');
INSERT INTO `question_subjective`
VALUES ('92e4ea3290c60a761a5f7db1d36774cb', '2cc865e31e22a3fe185e1645bf04fd69', '请写一篇议论文，讨论wzt是不是帅哥，要求：4000字以上',
        'wdnmd');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`                     varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户id',
    `type`                   tinyint(0)                                              NOT NULL COMMENT '用户类型',
    `name`                   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
    `email`                  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
    `password`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    `phone`                  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机',
    `avater`                 varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
    `check_code`             varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '验证码',
    `check_code_expire_time` datetime(0)                                             NOT NULL COMMENT '验证码过期时间',
    `status`                 tinyint(0)                                              NOT NULL COMMENT '状态',
    `is_online`              bit(1)                                                  NULL DEFAULT NULL COMMENT '是否在线',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `email` (`email`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES ('25d26c4eb4c29d57c9948bf5af20dc3f', 0, '小天', 'xiaotian@qq.com',
        '$2a$10$eS3JW3TMRVi.RHICd.jQueRdWge6CpitUkKKkO2.jtn5JyFNv93pm', '13289078113',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '233355', '2020-06-03 09:24:56', 1, b'0');
INSERT INTO `user`
VALUES ('2990714c1902465bd8b72a53fb58b041', 1, 'jxb', '540669078@qq.com',
        '$2a$10$eS3JW3TMRVi.RHICd.jQueRdWge6CpitUkKKkO2.jtn5JyFNv93pm', '13192287806',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '045935', '2020-04-28 16:24:24', 1, b'0');
INSERT INTO `user`
VALUES ('2cc865e31e22a3fe185e1645bf04fd69', 1, '罗翔', 'teacher1@qq.com',
        '$2a$10$eS3JW3TMRVi.RHICd.jQueRdWge6CpitUkKKkO2.jtn5JyFNv93pm', '13455673346',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '244477', '2020-04-27 14:47:37', 1, b'0');
INSERT INTO `user`
VALUES ('3239b29e922c20ae2f65ccd10a83a7d3', 0, '小明', 'xiaoming@qq.com',
        '$2a$10$eS3JW3TMRVi.RHICd.jQueRdWge6CpitUkKKkO2.jtn5JyFNv93pm', '16788829978',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '123456', '2020-05-31 15:28:22', 1, b'0');
INSERT INTO `user`
VALUES ('717db41901a4639628d20926606d8b06', 0, 'zhanxm', '942890268@qq.com',
        '$2a$10$eS3JW3TMRVi.RHICd.jQueRdWge6CpitUkKKkO2.jtn5JyFNv93pm', '1222222',
        'https://cdn.aiexam.zhanxm.cn/avater/942890268@qq.com.jpeg', '657319', '2020-06-10 16:52:52', 1, b'0');
INSERT INTO `user`
VALUES ('8412694c273574ec983002e94896e4cb', 0, '小静', '193666@qq.com',
        '$2a$10$eS3JW3TMRVi.RHICd.jQueRdWge6CpitUkKKkO2.jtn5JyFNv93pm', '16666212312',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '643476', '2020-04-27 14:47:45', 1, b'0');
INSERT INTO `user`
VALUES ('a71e3ecd240dac7746722140d5bd7253', 1, '', '962584889@qq.com',
        '$2a$10$y6kW972NOiu7CjUtrOx9t.RTGjxFdhp70DFqXHmbUn3TCqgCu/7KO', '',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '578367', '2020-06-08 21:35:12', 1, b'0');
INSERT INTO `user`
VALUES ('d2eaeba7c1a2a967fe749be6b998458b', 0, 'testStudent', 'student@qq.com',
        '$2a$10$eS3JW3TMRVi.RHICd.jQueRdWge6CpitUkKKkO2.jtn5JyFNv93pm', '13192287806',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '824132', '2020-05-15 02:25:57', 1, b'0');
INSERT INTO `user`
VALUES ('db654e323cd1e0050249205751aa5fd3', 1, '仙女', '951398016@qq.com',
        '$2a$10$eS3JW3TMRVi.RHICd.jQueRdWge6CpitUkKKkO2.jtn5JyFNv93pm', '110',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '708185', '2020-05-03 20:49:40', 1, b'0');
INSERT INTO `user`
VALUES ('dfb232f1c44075ae89a675881a705be2', 1, '', 'nmbtzjl@vip.qq.com',
        '$2a$10$YEE0LES0DWsxRg83O2.tO.ufJtOygAoWchtjdu6T1g9QLLfiF.PL2', '',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '975073', '2020-06-08 23:38:37', 0, b'0');
INSERT INTO `user`
VALUES ('f97295572e65540bfdf82676260e6984', 0, '小博', '190666@qq.com',
        '$2a$10$eS3JW3TMRVi.RHICd.jQueRdWge6CpitUkKKkO2.jtn5JyFNv93pm', '18971233123',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '783234', '2020-04-27 14:47:47', 1, b'0');
INSERT INTO `user`
VALUES ('fbd09c26ace94cb84b18070cdc4bde11', 1, '1', '123@qq.com',
        '$2a$10$pnI3Qca7m3ReptWIVI0ufuA4M5zkHP.JBeDti0Obuw7QwLd0Vz9cK', '1',
        'https://cdn.aiexam.zhanxm.cn/avater/default.png', '432610', '2020-06-16 14:12:48', 0, b'0');

-- ----------------------------
-- Table structure for user_class
-- ----------------------------
DROP TABLE IF EXISTS `user_class`;
CREATE TABLE `user_class`
(
    `id`         varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '表id',
    `user_id`    varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户id',
    `user_type`  tinyint(0)                                              NOT NULL COMMENT '用户类型',
    `class_id`   varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '班级id',
    `class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班级名',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_class` (`user_id`, `class_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户班级表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_class
-- ----------------------------
INSERT INTO `user_class`
VALUES ('121e71cc8a476b9553af9abb09143976', '2cc865e31e22a3fe185e1645bf04fd69', 1, '0b495598e2cb0285f74233e94beeaa92',
        '罗翔老师的0612测试课堂');
INSERT INTO `user_class`
VALUES ('21205ba9dda2634815dcccdbe0508711', '2cc865e31e22a3fe185e1645bf04fd69', 1, '91a98191a1d6495d6336c7ec14914d01',
        '厚大法考3');
INSERT INTO `user_class`
VALUES ('3bbdbc648a629b172b6e3c6f3bd10982', '2cc865e31e22a3fe185e1645bf04fd69', 1, 'c4fa7df8567cf6c883d8feee48899806',
        'test0616');
INSERT INTO `user_class`
VALUES ('44f5a3c11224e6c50a9cb8c9e64c54ca', '3239b29e922c20ae2f65ccd10a83a7d3', 0, '64fba23bc0b8ec8e692d1fbb6711ca03',
        '厚大法考4');
INSERT INTO `user_class`
VALUES ('4a3bd1f9bb286726d42effa84a1a6397', '2cc865e31e22a3fe185e1645bf04fd69', 1, 'b96f4987d26854440eeec6e9e0c6e0d9',
        '罗翔老师的0522课堂');
INSERT INTO `user_class`
VALUES ('54078eff876e3d1e8f724afbde4d0515', '2cc865e31e22a3fe185e1645bf04fd69', 1, '3784cd5ef963537e3be222fb0c347332',
        '123');
INSERT INTO `user_class`
VALUES ('59ea43aa60b4f5e0f4b5b5b5b1a851a9', '2990714c1902465bd8b72a53fb58b041', 1, 'b12258323daba9e70430bf899a6db703',
        '班级2');
INSERT INTO `user_class`
VALUES ('66ee4d545db73dade91d47ffd5b4e1b9', '2cc865e31e22a3fe185e1645bf04fd69', 1, '64fba23bc0b8ec8e692d1fbb6711ca03',
        '厚大法考4');
INSERT INTO `user_class`
VALUES ('6cb4ff9f4941a339fbf2019499767048', '2cc865e31e22a3fe185e1645bf04fd69', 1, 'c022172dfde19732ffa02bd9780824d6',
        '厚大法考8');
INSERT INTO `user_class`
VALUES ('79904d743210401d194c693bea53cc00', '2cc865e31e22a3fe185e1645bf04fd69', 1, '090cc0350610261cc8ac7289812e2e5d',
        '厚大法考10');
INSERT INTO `user_class`
VALUES ('7ef0b25507b7f19be0633a1237c06d5d', '2cc865e31e22a3fe185e1645bf04fd69', 1, '884fe0d497e5e85359ac63d56c271430',
        'testclass1');
INSERT INTO `user_class`
VALUES ('8c907f771feae8fbfe81533ff863940c', '2cc865e31e22a3fe185e1645bf04fd69', 1, 'a0a5b32a133c28c727c36404cc318d40',
        '罗翔老师的123课堂');
INSERT INTO `user_class`
VALUES ('92f240707f09de0164147ae4883de358', 'db654e323cd1e0050249205751aa5fd3', 1, 'b488edfbdd603df1e610b81f04c190e9',
        '仙女老师的阿万艾斯亿神奇魔法课堂');
INSERT INTO `user_class`
VALUES ('aca793b4258aa4cf4e593c762ac703a8', '2cc865e31e22a3fe185e1645bf04fd69', 1, '795373e3c1a4ecc24176db8c754f295e',
        '厚大法考5');
INSERT INTO `user_class`
VALUES ('b28e233b0b4f13e3049dcd467abcd979', '2cc865e31e22a3fe185e1645bf04fd69', 1, 'c17fab46fe68a073e70a6f01afa611e3',
        '厚大法考7');
INSERT INTO `user_class`
VALUES ('b6423f195e48ef6c6957c31e1d6fb2ed', '2990714c1902465bd8b72a53fb58b041', 1, '248accef36f8da9776fdcd5876e55f16',
        '班级1');
INSERT INTO `user_class`
VALUES ('bd283ee9c842093b5c931f4ab3e2ca23', '2cc865e31e22a3fe185e1645bf04fd69', 1, 'cb913ed69da22a9d5a03574010e6bcda',
        '罗翔老师的测试课堂');
INSERT INTO `user_class`
VALUES ('c935620d68f94bb4b13c1700edba655a', '2cc865e31e22a3fe185e1645bf04fd69', 1, 'ab7ad1f4d4c4f8dfc3ee7962c5b6cd56',
        '厚大法考9');
INSERT INTO `user_class`
VALUES ('d8ac8d7cb680afbbd9eb80e16d5bff3e', '2cc865e31e22a3fe185e1645bf04fd69', 1, '326e976db47cc5e91a928263057c07d7',
        '罗翔老师的test1课堂');
INSERT INTO `user_class`
VALUES ('d8b7464548e6692ccec444e472f7a46a', 'd2eaeba7c1a2a967fe749be6b998458b', 0, 'a0a5b32a133c28c727c36404cc318d40',
        '罗翔老师的123课堂');
INSERT INTO `user_class`
VALUES ('d9f91aa94f48f3113b84848a20336361', 'd2eaeba7c1a2a967fe749be6b998458b', 0, 'b96f4987d26854440eeec6e9e0c6e0d9',
        '罗翔老师的0522课堂');
INSERT INTO `user_class`
VALUES ('e49d9114337b26f37f7741be79f2f094', '2cc865e31e22a3fe185e1645bf04fd69', 1, '5c8bbf46108b9e85b050fcd4167991a6',
        'testclass');
INSERT INTO `user_class`
VALUES ('f705c79404aab7a0cf8578d9102be3c4', '2cc865e31e22a3fe185e1645bf04fd69', 1, 'e6ef770b08aa9cacad47f8d08284641c',
        '厚大法考2');
INSERT INTO `user_class`
VALUES ('f78746ad265069abaa9d12a01e948bc3', '25d26c4eb4c29d57c9948bf5af20dc3f', 0, '64fba23bc0b8ec8e692d1fbb6711ca03',
        '厚大法考4');
INSERT INTO `user_class`
VALUES ('fefde92e3950d638cbf8fb042a53929c', '2cc865e31e22a3fe185e1645bf04fd69', 1, '80d6c87945f214a035da20803d716090',
        '');
INSERT INTO `user_class`
VALUES ('ff48173e97dfedbd4105279a126143b5', '2cc865e31e22a3fe185e1645bf04fd69', 1, 'd48a7121de9f4544fbfdf3bf123975cd',
        '厚大法考6');

SET FOREIGN_KEY_CHECKS = 1;
