CREATE SEQUENCE seats_id_seq;

create table seats (
    id bigint not null DEFAULT NEXTVAL('seats_id_seq'),
    row  int not null,
    col int not null,
    status int default 0,
    primary key (id)
);

ALTER SEQUENCE seats_id_seq OWNED BY seats.id;