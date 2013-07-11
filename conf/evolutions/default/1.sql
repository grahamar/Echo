# --- Initiali DB schema for identity management

# --- !Ups

CREATE TABLE account (
    id         integer NOT NULL PRIMARY KEY,
    email      text NOT NULL UNIQUE,
    password   text NOT NULL,
    name       text NOT NULL,
    permission text NOT NULL
);

CREATE TABLE feedback (
    id         		integer NOT NULL PRIMARY KEY,
    receiver   		integer NOT NULL,
    rating     		integer NOT NULL,
    comment    		text,
    originalSender  integer NOT NULL,
    createdBy       integer NOT NULL,
    created			datetime NOT NULL
);

# --- !Downs

drop table if exists account;
drop table if exists feedback;