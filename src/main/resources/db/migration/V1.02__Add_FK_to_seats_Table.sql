alter table seats
add column users_id bigint not null;
Alter table seats add constraint fk_seats_users
foreign key (users_id) references seats(id);