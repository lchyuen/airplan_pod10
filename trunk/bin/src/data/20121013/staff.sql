CREATE TABLE staff
(id bigint not null auto_increment,
user_id bigint not null,
place_id bigint not null,
primary key (id),
FOREIGN KEY (user_id) REFERENCES user(id),
FOREIGN KEY (place_id) REFERENCES place(id)
);