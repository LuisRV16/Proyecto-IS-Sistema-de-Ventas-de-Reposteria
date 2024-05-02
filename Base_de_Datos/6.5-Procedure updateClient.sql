create procedure updateClient (
    @name varchar(40),
    @lastName1 varchar(20),
    @lastName2 varchar(20),
    @phone varchar(15),
    @email varchar(255),
    @street varchar(30),
    @interiorNumber varchar(5),
    @outdoorNumber varchar(6),
    @postalCode varchar(5),
    @colony varchar(30),
    @city varchar(30),
    @state varchar(30),
	@msg varchar(200) output
)
as
begin

	declare @id varchar(15) = (select idCliente from clientes where nombre = @name and apellido1 = @lastName1 and apellido2 = @lastName2)
	declare @band bit = 1;

	if @phone = ''
		begin
			set @msg = 'Ingrese el número de teléfono en el campo de texto.'
			set @band = 0
		end
	else if len(@phone) < 10 or @phone like '%[^0-9]%'
		begin
			set @msg = 'Ingrese el número de teléfono correcto.\nEl numero de telefono se compone de 10 números.\nNo se permiten carácteres distintos a dígitos.'
			set @band = 0
		end
	else if @street = '' or replace(@street, ' ', '') = ''
		begin
			set @msg = 'Ingrese la calle perteneciente a la dirección del cliente.'
			set @band = 0
		end
	else if len(@street) < 1 or @street like '%[^A-Za-z0-9 ]%'
		begin
			set @msg = 'Ingrese una calle válida.'
			set @band = 0
		end
	else if @interiorNumber = ''
		begin
			set @interiorNumber = 'S/N'
			set @band = 0
		end
	else if PATINDEX('%[^A-Z0-9]%', @interiorNumber) > 0
		begin
			set @msg = 'Ingrese un numero interior correcto.\nEl número interior puede estar compuesto por números y letras'
			set @band = 0
		end
	else if @outdoorNumber = ''
		begin
			set @msg = 'Ingrese el numero exterior perteneciente a la dirección del cliente.'
			set @band = 0
		end
	else if PATINDEX('%[^A-Z0-9]%', @outdoorNumber) > 0
		begin
			set @msg = 'Ingrese un numero interior correcto.\nEl número exterior puede estar compuesto por números y letras'
			set @band = 0
		end
	else if len(@postalCode) < 5 or @postalCode like '%[^0-9]%'
		begin
			set @msg = 'Ingrese el Código postal correcto.\nEl Código postal se compone de 5 números.\nNo se permiten carácteres distintos a dígitos.'
			set @band = 0
		end
	else if @colony = '' or replace(@colony, ' ', '') = ''
		begin
			set @msg = 'Ingrese la colonia perteneciente a la dirección del cliente.'
			set @band = 0
		end
	else if @colony like '%[^A-Z0-9 ]%'
		begin
			set @msg = 'Ingrese una colonia válida.'
			set @band = 0
		end
	else if @city = ''
		begin
			set @msg = 'Ingrese la ciudad perteneciente a la dirección del cliente.'
			set @band = 0
		end
	else if @state = ''
		begin
			set @msg = 'Ingrese el estado perteneciente a la dirección del cliente.'
			set @band = 0
		end
	else if @email = ''
		begin
			set @msg = 'Ingrese el correo electrónico en el campo de texto.'
			set @band = 0
		end
	else if charindex(' ', @email) > 0
		begin
			set @msg = 'Ingrese el correo electrónico correctamente.\nEl correo electrónico no debe contener espacios en blanco.'
			set @band = 0
		end
	else if left(@email, 1) like '.'
		begin
			set @msg = 'Ingrese el correo electrónico correctamente.\nEl correo electrónico no debe inicar con .'
			set @band = 0
		end
	else if left(@email, 1) like '@'
		begin
			set @msg = 'Ingrese el correo electrónico correctamente.\nEl correo electrónico no debe inicar con @'
			set @band = 0
		end
	else if right(@email, 1) like '.'
		begin
			set @msg = 'Ingrese el correo electrónico correctamente.\nEl correo electrónico no debe terminar con .'
			set @band = 0
		end
	else if right(@email, 1) like '@'
		begin
			set @msg = 'Ingrese el correo electrónico correctamente.\nEl correo electrónico no debe terminar con @'
			set @band = 0
		end
	else
		begin
			declare @contador int = 0
			declare @posicion int = 1
			declare @caracter varchar(1)

			while @posicion <= len(@email)
				begin
					set @caracter = substring(@email, @posicion, 1)

					if @caracter = '@'
					begin
						set @contador = @contador + 1
					end

					set @posicion = @posicion + 1

				end

			if @contador > 1
				begin
					set @msg = 'Ingrese el correo electrónico correctamente.\nEl correo electrónico no debe contener una @ solamente.'
					set @band = 0
				end
			else if @contador = 0
				begin
					set @msg = 'Ingrese el correo electrónico correctamente.\nEl correo electrónico debe contener una @.'
					set @band = 0
				end

			if @band = 1
				begin
					if  substring(@email, charindex('@', @email) - 1, 1) = '.'
						begin
							set @msg = 'Ingrese el correo electrónico correctamente.\nEl correo electrónico no debe tener un . antes del @'
							set @band = 0
						end
					else if substring(@email, charindex('@', @email) + 1, 1) = '.'
						begin
							set @msg = 'Ingrese el correo electrónico correctamente.\nEl correo electrónico no debe tener un . después del @'
							set @band = 0
						end
					else if charindex('.', substring(@email, charindex('@', @email) + 1, len(@email))) = 0
						begin
							set @msg = 'Ingrese el correo electrónico correctamente.\nEl correo electrónico debe contener la parte del dominio.'
							set @band = 0
						end
				end

			if @band = 1
				begin
					set @posicion = 1

					while @posicion <= len(@email)
						begin
							set @caracter = substring(@email, @posicion, 1)

							if @caracter in ('[', ']', '!', '#', '$', '%', '&', '\', '*', '+', '=', '?', '^', '|', '{', '}', char(34), '¿', '¡', '°', ';', ':', '/', '<', '>', '(', ')')
								begin
									set @msg = 'Carácter inválido en el Email.\nIngrese el Email correctamente.'
									set @band = 0
									break
								end

							set @posicion = @posicion + 1
						end
				end
		end

	if @band = 1
	begin
		update clientes
		set
			telefono = @phone,
			correo = @email,
			calle = @street,
			numeroInterior = @interiorNumber,
			numeroExterior = @outdoorNumber,
			codigoPostal = @postalCode,
			colonia = @colony,
			ciudad = @city,
			estado = @state
		where
			idCliente = @id

		set @msg = 'Datos del cliente actualizados correctamente.'
	end
end