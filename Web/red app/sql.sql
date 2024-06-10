/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - red_app
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`red_app` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `red_app`;

/*Table structure for table `availablebloods` */

DROP TABLE IF EXISTS `availablebloods`;

CREATE TABLE `availablebloods` (
  `ablood_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL,
  `allocated_id` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `stock` varchar(100) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ablood_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `availablebloods` */

insert  into `availablebloods`(`ablood_id`,`group_id`,`allocated_id`,`type`,`stock`,`date_time`) values 
(1,1,'1','blood_bank','1','2021-03-23 09:56:21'),
(2,4,'1','blood_bank','5','2021-03-23 09:56:28'),
(3,3,'1','blood_bank','5','2021-03-23 10:08:55'),
(4,1,'1','blood_bank','2','2021-03-24 15:33:13'),
(5,3,'1','blood_bank','2','2021-03-24 16:16:26'),
(6,5,'1','blood_bank','10','2021-03-24 16:16:42'),
(7,4,'1','blood_bank','10','2021-03-24 16:20:56'),
(8,7,'2','blood_bank','3','2023-02-07 14:29:20'),
(9,8,'1','blood_bank','1','2023-02-07 20:07:53');

/*Table structure for table `bloodbank` */

DROP TABLE IF EXISTS `bloodbank`;

CREATE TABLE `bloodbank` (
  `bank_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `bankname` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`bank_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `bloodbank` */

insert  into `bloodbank`(`bank_id`,`login_id`,`bankname`,`place`,`phone`,`email`,`longitude`,`latitude`) values 
(1,2,'red bank','ernamkulam','9536986557','general@kijh','76.28150939941406','9.972062915795036'),
(2,3,'RED','EKM','9865355877','BANK@TF','76.2800921904297','9.990295825246129'),
(3,10,'user qwerty','ernakaulam','9876543234','student@gmail.com','76.28003496822448','9.979560433325327');

/*Table structure for table `bloodgroups` */

DROP TABLE IF EXISTS `bloodgroups`;

CREATE TABLE `bloodgroups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `bloodgroups` */

insert  into `bloodgroups`(`group_id`,`group`) values 
(1,'A+'),
(3,'A-'),
(4,'B+'),
(5,'B-'),
(6,'AB+'),
(7,'AB-'),
(8,'O+');

/*Table structure for table `camp` */

DROP TABLE IF EXISTS `camp`;

CREATE TABLE `camp` (
  `camp_id` int(11) NOT NULL AUTO_INCREMENT,
  `organization_id` int(11) DEFAULT NULL,
  `allocates_id` int(11) DEFAULT NULL,
  `allocates_type` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`camp_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `camp` */

insert  into `camp`(`camp_id`,`organization_id`,`allocates_id`,`allocates_type`,`date`,`time`,`latitude`,`longitude`) values 
(1,1,1,'hospital','2021-03-24','02:07','9.986576516958147','76.27992052905275'),
(2,1,2,'blood_bank','2021-03-30','16:26','9.974345325387674','76.2839126586914'),
(3,1,2,'blood_bank','2023-02-19','14:33','9.976094601282911','76.28115881380172');

/*Table structure for table `campblood` */

DROP TABLE IF EXISTS `campblood`;

CREATE TABLE `campblood` (
  `cblood_id` int(11) NOT NULL AUTO_INCREMENT,
  `camp_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `no_of_units` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cblood_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `campblood` */

insert  into `campblood`(`cblood_id`,`camp_id`,`group_id`,`no_of_units`) values 
(1,1,1,'2'),
(2,1,3,'2'),
(3,3,7,'3');

/*Table structure for table `distribution` */

DROP TABLE IF EXISTS `distribution`;

CREATE TABLE `distribution` (
  `distribution_id` int(11) NOT NULL AUTO_INCREMENT,
  `receiver_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `unit_distribution` varchar(100) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`distribution_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `distribution` */

insert  into `distribution`(`distribution_id`,`receiver_id`,`group_id`,`unit_distribution`,`date_time`) values 
(1,2,5,'2','2023-02-07 20:30:39'),
(2,2,8,'2','2023-02-07 22:33:21'),
(3,7,6,'10','2023-03-06 13:14:06'),
(4,7,4,'2','2023-03-06 13:21:09'),
(5,9,4,'1','2023-03-06 21:06:53'),
(6,9,4,'2','2023-03-06 21:21:40');

/*Table structure for table `hospitals` */

DROP TABLE IF EXISTS `hospitals`;

CREATE TABLE `hospitals` (
  `hospital_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `hospital` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`hospital_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `hospitals` */

insert  into `hospitals`(`hospital_id`,`login_id`,`hospital`,`place`,`phone`,`email`,`latitude`,`longitude`) values 
(1,4,'ESIH','EKM','9865744558','eks@jhbg','9.994041010089063','76.28541469573975'),
(2,5,'Port Trust','Kochin','9865321478','kochin@ytgf','9.960988775707063','76.26906394958496');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin123','admin123','admin'),
(2,'bank','bank','blood_bank'),
(3,'bank1234','bank1234','blood_bank'),
(6,'org','org','organization'),
(7,'org1','org1','organization'),
(10,'bold','blod','blood_bank'),
(11,'orgen','orgen','organization'),
(26,'renu','renu','receiver'),
(25,'reshma','reshma','donor');

/*Table structure for table `organisation` */

DROP TABLE IF EXISTS `organisation`;

CREATE TABLE `organisation` (
  `organisation_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `organization_name` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`organisation_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `organisation` */

insert  into `organisation`(`organisation_id`,`login_id`,`organization_name`,`phone`,`email`) values 
(1,6,'blood','9865321447','blood@tgf'),
(2,7,'team red','9658987441','teamred@rd'),
(3,11,'qwertyuio','02345678907','student@gmail.com');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `requested_id` int(11) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `unit_required` varchar(100) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`user_id`,`requested_id`,`type`,`group_id`,`unit_required`,`date_time`,`status`) values 
(1,9,2,'pending',4,'1','2023-03-06 21:17:29','accepted'),
(2,8,0,'pending',1,'2','2023-03-07 13:42:06','reject');

/*Table structure for table `requirement_message` */

DROP TABLE IF EXISTS `requirement_message`;

CREATE TABLE `requirement_message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `bank_id` int(11) DEFAULT NULL,
  `donar_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `unit_required` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `requirement_message` */

insert  into `requirement_message`(`message_id`,`bank_id`,`donar_id`,`group_id`,`unit_required`,`description`) values 
(1,1,3,6,'2','needed'),
(2,2,8,1,'2','needed');

/*Table structure for table `status` */

DROP TABLE IF EXISTS `status`;

CREATE TABLE `status` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `donar_id` int(11) DEFAULT NULL,
  `donar_status` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `status` */

insert  into `status`(`status_id`,`donar_id`,`donar_status`,`date`) values 
(1,3,'availability','2023-02-07 23:18:46'),
(2,3,'availability','2023-03-06 12:52:24'),
(3,3,'availability','2023-03-06 12:52:31'),
(4,8,'availability','2023-03-07 10:10:09');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `age` varchar(100) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `utype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`login_id`,`first_name`,`last_name`,`age`,`group_id`,`phone`,`email`,`utype`) values 
(9,26,'renuka1','haiq','12',4,'1234567891','renu1@gmail.com','receiver'),
(8,25,'reshmae','sjijs','21',1,'1234567891','reshm@gmail.com','donor');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
