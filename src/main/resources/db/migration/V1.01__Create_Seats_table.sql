CREATE SEQUENCE seats_id_seq;


create table seats (
    id bigint not null DEFAULT NEXTVAL('seats_id_seq'),
    row int,
    col int,
    status varchar (255),
    primary key (id)
);

ALTER SEQUENCE seats_id_seq OWNED BY seats.id;