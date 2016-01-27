-- First query.
select * from coches;

-- Second query.
select * from coches where MODELO='gtd';

-- Third query.
insert into coches (codcoche, nombrech, modelo) values ('66','GuillermoCar','4x4');

-- Fourth query.
delete from coches where codcoche='66';

-- 5th query.
insert into coches (codcoche, nombrech, modelo) values ('66','GuillermoCar','4x4');
update coches set nombrech='TheWillyCar' where codcoche='66';
