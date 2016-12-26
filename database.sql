CREATE DATABASE IF NOT EXISTS mma;
USE mma;

CREATE TABLE IF NOT EXISTS Fighters (
  fighterId          int(11) NOT NULL AUTO_INCREMENT,
  fighterName        varchar (255)NOT NULL COLLATE utf8_unicode_ci,
  fighterUrl         varchar(255) COLLATE utf8_unicode_ci,
  nickName			 varchar(255) COLLATE utf8_unicode_ci,
  birthdate			 date,
  sherdogId			 VARCHAR(255) COLLATE utf8_unicode_ci,
  weightClass        varchar(255) COLLATE utf8_unicode_ci,
  wins				 int,
  losses		     int,
  nc		 		 int,
  draws		 		 int,
  winStreak			 int,
  dateCreated    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (fighterId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS FightResults (
  resultsId          int(11) NOT NULL AUTO_INCREMENT,
  fightResult        varchar (255) COLLATE utf8_unicode_ci,
  fightDate          date,
  event 			 varchar(255) COLLATE utf8_unicode_ci,
  fightMethod		 varchar(255) COLLATE utf8_unicode_ci,
  round			     VARCHAR(255) COLLATE utf8_unicode_ci,
  opponentId         varchar(255) COLLATE utf8_unicode_ci,
  opponentUrl		 varchar(255) COLLATE utf8_unicode_ci,
  eventUrl		 	 varchar(255) COLLATE utf8_unicode_ci,
  sherDogId			 varchar(255) COLLATE utf8_unicode_ci,
  wins			     int,
  losses			 int,
  winStreak			 int,
  dateCreated    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (resultsId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;





