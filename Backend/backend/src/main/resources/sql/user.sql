create table `user` (
    `uid` bigint not null auto_increment,
    `email` varchar(255) not null,
    `nickname` varchar(255) not null,
    `password` varchar(255) not null,
    `profileImage` varchar(255) DEFAULT NULL,
    `qrImage` varchar(255) DEFAULT NULL,
    `admin` tinyint not null,
    `facebook` varchar(255) default null,
    `instagram` varchar(255) default null,
    `github` varchar(255) default null,
    `introduce` varchar(500) default null,
    primary key (`uid`),
    unique key (`email`)
) engine=InnoDB charset=utf8