--FIRST
SELECT codoferta, precio FROM Oferta, EsRealizada, Agencia, Mayorista WHERE Mayorista.codmayorista=Agencia.codmayorista AND Agencia.codagencia=EsRealizada.codagencia AND EsRealizada.codoferta=Oferta.codoferta AND Mayorista.tipo='hoteles';

--SECOND
SELECT count(compra.codagencia), tipo from compra, EsRealizada, Agencia, Mayorista WHERE Agencia.codagencia=EsRealizada.codagencia AND EsRealizada.codagencia=Compra.codagencia and agencia.codmayorista = mayorista.codmayorista  Group by Mayorista.tipo;

--THRIRD
Select MAX(porcentaje) FROM descuentos, tiene, cliente, compra, esrealizada, oferta WHERE descuentos.coddescuento=tiene.coddescuento AND tiene.dni=cliente.dni AND cliente.dni=compra.dni AND compra.codOferta=esrealizada.codoferta AND compra.codagencia=esrealizada.codagencia AND esrealizada.codoferta=oferta.codoferta AND oferta.origen='Madrid';

FOURTH
SELECT DISTINCT dni, nombre FROM mayorista, agencia, esrealizada, compra, compra, cliente WHERE
	mayorista.codmayorista=agencia.codmayorista AND agencia.codagencia=esrealizada.codagencia AND
	esrealizada.codagencia=compra.codagencia AND compra.dni=cliente.dni AND mayorista.tipo='combinados'
	AND pagofraccionado='N';
	
--FIVE
SELECT DISTINCT nombremayorista, nombreagencia, destino, importedefinitivo, nombre, porcentaje FROM cliente, compra, esrealizada, oferta, agencia, mayorista, tiene, descuentos WHERE descuentos.coddescuento=tiene.coddescuento AND tiene.dni=cliente.dni AND cliente.dni=compra.dni AND compra.codoferta=esrealizada.codoferta AND compra.codagencia=esrealizada.codagencia AND esrealizada.codoferta=oferta.codoferta AND esrealizada.codagencia=agencia.codagencia AND agencia.codmayorista=mayorista.codmayorista;
	
--SIXTH	
SELECT nombreagencia FROM agencia, esrealizada WHERE agencia.codagencia=esrealizada.codagencia AND precio IN (SELECT MIN(precio) FROM esrealizada) AND plazasdisponibles > 0;
