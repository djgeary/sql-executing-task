DROP TABLE IF EXISTS test_table;
CREATE TABLE test_table (
	id int NULL,
	name varchar(255) NULL,
	type int NULL
);

INSERT INTO test_table (id, name, type) VALUES (1, 'test', 6);
INSERT INTO test_table (id, name, type) VALUES (2, 'abc', 6);
INSERT INTO test_table (id, name, type) VALUES (3, '123', 6);
INSERT INTO test_table (id, name, type) VALUES (4, 'test2', 4);
INSERT INTO test_table (id, name, type) VALUES (5, 'name', 4);
