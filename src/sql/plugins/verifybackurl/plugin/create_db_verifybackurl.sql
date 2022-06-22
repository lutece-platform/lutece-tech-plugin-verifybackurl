
--
-- Structure for table verifybackurl_authorized_url
--

DROP TABLE IF EXISTS verifybackurl_authorized_url;
CREATE TABLE verifybackurl_authorized_url (
id_authorized_url int(6) NOT NULL,
url long varchar NOT NULL,
name long varchar NOT NULL,
application_code varchar(100) DEFAULT NULL,
PRIMARY KEY (id_authorized_url)
);
