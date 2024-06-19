create procedure addSaleProduct
	@productName varchar(40),
	@quantity int,
	@price decimal(10,2),
	@subtotal decimal(10,2),
	@msg varchar(100) output
as
begin
	declare @idSale varchar(15) = (select top 1 idVenta from venta order by fechaDeVenta desc)
	declare @idProduct varchar (15) = (select idProducto from producto where nombre = @productName)

	update producto set existencia = existencia - @quantity where idProducto = @idProduct

	insert into ventaProducto (idProducto, idVenta, cantidad, precio, subtotal)
	values (@idProduct, @idSale, @quantity, @price, @subtotal)

	set @msg = 'Inserción del Producto en la venta realizada correctamente.'
end