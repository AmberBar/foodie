INSERT INTO `foodie`.`carousel` (`id`, `image_url`, `background_color`, `item_id`, `type`, `sort`, `is_show`, `create_time`, `update_time`) VALUES ('C-10011', 'http://122.152.205.72:88/group1/M00/00/05/CpoxxF0ZmHiAWwR7AAFdqZHw8jU876.png', '#f44661', 'nut-1004', '1', '1', '1', '2020-01-19', '2020-01-19');
INSERT INTO `foodie`.`carousel` (`id`, `image_url`, `background_color`, `cat_id`, `type`, `sort`, `is_show`, `create_time`, `update_time`) VALUES ('C-10012', 'http://122.152.205.72:88/group1/M00/00/05/CpoxxF0ZmG-ALsPRAAEX2Gk9FUg848.png', '#000240', '51', '2', '2', '1', '2020-01-19', '2020-01-19');
INSERT INTO `foodie`.`carousel` (`id`, `image_url`, `background_color`, `item_id`, `type`, `sort`, `is_show`, `create_time`, `update_time`) VALUES ('C-10015', 'http://122.152.205.72:88/group1/M00/00/05/CpoxxF0ZmHuAPlXvAAFe-H5_-Nw961.png', '#ff9801', 'cake-1006', '1', '3', '1', '2020-01-19', '2020-01-19');
INSERT INTO `foodie`.`carousel` (`id`, `image_url`, `background_color`, `cat_id`, `type`, `sort`, `is_show`, `create_time`, `update_time`) VALUES ('C-10021', 'http://122.152.205.72:88/group1/M00/00/05/CpoxxF0ZmH6AeuRrAAEZviPhyQ0768.png', '#55be59', '133', '2', '4', '1', '2020-01-19', '2020-01-19');
insert into category (id, name, type, father_id, logo, slogan, cat_image, bg_color)  values (1,"甜点/蛋糕",1, 0,"img/cake.png", "每一道甜品都能打开你的味蕾" , "http://122.152.205.72:88/foodie/category/cake.png" ,"#fe7a65");
insert into category (id, name, type, father_id, logo, slogan, cat_image, bg_color)  values (2,"饼干/膨化",1, 0,"img/cookies.png", "嘎嘣脆，一听到声音就开吃" , "http://122.152.205.72:88/foodie/category/cookies.png" ,"#f59cec");
insert into category (id, name, type, father_id, logo, slogan, cat_image, bg_color)  values (3,"熟食/肉类",1, 0,"img/meat.png", "食肉者最爱绝佳美食" , "http://122.152.205.72:88/foodie/category/meat.png" ,"#b474fe");
insert into category (id, name, type, father_id, logo, slogan, cat_image, bg_color)  values (4,"素食/卤味",1, 0,"img/luwei.png", "香辣甜辣麻辣，辣了才有味" , "http://122.152.205.72:88/foodie/category/duck.png" ,"#82ceff");
insert into category (id, name, type, father_id, logo, slogan, cat_image, bg_color)  values (5,"坚果/炒货",1, 0,"img/jianguo.png", "酥脆无比，休闲最佳" , "http://122.152.205.72:88/foodie/category/nut.png" ,"#c6a868");
insert into category (id, name, type, father_id, logo, slogan, cat_image, bg_color)  values (6,"糖果/蜜饯",1, 0,"img/sweet.png", "甜味是爱美者的最爱" , "http://122.152.205.72:88/foodie/category/mango.png" ,"#6bdea7");
insert into category (id, name, type, father_id, logo, slogan, cat_image, bg_color)  values (7,"巧克力",1, 0,"img/chocolate.png", "美容养颜，男女都爱" , "http://122.152.205.72:88/foodie/category/chocolate.png" ,"#f8c375");
insert into category (id, name, type, father_id, logo, slogan, cat_image, bg_color)  values (8,"海鲜/海味",1, 0,"img/lobster.png", "吃货们怎么能少了海鲜呢？" , "http://122.152.205.72:88/foodie/category/crab.png" ,"#84affe");
insert into category (id, name, type, father_id, logo, slogan, cat_image, bg_color)  values (9,"花茶/果茶",1, 0,"img/tea.png", "绿茶红茶怎能少得了" , "http://122.152.205.72:88/foodie/category/tea.png" ,"#ff9229");
insert into category (id, name, type, father_id, logo, slogan, cat_image, bg_color)  values (10,"生鲜/蔬果",1, 0,"img/food.png", "新鲜少不了，每日蔬果生鲜" , "http://122.152.205.72:88/foodie/category/meat2.png" ,"#6cc67c");

insert into category (id, name, type, father_id)  values (37,"蒸蛋糕",3, 11);
insert into category (id, name, type, father_id)  values (38,"软面包",3, 11);
insert into category (id, name, type, father_id)  values (39,"脱水蛋糕",3, 11);
insert into category (id, name, type, father_id)  values (40,"马卡龙",3, 11);
insert into category (id, name, type, father_id)  values (41,"甜甜圈",3, 11);
insert into category (id, name, type, father_id)  values (42,"三明治",3, 11);
insert into category (id, name, type, father_id)  values (43,"铜锣烧",3, 11);
insert into category (id, name, type, father_id)  values (44,"肉松饼",3, 12);
insert into category (id, name, type, father_id)  values (45,"华夫饼",3, 12);
insert into category (id, name, type, father_id)  values (46,"沙琪玛",3, 12);
insert into category (id, name, type, father_id)  values (47,"鸡蛋卷",3, 12);
insert into category (id, name, type, father_id)  values (48,"蛋饼",3, 12);
insert into category (id, name, type, father_id)  values (49,"凤梨酥",3, 12);
insert into category (id, name, type, father_id)  values (50,"手撕面包",3, 12);

insert into category (id, name, type, father_id)  values (12,"点心",2, 1);
insert into category (id, name, type, father_id)  values (11,"蛋糕",2, 1);


-- 商品
INSERT INTO `items` (`id`, `item_name`, `cat_id`, `root_cat_id`, `sell_counts`, `on_off_status`, `content`, `created_time`, `updated_time`) VALUES ('cake-1005', '【天天吃货】进口美食凤梨酥', '1', '0', '0', '66', 'hahah', '2020-04-23', '2020-04-23');
INSERT INTO `foodie`.`items_img` (`id`, `item_id`, `url`, `sort`, `is_main`, `created_time`, `updated_time`) VALUES ('1', 'cake-1005', 'http://122.152.205.72:88/foodie/cake-1005/img1.png', '1', '1', '2020-04-23 00:00:00', '2020-04-23 00:00:00');
UPDATE `foodie`.`items` SET `cat_id` = '11', `root_cat_id` = '1' WHERE (`id` = 'cake-1005');

INSERT INTO `items` (`id`, `item_name`, `cat_id`, `root_cat_id`, `sell_counts`, `on_off_status`, `content`, `created_time`, `updated_time`) VALUES ('cake-1006', '【天天吃货】机器猫最爱 铜锣烧 最美下午茶', '1', '0', '0', '66', 'hahah', '2020-04-23', '2020-04-23');
INSERT INTO `items_img` (`id`, `item_id`, `url`, `sort`, `is_main`, `created_time`, `updated_time`) VALUES ('2', 'cake-1006', 'http://122.152.205.72:88/foodie/cake-1006/img2.png', '1', '1', '2020-04-23 00:00:00', '2020-04-23 00:00:00');
UPDATE `foodie`.`items` SET `cat_id` = '11', `root_cat_id` = '1' WHERE (`id` = 'cake-1006');

INSERT INTO `items` (`id`, `item_name`, `cat_id`, `root_cat_id`, `sell_counts`, `on_off_status`, `content`, `created_time`, `updated_time`) VALUES ('cake-1001', '【天天吃货】真香预警 超级好吃 手撕面包 儿童早餐早饭', '11', '1', '0', '66', 'hahah', '2020-04-23', '2020-04-23');
INSERT INTO `items_img` (`id`, `item_id`, `url`, `sort`, `is_main`, `created_time`, `updated_time`) VALUES ('3', 'cake-1001', 'http://122.152.205.72:88/foodie/cake-1001/img1.png', '1', '1', '2020-04-23 00:00:00', '2020-04-23 00:00:00');

INSERT INTO `items` (`id`, `item_name`, `cat_id`, `root_cat_id`, `sell_counts`, `on_off_status`, `content`, `created_time`, `updated_time`) VALUES ('cake-1002', '【天天吃货】网红烘焙蛋糕 好吃的蛋糕', '11', '1', '0', '66', 'hahah', '2020-04-23', '2020-04-23');
INSERT INTO `items_img` (`id`, `item_id`, `url`, `sort`, `is_main`, `created_time`, `updated_time`) VALUES ('4', 'cake-1007', 'http://122.152.205.72:88/foodie/cake-1002/img1.png', '1', '1', '2020-04-23 00:00:00', '2020-04-23 00:00:00');

INSERT INTO `items` (`id`, `item_name`, `cat_id`, `root_cat_id`, `sell_counts`, `on_off_status`, `content`, `created_time`, `updated_time`) VALUES ('cake-1003', '【天天吃货】美味沙琪玛 超棒下午茶', '11', '1', '0', '66', 'hahah', '2020-04-23', '2020-04-23');
INSERT INTO `items_img` (`id`, `item_id`, `url`, `sort`, `is_main`, `created_time`, `updated_time`) VALUES ('5', 'cake-1007', 'http://122.152.205.72:88/foodie/cake-1004/img3.png', '1', '1', '2020-04-23 00:00:00', '2020-04-23 00:00:00');

INSERT INTO `items` (`id`, `item_name`, `cat_id`, `root_cat_id`, `sell_counts`, `on_off_status`, `content`, `created_time`, `updated_time`) VALUES ('cake-1004', '【天天吃货】超好吃华夫饼 美食诱惑 下午茶', '11', '1', '0', '66', 'hahah', '2020-04-23', '2020-04-23');
INSERT INTO `items_img` (`id`, `item_id`, `url`, `sort`, `is_main`, `created_time`, `updated_time`) VALUES ('6', 'cake-1007', 'http://122.152.205.72:88/foodie/cake-1004/img1.png', '1', '1', '2020-04-23 00:00:00', '2020-04-23 00:00:00');

UPDATE `foodie`.`items_img` SET `item_id` = 'cake-1004' WHERE (`id` = '6');
UPDATE `foodie`.`items_img` SET `item_id` = 'cake-1003', `url` = 'http://122.152.205.72:88/foodie/cake-1003/img1.png' WHERE (`id` = '5');
UPDATE `foodie`.`items_img` SET `url` = 'http://122.152.205.72:88/foodie/cake-1006/img1.png' WHERE (`id` = '2');
UPDATE `foodie`.`items_img` SET `item_id` = 'cake-1002' WHERE (`id` = '4');
