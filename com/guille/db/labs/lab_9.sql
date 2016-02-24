CREATE TABLE cine (
	codcine varchar(4) NOT NULL,
	localidad varchar(255),
	CONSTRAINT codcine_pk PRIMARY KEY (codcine)
);

CREATE TABLE cine3d (
	codcine varchar(4) NOT NULL,
	numsalas decimal(1,0),
	CONSTRAINT cine3d_codcine_pk PRIMARY KEY (codcine),
	CONSTRAINT codcine_fk FOREIGN KEY (codcine) REFERENCES cine(codcine)
);

CREATE TABLE sala (
   codsala varchar(4) NOT NULL,
   aforo decimal(3,0),   
   codcine varchar(4) NOT NULL,
   CONSTRAINT sala_pk PRIMARY KEY (CODSALA),
   CONSTRAINT sala_cine_fk FOREIGN KEY (codcine) REFERENCES cine (codcine)
);

CREATE TABLE pelicula(
   codpelicula varchar(4) NOT NULL,
   titulo varchar(20), 
   duracion decimal(2,0),   
   tipo varchar(20), 
   codpelicula_original varchar(4),
   CONSTRAINT pelicula_pk PRIMARY KEY (codpelicula),
   CONSTRAINT pelicula_pelicula_original_fk FOREIGN KEY (codpelicula_original) REFERENCES pelicula (codpelicula),
   CONSTRAINT pelicula_titulo_unique UNIQUE (TITULO),
   CONSTRAINT pelicula_tipo_check CHECK (TITULO IN ('ficcion','aventuras','terror'))   
);

CREATE TABLE proyecta(
   codpelicula varchar(4) NOT NULL,
   codsala varchar(4) NOT NULL,
   sesion decimal(2,0) NOT NULL,
   fecha date NOT NULL,   
   entradas_vendidas decimal(3,0),
   CONSTRAINT proyecta_pk PRIMARY KEY (codpelicula,codsala,sesion,fecha),
   CONSTRAINT proyecta_pelicula_fk FOREIGN KEY (codpelicula) REFERENCES pelicula (codpelicula),
   CONSTRAINT proyecta_sala_fk FOREIGN KEY (codsala) REFERENCES sala (codsala),
   CONSTRAINT proyecta_sesion_check CHECK (sesion IN (5,7,10))      
);

CREATE TABLE entrada(
   codentrada varchar(4) NOT NULL,
   precio decimal(3,0),   
   codpelicula varchar(4) NOT NULL,
   codsala varchar(4) NOT NULL,
   sesion decimal(2,0) NOT NULL,
   fecha date NOT NULL,  
   CONSTRAINT entrada_pk PRIMARY KEY (codentrada),
   CONSTRAINT entrada_proyecta_fk FOREIGN KEY (codpelicula,codsala,sesion,fecha) REFERENCES proyecta (codpelicula,codsala,sesion,fecha), 
);
