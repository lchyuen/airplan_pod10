CREATE TABLE points
(id bigint not null auto_increment,
date_created timestamp not null,
date_modified timestamp not null,
user_id bigint not null,
billing_id bigint not null,
amount int not null,
reason varchar(50) not null,
primary key (id),
FOREIGN KEY (user_id) REFERENCES user(id),
FOREIGN KEY (billing_id) REFERENCES billing(id)
);