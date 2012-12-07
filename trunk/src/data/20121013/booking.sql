
CREATE TABLE booking(
	id bigint not null auto_increment,
	date_created timestamp not null,
	date_modified timestamp not null,
	user_id bigint not null,
	cancel_state tinyint(1) not null default 1,
	FOREIGN KEY (user_id) REFERENCES user(id),
	PRIMARY KEY (id)
);

