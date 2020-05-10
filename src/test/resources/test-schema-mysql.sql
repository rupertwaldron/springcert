DROP TABLE IF EXISTS credentials;

CREATE TABLE IF NOT EXISTS credentials (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  credential_name VARCHAR(40) NOT NULL,
  url VARCHAR(200) NOT NULL,
  login VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  user VARCHAR(40) NOT NULL
);

INSERT INTO credentials(credential_name,url,login,password,user)values("PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick", "javainuse");
INSERT INTO credentials(credential_name,url,login,password,user)values("Amazon", "www.amazon.com", "pete", "football", "javainuse");
INSERT INTO credentials(credential_name,url,login,password,user)values("John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly", "javainuse");
INSERT INTO credentials(credential_name,url,login,password,user)values("Amazon", "www.amazon.com", "rupert", "sweetpea", "ruppyrup");
INSERT INTO credentials(credential_name,url,login,password,user)values("John Lewis", "www.johnlewis.com", "ruppyruyp@yahoo.co.uk", "deadsea", "ruppyrup");

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(200) NOT NULL,
  password VARCHAR(200) NOT NULL
);

INSERT INTO users(username, password)values("ruppyrup", "$2a$10$5/h7AMLixGPBNbHxpUqnYO9KqQkXxo452QZICiK6b/zLw77KMK61C");
INSERT INTO users(username, password)values("javainuse", "$2a$10$I6khl0Ysj1Wy4EceDUwSluVCVqlPQWck7mveScq9sBVahwilpTiC.");