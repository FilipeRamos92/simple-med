-- Inicialização de Especialidades
INSERT INTO specialities (name) VALUES('cardiologia');
INSERT INTO specialities (name) VALUES('fisiologia');
INSERT INTO specialities (name) VALUES('pediatria');

-- Inicialização de Médicos
INSERT INTO doctors
	("first_name", "last_name", "gender", "doctor_reg", "local_service", "speciality_id")
VALUES 
	('Filipe', 'Ramos', 'masculino', '1234', 'São Gonçalo', 1);

INSERT INTO doctors
	("first_name", "last_name", "gender", "doctor_reg", "local_service", "speciality_id")
VALUES 
	('Jéssica', 'Viana', 'feminino', '4321', 'Niterói', 3);

-- Inicialização de Pacientes
INSERT INTO patients 
    ("first_name", "last_name", "email", "birthdate", "gender") 
VALUES
    ('Zed', 'Oliveira', 'zed@email.com', '2014-04-10', 'masculino');

INSERT INTO patients 
    ("first_name", "last_name", "email", "birthdate", "gender") 
VALUES
    ('Lupita', 'Viana', 'lupita@email.com', '2015-07-21', 'feminino');

INSERT INTO patients 
    ("first_name", "last_name", "email", "birthdate", "gender")  
VALUES
    ('Kira', 'Viana', 'kira@email.com', '2010-04-02', 'feminino');