
create procedure addEmployee
	@name varchar(40) not null,
	@lastName1 varchar(20) not null,
	@lastName2 varchar(20),
	@position varchar(30) not null,
	@salary decimal(10, 2) not null,
	@idSupervisor varchar(15),
	@rfc varchar(13) not null,
	@curp varchar(18) not null,
	@nss varchar(11) not null,
	@dateHire date not null,
	@birthdate date not null,
	@gender varchar(1) not null,
	@civilStatus varchar(10) not null,
	@status varchar(8) not null,
	@email varchar(255) not null,
	@street varchar(30) not null,
	@interiorNumber varchar(5) not null,
	@outdoorNumber varchar(6) not null,
	@postalCode varchar(5) not null,
	@colony varchar(30) not null,
	@city varchar(30) not null,
	@state varchar(30) not null
as
begin
	declare @id varchar(15)

	create table #tempRandomId (randomId varchar(15))
	
	insert into  #tempRandomId (randomId) exec getRandomId

	select top 1 @id = randomId from #tempRandomId

	while exists (select * from empleado where idEmpleado like @id)
	begin
		delete from #tempRandomId
		insert into  #tempRandomId (randomId) exec getRandomId
		select top 1 @id = randomId from #tempRandomId
	end

	drop table #tempRandomId

	insert into empleado
		(idEmpleado, nombre, apellido1, apellido2, puesto, salario, idSupervisor, rfc, curp, nss,
		 fechaContratacion, fechaDeNacimiento, sexo, estadoCivil, estatus, correo, numeroInterior,
		 numeroExterior, codigoPostal, colonia, ciudad, estado)
	values
		(@id, @name, @lastName1, @lastName2, @position, @salary, @idSupervisor, @rfc, @curp, @nss,
		 @dateHire, @birthdate, @gender, @civilStatus, @status, @email, @street, @interiorNumber,
		 @outdoorNumber, @postalCode, @colony, @city, @state)

	print 'Registro exitoso'

end