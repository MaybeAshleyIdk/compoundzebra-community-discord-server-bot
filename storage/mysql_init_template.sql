------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-----------------------                                                                         ------------------------
-----------------------                           !!!OUTDATED FILE!!!                           ------------------------
-----------------------                                                                         ------------------------
-----------------------                 We're gonna use SQLite instead of MySQL.                ------------------------
-----------------------                                                                         ------------------------
-----------------------  See the file 'schema.mysql.sql' that is located in this directory for  ------------------------
-----------------------          the rationale as to why SQLite was chosen over MySQL           ------------------------
-----------------------                                                                         ------------------------
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------

START TRANSACTION;

FLUSH PRIVILEGES;

DROP USER IF EXISTS 'czbot_user'@'localhost';
CREATE USER 'czbot_user'@'localhost' IDENTIFIED BY '{password}';

DROP DATABASE IF EXISTS `czbot_{environment_type}`;
CREATE DATABASE `czbot_{environment_type}`;

use `czbot_{environment_type}`;

{schema}

GRANT ALL ON `czbot_{environment_type}`.* TO 'czbot_user'@'localhost';

COMMIT;
