DROP TABLE IF EXISTS `patient`;

CREATE TABLE `patient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(15) NOT NULL,
  `last_name` varchar(15) NOT NULL,
  `diagnosis` varchar(45) not null,
  `ins_number` varchar(15) NOT NULL,
  `doctor_name` varchar(20) NOT NULL,
  `status` ENUM('TREATED', 'DISCHARGED') not null,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`ins_number`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `treatment`;

create table `treatment` (
  `id` int not null auto_increment,
  `type` enum('DRUG', 'PROCEDURE') not null,
  `name` varchar(30) not null,
  primary key(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `appointment`;

CREATE TABLE `appointment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int DEFAULT NULL, 
  `treatment_id` int not null,
  `pattern_days` varchar(25),
  `receipt_times` varchar(20),
  `dosage` varchar(25) default null,
  `status` ENUM('VALID', 'INVALID') default null,
  `created_at` TIMESTAMP default NULL,
  `due_date` TIMESTAMP DEFAULT null,
  PRIMARY KEY(`id`),
  CONSTRAINT `FK_PATIENT` FOREIGN KEY (`patient_id`) 
  REFERENCES `patient` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  constraint `FK_TREATMENT` foreign key (`treatment_id`)
  references `treatment` (`id`) on delete no action on update no action
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `event`;

create table `event` (
  `id` int not null auto_increment,
  `appointment_id` int not null,
  `status` enum('SCHEDULED', 'COMPLETED', 'CANCELLED') not null,
  `commentary` varchar(30) default null,
  `date` timestamp not null,
  primary key(`id`),
  constraint `FK_APPOINMENT` foreign key (`appointment_id`)
  references `appointment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `user`;

create table `user`(
  `id` int not null auto_increment,
  `username` varchar(20) not null,
  `password` varchar(70) not null,
  `role` ENUM('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_NURSE') not null,
  `firstName` varchar(20) not null,
  `lastName` varchar(20) not null,
  Primary key(`id`),
  unique key(`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;