alter table medicos add activo tinyint;
update medicos set activo = 1;


alter table pacientes add activo tinyint;
update pacientes set activo = 1;
