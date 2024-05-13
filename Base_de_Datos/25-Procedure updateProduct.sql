create procedure updateProduct
	@id varchar(15),
	@oldName varchar(40),
	@name varchar(40),
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
	declare @salePrice float

	if @name = '' or replace(@name, ' ', '') = ''
		begin
			set @msg = 'Ingrese el nombre del producto en el campo de texto.'
		end
	else if len(@name) = 1 or @name like '%[^A-Za-z0-9 ]%'
		begin
			set @msg = 'Ingrese el nombre correcto.' + CHAR(13) + CHAR(10) + 'El nombre debe contener más de una letra.' + CHAR(13) + CHAR(10) + 'El nombre no debe contener números.'
		end
	else if @description = '' or replace(@name, ' ', '') = ''
		begin
			set @msg = 'Ingrese una descripción válida del producto en el área de texto.'
		end
	else if @kindOfProduct is null
		begin
			set @msg = 'Seleccione el tipo de producto correcto.'
		end
	else if @productImage is null
		begin
			set @msg = 'Seleccione una imagen para el producto.'
		end
	else if exists (select * from producto where nombre = @name) and @oldName != @name
		begin
			set @msg = 'Un producto ya se encuentra registrado con este nombre.'
		end
	else
		begin
			set @discount = @discount/100
			set @salePrice = @normalPrice - @normalPrice * @discount

			update producto
			set
				nombre = @name,
				peso = @weight,
				descripcion = @description,
				existencia = @stock,
				precioNormal = @normalPrice,
				descuento = @discount,
				precioVenta = @salePrice,
				tipoDeProducto = @kindOfProduct,
				imagenDelProducto = @productImage
			where
				idProducto = @id

			set @msg = 'El producto ha sido actualizado exitosamente.'

		end
end