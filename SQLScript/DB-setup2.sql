DROP SEQUENCE user_id_seq;
DROP SEQUENCE favorite_id_seq;
DROP SEQUENCE total_likes_id_seq;
DROP SEQUENCE daily_likes_id_seq;

DROP TABLE daily_likes;
DROP TABLE total_likes;
DROP TABLE favorite;
DROP TABLE animal;
DROP TABLE users;

-- Create the user table --
CREATE SEQUENCE user_id_seq;
CREATE TABLE users (
    user_id number PRIMARY KEY,                 -- = user ID. 
    current_ip varchar2(50) NOT NULL,           -- = the users IP?
    current_ip_location varchar2(50) NOT NULL,  -- = the users location?
    display_name varchar2(50) UNIQUE,           -- = their display name (for messages or interactions)
    email varchar2(50) UNIQUE,                  -- = the email of the user
    secure_key varchar2(50) NOT NULL,           -- = 
    salt varchar2(50) NOT NULL                  -- = 
);

INSERT INTO users (user_id, current_ip, current_ip_location, display_name, email, secure_key, salt)
    VALUES (user_id_seq.nextval, '?', '?', '?', '?', '?', '?');

-- animal_id can be linked to the id we get off of petfinder.com --
-- store basic information users might be interested.
CREATE TABLE animal (
  animal_id number PRIMARY KEY, -- = Primay key can be the actual animal id on petfinder
  animal_type varchar2(30),     -- = Dog, Cat, Bird, etc.
  species varchar2(30),         -- = Huskey, Lab, etc
  age NUMBER,                   -- = age
  gender varchar2(10),          -- = gender
  animal_size NUMBER                   -- = of the animal
);

-- favorite animal, so we can re-get the information on that animal
-- ALSO USED AS FAVORITE PICTURE
CREATE SEQUENCE favorite_id_seq;
CREATE TABLE favorite (
    favorite_id number PRIMARY KEY,         -- = 
    user_id number NOT NULL                 -- = 
        REFERENCES users(user_id),
    animal_id number                        --changed type to number
        REFERENCES animal (animal_id)       --added reference
);

-- table for the total number of likes for all animals (cats, dogs, birds, pigs, etc)
CREATE SEQUENCE total_likes_id_seq;
CREATE TABLE total_likes (
    total_likes_id number PRIMARY KEY,
    species varchar2(15),
    likes number
);

-- table for the total number of likes for all animals (cats, dogs, birds, pigs, etc)
CREATE SEQUENCE daily_likes_id_seq;
CREATE TABLE daily_likes (
    daily_likes_id number PRIMARY KEY,
    species varchar2(15),
    likes number,
    current_date TIMESTAMP
);

--test entries
INSERT INTO animal (animal_id, animal_type, species, age, gender, animal_size)
    VALUES (666, 'tarantula', 'red knee', '10000', 'nb', '10000');
    
INSERT INTO users (user_id, current_ip, current_ip_location, display_name, email, secure_key, salt)
    VALUES (user_id_seq.nextval, 'fake ip', 'fake location', 'fake name', 'fake email', 'fake key', 'pass');
    
INSERT INTO favorite (favorite_id, user_id, animal_id)
    VALUES (favorite_id_seq.nextval, 1, 666);
    
commit;
