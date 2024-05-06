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
	set @idClient = (select dbo.getIdClientByName(@nameClient, @lastName1Client, @lastName2Client))
	declare @idEmployee varchar(13)
	set @idEmployee = (select dbo.getIdEmployeeByName(@nameEmployee, @lastName1Employee, @lastName2Employee))
	declare @idSale varchar(15)

	exec getRandomId @length = 15, @randomId = @idSale output

	while exists (select * from venta where idVenta = @idSale)
	begin
		exec getRandomId @length = 15, @randomId = @idSale output
	end

	insert into venta (idVenta,	fechaDeVenta, idCliente, idEmpleado, iva, subtotal, total, metodoPago)
	values (@idSale, SYSDATETIME(), @idClient, @idEmployee, @iva, @subtotal, @total, @payMeth)

	set @msg = 'Se ha realizado la venta satisfactoriamente.'

end