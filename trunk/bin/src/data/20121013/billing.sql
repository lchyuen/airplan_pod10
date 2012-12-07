CREATE TABLE billing
(id bigint not null auto_increment,
date_created timestamp not null,
date_modified timestamp not null,
user_id bigint not null,
booking_id bigint not null,
amount bigint not null,
name varchar(100) default null,
credit_card_number varchar(250) default null,
is_loyalty_points_used tinyint(1) not null default 0,
primary key (id),
FOREIGN KEY (user_id) REFERENCES user(id),
FOREIGN KEY (booking_id) REFERENCES booking(id)
);