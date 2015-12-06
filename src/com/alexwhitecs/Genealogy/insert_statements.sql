INSERT INTO individual
(individual_ID, given_name, surname, sex)
VALUES (individualID, name, name, sex);

INSERT INTO individual
(x_ref_id, given_name, surname, sex)
VALUES ("@CHILD@", "", "/Child/ ", "null");

INSERT INTO individual
(x_ref_id, given_name, surname, sex)
SELECT * FROM (SELECT x_ref_individual, given_name, surname) AS tmp
WHERE NOT EXISTS (
    SELECT x_ref_id FROM individual WHERE x_ref_id = x_ref_individual
) LIMIT 1;

INSERT INTO individual
(x_ref_id, given_name, surname, sex)
SELECT * FROM (SELECT "@MOTHER@", "", "/Mother/ ", "F" AS tmp
WHERE NOT EXISTS (
    SELECT x_ref_id FROM individual WHERE x_ref_id = x_ref_individual
) LIMIT 1;

