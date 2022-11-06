CREATE SEQUENCE IF NOT EXISTS user_seq;
CREATE SEQUENCE IF NOT EXISTS friend_seq;
CREATE SEQUENCE IF NOT EXISTS event_seq;

CREATE TABLE IF NOT EXISTS users
(
    "id"               	INTEGER 		   PRIMARY KEY NOT NULL,
    "login"             VARCHAR(64)        UNIQUE NOT NULL,
    "salt"          	VARCHAR(50)        NOT NULL,
    "password"          VARCHAR(100)       NOT NULL,
    "email"             VARCHAR(128),
    "city"              VARCHAR(128),
    "first_name"        VARCHAR(128) 	   NOT NULL,
    "last_name"        	VARCHAR(128)       NOT NULL,
    "phone_number"      VARCHAR(11)        UNIQUE NOT NULL,
    "friends_number"    INTEGER            NOT NULL DEFAULT 0,
    "created_at"        TIMESTAMP          NOT NULL,
    "refresh_token"     TEXT
);

CREATE TABLE IF NOT EXISTS friends
(
    "id"                INTEGER            PRIMARY KEY NOT NULL,
    "status"            VARCHAR(10)        NOT NULL,
    "to_id"             INTEGER            NOT NULL,
    "from_id"           INTEGER            NOT NULL,

    CONSTRAINT fk_friend_from FOREIGN KEY ("from_id") REFERENCES users (id),
    CONSTRAINT fk_friend_to FOREIGN KEY ("to_id") REFERENCES users (id)
);

CREATE TABLE IF NOT EXIST events
(
	"id"                INTEGER            PRIMARY KEY NOT NULL,
	"name"              VARCHAR(128)       NOT NULL,
	"description"       TEXT,
	"datetime_start"    TIMESTAMP          NOT NULL,
	"datetime_end"      TIMESTAMP,
	"address"           VARCHAR(128)       NOT NULL,
	"max_members"       INTEGER,
	"age_min"           INTEGER,
	"age_max"           INTEGER,
	"is_online"         BOOLEAN 		   NOT NULL,
	"is_private"        BOOLEAN            NOT NULL,
	"creator_id"        INTEGER            NOT NULL,

	CONSTRAINT fk_event_creator FOREIGN KEY ("creator_id") REFERENCES users ("id")
);