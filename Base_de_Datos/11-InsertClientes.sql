
declare @mesg varchar(200)
EXEC addClient @name = 'Luis i',
@lastName1 = 'L�pez',
@lastName2 = 'Mart�nez', 
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

EXEC addClient 'Sebasti�n', 'Garc�a', 'Fern�ndez', 'SEGF092345JF4', '6549873215', 'sebastiangarcia@example.com', 'Calle Secundaria', '225', '2300', '45678', 'Colonia Moderna', 'Ciudad Peque�a', 'Estado V', @msg = @mesg output;
select @mesg

EXEC addClient 'Valentina', 'Hern�ndez', 'Rodr�guez', 'VAHR012039UI9', '3216549875', 'valentinahernandez@example.com', 'Avenida Norte', '230', '2400', '87654', 'Colonia Central', 'Ciudad Mediana', 'Estado W', @msg = @mesg output;
select @mesg

EXEC addClient 'David', 'Mart�nez', 'G�mez', 'DAMG983451JRT', '4567891235', 'davidmartinez@example.com', 'Calle Sur', '240', '2500', '23456', 'Colonia Hist�rica', 'Ciudad Peque�a', 'Estado X', @msg = @mesg output;
select @mesg

EXEC addClient 'Camila', 'D�az', 'P�rez', 'PEDC678902FT6', '9871234565', 'camiladiaz@example.com', 'Avenida Oeste', '250', '2600', '76543', 'Colonia Antigua', 'Ciudad Grande', 'Estado Y', @msg = @mesg output;
select @mesg

EXEC addClient 'Mart�n', 'S�nchez', 'Fern�ndez', 'RTYU345678IT5', '6543217895', 'martinsanchez@example.com', 'Calle Este', '260', '2700', '67890', 'Colonia Nueva', 'Ciudad Mediana', 'Estado Z', @msg = @mesg output;
select @mesg

EXEC addClient 'Sara', 'L�pez', 'Gonz�lez', 'ERTY678890HYS', '3219876545', 'saralopez@example.com', 'Avenida Principal', '270', '2800', '34567', 'Colonia Moderna', 'Ciudad Peque�a', 'Estado AA', @msg = @mesg output;
select @mesg

EXEC addClient 'Samuel', 'Garc�a', 'Mart�nez', 'HUYJ673421KJ8', '4561237895', 'samuelgarcia@example.com', 'Calle Secundaria', '280', '2900', '89012', 'Colonia Bella', 'Ciudad Grande', 'Estado BB', @msg = @mesg output;
select @mesg

EXEC addClient 'Emma', 'Hern�ndez', 'Rodr�guez', 'UIUO907856OP8', '7894561235', 'emmahernandez@example.com', 'Avenida Norte', '290', '3000', '65432', 'Colonia Central', 'Ciudad Mediana', 'Estado CC', @msg = @mesg output;
select @mesg

EXEC addClient 'Mateo', 'Mart�nez', 'G�mez', 'QWER123456RT6','1237894565', 'mateomartinez@example.com', 'Calle Sur', '300', '3100', '21098', 'Colonia Hist�rica', 'Ciudad Peque�a', 'Estado DD', @msg = @mesg output;
select @mesg

EXEC addClient 'Albus Percival Wulfric', 'Brian', 'Dumbledore', 'ALBD349856LK9', '1237894565', 'mateomartinez@example.com', 'Calle Sur', '300', '3100', '21098', 'Colonia Hist�rica', 'Ciudad Peque�a', 'Estado DD', @msg = @mesg output;
select @mesg

EXEC addClient 'Generico', 'Gen', null, 'GGGG111111GGG' , '1230878945', 'empresa@example.com', 'Calle', '1234', '1234', '12345', 'Colonia', 'Ciudad', 'Estado', @msg = @mesg output;
select @mesg

select * from cliente