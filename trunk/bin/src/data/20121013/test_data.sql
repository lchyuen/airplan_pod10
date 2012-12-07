INSERT INTO user (date_created, date_modified, name, email_address, password, salt, current_point_balance, user_type, session_token, session_expiry_date) VALUES 
(now(), now(), "user1", "user1@hotmail.com", "46f06d8e2be990ad", "eaa4fba3-5009-48", 0, 0, "e68f758e5bb2aaaa",now()),
(now(), now(), "user2", "user2@hotmail.com", "7a1ecf56da38e807", "a5c2470b-bdb7-49", 1000000, 0, "c49d605d5d6fe873",now()),
(now(), now(), "admin1", "admin1@ryanair.com", "20ed23c5d8680726", "f64a16e9-7580-4e", 0, 1, "142960124844653f",now()),
(now(), now(), "admin2", "admin2@ryanair.com", "5af08e0f83f99a69", "6fb29f17-60f9-4d", 0, 1, "201ca6b7cdec7faa",now()),
(now(), now(), "admin3", "admin3@ryanair.com", "58cd2f67c8ea29b6", "5dbfabfc-f5ac-44", 0, 1, "63e1cbcec4e71cae",now()),
(now(), now(), "manager1", "manager1@ryanair.com", "beb8f6e8a249ea47", "09e91f27-8a79-4a", 0, 2, "6afd9ddae350668e",now()),
(now(), now(), "manager2", "manager2@ryanair.com", "528e22c11ce490e9", "b2bf399b-bed1-42", 0, 2, "6c53532d737408ae",now());


INSERT INTO booking(date_created, date_modified, user_id) VALUES
(now(), now(), 1),
(now(), now(), 2);


INSERT INTO ticket	(flight_id, booking_id, seat_type, seat_pref, meal_pref, passenger_name, cost, state) VALUES
(1, 1, 1, 'No Preference', 'No Preference', 'Ryan', 599, 1),
(2, 1, 1, 'No Preference', 'No Preference', 'Anuj', 599, 1),
(3, 1, 1, 'No Preference', 'No Preference', 'Yonatan', 599, 1),
(4, 1, 1, 'No Preference', 'No Preference', 'Scott', 599, 1),
(5, 1, 1, 'No Preference', 'No Preference', 'Nathaniel', 599, 1),
(6, 1, 1, 'No Preference', 'No Preference', 'Danny', 599, 1),
(7, 1, 1, 'No Preference', 'No Preference', 'Kevin', 599, 1),
(8, 2, 2, 'No Preference', 'No Preference', 'Ryan', 2995, 1),
(9, 2, 2, 'No Preference', 'No Preference', 'Anuj', 2995, 1),
(10, 2, 2, 'No Preference', 'No Preference', 'Yonatan', 2995, 1),
(11, 2, 2, 'No Preference', 'No Preference', 'Scott', 2995, 1),
(12, 2, 2, 'No Preference', 'No Preference', 'Nathaniel', 2995, 1),
(13, 2, 2, 'No Preference', 'No Preference', 'Danny', 2995, 1),
(14, 2, 2, 'No Preference', 'No Preference', 'Kevin', 2995, 1);


INSERT INTO billing (user_id, booking_id, amount, name, credit_card_number, is_loyalty_points_used) VALUES 
(1, 1, 5391, "Anuj", "4111111111111111", 0),
(2, 2, 26955, "Not Anuj", "4111111111111112", 0);

INSERT INTO staff (user_id, place_id) VALUES 
(3, 28),
(4, 21),
(5, 1);