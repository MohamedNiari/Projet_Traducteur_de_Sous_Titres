CREATE DATABASE `translation` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `texte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `texteATraduire` varchar(200) NOT NULL,
  `texteTraduit` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26551 DEFAULT CHARSET=utf8;