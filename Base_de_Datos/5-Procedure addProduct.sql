
create procedure addProduct
	@name varchar(50),
	@weight int, -- peso en gramos
	@description varchar(400),
	@stock int,
	@normalPrice float,
	@discount float, -- solo valores entre 0 y 1
	@kindOfProduct varchar(6),
	@productImage varbinary(max)
as
begin
	declare @id varchar(15)
	declare @salePrice float

	exec getRandomId @length = 15, @randomId = @id output

	while exists (select * from producto where idProducto = @id)
	begin
		exec getRandomId @length = 15, @randomId = @id output
	end

	set @salePrice = @normalPrice - @normalPrice * @discount

	insert into producto
		(idProducto, nombre, peso, descripcion, existencia, precioNormal, descuento, precioVenta, tipoDeProducto,
		 imagenDelProducto)
	values
		(@id, @name, @weight, @description, @stock, @normalPrice, @discount, @salePrice, @kindOfProduct, @productImage)

	print 'Registro exitoso'

end
