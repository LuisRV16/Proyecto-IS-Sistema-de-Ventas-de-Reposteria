create function getIdEmployeeByName(@name varchar(40), @lastName1 varchar(20), @lastName2 varchar(20))
returns varchar(13)
as
begin
	declare @id varchar(13)

	set @lastName2 = nullif(@lastName2, '')

	if @lastName2 is not null
		set @id = (select rfc from empleado where nombre = @name and apellido1 = @lastName1 and apellido2 = @lastName2)
	else
		set @id = (select rfc from empleado where nombre = @name and apellido1 = @lastName1 and apellido2 is null)
	return @id
end