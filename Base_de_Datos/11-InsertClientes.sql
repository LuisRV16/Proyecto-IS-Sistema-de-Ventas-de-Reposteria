
EXEC addClient 'Juan', 'Pérez', 'González', '1234567890', 'juanperez@example.com', 'Calle Principal', '10', '100', '12345', 'Centro', 'Ciudad', 'Estado';

declare @mesg varchar(200)
EXEC addClient @name = 'Luis i',
@lastName1 = 'López',
@lastName2 = 'Martínez', 
@rfc = 'GHFD456789JD3',
@phone = '9876543214',
@email = 'gabrielalopez@example.com', 
@street = 'Avenida Principal',
@interiorNumber = '215',
@outdoorNumber = '2200',
@postalCode = '43556',
@colony = 'Colonia Bella',
@city = 'Ciudad Grande',
@state = 'Estado U',
@msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'Sebastián', 'García', 'Fernández', 'SEGF092345JF4', '6549873215', 'sebastiangarcia@example.com', 'Calle Secundaria', '225', '2300', '45678', 'Colonia Moderna', 'Ciudad Pequeña', 'Estado V', @msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'Valentina', 'Hernández', 'Rodríguez', 'VAHR012039UI9', '3216549875', 'valentinahernandez@example.com', 'Avenida Norte', '230', '2400', '87654', 'Colonia Central', 'Ciudad Mediana', 'Estado W', @msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'David', 'Martínez', 'Gómez', 'DAMG983451JRT', '4567891235', 'davidmartinez@example.com', 'Calle Sur', '240', '2500', '23456', 'Colonia Histórica', 'Ciudad Pequeña', 'Estado X', @msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'Camila', 'Díaz', 'Pérez', '9871234565', 'camiladiaz@example.com', 'Avenida Oeste', '250', '2600', '76543', 'Colonia Antigua', 'Ciudad Grande', 'Estado Y', @msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'Martín', 'Sánchez', 'Fernández', 'RTYU345678IT5', '6543217895', 'martinsanchez@example.com', 'Calle Este', '260', '2700', '67890', 'Colonia Nueva', 'Ciudad Mediana', 'Estado Z', @msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'Sara', 'López', 'González', '3219876545', 'ERTY678890HYS', 'saralopez@example.com', 'Avenida Principal', '270', '2800', '34567', 'Colonia Moderna', 'Ciudad Pequeña', 'Estado AA', @msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'Samuel', 'García', 'Martínez', '4561237895', 'HUYJ673421KJ8', 'samuelgarcia@example.com', 'Calle Secundaria', '280', '2900', '89012', 'Colonia Bella', 'Ciudad Grande', 'Estado BB', @msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'Emma', 'Hernández', 'Rodríguez', '7894561235', 'UIUO907856OP8', 'emmahernandez@example.com', 'Avenida Norte', '290', '3000', '65432', 'Colonia Central', 'Ciudad Mediana', 'Estado CC', @msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'Mateo', 'Martínez', 'Gómez', '1237894565', 'QWER123456RT6', 'mateomartinez@example.com', 'Calle Sur', '300', '3100', '21098', 'Colonia Histórica', 'Ciudad Pequeña', 'Estado DD', @msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'Albus Percival Wulfric', 'Brian', 'Dumbledore', 'ALBD349856LK9', '1237894565', 'mateomartinez@example.com', 'Calle Sur', '300', '3100', '21098', 'Colonia Histórica', 'Ciudad Pequeña', 'Estado DD', @msg = @mesg output;
select @mesg

declare @mesg varchar(200)
EXEC addClient 'Generico', 'Gen', null, 'GGGG111111GGG' , '1230878945', 'empresa@example.com', 'Calle', '1234', '1234', '12345', 'Colonia', 'Ciudad', 'Estado', @msg = @mesg output;
select @mesg

select * from clientes