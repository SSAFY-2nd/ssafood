drop table if exists post;

create table `post` (
	`pid` bigint auto_increment,
    `uid` bigint ,
    `publishedTime` timestamp default now() not null,
    `author` varchar(255) not null,
    `title` varchar(255) not null,
    `content` longtext not null,
    `hits` bigint,
    `likes` bigint,
    `thumbnail` varchar(255) ,
    `isTemp` boolean default false,
    `category` varchar(255) ,
    foreign key(`uid`) references user(uid) on delete cascade,
	primary key(`pid`)
) engine=innoDB default charset=utf8;