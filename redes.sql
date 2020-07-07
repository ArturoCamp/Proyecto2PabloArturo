create database redesB51675
use redesB51675

create table Usuarios(
IdUsuario varchar(60) ,
Nombre varchar(60) 
constraint pk_Nombre primary key (Nombre)

)

drop table Usuarios
drop table Usuarios
drop procedure addUser
select *
from Usuarios

insert into Usuarios (IdUsuario,Nombre)
values (4,'marco')

CREATE PROCEDURE addUser(
 @pNombre VARCHAR(50),
 @pid varchar(60)
)
AS
BEGIN
 SET NOCOUNT ON;
 INSERT INTO Usuarios
 (Nombre, idUsuario)
 VALUES (@pNombre, @pid);
END
GO

drop procedure devolverUsuarios
CREATE PROCEDURE devolverUsuarios ( 
@nombre varchar(60),
   @pidUsuarios varchar(60)
  
   )
AS  
BEGIN  
   SELECT idUsuario, Nombre
   FROM Usuarios
 
END
go

