DROP USER project2 CASCADE;

CREATE USER project2
IDENTIFIED BY p4ssw0rd
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;
GRANT CONNECT TO project2;
GRANT RESOURCE TO project2;
GRANT CREATE SESSION TO project2;
GRANT CREATE TABLE TO project2;
GRANT CREATE VIEW TO project2;
conn project2/p4ssw0rd;


/************************************
Tables and sequences
************************************/
-- Create the user table --
CREATE SEQUENCE user_id_seq;
CREATE TABLE users (
    user_id number PRIMARY KEY,                 -- = user ID. 
    current_ip varchar2(50) NOT NULL,           -- = the users IP?
    current_ip_location varchar2(50) NOT NULL,  -- = the users location?
    display_name varchar2(50) UNIQUE,           -- = their display name (for messages or interactions)
    email varchar2(50),                  -- = the email of the user
    secure_key varchar2(50),                    -- = 
    salt varchar2(50)                           -- = 
);

-- animal_id can be linked to the id we get off of petfinder.com --
-- store basic information users might be interested.
--CREATE TABLE animal (
--  animal_id number PRIMARY KEY, -- = Primay key can be the actual animal id on petfinder
--  animal_type varchar2(30),     -- = Dog, Cat, Bird, etc.
--  species varchar2(30),         -- = Huskey, Lab, etc
--  age varchar2(10),                   -- = age
--  gender varchar2(10),          -- = gender
--  animal_size varchar2(10)                   -- = of the animal
--);

CREATE SEQUENCE photo_id_seq;
CREATE TABLE photo (
    photo_id number PRIMARY KEY,
    animal_id number,
    user_id number NOT NULL                 -- = 
        REFERENCES users(user_id),
    full_url varchar2(100),
    adoption_url varchar2(100)
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
-- NOT BEING USED
--INSERT INTO animal (animal_id, animal_type, species, age, gender, animal_size)
--    VALUES (666, 'tarantula', 'red knee', '10000', 'nb', '10000');
    


--INSERT INTO users (user_id, current_ip, current_ip_location, display_name, email, secure_key, salt)
--    VALUES (user_id_seq.nextval, '?', '?', '?', '?', '?', '?');
--    

--INSERT INTO users (user_id, current_ip, current_ip_location, display_name, email, secure_key, salt)
--    VALUES (user_id_seq.nextval, '?', '?', 'user', '?', '?', 'pass');
--
--INSERT INTO photo (animal_id, photo_id, full_url, )
--    VALUES (666, 123, 'dogpicture1.com');
--
--INSERT INTO photo (animal_id, photo_id, full_url)
--    VALUES (4522, 800, 'dogpicture2.com');
--    
--INSERT INTO photo (animal_id, photo_id, full_url)
--    VALUES (473, 324, 'dogpicture3.com');
--    
--INSERT INTO photo (animal_id, photo_id, full_url)
--    VALUES (473, 325, 'dogpicture4.com');
--
--INSERT INTO favorite (favorite_id, user_id, photo_id)
--    VALUES (favorite_id_seq.nextval, 1, 123);
--    
--INSERT INTO favorite (favorite_id, user_id, photo_id)
--    VALUES (favorite_id_seq.nextval, 1, 324);
--    
--INSERT INTO favorite (favorite_id, user_id, photo_id)
--    VALUES (favorite_id_seq.nextval, 2, 123);
--
--SELECT photo_id FROM photo WHERE photo_id = 325;
--
--SELECT * FROM favorite f INNER JOIN photo p ON (f.photo_id = p.photo_id) WHERE user_id = 1;

commit;
