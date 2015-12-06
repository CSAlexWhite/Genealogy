CREATE TABLE IF NOT EXISTS INDIVIDUAL (
individual_id INT(10) NOT NULL AUTO_INCREMENT,
x_ref_id VARCHAR(255),
given_name VARCHAR(255),
surname VARCHAR(255),
sex VARCHAR(255),
PRIMARY KEY (individual_id)
);

CREATE TABLE IF NOT EXISTS FAMILY (
family_id INT(10) NOT NULL AUTO_INCREMENT,
x_ref_id VARCHAR(255),
husband VARCHAR(255),
wife VARCHAR(255),
number_children INT(10),
PRIMARY KEY (family_id)
);

CREATE TABLE IF NOT EXISTS INDIVIDUAL_EVENT (
individual_id INT(10) NOT NULL,
type VARCHAR(255),
date date,
place_id INT(10),
age_at_event INT(10),
family_id INT(10),
adopted_by int(10),
PRIMARY KEY (individual_id)
);

CREATE TABLE IF NOT EXISTS FAMILY_EVENT (
family_id INT(10) NOT NULL,
type VARCHAR(255),
date date,
place_id INT(10),
event_husband INT(10),
event_wife INT(10),
PRIMARY KEY (family_id)
);

CREATE TABLE IF NOT EXISTS PLACE (
place_id INT(10) NOT NULL AUTO_INCREMENT,
place_name VARCHAR(255),
latitude VARCHAR(255),
longitude VARCHAR(255),
family_id INT(10),
adopted_by INT(10),
PRIMARY KEY (place_id)
);

CREATE TABLE IF NOT EXISTS ASSOCIATION (
individual_a_id INT(10) NOT NULL,
individual_b_id INT(10) NOT NULL,
PRIMARY KEY (individual_a_id, individual_b_id)
);

CREATE TABLE IF NOT EXISTS CHILD_FAMILY (
individual_id INT(10) NOT NULL,
family_id INT(10) NOT NULL,
PRIMARY KEY (individual_id, family_id)
);

CREATE TABLE IF NOT EXISTS SPOUSE_FAMILY (
individual_id INT(10) NOT NULL,
family_id INT(10) NOT NULL,
PRIMARY KEY (individual_id, family_id)
);