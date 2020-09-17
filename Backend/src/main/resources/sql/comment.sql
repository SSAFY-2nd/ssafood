create table `comment` (
	`cid` bigint auto_increment,
    `pid` bigint,
    `uid` bigint,
    `parent` bigint default 0,
    `registrationTime` timestamp default now() not null,
    `author` varchar(255) not null,
    `content` varchar(255) not null,
    foreign key(`pid`) references post(pid) on delete cascade,
    foreign key(`uid`) references user(uid) on delete cascade,
    foreign key(`parent`) references  comment(cid) on delete cascade,
    primary key(`cid`)
) engine=innoDB default charset=utf8;