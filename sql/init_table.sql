-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: foodie
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carousel`
--

DROP TABLE IF EXISTS `carousel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `carousel` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `image_url` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '图片 图片地址',
  `background_color` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '背景色 背景颜色',
  `item_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商品id 商品id',
  `cat_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商品分类id 商品分类id',
  `type` int(11) NOT NULL COMMENT '轮播图类型 轮播图类型，用于判断，可以根据商品id或者分类进行页面跳转，1：商品 2：分类',
  `sort` int(11) NOT NULL COMMENT '轮播图展示顺序 轮播图展示顺序，从小到大',
  `is_show` int(11) NOT NULL COMMENT '是否展示 是否展示，1：展示    0：不展示',
  `create_time` datetime NOT NULL COMMENT '创建时间 创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间 更新',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='轮播图 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carousel`
--

LOCK TABLES `carousel` WRITE;
/*!40000 ALTER TABLE `carousel` DISABLE KEYS */;
/*!40000 ALTER TABLE `carousel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 分类id主键',
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '分类名称 分类名称',
  `type` int(11) NOT NULL COMMENT '分类类型 分类得类型，\n1:一级大分类\n2:二级分类\n3:三级小分类',
  `father_id` int(11) NOT NULL COMMENT '父id 父id 上一级依赖的id，1级分类则为0，二级三级分别依赖上一级',
  `logo` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '图标 logo',
  `slogan` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '口号',
  `cat_image` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '分类图',
  `bg_color` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '背景颜色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品分类 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `items` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品主键id',
  `item_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '商品名称 商品名称',
  `cat_id` int(11) NOT NULL COMMENT '分类外键id 分类id',
  `root_cat_id` int(11) NOT NULL COMMENT '一级分类外键id 一级分类id，用于优化查询',
  `sell_counts` int(11) NOT NULL COMMENT '累计销售 累计销售',
  `on_off_status` int(11) NOT NULL COMMENT '上下架状态 上下架状态,1:上架 2:下架',
  `content` text COLLATE utf8_bin NOT NULL COMMENT '商品内容 商品内容',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items_comments`
--

DROP TABLE IF EXISTS `items_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `items_comments` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'id主键',
  `user_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户id 用户名须脱敏',
  `item_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品id',
  `item_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `item_spec_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '商品规格id 可为空',
  `sepc_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '规格名称 可为空',
  `comment_level` int(11) NOT NULL COMMENT '评价等级 1：好评 2：中评 3：差评',
  `content` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '评价内容',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品评价表 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items_comments`
--

LOCK TABLES `items_comments` WRITE;
/*!40000 ALTER TABLE `items_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `items_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items_img`
--

DROP TABLE IF EXISTS `items_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `items_img` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '图片主键',
  `item_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品外键id 商品外键id',
  `url` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '图片地址 图片地址',
  `sort` int(11) NOT NULL COMMENT '顺序 图片顺序，从小到大',
  `is_main` int(11) NOT NULL COMMENT '是否主图 是否主图，1：是，0：否',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品图片 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items_img`
--

LOCK TABLES `items_img` WRITE;
/*!40000 ALTER TABLE `items_img` DISABLE KEYS */;
/*!40000 ALTER TABLE `items_img` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items_param`
--

DROP TABLE IF EXISTS `items_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `items_param` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品参数id',
  `item_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '商品外键id',
  `produc_place` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '产地 产地，例：中国江苏',
  `foot_period` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '保质期 保质期，例：180天',
  `brand` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '品牌名 品牌名，例：三只大灰狼',
  `factory_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '生产厂名 生产厂名，例：大灰狼工厂',
  `factory_address` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '生产厂址 生产厂址，例：大灰狼生产基地',
  `packaging_method` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '包装方式 包装方式，例：袋装',
  `weight` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '规格重量 规格重量，例：35g',
  `storage_method` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '存储方法 存储方法，例：常温5~25°',
  `eat_method` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '食用方式 食用方式，例：开袋即食',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品参数 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items_param`
--

LOCK TABLES `items_param` WRITE;
/*!40000 ALTER TABLE `items_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `items_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items_spec`
--

DROP TABLE IF EXISTS `items_spec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `items_spec` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品规格id',
  `item_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品外键id',
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '规格名称',
  `stock` int(11) NOT NULL COMMENT '库存',
  `discounts` decimal(4,2) NOT NULL COMMENT '折扣力度',
  `price_discount` int(11) NOT NULL COMMENT '优惠价',
  `price_normal` int(11) NOT NULL COMMENT '原价',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items_spec`
--

LOCK TABLES `items_spec` WRITE;
/*!40000 ALTER TABLE `items_spec` DISABLE KEYS */;
/*!40000 ALTER TABLE `items_spec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_items` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `order_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '归属订单id',
  `item_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品id',
  `item_img` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '商品图片',
  `item_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `item_spec_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '规格id',
  `item_spec_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '规格名称',
  `price` int(11) NOT NULL COMMENT '成交价格',
  `buy_counts` int(11) NOT NULL COMMENT '购买数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单商品关联表 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_status`
--

DROP TABLE IF EXISTS `order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_status` (
  `order_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '订单ID 对应订单表的主键id',
  `order_status` int(11) NOT NULL COMMENT '订单状态',
  `created_time` datetime DEFAULT NULL COMMENT '订单创建时间 对应[10:待付款]状态',
  `pay_time` datetime DEFAULT NULL COMMENT '支付成功时间 对应[20:已付款，待发货]状态',
  `deliver_time` datetime DEFAULT NULL COMMENT '发货时间 对应[30：已发货，待收货]状态',
  `success_time` datetime DEFAULT NULL COMMENT '交易成功时间 对应[40：交易成功]状态',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间 对应[50：交易关闭]状态',
  `comment_time` datetime DEFAULT NULL COMMENT '留言时间 用户在交易成功后的留言时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单状态表 订单的每个状态更改都需要进行记录\n10：待付款  20：已付款，待发货  30：已发货，待收货（7天自动确认）  40：交易成功（此时可以评价）50：交易关闭（待付款时，用户取消 或 长时间未付款，系统识别后自动关闭）\n退货/退货，此分支流程不做，所以不加入';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_status`
--

LOCK TABLES `order_status` WRITE;
/*!40000 ALTER TABLE `order_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '订单主键 同时也是订单编号',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `receiver_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '收货人快照',
  `receiver_mobile` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '收货人手机号快照',
  `receiver_address` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '收货地址快照',
  `total_amount` int(11) NOT NULL COMMENT '订单总价格',
  `real_pay_amount` int(11) NOT NULL COMMENT '实际支付总价格',
  `post_amount` int(11) NOT NULL DEFAULT '0' COMMENT '邮费 默认可以为零，代表包邮',
  `pay_method` int(11) NOT NULL COMMENT '支付方式 1:微信 2:支付宝',
  `left_msg` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '买家留言',
  `extand` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '扩展字段',
  `is_comment` int(11) NOT NULL COMMENT '买家是否评价 1：已评价，0：未评价',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除状态 1: 删除 0:未删除',
  `created_time` datetime NOT NULL COMMENT '创建时间（成交时间）',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单表 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_address` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '地址主键id',
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '关联用户id',
  `receiver` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '收件人姓名',
  `mobile` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '收件人手机号',
  `province` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '省份',
  `city` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '城市',
  `district` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '区县',
  `detail` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '详细地址',
  `extand` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '扩展字段',
  `is_default` int(11) DEFAULT NULL COMMENT '是否默认地址 1:是  0:否',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户地址表 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '主键id 用户id',
  `username` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名 用户名',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码 密码',
  `nickname` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称 昵称',
  `realname` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名 真实姓名',
  `face` varchar(1024) COLLATE utf8_bin NOT NULL COMMENT '头像 头像',
  `mobile` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号 手机号',
  `email` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱地址 邮箱地址',
  `sex` int(11) DEFAULT NULL COMMENT '性别 性别 1:男  0:女  2:保密',
  `birthday` date DEFAULT NULL COMMENT '生日 生日',
  `created_time` datetime NOT NULL COMMENT '创建时间 创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间 更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-01 18:44:24
