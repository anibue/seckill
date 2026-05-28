-- V2__init_data.sql
-- 插入测试用户
INSERT INTO `sk_user` (`id`, `nickname`, `password`, `salt`, `register_date`, `last_login_date`, `login_count`) VALUES
('18181818181', 'jesper', 'b7797cce01b4b131b433b6acf4add449', '1a2b3c4d', '2018-05-21 21:10:21', '2018-05-21 21:10:25', 1),
('18217272828', 'jesper', 'b7797cce01b4b131b433b6acf4add449', '1a2b3c4d', '2018-05-21 21:10:21', '2018-05-21 21:10:25', 1);

-- 插入商品
INSERT INTO `sk_goods` (`id`, `goods_name`, `goods_title`, `goods_img`, `goods_detail`, `goods_price`, `goods_stock`) VALUES
(1, 'iphoneX', 'Apple/苹果iPhone X 全网通4G手机苹果X 10', '/img/iphonex.png', 'Apple/苹果iPhone X 全网通4G手机苹果X 10', 7788.00, 100),
(2, '华为 Mate 10', 'Huawei/华为 Mate 10 6G+128G 全网通4G智能手机', '/img/meta10.png', 'Huawei/华为 Mate 10 6G+128G 全网通4G智能手机', 4199.00, 50);

-- 插入秒杀商品
INSERT INTO `sk_goods_seckill` (`id`, `goods_id`, `seckill_price`, `stock_count`, `start_date`, `end_date`, `version`) VALUES
(1, 1, 0.01, 8, '2018-05-22 17:22:52', '2018-05-22 18:23:00', 0),
(2, 2, 0.01, 8, '2018-04-29 22:56:10', '2018-05-01 22:56:15', 0);
