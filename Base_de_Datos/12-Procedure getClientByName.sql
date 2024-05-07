create procedure getClientByName
	@name varchar(40),
	@lastName1 varchar(20),
	@lastName2 varchar(20)
as
begin

	set @lastName2 = nullif(@lastName2, '')
	if @lastName2 is not null
		select * from cliente where nombre = @name and apellido1 = @lastName1 and apellido2 = @lastName2
	else
		select * from cliente where nombre = @name and apellido1 = @lastName1 and apellido2 is null
end