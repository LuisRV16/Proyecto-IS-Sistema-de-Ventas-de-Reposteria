declare @mesg varchar(100)
exec addManager
	@name = 'Bruce',
	@lastName1 = 'Wayne',
	@lastName2 = null,
	@salary = 30145.67,
	@idSupervisor = null,
	@rfc = 'WYAB720219JK2',
	@curp = 'WYAB720219H11YLSA3',
	@nss = '19047320346',
	@dateHire = '2024/04/17',
	@birthdate = '1972/02/19',
	@gender = 'm',
	@civilStatus = 'soltero',
	@status = 'activo',
	@email = 'batman@gmail.com',
	@street = 'Abasolo',
	@interiorNumber = '5',
	@outdoorNumber = '104',
	@postalCode = '94866',
	@colony = 'Golondrinas',
	@city = 'Tampico',
	@state = 'Tamaulipas',
	@pwd = 'zumr',
	@msg = @mesg output
select @mesg

/*
drop user WYAB72021920346
delete from empleado where rfc = 'WYAB720219JK2'
select * from empleado*/