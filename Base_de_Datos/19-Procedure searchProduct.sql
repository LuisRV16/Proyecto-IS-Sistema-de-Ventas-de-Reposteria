create procedure searchProduct
	@searchString varchar(40),
	@order varchar(6)
as
begin
	if (@order is null or @order like 'Todo')
		begin
			select * from producto where nombre like '%' + @searchString + '%'
		end
	else if @order like 'Pastel'
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