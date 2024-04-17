
create procedure addEmployee
	@name varchar(40),
	@lastName1 varchar(20),
	@lastName2 varchar(20),
	@position varchar(30),
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
	@state varchar(30)
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