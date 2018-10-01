alter table seats
add column users_id bigint;
Alter table seats add constraint fk_seats_users
foreign key (users_id) references users(id);