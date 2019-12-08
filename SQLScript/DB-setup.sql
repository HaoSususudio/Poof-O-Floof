DROP SEQUENCE user_id_seq;
DROP SEQUENCE user_session_id_seq;
DROP SEQUENCE favorite_photo_id_seq;
DROP SEQUENCE photo_view_id_seq;
DROP SEQUENCE animal_id_seq;
DROP TABLE favorite;
DROP TABLE user_login;
DROP TABLE user_session;
DROP TABLE user_info;
DROP TABLE photo;
DROP TABLE animal;


/************************************
Tables and sequences
************************************/
-- we can do this multiple ways, Have a user login through Email, OR a username, OR both. 
-- display name is for sending messages
CREATE SEQUENCE user_id_seq;
CREATE SEQUENCE user_session_id_seq;
CREATE SEQUENCE favorite_photo_id_seq;
CREATE SEQUENCE photo_view_id_seq;
CREATE SEQUENCE animal_id_seq;

CREATE TABLE user_info (
    user_id number PRIMARY KEY,
    current_ip varchar2(50) NOT NULL,
    current_ip_location varchar2(50) NOT NULL,
    display_name varchar2(50) UNIQUE
);

CREATE TABLE user_login (
    email varchar2(50) PRIMARY KEY,
    secure_key varchar2(50) NOT NULL,
    salt varchar2(50) NOT NULL,
    user_id number NOT NULL REFERENCES user_info(user_id)
);

CREATE TABLE user_session (
  session_id number PRIMARY KEY,
  user_id number NOT NULL REFERENCES user_info(user_id),
  ip varchar2(50) NOT NULL,
  ip_location varchar2(50) NOT NULL,
  session_start TIMESTAMP,
  session_end TIMESTAMP
);

--mostly placeholder
--currently animal basic, may change to animal full
CREATE TABLE animal (
    animal_id number PRIMARY KEY,
    animal_type varchar2(50),
    species varchar2(50),
    age varchar2(50),
    gender varchar2(50),
    animal_size varchar2(50),
    url varchar2(200)
);

CREATE TABLE photo (
  photo_id number PRIMARY KEY,
  animal_id REFERENCES animal(animal_id),
  url varchar2(200)
);

--table for storing favorite photos
CREATE TABLE favorite (
    photo_id number REFERENCES photo(photo_id),
    user_id number REFERENCES user_info(user_id)
);

INSERT INTO animal (animal_id, animal_type, species, age, gender, animal_size, url)
    VALUES (animal_id_seq.nextval, 'tarantula', 'red knee', '10000', 'nb', '10000', 'beware.au');

commit;