create procedure getSalesProducts
	@idSale varchar(15)
as
begin
	select
		p.nombre, vp.precio, vp.cantidad, vp.subtotal
	from
		ventaProducto as vp join producto as p
		on vp.idProducto = p.idProducto
		where vp.idVenta = @idSale
end