create database if not exists `myCrawler`;
use `myCrawler`;

drop table if exists `doneList`;

create table `doneList` (
	`id` int not null auto_increment,
    `web` varchar(30) default null,
    `board` varchar(30) default null,
    `article` varchar(255) default null,
    `articleDate` varchar(10) default null,
    `createAt` datetime default CURRENT_TIMESTAMP,
    primary key (`id`)
);

drop table if exists `website`;

create table `website` (
	`id` int not null auto_increment,
    `web` varchar(30) default null,
    `board` varchar(30) default null,
    `createAt` datetime default CURRENT_TIMESTAMP,
    primary key (`id`)
);