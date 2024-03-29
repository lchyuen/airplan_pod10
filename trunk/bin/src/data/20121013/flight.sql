CREATE TABLE flight
(id bigint not null auto_increment,
date_created timestamp not null,
date_modified timestamp not null,
origin varchar(20) not null,
destination varchar(20) not null,
departure_time timestamp not null,
arrival_time timestamp not null,
actual_departure_time timestamp null default null,
actual_arrival_time timestamp null default null,
airline_name varchar(20) not null,
total_first_class int not null default 50,
first_class_avail int not null default 50,
total_economy int not null default 200,
economy_avail int not null default 200,
base_cost int not null,
primary key (id)  );