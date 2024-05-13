create procedure getEmployeeByUser
	@user varchar(15),
	@name varchar(40) output,
	@lastName1 varchar(30) output,
	@lastName2 varchar(30) output,
	@positiion varchar(30) output
as
begin
	set @name = (select nombre from empleado where nombreUsuario = @user)
	set @lastName1 = (select apellido1 from empleado where nombreUsuario = @user)
	set @lastName2 = (select apellido2 from empleado where nombreUsuario = @user)
	set @positiion = (select puesto from empleado where nombreUsuario = @user)
end