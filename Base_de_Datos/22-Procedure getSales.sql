create procedure getSales
as
begin
	select 
		idVenta, cast(fechaDeVenta as date), cast(fechaDeVenta as time),
		c.nombre, c.apellido1, c.apellido2,
		e.nombre, e.apellido1, e.apellido2, 
		subtotal, iva, total, metodoPago
	from
		venta as v join cliente as c
		on v.idCliente = c.rfc
		join empleado as e
		on v.idEmpleado = e.rfc
		order by fechaDeVenta asc
end