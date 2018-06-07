/* Lantsberger Scoring Management Platform Database Initialisers */

DROP DATABASE IF EXISTS lsmp;
CREATE DATABASE lsmp;

CREATE TABLE labDB
(
    ID INTEGER PRIMARY KEY,
    name TEXT,
    timestamp TEXT,
    Period INTEGER,
    Lab1_0 INTEGER
);
