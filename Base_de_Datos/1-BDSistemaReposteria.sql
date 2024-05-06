create database sistemaReposteria

use sistemaReposteria

create table empleado (
    rfc varchar(13) primary key,
    nombre varchar(40) not null,
    apellido1 varchar(20) not null,
    apellido2 varchar(20),
    puesto varchar(30) not null,
    salario decimal(10,2) not null,
    idSupervisor varchar(13),
    curp varchar(18) not null,
    nss varchar(11) not null,
    fechaContratacion date not null,
    fechaDeNacimiento date not null,
    sexo varchar(1) check (sexo in('m', 'f', 'o')) not null,
    estadoCivil varchar(10) check (estadoCivil in ('soltero', 'casado', 'divorciado', 'viudo')),
    estatus varchar(8) check (estatus in ('activo', 'inactivo', 'despido', 'renuncia')),
    correo varchar(255) not null,
    calle varchar(30) not null,
    numeroInterior varchar(5) not null,
    numeroExterior varchar(6) not null,
    codigoPostal varchar(5) not null,
    colonia varchar(30) not null,
    ciudad varchar(30) not null,
    estado varchar(30) not null,
	foreign key (idSupervisor) references empleado(rfc)
)

-- Se agrega a la tabla empleado el campo de nombreUsuario
alter table empleado
add nombreUsuario varchar(15) unique
-- Permite nulos porque solo se crea un login para los cajeros

create table telefonoEmpleado (
	telefono varchar(10) not null,
	idEmpleado varchar(13) not null,
	primary key (telefono, idEmpleado),
	foreign key (idEmpleado) references empleado(rfc)
)

create table cliente (
	rfc varchar(13) primary key,
	nombre varchar(40) not null,
	apellido1 varchar(20) not null,
	apellido2 varchar(20),
	telefono varchar(10) not null,
	correo varchar(255) not null,
	calle varchar(30) not null,
    numeroInterior varchar(5) not null,
    numeroExterior varchar(6) not null,
	codigoPostal varchar(5) not null,
	colonia varchar(30) not null,
    ciudad varchar(30) not null,
    estado varchar(30) not null,
)

create table venta (
	idVenta varchar(15) primary key,
	fechaDeVenta datetime not null,
	idCliente varchar(13) not null,
	idEmpleado varchar(13) not null,
	subtotal decimal(10,2) not null, -- se obtiene de la suma de todas las ocurrencias de la tabla ventaProducto en las que coincida el idVenta
	iva decimal(10,2) not null,
	total decimal(10,2) not null, -- se obtiene de la suma de subtotal con el subtotal multiplicado por el iva (0.16)
	metodoPago varchar(18) check (metodoPago in ('efectivo', 'tarjeta de debito', 'tarjeta de credito')) not null
	foreign key (idCliente) references cliente (rfc),
	foreign key (idEmpleado) references empleado (rfc)
)

create table factura (
	idFactura varchar(20) primary key,
	idVenta varchar(15) unique not null,
	fecha datetime not null,
	foreign key (idVenta) references venta(idVenta)
)

create table producto (
	idProducto varchar(15) primary key,
	nombre varchar(40) unique not null,
	peso int not null, -- el peso del producto en gramos
	descripcion varchar(400) not null,
	existencia int,
	precioNormal decimal(10,2) not null,
	descuento decimal(10,2),
	precioVenta decimal(10,2) not null,
	tipoDeProducto varchar(6) check (tipoDeProducto in ('pastel', 'dulce')) not null,
	imagenDelProducto varbinary(max)
)

create table ventaProducto (
	idProducto varchar(15) not null,
	idVenta varchar(15) not null,
	cantidad int not null,
	precio decimal(10,2) not null,
	subtotal decimal(10,2) not null -- atributo derivado, se obtiene de la multiplicacion de cantidad con precio
	primary key (idProducto, idVenta),
	foreign key (idProducto) references producto(idProducto),
	foreign key (idVenta) references venta(idVenta)
)

create table almacen (
	idMaterial varchar(15) primary key,
	nombre varchar(30) unique not null,
	cantidad int not null
)

create table preparacion(
	idMaterial varchar(15) not null,
	idProducto varchar(15) not null,
	racion varchar(40) not null,
	primary key (idMaterial, idProducto),
	foreign key (idMaterial) references almacen(idMaterial),
	foreign key (idProducto) references producto(idProducto)
)

create table proveedor (
	idProveedor varchar(15) primary key,
	nombreEmpresa varchar(40) not null,
	nombreDelContacto varchar(40) not null,
	puestoDelContacto varchar(40) not null
)

create table telefonoProveedor (
	idProveedor varchar(15) not null,
	telefono varchar(10) not null,
	primary key (idProveedor, telefono),
	foreign key (idProveedor) references proveedor(idProveedor)
)

create table compraProveedor (
	idCompra varchar(15) primary key,
	idProveedor varchar(15) not null,
	fechaInicial datetime not null, -- puede ser la fecha en la que se realiza el pedido
	fechaFinal datetime, -- fecha en la que dentro del periodo de tiempo, ha sido entregado el pedido y se realizo el pago completo
	subtotal decimal(10,2),
	iva decimal(10,2),
	total decimal(10,2),
	metodoPago varchar(18) check (metodoPago in ('efectivo', 'tarjeta de debito', 'tarjeta de credito')),
	estadoDelPago varchar(10) check (estadoDelPago in ('pagado', 'pendiente', 'no pagado')) not null,
	estadoDeEntrega varchar(18) check (estadoDeEntrega in ('entregado', 'en proceso', 'cancelado')) not null,
	foreign key (idProveedor) references proveedor(idProveedor)
)

create table compraMaterial (
	idCompra varchar(15) not null,
	idMaterial varchar(15) not null,
	cantidad int not null,
	precio decimal(10,2) not null,
	subtotal decimal(10,2) not null
	foreign key (idCompra) references compraProveedor(idCompra),
	foreign key (idMaterial) references almacen(idMaterial)
)