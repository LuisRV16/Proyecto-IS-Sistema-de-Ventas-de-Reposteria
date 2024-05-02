create procedure getClientByName
	@name varchar(40),
	@lastName1 varchar(20),
	@lastName2 varchar(20)
as
begin
	select * from clientes where nombre = @name and apellido1 = @lastName1 and apellido2 = @lastName2
end