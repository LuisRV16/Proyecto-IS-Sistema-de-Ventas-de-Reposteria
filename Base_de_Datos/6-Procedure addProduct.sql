create procedure addProduct
	@name varchar(50),
	@weight int, -- peso en gramos
	@description varchar(400),
	@stock int,
	@normalPrice float,
	@discount float, -- solo valores entre 0 y 1
	@kindOfProduct varchar(6),
	@productImage varbinary(max),
	@msg varchar(200) output
as
begin
	declare @id varchar(15)
	declare @salePrice float

	exec getRandomId @length = 15, @randomId = @id output

	while exists (select * from producto where idProducto = @id)
	begin
		exec getRandomId @length = 15, @randomId = @id output
	end

	if @name = '' or replace(@name, ' ', '') = ''
		begin
			set @msg = 'Ingrese el nombre del producto en el campo de texto.'
		end
	else if len(@name) = 1 or @name like '%[^A-Za-z0-9 ]%'
		begin
			set @msg = 'Ingrese el nombre correcto.' + CHAR(13) + CHAR(10) + 'El nombre debe contener m�s de una letra.' + CHAR(13) + CHAR(10) + 'El nombre no debe contener n�meros.'
		end
	else if @description = '' or replace(@name, ' ', '') = ''
		begin
			set @msg = 'Ingrese una descripci�n v�lida del producto en el �rea de texto.'
		end
	else if @kindOfProduct is null
		begin
			set @msg = 'Seleccione el tipo de producto correcto.'
		end
	else if @productImage is null
		begin
			set @msg = 'Seleccione una imagen para el producto.'
		end
	else if exists (select * from producto where nombre = @name)
		begin
			set @msg = 'Un producto ya se encuentra registrado con este nombre.'
		end
	else
		begin
			set @discount = @discount/100
			set @salePrice = @normalPrice - @normalPrice * @discount

			insert into producto
			(idProducto, nombre, peso, descripcion, existencia, precioNormal, descuento, precioVenta, tipoDeProducto,
				imagenDelProducto)
			values
			(@id, @name, @weight, @description, @stock, @normalPrice, @discount, @salePrice, @kindOfProduct, @productImage)

			set @msg = 'El producto ha sido registrado exitosamente.'

		end

end