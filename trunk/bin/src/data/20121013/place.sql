CREATE TABLE place
(id bigint not null auto_increment,
date_created timestamp not null,
date_modified timestamp not null,
name varchar(40) not null,
code varchar(10) not null,
is_first_class_lounge_avail boolean not null,
primary key (id)  );