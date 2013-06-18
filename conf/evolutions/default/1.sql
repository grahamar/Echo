# --- Initiali DB schema for identity management

# --- !Ups

create table users (
  email                     varchar(255) not null primary key,
  name                      varchar(255) not null,
  password                  varchar(255) not null
);

# --- !Downs

drop table if exists user;