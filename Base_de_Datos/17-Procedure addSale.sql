create procedure addSale
	@nameClient varchar(40),
	@lastName1Client varchar(30),
	@lastName2Client varchar(30),
	@nameEmployee varchar(40),
	@lastName1Employee varchar(30),
	@lastName2Employee varchar(30),
	@iva decimal(10,2),
	@subtotal decimal(10,2),
	@total decimal(10,2),
	@payMeth varchar(18),
	@msg varchar(60) output
as
begin

	declare @idClient varchar(13)
	declare @idEmployee varchar(13)
	declare @idSale varchar(15)

	exec getRandomId @length = 15, @randomId = @idSale output

	while exists (select * from venta where idVenta = @idSale)
	begin
		exec getRandomId @length = 15, @randomId = @idSale output
	end

	set @lastName2Client = nullif(@lastName2Client, '')
	set @lastName2Employee = nullif(@lastName2Employee, '')

	if @lastName2Client is not null
		set @idClient = (select rfc from cliente where nombre = @nameClient and apellido1 = @lastName1Client and apellido2 = @lastName2Client)
	else
		set @idClient = (select rfc from cliente where nombre = @nameClient and apellido1 = @lastName1Client and apellido2 is null)

	if @lastName2Employee is not null
		set @idEmployee = (select rfc from empleado where nombre = @nameEmployee and apellido1 = @lastName1Employee and apellido2 = @lastName2Employee)
	else
		set @idEmployee = (select rfc from empleado where nombre = @nameEmployee and apellido1 = @lastName1Employee and apellido2 is null)

	insert into venta (idVenta,	fechaDeVenta, idCliente, idEmpleado, iva, subtotal, total, metodoPago)
	values (@idSale, SYSDATETIME(), @idClient, @idEmployee, @iva, @subtotal, @total, @payMeth)

	set @msg = 'Se ha realizado la venta satisfactoriamente.'

end