/*
// login as root
sudo mysql -u root
*/

CREATE SCHEMA olework DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'oleapp'@'%' IDENTIFIED BY 'passw0rd';
GRANT ALL PRIVILEGES ON olework.* To 'oleapp'@'%' WITH GRANT OPTION;

CREATE USER 'oleapp'@'localhost' IDENTIFIED BY 'passw0rd';
GRANT ALL PRIVILEGES ON olework.* To 'oleapp'@'%' WITH GRANT OPTION;

flush privileges;

/*
// login como oleapp
mysql -vvv -h 127.0.0.1 -u oleapp -ppassw0rd olework
*/

/*
// cambiar clave
ALTER USER 'oleapp'@'%' IDENTIFIED BY 'clave_nueva';
ALTER USER 'oleapp'@'localhost' IDENTIFIED BY 'clave_nueva';
*/
