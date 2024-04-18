
create procedure addProduct
	@name varchar(50),
	@weight int, -- peso en gramos
	@description varchar(400),
	@stock int,
	@normalPrice float,
	@discount float, -- solo valores entre 0 y 1
	@kindOfProduct varchar(6),
	@normalOrPersonalized varchar(13),
	@productImage varbinary(max)
as
begin
	declare @id varchar(15)
	declare @salePrice float

	exec getRandomId @length = 15, @randomId = @id output

<<<<<<< HEAD
	while exists (select * from empleado where idEmpleado = @id)
=======
	while exists (select * from producto where idProducto = @id)
>>>>>>> 2f7963f584570144f5c9e8f14cbcf0aaf4166e72
	begin
		exec getRandomId @length = 15, @randomId = @id output
	end

	set @salePrice = @normalPrice - @normalPrice * @discount

	insert into producto
		(idProducto, nombre, peso, descripcion, existencia, precioNormal, descuento, precioVenta, tipoDeProducto,
		 normalOrPersonalizado, imagenDelProducto)
	values
		(@id, @name, @weight, @description, @stock, @normalPrice, @discount, @salePrice, @kindOfProduct,
		 @normalOrPersonalized, @productImage)

	print 'Registro exitoso'

end
