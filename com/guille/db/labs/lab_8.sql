CREATE TABLE Alumno(
dnia varchar(9) NOT NULL,
noma varchar(255),
apella varchar(255),
domiciolioa varchar(255),
f_nacimiento date,
f_ingreso date,
CONSTRAINT dnia PRIMARY KEY (dnia)
);

CREATE TABLE Profesor(
dnip varchar(9) NOT NULL,
nombrep varchar(255),
apellidop varchar(255),
domiciliop varchar(255),
CONSTRAINT dnip PRIMARY KEY(dnip)
);

CREATE TABLE Carrera(
codcarr varchar(255) NOT NULL,
nombrec varchar(255),
duracion INTEGER,
CONSTRAINT codcarr PRIMARY KEY(codcarr)
);

CREATE TABLE Asignatura(
codasig varchar(255) NOT NULL,
nomasig varchar(255),
curso varchar(255),
creditos INTEGER,
tipo varchar(255),
codcarr varchar(255) NOT NULL,
CONSTRAINT codasigpf PRIMARY KEY(codasig),
CONSTRAINT codcarrfk FOREIGN KEY(codcarr) REFERENCES Carrera(codcarr),
CHECK(tipo='mandatory' OR tipo='elective' OR tipo='free elective')
);

CREATE TABLE califica
(
dnia varchar(9) NOT NULL,
codasig varchar(255) NOT NULL,
dnip varchar(9) NOT NULL,
nota INTEGER,
f_calificacion DATE,
CONSTRAINT calificapk PRIMARY KEY (dnia, codasig),
CONSTRAINT calificadniafk FOREIGN KEY (dnia) REFERENCES Alumno(dnia),
CONSTRAINT calificacodasigfk FOREIGN KEY (codasig) REFERENCES Asignatura(codasig),
CONSTRAINT calificadnipfk FOREIGN KEY (dnip) REFERENCES Profesor(dnip),
);
