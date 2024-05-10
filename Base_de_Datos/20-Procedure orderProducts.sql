create procedure orderProducts
	@searchString varchar(40),
	@order varchar(6)
as
begin
	if @searchString like ''
		begin
			if @order like 'Pastel'
				begin
					select * from producto
					order by tipoDeProducto desc
				end
			else if @order like 'Dulce'
				begin
					select * from producto
					order by tipoDeProducto asc
				end
		end
	else
		begin
			if @order like 'Pastel'
				begin
					select * from producto where nombre like '%' + @searchString + '%'
					order by tipoDeProducto desc
				end
			else if @order like 'Dulce'
				begin
					select * from producto where nombre like '%' + @searchString + '%'
					order by tipoDeProducto asc
				end	
		end
end