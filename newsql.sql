/*
SQLyog Community
MySQL - 10.4.25-MariaDB : Database - eder_app
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`eder_app` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `eder_app`;

/*Table structure for table `appoinments` */

DROP TABLE IF EXISTS `appoinments`;

CREATE TABLE `appoinments` (
  `appoinment_id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `reminder` varchar(100) DEFAULT NULL,
  `appoin_amount` varchar(100) DEFAULT NULL,
  `app_status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`appoinment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `appoinments` */

insert  into `appoinments`(`appoinment_id`,`doctor_id`,`user_id`,`date`,`time`,`status`,`reminder`,`appoin_amount`,`app_status`) values 
(2,3,2,'3/7/2023','10:59:00','paid','appointments ','500','paid');

/*Table structure for table `billdetails` */

DROP TABLE IF EXISTS `billdetails`;

CREATE TABLE `billdetails` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `bill_date` varchar(100) DEFAULT NULL,
  `bill_time` varchar(100) DEFAULT NULL,
  `bill_amount` varchar(100) DEFAULT NULL,
  `reminder` varchar(100) DEFAULT NULL,
  `bill_status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `billdetails` */

/*Table structure for table `doctors` */

DROP TABLE IF EXISTS `doctors`;

CREATE TABLE `doctors` (
  `doctor_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `qualification` varchar(100) DEFAULT NULL,
  `license` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `doctors` */

insert  into `doctors`(`doctor_id`,`login_id`,`fname`,`lname`,`place`,`phone`,`email`,`qualification`,`license`) values 
(1,10,'sds','dfd','fdfdyygg','8136840873','gfdg@f','nbhjb','1bvb'),
(2,11,'sara','mary','kochi','9870987890','sara@gmail.com','nbvghvhg','2nb nb'),
(3,15,'amruthq','ss','kochi','9876545678','am@gmail.com','13bbggf ','mbss');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `feedback` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`feedback_id`,`user_id`,`feedback`,`date`) values 
(1,1,'hlo','2-2-2022'),
(2,2,'gggg','2022-03-16'),
(3,2,'kkk','2022-03-16'),
(4,1,'good','2023-02-08');

/*Table structure for table `food` */

DROP TABLE IF EXISTS `food`;

CREATE TABLE `food` (
  `food_id` int(11) NOT NULL AUTO_INCREMENT,
  `appoinment_id` int(11) DEFAULT NULL,
  `doctor_id` int(11) DEFAULT NULL,
  `food_tyme` varchar(100) DEFAULT NULL,
  `food_details` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`food_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `food` */

insert  into `food`(`food_id`,`appoinment_id`,`doctor_id`,`food_tyme`,`food_details`) values 
(1,1,1,'Hvvvg','Hbvg'),
(2,1,2,'vbbn','6'),
(3,1,2,'Hgygr43e','12');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'dboy','dboy','delivery'),
(3,'shopshop','shopshop','mshop'),
(4,'admin','admin','admin'),
(5,'shop2shop2','shop2shop2','mshop'),
(6,'rtyuuiirrtyuu','sdfghjkdfg','mshop'),
(7,'user','user','user'),
(8,'ddd','ddd','delivery'),
(9,'z','z','user'),
(10,'d','d','doctor'),
(11,'s','s','doctor'),
(12,'s1','ss1','shop'),
(13,'s2','ss2','shop'),
(14,'shopshop12','shopshop12','mshop'),
(15,'doc','doc','doctor'),
(16,'gghhj','gghjj','user');

/*Table structure for table `medication` */

DROP TABLE IF EXISTS `medication`;

CREATE TABLE `medication` (
  `medication_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `reminder` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `doctor_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`medication_id`)
) ENGINE=InnoDB AUTO_INCREMENT=274 DEFAULT CHARSET=latin1;

/*Data for the table `medication` */

/*Table structure for table `medicinedetails` */

DROP TABLE IF EXISTS `medicinedetails`;

CREATE TABLE `medicinedetails` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `prescription_id` int(11) DEFAULT NULL,
  `medicine_id` int(11) DEFAULT NULL,
  `quantity` varchar(50) DEFAULT NULL,
  `rate` varchar(50) DEFAULT NULL,
  `total` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `medicinedetails` */

insert  into `medicinedetails`(`detail_id`,`prescription_id`,`medicine_id`,`quantity`,`rate`,`total`) values 
(2,1,3,'4','100','400'),
(3,2,3,'2','100','200'),
(4,4,3,'5','100','500');

/*Table structure for table `medicines` */

DROP TABLE IF EXISTS `medicines`;

CREATE TABLE `medicines` (
  `medicine_id` int(11) NOT NULL AUTO_INCREMENT,
  `medicalshop_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `medicine` varchar(50) DEFAULT NULL,
  `details` varchar(50) DEFAULT NULL,
  `rate` varchar(50) DEFAULT NULL,
  `quantity` varchar(50) DEFAULT NULL,
  `mdate` varchar(50) DEFAULT NULL,
  `edate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `medicines` */

insert  into `medicines`(`medicine_id`,`medicalshop_id`,`type_id`,`medicine`,`details`,`rate`,`quantity`,`mdate`,`edate`) values 
(2,1,1,'med1','asdfghj','10','500',NULL,NULL),
(3,1,3,'med1','asdfghj0','100','4993',NULL,NULL),
(5,1,3,'mm','eeeeeeee','55','8','2022-05-05','2022-05-31');

/*Table structure for table `news` */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `news_heading` varchar(50) DEFAULT NULL,
  `news_details` varchar(100) DEFAULT NULL,
  `news_date` varchar(100) DEFAULT NULL,
  `summry` varchar(1500) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

/*Data for the table `news` */

insert  into `news`(`type_id`,`news_heading`,`news_details`,`news_date`,`summry`) values 
(27,'xx','Having your database developers do a mountain of work in a short time frame is not the best way to e','2023-06-21 02:01:58','The parent tongue, called Proto-Indo-European, was spoken about 5,000 years ago by nomads believed to have roamed the southeastern European plains.Germanic, one of the language groups descended from this ancestral speech, is usually divided by scholars into three regional groups: East (Burgundian, Vandal, and Gothic, all extinct), North (Icelandic, Faroese, Norwegian, Swedish, and Danish), and West (German, Dutch and Flemish, Frisian, and English).'),
(28,',sjscvsgcgch',',mcnshcgcyudhwjd','2023-06-21 02:08:15','Modern English is analytic (i.e., relatively uninflected), whereas Proto-Indo-European, the ancestral tongue of most of the modern European languages (e.g., German, French, Russian, Greek), was synthetic, or inflected.\nDuring the course of thousands of years, English words have been slowly simplified from the inflected variable forms found in Sanskrit, Greek, Latin, Russian, and German, toward invariable forms, as in Chinese and Vietnamese.'),
(29,'A Look at the Match with Zheng','Entering the Caribbean Open','2023-06-21 02:34:16','So were Indonesia (chair of the Association of Southeast Asian Nations), South Korea, and Vietnam.Kei Nishikori celebrates his victory in the Caribbean Open mens singles final on June 18 in Palmas del Mar, Puerto Rico.\nKei Nishikori completed his memorable comeback tournament with a straight-sets victory over American teenager Michael Zheng on Sunday, June 18 in the Caribbean Open mens singles final.');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `pay_amount` varchar(50) DEFAULT NULL,
  `pay_date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

/*Table structure for table `prescription` */

DROP TABLE IF EXISTS `prescription`;

CREATE TABLE `prescription` (
  `pres_id` int(11) NOT NULL AUTO_INCREMENT,
  `appoinment_id` int(11) DEFAULT NULL,
  `file` varchar(1000) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pres_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `prescription` */

insert  into `prescription`(`pres_id`,`appoinment_id`,`file`,`date`,`status`) values 
(2,1,'static/0d33c2ec-bb00-4860-9c09-f85da75edfc2abc.jpg','2023-06-05','pending'),
(3,1,'static/75667574-207b-40e6-ab89-370e29cfefdfabc.jpg','2023-06-05','pending'),
(4,22,'static/690d9950-41fb-4216-915a-b3260c4383b9abc.jpg','2023-06-19','pending');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `rating_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `boy_id` int(11) DEFAULT NULL,
  `rated` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

insert  into `rating`(`rating_id`,`user_id`,`boy_id`,`rated`,`date`) values 
(1,1,1,'4','2-2-2021'),
(2,2,2,'4.5','2022-03-16');

/*Table structure for table `summary` */

DROP TABLE IF EXISTS `summary`;

CREATE TABLE `summary` (
  `summary_id` int(11) NOT NULL AUTO_INCREMENT,
  `news_id` int(11) DEFAULT NULL,
  `summary` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`summary_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `summary` */

insert  into `summary`(`summary_id`,`news_id`,`summary`) values 
(5,27,'The parent tongue, called Proto-Indo-European, was spoken about 5,000 years ago by nomads believed to have roamed the southeastern European plains.Germanic, one of the language groups descended from this ancestral speech, is usually divided by scholars into three regional groups: East (Burgundian, Vandal, and Gothic, all extinct), North (Icelandic, Faroese, Norwegian, Swedish, and Danish), and West (German, Dutch and Flemish, Frisian, and English).'),
(6,28,'Modern English is analytic (i.e., relatively uninflected), whereas Proto-Indo-European, the ancestral tongue of most of the modern European languages (e.g., German, French, Russian, Greek), was synthetic, or inflected.\nDuring the course of thousands of years, English words have been slowly simplified from the inflected variable forms found in Sanskrit, Greek, Latin, Russian, and German, toward invariable forms, as in Chinese and Vietnamese.'),
(7,29,'So were Indonesia (chair of the Association of Southeast Asian Nations), South Korea, and Vietnam.Kei Nishikori celebrates his victory in the Caribbean Open mens singles final on June 18 in Palmas del Mar, Puerto Rico.\nKei Nishikori completed his memorable comeback tournament with a straight-sets victory over American teenager Michael Zheng on Sunday, June 18 in the Caribbean Open mens singles final.');

/*Table structure for table `uploadprescription` */

DROP TABLE IF EXISTS `uploadprescription`;

CREATE TABLE `uploadprescription` (
  `prescription_id` int(11) NOT NULL AUTO_INCREMENT,
  `appoinment_id` int(11) DEFAULT NULL,
  `uploadfile` varchar(50) DEFAULT NULL,
  `totalamount` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`prescription_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `uploadprescription` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `dob` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`login_id`,`firstname`,`lastname`,`dob`,`phone`,`email`,`place`,`district`) values 
(1,7,'jif','rt','rtyu','8136840879','ertyu','sdfgh','h'),
(2,9,'Anu','anu','16-03-2000','8136840879','fhh@gmail.com','78965432190','alza'),
(3,16,'jjj','yhh','05-07-2023','9876543234','am@gmail.com','hhb','ghhhy');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
