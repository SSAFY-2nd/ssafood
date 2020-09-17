drop table if exists tag;
drop table if exists taglist;

create table `tag` (
	`tid` bigint not null auto_increment,
    `name` varchar(255) NOT NULL,
    `hits` bigint,
    primary key(`tid`),
    unique KEY(`name`)
) engine=InnoDB charset=utf8;

create table taglist (
    `pid` bigint not null,
    `tid` bigint not null,
    foreign key(`pid`) references post(`pid`) on delete cascade,
    foreign key(`tid`) references tag(`tid`) on delete cascade
) engine=InnoDB charset=utf8;

