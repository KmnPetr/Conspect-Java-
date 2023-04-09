CREATE TABLE Person(
    id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name varchar(100) NOT NULL,
    age int CHECK ( age<100 )
);
CREATE TABLE Item(
    id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    person_id int REFERENCES Person(id) ON DELETE SET NULL,
    item_name varchar(100) NOT NULL
);
CREATE TABLE Passport(
                         person_id int PRIMARY KEY REFERENCES Person(id) ON DELETE CASCADE,
                         passport_number int NOT NULL
);
CREATE TABLE Passport(
                         id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                         person_id int UNIQUE REFERENCES Person(id) ON DELETE CASCADE,
                         passport_number int NOT NULL
);
CREATE TABLE Actor(
                      id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                      name varchar(100) NOT NULL UNIQUE,
                      age int CHECK ( age>0 )
);
CREATE TABLE Movie(
                      id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                      name varchar(200) NOT NULL,
                      year_of_production int CHECK ( year_of_production>1900 )
);
CREATE TABLE Actor_Movie(
                            actor_id int REFERENCES Actor(id),
                            movie_id int REFERENCES Movie(id),
                            PRIMARY KEY (actor_id,movie_id)
);