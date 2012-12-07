-- For state:
-- 0 = Reserved
-- 1 = Paid
-- 2 = Boarding Pass Printed
-- 3 = Cancelled

CREATE TABLE ticket(
id bigint not null auto_increment,
flight_id bigint not null,
booking_id bigint not null,
date_created timestamp not null default now(),
date_modified timestamp not null,
seat_type varchar(30) not null,
seat_pref varchar(30) not null,
meal_pref varchar(30) not null,
passenger_name varchar(100) not null,
cost bigint not null,
state int not null,
FOREIGN KEY (flight_id) REFERENCES flight(id),
FOREIGN KEY (booking_id) REFERENCES booking(id),
PRIMARY KEY(id)
);

