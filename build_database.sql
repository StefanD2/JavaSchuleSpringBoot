# Angabe siehe Tafel (bzw OneNote Labor):
# Datanbank Adressen
# Person(id Person(Primärschlüssel(=unterstrichen)) -n- wohntIn(Primärschlüssel) -m- Adresse -n- -1- Ort

drop database IF EXISTS 5badressen;
create database 5badressen;
use 5badressen;

create table Person
(
    idPerson int(10) unsigned auto_increment primary key,
    Vorname  varchar(40),
    Famname  varchar(40) not null,
    Telefon  varchar(30),
    Geburt   date,
    email    varchar(100)
);

create table Ort
(
    idOrt    int(10) unsigned auto_increment primary key,
    PLZ      varchar(10) not null,
    Ortsname varchar(50) not null,
    Telefon  varchar(15)
);

create table Adresse
(
    idAdresse int(10) unsigned auto_increment primary key,
    Strasse   varchar(50),
    HausNr    varchar(30),
    idOrt     int(10) unsigned not null
);

create table Person_wohntin_Adresse
(
    idWohntin int(10) unsigned auto_increment primary key,
    seit      date,
    idPerson  int(10) unsigned not null,
    idAdresse int(10) unsigned not null
);


create table User
(
    idUser   int(10) unsigned auto_increment primary key,
    username varchar(30)  not null,
    password varchar(100) not null,
    role     varchar(100)
);


insert into Ort (`PLZ`, `Ortsname`, `Telefon`)
values ('3100', 'St. Pölten', '+43'),
       ('3435', 'Zwentendorf', '02277');

insert into Adresse(`Strasse`, `HausNr`, `idOrt`)
values ('Waldstraße', '3', 1),
       ('Josef-Mohnl-Gasse', '8', 2),
       ('Hauptstraße', '27', 2),
       ('Maraizellerstraße', '15', 1);

insert into Person(Geburt, Famname, Vorname, Telefon)
values ('2002-03-26', 'Deimel', 'Stefan', '6763939588'),
       ('2002-03-24', 'Eilmsteiner', 'Philipp', '6965244862'),
       ('1908-01-01', 'Stöger', 'Julia', '458618423163'),
       ('2000-12-24', 'Wurm', 'Stephan', '465646873543');

insert into Person_wohntin_Adresse(idPerson, idAdresse, seit)
values (1, 1, '2002-03-26'),
       (1, 2, '2016-07-02'),
       (2, 1, '2016-07-02'),
       (3, 1, '2016-07-03'),
       (4, 1, '2016-07-01'),
       (3, 3, '1908-01-01'),
       (4, 4, '2005-04-30');

# add htl-email-adresse für alle die in St. Pölten wohnen
update Person
set email=concat(lower(`vorname`), '.', lower(`famname`), '@htlstp.at')
where idPerson in (select idPerson
                   from Person_wohntin_Adresse
                   where idAdresse in (select idAdresse
                                       from Adresse
                                       where idOrt = (select idOrt from Ort where ortsname = 'St. Pölten')));

insert into user(idUser, username, password, role)
VALUES (1, 'GUEST', '$2a$10$oR8SYrWFYpEFpTUydt8p.uh.5rlRkERaCgf34FQf7EBcujDgkM/Mu', 'GUEST'),
       (2, 'USER', '$2a$10$vkg0/jnR87nYByx3DWaldeVqVoHkHz/dFN9xo0izG2JwuyvKnNsri', 'GUEST;USER'),
       (3, 'ADMIN', '$2a$10$hHsPMtU3JevXP01xUq96quijB3YvOtL1bW4olVYGpQm8o0.tcJRxq', 'GUEST;USER;ADMIN')
