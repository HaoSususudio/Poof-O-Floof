DROP TABLE favorite_photo;
DROP TABLE photo_view;
DROP TABLE animal;
-------Create favorite_photo table later for favorite functionality-------
CREATE TABLE favorite_photo (
  favorite_photo_id number PRIMARY KEY,
  user_id REFERENCES user_info(user_id),
  photo_id REFERENCES photo(photo_id)
);

--photo_view table is to store the relationship of each photo to each user
--Create photo_view table later to:
--1. Store if a user viewed a certain phtoto. Zero for no. One for yes.
--2. Store the preference of a user for a photo. 
--Zero for no preference (default). Plus one for like. Minus one for dislike. 
CREATE TABLE photo_view (
  photo_view_id number PRIMARY KEY,
  photo_id REFERENCES photo(photo_id),
  user_viewed number(1) DEFAULT 0,
  user_preference number(1) DEFAULT 0
);

--Create Animal table if we decided to cache petfinder.com DB---
--current plan is to store animal_id only in the TABLE photo, and fetch addtional data via their API as needed-----
CREATE TABLE animal (
  animal_id number PRIMARY KEY
  animal_type varchar2(30),
  species varchar2(30),
  breed_primary varchar2(70),
  breed_secondary varchar2(70),
  breed_mixed varchar2(5),
  color_primary varchar2(30),
  color_secondary varchar2(30),
  color_tertiary varchar2(30),
  age varchar2(30)
  gender varchar2(10),
  size varchar2(30),
  coat varchar2(30),
  status varchar2(30),
  url varchar2(200)
);


-- messages being sent from one user to another.
-- animal_id is so that we can reget the pictures.
CREATE SEQUENCE message_id_seq;
CREATE TABLE message (
    message_id number PRIMARY KEY,
    sender_id number NOT NULL
        REFERENCES users(user_id),
    receiver_id number NOT NULL
        REFERENCES users(user_id),
    animal_id varchar2(15), 
    messages varchar2(100) UNIQUE NOT NULL,
    -- if I remember correctly, number(1,0) should only allow 0 to 9 (single digit numbers)
    read number(1, 0) NOT NULL,
    message_date TIMESTAMP
);
-- favorite animal, so we can reget the information on that animal
CREATE SEQUENCE favorite_id_seq;
CREATE TABLE favorite (
    favorite_id number PRIMARY KEY,
    user_id number NOT NULL
        REFERENCES users(user_id),
    animal_id varchar2(15)
);
-- table for the total number of likes for all animals (cats, dogs, birds, pigs, etc)
CREATE SEQUENCE total_likes_id_seq;
CREATE TABLE total_likes (
    total_likes_id number PRIMARY KEY,
    specie varchar2(15),
    likes number
);
-- table for the total number of likes for all animals (cats, dogs, birds, pigs, etc)
CREATE SEQUENCE daily_likes_id_seq;
CREATE TABLE daily_likes (
    daily_likes_id number PRIMARY KEY,
    specie varchar2(15),
    likes number,
    current_date TIMESTAMP
);
/**********************************
*       Insert Table Values       *
**********************************/