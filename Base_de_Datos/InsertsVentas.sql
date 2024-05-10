-- insercion de empleado
declare @mesg varchar(100)
exec addEmployee
	@name = 'Ramiro',
	@lastName1 = 'Zu�iga',
	@lastName2 = 'Mata',
	@position = 'Cajero',
	@salary = 1700.56,
	@idSupervisor = null,
	@rfc = 'ZUMR990312TY7',
	@curp = 'ZUMR990312HTSYLSA3',
	@nss = '19047304578',
	@dateHire = '2024/04/17',
	@birthdate = '1999/03/12',
	@gender = 'm',
	@civilStatus = 'soltero',
	@status = 'activo',
	@email = 'ramiro@jmail.com',
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