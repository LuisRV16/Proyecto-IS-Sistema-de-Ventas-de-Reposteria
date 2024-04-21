-- insercion de empleado

exec addEmployee
	@name = 'Ramiro',
	@lastName1 = 'Zuñiga',
	@lastName2 = 'Mata',
	@position = 'Cajero',
	@salary = 1700.56,
	@idSupervisor = null,
	@rfc = 'ZUMR990312TY7',
	@curp = 'ZUMR990312HTSYLSA3',
	@nss = '19047304578',
	@dateHire = '2024/04/17',
	@birthdate = '1999/03/12',
	@gender = 'm',
	@civilStatus = 'soltero',
	@status = 'activo',
	@email = 'ramiro@jmail.com',
	@street = 'Abasolo',
	@interiorNumber = '5',
	@outdoorNumber = '104',
	@postalCode = '94866',
	@colony = 'Golondrinas',
	@city = 'Tampico',
	@state = 'Tamaulipas'

exec addProduct
	@name = 'Brownie',
	@weight = 100, -- peso en gramos
	@description = 'Delicioso brownie de chocolate',
	@stock = 15,
	@normalPrice = 25.0,
	@discount = 0.0, -- solo valores entre 0 y 1
	@kindOfProduct = 'dulce',
	@normalOrPersonalized = 'stock',
	@productImage = NULL

select * from producto
select * from empleado
select * from almacen
select * from clientes



update producto set precioVenta = 25.55 where nombre = 'Brownie'

delete from empleado where idEmpleado = ''
delete from producto where idProducto = ''
