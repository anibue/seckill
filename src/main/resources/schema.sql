-- 创建用户表
CREATE TABLE IF NOT EXISTS `sk_user` (
    `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户id',
    `nickname` VARCHAR(255) NOT NULL COMMENT '昵称',
    `password` VARCHAR(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt',
    `salt` VARCHAR(10) DEFAULT NULL COMMENT '混淆盐',
    `head` VARCHAR(128) DEFAULT NULL COMMENT '头像，云存储的ID',
    `register_date` DATETIME DEFAULT NULL COMMENT '注册时间',
    `last_login_date` DATETIME DEFAULT NULL COMMENT '上次登录时间',
    `login_count` INT(11) DEFAULT NULL COMMENT '登录次数',
    PRIMARY KEY (`id`)
);

-- 创建商品表
CREATE TABLE IF NOT EXISTS `sk_goods` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `goods_name` VARCHAR(30) DEFAULT NULL COMMENT '商品名称',
    `goods_title` VARCHAR(64) DEFAULT NULL COMMENT '商品标题',
    `goods_img` VARCHAR(64) DEFAULT NULL COMMENT '商品图片',
    `goods_detail` LONGTEXT COMMENT '商品详情',
    `goods_price` DECIMAL(10,2) DEFAULT NULL,
    `goods_stock` INT(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
    PRIMARY KEY (`id`)
);

-- 创建秒杀商品表
CREATE TABLE IF NOT EXISTS `sk_goods_seckill` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品id',
    `goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品id',
    `seckill_price` DECIMAL(10,2) DEFAULT '0.00' COMMENT '秒杀价',
    `stock_count` INT(11) DEFAULT NULL COMMENT '库存数量',
    `start_date` DATETIME DEFAULT NULL COMMENT '秒杀开始时间',
    `end_date` DATETIME DEFAULT NULL COMMENT '秒杀结束时间',
    `version` INT(11) DEFAULT NULL COMMENT '并发版本控制',
    PRIMARY KEY (`id`)
);

-- 创建订单表
CREATE TABLE IF NOT EXISTS `sk_order` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(20) DEFAULT NULL,
    `order_id` BIGINT(20) DEFAULT NULL,
    `goods_id` BIGINT(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `u_uid_gid` (`user_id`,`goods_id`)
);

-- 创建订单详情表
CREATE TABLE IF NOT EXISTS `sk_order_info` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(20) DEFAULT NULL,
    `goods_id` BIGINT(20) DEFAULT NULL,
    `delivery_addr_id` BIGINT(20) DEFAULT NULL,
    `goods_name` VARCHAR(30) DEFAULT NULL,
    `goods_count` INT(11) DEFAULT NULL,
    `goods_price` DECIMAL(10,2) DEFAULT NULL,
    `order_channel` TINYINT(4) DEFAULT NULL COMMENT '订单渠道，1在线，2android，3ios',
    `status` TINYINT(4) DEFAULT NULL COMMENT '订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
    `create_date` DATETIME DEFAULT NULL,
    `pay_date` DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`)
);
