DROP TABLE IF EXISTS credentials_test;

CREATE TABLE credentials_test (
  credentialId VARCHAR(40) NOT NULL,
  url VARCHAR(200) NOT NULL,
  login VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  user VARCHAR(40) NOT NULL
);

INSERT INTO credentials_test(credentialId,url,login,password,user)values("PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick", "javainuse");
INSERT INTO credentials_test(credentialId,url,login,password,user)values("Amazon", "www.amazon.com", "pete", "football", "javainuse");
INSERT INTO credentials_test(credentialId,url,login,password,user)values("John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly", "javainuse");
INSERT INTO credentials_test(credentialId,url,login,password,user)values("Amazon", "www.amazon.com", "rupert", "sweetpea", "ruppyrup");
INSERT INTO credentials_test(credentialId,url,login,password,user)values("John Lewis", "www.johnlewis.com", "ruppyruyp@yahoo.co.uk", "deadsea", "ruppyrup");