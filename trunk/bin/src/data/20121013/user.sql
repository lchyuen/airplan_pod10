CREATE TABLE user(
id bigint not null auto_increment,
date_created timestamp not null default now(),
date_modified timestamp not null,
name varchar(100) not null,
email_address varchar(100) not null,
salt varchar(100) not null,
password varchar(1000) not null,
current_point_balance int not null default 0,
user_type int not null default 0,
session_token varchar(16) null default null,
session_expiry_date timestamp null default null,
PRIMARY KEY(id)
);