-- DROP TABLE IF EXISTS credentials;

CREATE TABLE IF NOT EXISTS credentials (
  credentialId VARCHAR(40) NOT NULL,
  url VARCHAR(200) NOT NULL,
  login VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL
);

-- INSERT INTO credentials(credentialId,url,login,password)values("PondPlanet", "www.pondplanet.com", "ruppyrup", "feelsick");
-- INSERT INTO credentials(credentialId,url,login,password)values("Amazon", "www.amazon.com", "pete", "football");
-- INSERT INTO credentials(credentialId,url,login,password)values("John Lewis", "www.johnlewis.com", "rupert.waldron@yahoo.co.uk", "polly");