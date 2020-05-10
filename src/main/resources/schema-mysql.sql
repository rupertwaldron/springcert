DROP TABLE IF EXISTS credentials;

CREATE TABLE IF NOT EXISTS credentials (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  credential_name VARCHAR(40) NOT NULL,
  url VARCHAR(200) NOT NULL,
  login VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  user VARCHAR(40) NOT NULL
);

INSERT INTO credentials(credential_name,url,login,password,user)values("PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick", "ruppyrup");
INSERT INTO credentials(credential_name,url,login,password,user)values("Amazon", "www.amazon.com", "pete", "football", "rup");
INSERT INTO credentials(credential_name,url,login,password,user)values("John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly", "rup");

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(200) NOT NULL,
  password VARCHAR(200) NOT NULL
);

INSERT INTO users(username, password)values("ruppyrup", "$2a$10$5/h7AMLixGPBNbHxpUqnYO9KqQkXxo452QZICiK6b/zLw77KMK61C");
INSERT INTO users(username, password)values("rup", "$2a$10$d.pL4SZ1KC3DdZUF1toF4.OiT7arGEf91cOstJtsy8ql2Duns2wpa");
