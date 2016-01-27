-- First query.
SELECT * FROM coches;

-- Second query.
SELECT * FROM coches where modelo='gtd';

-- Third query.
INSERT into coches (codcoche, nombrech, modelo) VALUES ('66','GuillermoCar','4x4');

-- Fourth query.
DELETE FROM coches WHERE codcoche='66';

-- Fifth query. (we add the new car in order not to change the natural state of the database)
INSERT into coches (codcoche, nombrech, modelo) VALUES ('66','GuillermoCar','4x4');
UPDATE coches SET nombrech='TheWillyCar' WHERE codcoche='66';
