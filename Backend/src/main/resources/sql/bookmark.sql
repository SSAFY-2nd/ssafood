create table bookmark (
    `uid` bigint not null,
    `pid` bigint not null,
	`registartionTime` timestamp default now() not null,
    foreign key(`pid`) references post(`pid`) on delete cascade,
    foreign key(`uid`) references user(`uid`) on delete cascade
) engine=InnoDB charset=utf8;