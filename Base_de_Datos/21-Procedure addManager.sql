create procedure addManager
	@name varchar(40),
	@lastName1 varchar(20),
	@lastName2 varchar(20),
	@salary decimal(10, 2),
	@idSupervisor varchar(15),
	@rfc varchar(13),
	@curp varchar(18),
	@nss varchar(11),
	@dateHire date,
	@birthdate date,
	@gender varchar(1),
	@civilStatus varchar(10),
	@status varchar(8),
	@email varchar(255),
	@street varchar(30),
	@interiorNumber varchar(5),
	@outdoorNumber varchar(6),
	@postalCode varchar(5),
	@colony varchar(30),
	@city varchar(30),
	@state varchar(30),
	@pwd varchar(15),
	@msg varchar(100) output
as
begin
	declare @position varchar(30) = 'Gerente'
	declare @userName varchar(15)
	declare @sql nvarchar(100)

	set @userName = LEFT(@rfc, 10);
	set @userName = @userName + RIGHT(@nss, 5)

	if not exists (select * from empleado where rfc = @rfc)
	begin
		
		SET @sql = N'CREATE LOGIN ' + QUOTENAME(@userName) + N' WITH PASSWORD = N''' + @pwd + N''''
		EXEC sp_executesql @sql;
		set @sql = 'CREATE USER ' + QUOTENAME(@userName) + ' FOR LOGIN ' + QUOTENAME(@userName)
		exec sp_executesql @sql
		set @sql = 'ALTER ROLE manager ADD MEMBER ' + QUOTENAME(@userName)
		exec sp_executesql @sql

		insert into empleado
		(rfc, nombre, apellido1, apellido2, puesto, salario, idSupervisor, curp, nss,
		 fechaContratacion, fechaDeNacimiento, sexo, estadoCivil, estatus, correo, calle, numeroInterior,
		 numeroExterior, codigoPostal, colonia, ciudad, estado, nombreUsuario)
		values
			(@rfc, @name, @lastName1, @lastName2, @position, @salary, @idSupervisor, @curp, @nss,
			 @dateHire, @birthdate, @gender, @civilStatus, @status, @email, @street, @interiorNumber,
			 @outdoorNumber, @postalCode, @colony, @city, @state, @userName)

		set @msg =  'Registro exitoso.' + CHAR(13) + CHAR(10) + 'El usuario para el gerente es: '
			+ @userName + CHAR(13) + CHAR(10) + 'Su contraseña es: ' + @pwd
	end
	else
	begin
		set @msg = 'El gerente ya ha sido registrado con anterioridad.'
	end

end